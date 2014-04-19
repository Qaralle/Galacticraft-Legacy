package micdoodle8.mods.galacticraft.core.items;

import java.util.ArrayList;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.GCCoreBlocks;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.moon.items.GCMoonItem;
import micdoodle8.mods.galacticraft.moon.items.GCMoonItemCheese;
import micdoodle8.mods.galacticraft.moon.items.GCMoonItemRawIron;
import micdoodle8.mods.galacticraft.moon.items.GCMoonItemReed;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * GCCoreItems.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GCCoreItems
{
	public static Item oxTankLight;
	public static Item oxTankMedium;
	public static Item oxTankHeavy;
	public static Item oxMask;
	public static Item rocketTier1;
	public static Item sensorGlasses;
	public static Item sensorLens;
	public static Item steelPickaxe;
	public static Item steelAxe;
	public static Item steelHoe;
	public static Item steelSpade;
	public static Item steelSword;
	public static Item steelHelmet;
	public static Item steelChestplate;
	public static Item steelLeggings;
	public static Item steelBoots;
	public static Item canister;
	public static Item oxygenVent;
	public static Item oxygenFan;
	public static Item oxygenConcentrator;
	public static Item rocketEngine;
	public static Item heavyPlatingTier1;
	public static Item partNoseCone;
	public static Item partFins;
	public static Item buggy;
	public static Item flag;
	public static Item oxygenGear;
	public static Item parachute;
	public static Item canvas;
	public static Item flagPole;
	public static Item oilCanister;
	public static Item fuelCanister;
	public static Item oilExtractor;
	public static Item schematic;
	public static Item key;
	public static Item partBuggy;
	public static Item knowledgeBook;
	public static Item basicItem;
	public static Item battery;
	public static Item infiniteBatery;
	public static Item meteorChunk;
	public static Item wrench;
	public static Item cheeseCurd;
	public static Item meteoricIronRaw;
	public static Item meteoricIronIngot;
	public static Item cheeseBlock;

	public static ArmorMaterial ARMOR_SENSOR_GLASSES = EnumHelper.addArmorMaterial("SENSORGLASSES", 200, new int[] { 0, 0, 0, 0 }, 0);
	public static ArmorMaterial ARMOR_STEEL = EnumHelper.addArmorMaterial("steel", 30, new int[] { 3, 8, 6, 3 }, 12);
	public static ToolMaterial TOOL_STEEL = EnumHelper.addToolMaterial("steel", 3, 768, 5.0F, 2, 8);

	public static ArrayList<Item> hiddenItems = new ArrayList<Item>();

	public static void initItems()
	{
		GCCoreItems.oxTankLight = new GCCoreItemOxygenTank(1, "oxygenTankLightFull");
		GCCoreItems.oxTankMedium = new GCCoreItemOxygenTank(2, "oxygenTankMedFull");
		GCCoreItems.oxTankHeavy = new GCCoreItemOxygenTank(3, "oxygenTankHeavyFull");
		GCCoreItems.oxMask = new GCCoreItemOxygenMask("oxygenMask");
		GCCoreItems.rocketTier1 = new GCCoreItemSpaceship("spaceship");
		GCCoreItems.sensorGlasses = new GCCoreItemSensorGlasses("sensorGlasses");
		GCCoreItems.steelPickaxe = new GCCoreItemPickaxe("steel_pickaxe");
		GCCoreItems.steelAxe = new GCCoreItemAxe("steel_axe");
		GCCoreItems.steelHoe = new GCCoreItemHoe("steel_hoe");
		GCCoreItems.steelSpade = new GCCoreItemSpade("steel_shovel");
		GCCoreItems.steelSword = new GCCoreItemSword("steel_sword");
		GCCoreItems.steelHelmet = new GCCoreItemArmor(0, "helmet");
		GCCoreItems.steelChestplate = new GCCoreItemArmor(1, "chestplate");
		GCCoreItems.steelLeggings = new GCCoreItemArmor(2, "leggings");
		GCCoreItems.steelBoots = new GCCoreItemArmor(3, "boots");
		GCCoreItems.canister = new GCCoreItemCanister("canister");
		GCCoreItems.oxygenVent = new GCCoreItem("airVent");
		GCCoreItems.oxygenFan = new GCCoreItem("airFan");
		GCCoreItems.oxygenConcentrator = new GCCoreItem("oxygenConcentrator");
		GCCoreItems.heavyPlatingTier1 = new GCCoreItem("heavyPlating");
		GCCoreItems.rocketEngine = new GCCoreItemRocketEngine("engine");
		GCCoreItems.partFins = new GCCoreItem("rocketFins");
		GCCoreItems.partNoseCone = new GCCoreItem("noseCone");
		GCCoreItems.sensorLens = new GCCoreItem("sensorLens");
		GCCoreItems.buggy = new GCCoreItemBuggy("buggy");
		GCCoreItems.flag = new GCCoreItemFlag("flag");
		GCCoreItems.oxygenGear = new GCCoreItemOxygenGear("oxygenGear");
		GCCoreItems.parachute = new GCCoreItemParachute("parachute");
		GCCoreItems.canvas = new GCCoreItem("canvas");
		GCCoreItems.oilCanister = new GCCoreItemOilCanister("oilCanisterPartial");
		GCCoreItems.fuelCanister = new GCCoreItemFuelCanister("fuelCanisterPartial");
		GCCoreItems.flagPole = new GCCoreItem("steelPole");
		GCCoreItems.oilExtractor = new GCCoreItemOilExtractor("oilExtractor");
		GCCoreItems.schematic = new GCCoreItemSchematic("schematic");
		GCCoreItems.key = new GCCoreItemKey("key");
		GCCoreItems.partBuggy = new GCCoreItemBuggyMaterial("buggymat");
		GCCoreItems.knowledgeBook = new GCCoreItemKnowledgeBook("knowledgeBook");
		GCCoreItems.basicItem = new GCCoreItemBasic("basicItem");
		GCCoreItems.battery = new GCCoreItemBattery("battery");
		GCCoreItems.infiniteBatery = new GCCoreItemInfiniteBattery("infiniteBattery");
		GCCoreItems.meteorChunk = new GCCoreItemMeteorChunk("meteorChunk");
		GCCoreItems.wrench = new GCCoreItemWrench("standardWrench");
		GCCoreItems.cheeseCurd = new GCMoonItemCheese(1, 0.1F, false);
		GCCoreItems.cheeseBlock = new GCMoonItemReed(GCCoreBlocks.cheeseBlock, "cheeseBlock");
		GCCoreItems.meteoricIronRaw = new GCMoonItemRawIron("meteoricIronRaw");
		GCCoreItems.meteoricIronIngot = new GCMoonItem("meteoricIronIngot");

		GCCoreItems.hiddenItems.add(GCCoreItems.knowledgeBook);

		GCCoreItems.registerHarvestLevels();

		GCCoreUtil.registerGalacticraftItem("oxygenTankLightFull", GCCoreItems.oxTankLight);
		GCCoreUtil.registerGalacticraftItem("oxygenTankMediumFull", GCCoreItems.oxTankMedium);
		GCCoreUtil.registerGalacticraftItem("oxygenTankHeavyFull", GCCoreItems.oxTankHeavy);
		GCCoreUtil.registerGalacticraftItem("oxygenTankLightEmpty", GCCoreItems.oxTankLight, 90);
		GCCoreUtil.registerGalacticraftItem("oxygenTankMediumEmpty", GCCoreItems.oxTankMedium, 180);
		GCCoreUtil.registerGalacticraftItem("oxygenTankHeavyEmpty", GCCoreItems.oxTankHeavy, 270);
		GCCoreUtil.registerGalacticraftItem("oxygenMask", GCCoreItems.oxMask);
		GCCoreUtil.registerGalacticraftItem("rocketTier1", GCCoreItems.rocketTier1, 0);
		GCCoreUtil.registerGalacticraftItem("rocketTier1_18cargo", GCCoreItems.rocketTier1, 1);
		GCCoreUtil.registerGalacticraftItem("rocketTier1_36cargo", GCCoreItems.rocketTier1, 2);
		GCCoreUtil.registerGalacticraftItem("rocketTier1_54cargo", GCCoreItems.rocketTier1, 3);
		GCCoreUtil.registerGalacticraftItem("rocketTier1_prefueled", GCCoreItems.rocketTier1, 4);
		GCCoreUtil.registerGalacticraftItem("heavyDutyPickaxe", GCCoreItems.steelPickaxe);
		GCCoreUtil.registerGalacticraftItem("heavyDutyShovel", GCCoreItems.steelSpade);
		GCCoreUtil.registerGalacticraftItem("heavyDutyAxe", GCCoreItems.steelAxe);
		GCCoreUtil.registerGalacticraftItem("heavyDutyHoe", GCCoreItems.steelHoe);
		GCCoreUtil.registerGalacticraftItem("heavyDutySword", GCCoreItems.steelSword);
		GCCoreUtil.registerGalacticraftItem("heavyDutyHelmet", GCCoreItems.steelHelmet);
		GCCoreUtil.registerGalacticraftItem("heavyDutyChestplate", GCCoreItems.steelChestplate);
		GCCoreUtil.registerGalacticraftItem("heavyDutyLeggings", GCCoreItems.steelLeggings);
		GCCoreUtil.registerGalacticraftItem("heavyDutyBoots", GCCoreItems.steelBoots);
		GCCoreUtil.registerGalacticraftItem("tinCanister", GCCoreItems.canister, 0);
		GCCoreUtil.registerGalacticraftItem("copperCanister", GCCoreItems.canister, 1);
		GCCoreUtil.registerGalacticraftItem("oxygenVent", GCCoreItems.oxygenVent);
		GCCoreUtil.registerGalacticraftItem("oxygenFan", GCCoreItems.oxygenFan);
		GCCoreUtil.registerGalacticraftItem("oxygenConcentrator", GCCoreItems.oxygenConcentrator);
		GCCoreUtil.registerGalacticraftItem("heavyPlatingTier1", GCCoreItems.heavyPlatingTier1);
		GCCoreUtil.registerGalacticraftItem("rocketEngineTier1", GCCoreItems.rocketEngine, 0);
		GCCoreUtil.registerGalacticraftItem("rocketBoosterTier1", GCCoreItems.rocketEngine, 1);
		GCCoreUtil.registerGalacticraftItem("rocketFins", GCCoreItems.partFins);
		GCCoreUtil.registerGalacticraftItem("rocketNoseCone", GCCoreItems.partNoseCone);
		GCCoreUtil.registerGalacticraftItem("sensorLens", GCCoreItems.sensorLens);
		GCCoreUtil.registerGalacticraftItem("moonBuggy", GCCoreItems.buggy, 0);
		GCCoreUtil.registerGalacticraftItem("moonBuggy_18cargo", GCCoreItems.buggy, 1);
		GCCoreUtil.registerGalacticraftItem("moonBuggy_36cargo", GCCoreItems.buggy, 2);
		GCCoreUtil.registerGalacticraftItem("moonBuggy_54cargo", GCCoreItems.buggy, 3);
		GCCoreUtil.registerGalacticraftItem("flagAmerican", GCCoreItems.flag, 0);
		GCCoreUtil.registerGalacticraftItem("flagBlack", GCCoreItems.flag, 1);
		GCCoreUtil.registerGalacticraftItem("flagLightBlue", GCCoreItems.flag, 2);
		GCCoreUtil.registerGalacticraftItem("flagLime", GCCoreItems.flag, 3);
		GCCoreUtil.registerGalacticraftItem("flagBrown", GCCoreItems.flag, 4);
		GCCoreUtil.registerGalacticraftItem("flagBlue", GCCoreItems.flag, 5);
		GCCoreUtil.registerGalacticraftItem("flagGray", GCCoreItems.flag, 6);
		GCCoreUtil.registerGalacticraftItem("flagGreen", GCCoreItems.flag, 7);
		GCCoreUtil.registerGalacticraftItem("flagLightGray", GCCoreItems.flag, 8);
		GCCoreUtil.registerGalacticraftItem("flagMagenta", GCCoreItems.flag, 9);
		GCCoreUtil.registerGalacticraftItem("flagOrange", GCCoreItems.flag, 10);
		GCCoreUtil.registerGalacticraftItem("flagPink", GCCoreItems.flag, 11);
		GCCoreUtil.registerGalacticraftItem("flagPurple", GCCoreItems.flag, 12);
		GCCoreUtil.registerGalacticraftItem("flagRed", GCCoreItems.flag, 13);
		GCCoreUtil.registerGalacticraftItem("flagCyan", GCCoreItems.flag, 14);
		GCCoreUtil.registerGalacticraftItem("flagYellow", GCCoreItems.flag, 15);
		GCCoreUtil.registerGalacticraftItem("flagWhite", GCCoreItems.flag, 16);
		GCCoreUtil.registerGalacticraftItem("oxygenGear", GCCoreItems.oxygenGear);
		GCCoreUtil.registerGalacticraftItem("parachuteWhite", GCCoreItems.parachute, 0);
		GCCoreUtil.registerGalacticraftItem("parachuteBlack", GCCoreItems.parachute, 1);
		GCCoreUtil.registerGalacticraftItem("parachuteLightBlue", GCCoreItems.parachute, 2);
		GCCoreUtil.registerGalacticraftItem("parachuteLime", GCCoreItems.parachute, 3);
		GCCoreUtil.registerGalacticraftItem("parachuteBrown", GCCoreItems.parachute, 4);
		GCCoreUtil.registerGalacticraftItem("parachuteBlue", GCCoreItems.parachute, 5);
		GCCoreUtil.registerGalacticraftItem("parachuteGray", GCCoreItems.parachute, 6);
		GCCoreUtil.registerGalacticraftItem("parachuteGreen", GCCoreItems.parachute, 7);
		GCCoreUtil.registerGalacticraftItem("parachuteLightGray", GCCoreItems.parachute, 8);
		GCCoreUtil.registerGalacticraftItem("parachutePink", GCCoreItems.parachute, 9);
		GCCoreUtil.registerGalacticraftItem("parachuteOrange", GCCoreItems.parachute, 10);
		GCCoreUtil.registerGalacticraftItem("parachutePink", GCCoreItems.parachute, 11);
		GCCoreUtil.registerGalacticraftItem("parachutePurple", GCCoreItems.parachute, 12);
		GCCoreUtil.registerGalacticraftItem("parachuteRed", GCCoreItems.parachute, 13);
		GCCoreUtil.registerGalacticraftItem("parachuteCyan", GCCoreItems.parachute, 14);
		GCCoreUtil.registerGalacticraftItem("parachuteYellow", GCCoreItems.parachute, 15);
		GCCoreUtil.registerGalacticraftItem("canvas", GCCoreItems.canvas);
		GCCoreUtil.registerGalacticraftItem("fuelCanisterFull", GCCoreItems.fuelCanister, 1);
		GCCoreUtil.registerGalacticraftItem("oilCanisterFull", GCCoreItems.oilCanister, 1);
		GCCoreUtil.registerGalacticraftItem("liquidCanisterEmpty", GCCoreItems.oilCanister, GCCoreItems.oilCanister.getMaxDamage());
		GCCoreUtil.registerGalacticraftItem("steelPole", GCCoreItems.flagPole);
		GCCoreUtil.registerGalacticraftItem("oilExtractor", GCCoreItems.oilExtractor);
		GCCoreUtil.registerGalacticraftItem("schematicMoonBuggy", GCCoreItems.schematic, 0);
		GCCoreUtil.registerGalacticraftItem("schematicRocketTier2", GCCoreItems.schematic, 1);
		GCCoreUtil.registerGalacticraftItem("tier1Key", GCCoreItems.key);
		GCCoreUtil.registerGalacticraftItem("buggyMaterialWheel", GCCoreItems.partBuggy, 0);
		GCCoreUtil.registerGalacticraftItem("buggyMaterialSeat", GCCoreItems.partBuggy, 1);
		GCCoreUtil.registerGalacticraftItem("buggyMaterialStorage", GCCoreItems.partBuggy, 2);
		GCCoreUtil.registerGalacticraftItem("solarModuleSingle", GCCoreItems.basicItem, 0);
		GCCoreUtil.registerGalacticraftItem("solarModuleFull", GCCoreItems.basicItem, 1);
		GCCoreUtil.registerGalacticraftItem("batteryEmpty", GCCoreItems.battery, 100);
		GCCoreUtil.registerGalacticraftItem("batteryFull", GCCoreItems.battery, 0);
		GCCoreUtil.registerGalacticraftItem("infiniteBattery", GCCoreItems.infiniteBatery);
		GCCoreUtil.registerGalacticraftItem("rawSilicon", GCCoreItems.basicItem, 2);
		GCCoreUtil.registerGalacticraftItem("ingotCopper", GCCoreItems.basicItem, 3);
		GCCoreUtil.registerGalacticraftItem("ingotTin", GCCoreItems.basicItem, 4);
		GCCoreUtil.registerGalacticraftItem("ingotAluminum", GCCoreItems.basicItem, 5);
		GCCoreUtil.registerGalacticraftItem("compressedCopper", GCCoreItems.basicItem, 6);
		GCCoreUtil.registerGalacticraftItem("compressedTin", GCCoreItems.basicItem, 7);
		GCCoreUtil.registerGalacticraftItem("compressedAluminum", GCCoreItems.basicItem, 8);
		GCCoreUtil.registerGalacticraftItem("compressedSteel", GCCoreItems.basicItem, 9);
		GCCoreUtil.registerGalacticraftItem("compressedBronze", GCCoreItems.basicItem, 10);
		GCCoreUtil.registerGalacticraftItem("compressedIron", GCCoreItems.basicItem, 11);
		GCCoreUtil.registerGalacticraftItem("waferSolar", GCCoreItems.basicItem, 12);
		GCCoreUtil.registerGalacticraftItem("waferBasic", GCCoreItems.basicItem, 13);
		GCCoreUtil.registerGalacticraftItem("waferAdvanced", GCCoreItems.basicItem, 14);
		GCCoreUtil.registerGalacticraftItem("dehydratedApple", GCCoreItems.basicItem, 15);
		GCCoreUtil.registerGalacticraftItem("dehydratedCarrot", GCCoreItems.basicItem, 16);
		GCCoreUtil.registerGalacticraftItem("dehydratedMelon", GCCoreItems.basicItem, 17);
		GCCoreUtil.registerGalacticraftItem("dehydratedPotato", GCCoreItems.basicItem, 18);
		GCCoreUtil.registerGalacticraftItem("frequencyModule", GCCoreItems.basicItem, 19);
		GCCoreUtil.registerGalacticraftItem("meteorThrowable", GCCoreItems.meteorChunk);
		GCCoreUtil.registerGalacticraftItem("meteorThrowableHot", GCCoreItems.meteorChunk, 1);
		GCCoreUtil.registerGalacticraftItem("standardWrench", GCCoreItems.wrench);

		for (int i = 0; i < GCCoreItemBasic.names.length; i++)
		{
			if (GCCoreItemBasic.names[i].contains("ingot") || GCCoreItemBasic.names[i].contains("compressed") || GCCoreItemBasic.names[i].contains("wafer"))
			{
				OreDictionary.registerOre(GCCoreItemBasic.names[i], new ItemStack(GCCoreItems.basicItem, 1, i));
			}
		}

		OreDictionary.registerOre("plateMeteoricIron", new ItemStack(GCCoreItems.meteoricIronIngot, 1, 1));
		OreDictionary.registerOre("ingotMeteoricIron", new ItemStack(GCCoreItems.meteoricIronIngot, 1, 0));
		
		registerItems();
	}

	public static void registerHarvestLevels()
	{
//		MinecraftForge.setToolClass(GCCoreItems.steelPickaxe, "pickaxe", 4);
//		MinecraftForge.setToolClass(GCCoreItems.steelAxe, "axe", 4);
//		MinecraftForge.setToolClass(GCCoreItems.steelSpade, "shovel", 4); TODO Set harvest levels
	}

	public static void registerItems()
	{
		GameRegistry.registerItem(GCCoreItems.rocketTier1, GCCoreItems.rocketTier1.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxMask, GCCoreItems.oxMask.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxygenGear, GCCoreItems.oxygenGear.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxTankLight, GCCoreItems.oxTankLight.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxTankMedium, GCCoreItems.oxTankMedium.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxTankHeavy, GCCoreItems.oxTankHeavy.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.sensorLens, GCCoreItems.sensorLens.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.sensorGlasses, GCCoreItems.sensorGlasses.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelPickaxe, GCCoreItems.steelPickaxe.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelAxe, GCCoreItems.steelAxe.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelHoe, GCCoreItems.steelHoe.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelSpade, GCCoreItems.steelSpade.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelSword, GCCoreItems.steelSword.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelHelmet, GCCoreItems.steelHelmet.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelChestplate, GCCoreItems.steelChestplate.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelLeggings, GCCoreItems.steelLeggings.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.steelBoots, GCCoreItems.steelBoots.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.canister, GCCoreItems.canister.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxygenVent, GCCoreItems.oxygenVent.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxygenFan, GCCoreItems.oxygenFan.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oxygenConcentrator, GCCoreItems.oxygenConcentrator.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.rocketEngine, GCCoreItems.rocketEngine.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.heavyPlatingTier1, GCCoreItems.heavyPlatingTier1.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.partNoseCone, GCCoreItems.partNoseCone.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.partFins, GCCoreItems.partFins.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.flagPole, GCCoreItems.flagPole.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.canvas, GCCoreItems.canvas.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oilCanister, GCCoreItems.oilCanister.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.fuelCanister, GCCoreItems.fuelCanister.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.oilExtractor, GCCoreItems.oilExtractor.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.schematic, GCCoreItems.schematic.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.key, GCCoreItems.key.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.partBuggy, GCCoreItems.partBuggy.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.buggy, GCCoreItems.buggy.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.basicItem, GCCoreItems.basicItem.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.battery, GCCoreItems.battery.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.infiniteBatery, GCCoreItems.infiniteBatery.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.meteorChunk, GCCoreItems.meteorChunk.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.wrench, GCCoreItems.wrench.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.cheeseCurd, GCCoreItems.cheeseCurd.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.meteoricIronRaw, GCCoreItems.meteoricIronRaw.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.meteoricIronIngot, GCCoreItems.meteoricIronIngot.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.cheeseBlock, GCCoreItems.cheeseBlock.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.flag, GCCoreItems.flag.getUnlocalizedName(), GalacticraftCore.MODID);
		GameRegistry.registerItem(GCCoreItems.parachute, GCCoreItems.parachute.getUnlocalizedName(), GalacticraftCore.MODID);
	}
}
