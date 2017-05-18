package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.tile.ITileClientUpdates;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.network.PacketDynamic;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.RedstoneUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

//ITileClientUpdates for changing in facing;  IPacketReceiver for initial transfer of NBT Data (airToRestore)
public class TileEntityArclamp extends TileEntity implements ITickable, ITileClientUpdates, IPacketReceiver
{
    private static final int LIGHTRANGE = 14;
    private int ticks = 0;
    private int sideRear = 0;
    public int facing = 0;
    private HashSet<BlockVec3> airToRestore = new HashSet();
    private static intBucket[] buckets;
    private static AtomicBoolean usingBuckets = new AtomicBoolean();
    /**
     * Every element is a packed bit value: 0000000000LLLLzzzzzzyyyyyyxxxxxx. The
     * 4-bit L is a light level used when darkening blocks. 6-bit numbers x, y and z represent the block's offset from
     * the original block, plus 32 (i.e. value of 31 would mean a -1 offset
     */
    private static int[] lightUpdateBlockList;
    private boolean isActive = false;
    private AxisAlignedBB thisAABB;
    private AxisAlignedBB renderAABB;
    private Vec3d thisPos;
    private int facingSide = 0;

    static
    {
        buckets = new intBucket[256];
        lightUpdateBlockList = new int[32768];
        checkedInit();
    }
    
    @Override
    public void update()
    {
        boolean initialLight = false;
//        if (this.updateClientFlag)
//        {
//            this.updateAllInDimension();
//            this.updateClientFlag = false;
//        }

        if (RedstoneUtil.isBlockReceivingRedstone(this.world, this.getPos()))
        {
            if (this.isActive)
            {
                this.isActive = false;
                this.revertAir();
                this.markDirty();
            }
        }
        else if (!this.isActive)
        {
            this.isActive = true;
            initialLight = true;
        }

        if (this.isActive)
        {
            //Test for first tick after placement
            if (this.thisAABB == null)
            {
                initialLight = true;
                int side = this.getBlockMetadata();
                switch (side)
                {
                case 0:
                    this.sideRear = side; //Down
                    this.facingSide = this.facing + 2;
                    break;
                case 1:
                    this.sideRear = side; //Up
                    this.facingSide = this.facing + 2;
                    break;
                case 2:
                    this.sideRear = side; //North
                    this.facingSide = this.facing;
                    if (this.facing > 1)
                    {
                        this.facingSide = 7 - this.facing;
                    }
                    break;
                case 3:
                    this.sideRear = side; //South
                    this.facingSide = this.facing;
                    if (this.facing > 1)
                    {
                        this.facingSide += 2;
                    }
                    break;
                case 4:
                    this.sideRear = side; //West
                    this.facingSide = this.facing;
                    break;
                case 5:
                    this.sideRear = side; //East
                    this.facingSide = this.facing;
                    if (this.facing > 1)
                    {
                        this.facingSide = 5 - this.facing;
                    }
                    break;
                default:
                }
                
                this.thisAABB = getAABBforSideAndFacing();
            }

            if (initialLight || this.ticks % 100 == 0)
            {
                this.lightArea();
            }

            if (!this.world.isRemote && this.world.rand.nextInt(10) == 0)
            {
                List<Entity> moblist = this.world.getEntitiesInAABBexcluding(null, this.thisAABB, IMob.MOB_SELECTOR);

                if (!moblist.isEmpty())
                {
                    Vec3d thisVec3 = new Vec3d(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
                    for (Entity entry : moblist)
                    {
                        if (!(entry instanceof EntityCreature))
                        {
                            continue;
                        }
                        EntityCreature mob = (EntityCreature) entry;
                        //Check whether the mob can actually *see* the arclamp tile
                        //if (this.world.func_147447_a(thisPos, Vec3d.createVectorHelper(e.posX, e.posY, e.posZ), true, true, false) != null) continue;

                        PathNavigate nav = mob.getNavigator();
                        if (nav == null)
                        {
                            continue;
                        }
                        
                        Vec3d vecNewTarget = RandomPositionGenerator.findRandomTargetBlockAwayFrom(mob, 28, 11, this.thisPos);
                        if (vecNewTarget == null)
                        {
                            continue;
                        }

                        double distanceNew = vecNewTarget.distanceTo(thisVec3);
                        double distanceCurrent = thisVec3.squareDistanceTo(new Vec3d(mob.posX, mob.posY, mob.posZ));
                        if (distanceNew > distanceCurrent)
                        {
                            Vec3d vecOldTarget = null;
                            if (nav.getPath() != null && !nav.getPath().isFinished())
                            {
                                vecOldTarget = nav.getPath().getPosition(mob);
                            }
                            if (vecOldTarget == null || distanceCurrent > vecOldTarget.squareDistanceTo(thisVec3))
                            {
                                nav.tryMoveToXYZ(vecNewTarget.xCoord, vecNewTarget.yCoord, vecNewTarget.zCoord, 1.3D);
                            }
                        }
                    }
                }
            }
        }

        this.ticks++;
    }

    private AxisAlignedBB getAABBforSideAndFacing()
    {
        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();
        int rangeForSide[] = new int[6];
        for (int i = 0; i < 6; i++)
        {
            rangeForSide[i] = (i == this.sideRear) ? 2 : (i == (this.facingSide ^ 1)) ? 4 : 25;
        }
        return new AxisAlignedBB(x - rangeForSide[4], y - rangeForSide[0], z - rangeForSide[2], x + rangeForSide[5], y + rangeForSide[1], z + rangeForSide[3]);
    }

    @Override
    public void onLoad()
    {
        this.thisPos = new Vec3d(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D);
        this.ticks = 0;
        this.thisAABB = null;
        if (this.world.isRemote)
        {
            this.clientOnLoad();
            GalacticraftCore.packetPipeline.sendToServer(new PacketDynamic(this));
        }
        else
        {
            this.isActive = true;
        }
    }

    @Override
    public void invalidate()
    {
        this.revertAir();
        this.isActive = false;
        super.invalidate();
    }

    public void lightArea()
    {
        if (usingBuckets.getAndSet(true))
        {
            return;
        }
        Block air = Blocks.AIR;
        Block breatheableAirID = GCBlocks.breatheableAir;
        IBlockState brightAir = GCBlocks.brightAir.getDefaultState();
        IBlockState brightBreatheableAir = GCBlocks.brightBreatheableAir.getDefaultState();
        boolean dirty = false;
        checkedClear();
        HashSet airToRevert = new HashSet();
        airToRevert.addAll(airToRestore);
        LinkedList airNew = new LinkedList();
        LinkedList<BlockVec3> currentLayer = new LinkedList();
        LinkedList<BlockVec3> nextLayer = new LinkedList();
        BlockVec3 thisvec = new BlockVec3(this);
        currentLayer.add(thisvec);
        World world = this.world;
        int sideskip1 = this.sideRear;
        int sideskip2 = this.facingSide ^ 1;
        int side, bits;
        for (int i = 0; i < 6; i++)
        {
            if (i != sideskip1 && i != sideskip2 && i != (sideskip1 ^ 1) && i != (sideskip2 ^ 1))
            {
                BlockVec3 onEitherSide = thisvec.newVecSide(i);
                IBlockState state = onEitherSide.getBlockStateSafe_noChunkLoad(world);
                if (state.getBlock().getLightOpacity(state) < 15)
                {
                    currentLayer.add(onEitherSide);
                }
            }
        }
        BlockVec3 inFront = new BlockVec3(this);
        for (int i = 0; i < 4; i++)
        {
            inFront = inFront.newVecSide(this.facingSide);
            IBlockState state = inFront.getBlockStateSafe_noChunkLoad(world);
            if (state.getBlock().getLightOpacity(state) == 15)
            {
                break;
            }
            inFront = inFront.newVecSide(sideskip1 ^ 1);
            state = inFront.getBlockStateSafe_noChunkLoad(world);
            if (state.getBlock().getLightOpacity(state) < 15)
            {
                currentLayer.add(inFront);
            }
            else
            {
                break;
            }
        }

        inFront = new BlockVec3(this).newVecSide(this.facingSide);

        for (int count = 0; count < LIGHTRANGE; count++)
        {
            for (BlockVec3 vec : currentLayer)
            {
                //Shape the arc lamp lighted area to more of a cone in front of it
                if (count > 1)
                {
                    int offset = 0;
                    switch (this.facingSide)
                    {
                    case 0:
                        offset = inFront.y - vec.y;
                        break;
                    case 1:
                        offset = vec.y - inFront.y;
                        break;
                    case 2:
                        offset = inFront.z - vec.z;
                        break;
                    case 3:
                        offset = vec.z - inFront.z;
                        break;
                    case 4:
                        offset = inFront.x - vec.x;
                        break;
                    case 5:
                        offset = vec.x - inFront.x;
                        break;
                    }
                    int offset2 = 0;
                    switch (this.sideRear ^ 1)
                    {
                    case 0:
                        offset2 = inFront.y - vec.y;
                        break;
                    case 1:
                        offset2 = vec.y - inFront.y;
                        break;
                    case 2:
                        offset2 = inFront.z - vec.z;
                        break;
                    case 3:
                        offset2 = vec.z - inFront.z;
                        break;
                    case 4:
                        offset2 = inFront.x - vec.x;
                        break;
                    case 5:
                        offset2 = vec.x - inFront.x;
                        break;
                    }
                    if (offset2 - 2 > offset) offset = offset2 - 2;
                    if (Math.abs(vec.x - inFront.x) > offset + 2) continue;
                    if (Math.abs(vec.y - inFront.y) > offset + 2) continue; 
                    if (Math.abs(vec.z - inFront.z) > offset + 2) continue; 
                }
                
                //Now process each layer outwards from the source, finding new blocks to light (similar to ThreadFindSeal)
                //This is high performance code using our own custom HashSet (that's intBucket)
                side = 0;
                bits = vec.sideDoneBits;
                boolean doShine = false;
                do
                {
                    //Skip the side which this was entered from
                    //and never go 'backwards'
                    if ((bits & (1 << side)) == 0)
                    {
                        BlockVec3 sideVec = vec.newVecSide(side);
                        boolean toAdd = false;
                        if (!checkedContains(vec, side))
                        {
                            checkedAdd(sideVec);
                            toAdd = true;
                        }

                        IBlockState bs = sideVec.getBlockStateSafe_noChunkLoad(world);
                        Block b = bs.getBlock();
                        if (b instanceof BlockAir)
                        {
                            if (toAdd && side != sideskip1 && side != sideskip2)
                            {
                                nextLayer.add(sideVec);
                            }
                        }
                        else
                        {
                            doShine = true;
                            //Glass blocks go through to the next layer as well
                            if (side != sideskip1 && side != sideskip2)
                            {
                                if (toAdd && b != null && b.getLightOpacity(bs, world, sideVec.toBlockPos()) == 0)
                                {
                                    nextLayer.add(sideVec);
                                }
                            }
                        }
                    }
                    side++;
                }
                while (side < 6);

                if (doShine)
                {
                    airNew.add(vec);
                    Block id = vec.getBlockStateSafe_noChunkLoad(world).getBlock();
                    if (Blocks.AIR == id)
                    {
                        this.brightenAir(world, vec, brightAir);
                        dirty = true;
                    }
                    else if (id == breatheableAirID)
                    {
                        this.brightenAir(world, vec, brightBreatheableAir);
                        dirty = true;
                    }
                }
            }
            if (nextLayer.size() == 0)
            {
                break;
            }
            currentLayer = nextLayer;
            nextLayer = new LinkedList<BlockVec3>();
        }

        if (dirty) this.markDirty();
        
        //Look for any holdover bright blocks which are no longer lit (e.g. because the Arc Lamp became blocked in a tunnel)
        airToRevert.removeAll(airNew);
        for (Object obj : airToRevert)
        {
            BlockVec3 vec = (BlockVec3) obj;
            this.setDarkerAir(vec);
            this.airToRestore.remove(vec);
        }
        usingBuckets.set(false);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.facing = nbt.getInteger("Facing");

        this.airToRestore.clear();
        NBTTagList airBlocks = nbt.getTagList("AirBlocks", 10);
        if (airBlocks.tagCount() > 0)
        {
            for (int j = airBlocks.tagCount() - 1; j >= 0; j--)
            {
                NBTTagCompound tag1 = airBlocks.getCompoundTagAt(j);
                if (tag1 != null)
                {
                    this.airToRestore.add(BlockVec3.readFromNBT(tag1));
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("Facing", this.facing);

        NBTTagList airBlocks = new NBTTagList();

        for (BlockVec3 vec : this.airToRestore)
        {
            NBTTagCompound tag = new NBTTagCompound();
            vec.writeToNBT(tag);
            airBlocks.appendTag(tag);
        }
        nbt.setTag("AirBlocks", airBlocks);
        return nbt;
    }

    public void facingChanged()
    {
        this.facing -= 2;
        if (this.facing < 0)
        {
            this.facing = 1 - this.facing;
            //facing sequence: 0 - 3 - 1 - 2
        }

        this.updateAllInDimension();
        this.thisAABB = null;
        this.revertAir();
        this.markDirty();
        this.ticks = 91;
    }

    private void brightenAir(World world, BlockVec3 vec, IBlockState brighterAir)
    {
        //No block update on server - not necessary for changing air to air (also must not trigger a sealer edge check!)
        world.setBlockState(vec.toBlockPos(), brighterAir, (this.world.isRemote) ? 2 : 0);
        this.airToRestore.add(vec);
    }
    
    private void setDarkerAir(BlockVec3 vec)
    {
        //No block update on server - not necessary for changing air to air (also must not trigger a sealer edge check!)
        Block b = vec.getBlockState(this.world).getBlock();
        BlockPos pos = vec.toBlockPos();
        if (b == GCBlocks.brightAir)
        {
            this.world.setBlockState(pos, Blocks.AIR.getDefaultState(), (this.world.isRemote) ? 2 : 0);
            this.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
        else if (b == GCBlocks.brightBreatheableAir)
        {
            this.world.setBlockState(pos, GCBlocks.breatheableAir.getDefaultState(), (this.world.isRemote) ? 2 : 0);
            this.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
    }

    private void revertAir()
    {
        for (BlockVec3 vec : this.airToRestore)
        {
            this.setDarkerAir(vec);
        }
        this.airToRestore.clear();
        this.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }

    public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos)
    {
        if (!this.world.isAreaLoaded(pos, 17, false))
        {
            return false;
        }
        else
        {
            int i = 0;
            int index = 0;
            int blockLight = this.world.getLightFor(lightType, pos);
            int rawLight = this.getRawLight(pos, lightType);
            int x = pos.getX() - 32;
            int y = pos.getY() - 32;
            int z = pos.getZ() - 32;
            int opacity;
            int range;
            int savedLight;

            if (rawLight > blockLight)  //Light switched on
            {
                lightUpdateBlockList[index++] = 133152; //32, 32, 32 = the 0 position
            }
            else if (rawLight < blockLight)  //Light switched off ?
            {
                lightUpdateBlockList[index++] = 133152 | blockLight << 18;

                while (i < index)   //This becomes CRAZY LARGE
                {
                    int value = lightUpdateBlockList[i++];
                    int xx = (value & 63) - x;
                    int yy = (value >> 6 & 63) - y;
                    int zz = (value >> 12 & 63) - z;
                    int arraylight = value >> 18 & 15;
                    if (arraylight > 0)
                    {
                        BlockPos blockpos = new BlockPos(xx, yy, zz);
                        if (this.world.getLightFor(lightType, blockpos) == arraylight)   //Only gonna happen once (definitely will happen the first iteration)
                        {
                            this.world.setLightFor(lightType, blockpos, 0);  //= flagdone
                            //TODO prevent this from notifyLightSet on server side

                            range = MathHelper.abs(xx - x - 32) + MathHelper.abs(yy - y - 32) + MathHelper.abs(zz - z - 32);
                            if (range < 17)
                            {
                                for (BlockPos vec : GCCoreUtil.getPositionsAdjoining(xx, yy, zz))
                                {
                                    savedLight = this.world.getLightFor(lightType, vec);
                                    if (savedLight == 0) continue;  //eliminate backtracking
                                    IBlockState bs = this.world.getBlockState(vec); 
                                    opacity = bs.getBlock().getLightOpacity(bs, this.world, vec);
                                    if (opacity <= 0) opacity = 1;
                                    //Tack positions onto the list as long as it looks like lit from here. i.e. saved light is adjacent light - opacity!
                                    //There will be some errors due to coincidence / equality of light levels from 2 sources
                                    if (savedLight == arraylight - opacity && index < lightUpdateBlockList.length)
                                    {
                                        lightUpdateBlockList[index++] = vec.getX() - x | vec.getY() - y << 6 | vec.getZ() - z << 12 | savedLight << 18;
                                    }
                                }
                            }
                        }
                    }
                }

                i = 0;
            }

            while (i < index)
            {
                int value = lightUpdateBlockList[i++];
                int xx = (value & 63) + x;
                int yy = (value >> 6 & 63) + y;
                int zz = (value >> 12 & 63) + z;
                BlockPos blockpos1 = new BlockPos(xx, yy, zz);
                savedLight = this.world.getLightFor(lightType, blockpos1);   //Gonna be 0 for most of the list
                int theRawLight = this.getRawLight(blockpos1, lightType);

                if (theRawLight != savedLight)
                {
                    this.world.setLightFor(lightType, blockpos1, theRawLight);   //<-------the light setting
                    //TODO prevent this from notifyLightSet on server side

                    if (theRawLight > savedLight)
                    {
                        range = MathHelper.abs(xx - x - 32) + MathHelper.abs(yy - y - 32) + MathHelper.abs(zz - z - 32);
                        if (range < 17 && index < lightUpdateBlockList.length - 6)
                        {
                            for (BlockPos vec : GCCoreUtil.getPositionsAdjoining(xx, yy, zz))
                            {
                                if (this.world.getLightFor(lightType, vec) < theRawLight)
                                {
                                    //Tack even more positions on to the end of the list - this propagates
                                    lightUpdateBlockList[index++] = vec.getX() - x + ((vec.getZ() - z << 6) + vec.getY() - y << 6);
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

/**
 * From vanilla.  This is buggy, gets confused if two 0 opacity or low opacity blocks adjacent (e.g. redstone wire, stairs)
 * if those blocks are receiving similar light levels from another source
 */
    private int getRawLight(BlockPos pos, EnumSkyBlock lightType)
    {
        {
            IBlockState bs = this.world.getBlockState(pos); 
            Block block = bs.getBlock();
            int blockLight = block.getLightValue(bs, this.world, pos);
            int light = lightType == EnumSkyBlock.SKY ? 0 : blockLight;
            int opacity = block.getLightOpacity(bs, this.world, pos);

            if (opacity >= 15 && blockLight > 0)
            {
                opacity = 1;
            }

            if (opacity < 1)
            {
                opacity = 1;
            }

            if (opacity >= 15)
            {
                return 0;
            }
            else if (light >= 14)
            {
                return light;
            }
            else
            {
                for (BlockPos blockpos : GCCoreUtil.getPositionsAdjoining(pos))
                {
                    int otherlight = this.world.getLightFor(lightType, blockpos) - opacity;  //Circular reference?
                    if (otherlight > light)
                    {
                        light = otherlight;
                        if (light >= 14)
                        {
                            return light;
                        }
                    }
                }

                return light;
            }
        }
    }
    
    public boolean getEnabled()
    {
        return !RedstoneUtil.isBlockReceivingRedstone(this.world, this.getPos());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }


    private void checkedAdd(BlockVec3 vec)
    {
        int dx = this.pos.getX() - vec.x;
        int dz = this.pos.getZ() - vec.z;
        if (dx < -8191 || dx > 8192) return;
        if (dz < -8191 || dz > 8192) return;
        intBucket bucket = buckets[((dx & 15) << 4) + (dz & 15)];
        bucket.add(vec.y + ((dx & 0x3FF0) + ((dz & 0x3FF0) << 10) << 4));
    }

    /**
     * Currently unused - the sided implementation is used instead
     */
    private boolean checkedContains(BlockVec3 vec)
    {
        int dx = this.pos.getX() - vec.x;
        int dz = this.pos.getZ() - vec.z;
        if (dx < -8191 || dx > 8192) return true;
        if (dz < -8191 || dz > 8192) return true;
        intBucket bucket = buckets[((dx & 15) << 4) + (dz & 15)];
        return bucket.contains(vec.y + ((dx & 0x3FF0) + ((dz & 0x3FF0) << 10) << 4));
    }

    private boolean checkedContains(BlockVec3 vec, int side)
    {
        int y = vec.y;
        int dx = this.pos.getX() - vec.x;
        int dz = this.pos.getZ() - vec.z;
        switch (side)
        {
        case 0:
            y--;
            if (y < 0) return true;
            break;
        case 1:
            y++;
            if (y > 255) return true;
            break;
        case 2:
            dz++;
            break;
        case 3:
            dz--;
            break;
        case 4:
            dx++;
            break;
        case 5:
            dx--;
        }
        if (dx < -8191 || dx > 8192) return true;
        if (dz < -8191 || dz > 8192) return true;
        intBucket bucket = buckets[((dx & 15) << 4) + (dz & 15)];
        return bucket.contains(y + ((dx & 0x3FF0) + ((dz & 0x3FF0) << 10) << 4));
    }

    private static void checkedInit()
    {
        for (int i = 0; i < 256; i++)
        {
            buckets[i] = new intBucket();
        }
    }

    private static void checkedClear()
    {
        for (int i = 0; i < 256; i++)
        {
            buckets[i].clear();
        }
    }

    public static class intBucket
    {
        private int maxSize = 24;  //default size
        private int size = 0;
        private int[] table = new int[maxSize];
        
        public void add(int i)
        {
            if (this.contains(i))
                return;
            
            if (size >= maxSize)
            {
                int[] newTable = new int[maxSize + maxSize];
                System.arraycopy(table, 0, newTable, 0, maxSize);
                table = newTable;
                maxSize += maxSize;
            }
            table[size] = i;
            size++;
        }

        public boolean contains(int test)
        {
            for (int i = size - 1; i >= 0; i--)
            {
                if (table[i] == test)
                    return true;
            }
            return false;
        }
        
        public void clear()
        {
            size = 0;
        }
        
        public int size()
        {
            return size;
        }
          
        public int[] contents()
        {
            return table;
        }
    }

    @Override
    public void buildDataPacket(int[] data)
    {
        data[0] = this.facing;
    }

    @Override
    public void updateClient(List<Object> data)
    {
        this.facing = (Integer) data.get(1);
        this.revertAir();
        this.thisAABB = null;
        this.ticks = 86;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        if (this.renderAABB == null)
        {
            this.renderAABB = new AxisAlignedBB(pos, pos.add(1, 1, 1));
        }
        return this.renderAABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return Constants.RENDERDISTANCE_LONG;
    }

    @Override
    public void getNetworkedData(ArrayList<Object> sendData)
    {
        for (BlockVec3 vec : this.airToRestore)
        {
            sendData.add(vec);
        }
    }

    @Override
    public void decodePacketdata(ByteBuf buffer)
    {
        while (buffer.readableBytes() >= 12)
        {
            int x = buffer.readInt();
            int y = buffer.readInt();
            int z = buffer.readInt();
            this.airToRestore.add(new BlockVec3(x, y, z));
        }
    }
}
