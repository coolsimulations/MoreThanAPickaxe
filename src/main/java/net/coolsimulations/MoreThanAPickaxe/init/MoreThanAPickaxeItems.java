package net.coolsimulations.MoreThanAPickaxe.init;

import com.ToMe.trigems.ConfigHandler;
import com.ToMe.trigems.TriGemsMod;
import com.mjr.extraplanets.Config;
import com.xmods.tools.api.material.OTA_ToolMaterial;

import at.xander.fancytools.handler.MaterialHandler;
import intfox.simplyplatinum.init.ModTools;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import mod.gttiqwt.emeraldobsidian.init.ModItems;
import mods.railcraft.common.items.ItemMaterials;
import net.coolsimulations.MoreThanAPickaxe.MoreThanAPickaxe;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemEndAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemFireproofAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGobberAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGravititeAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemHolystoneAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemZaniteAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.emeralds.material.EmeraldMaterial;
import thedarkcolour.futuremc.config.FConfig;

public class MoreThanAPickaxeItems {

	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;

	public static Item emerald_adze;
	public static Item obsidian_adze;
	public static Item netherite_adze;

	public static Item amethyst_adze;
	public static Item ruby_adze;
	public static Item sapphire_adze;
	public static Item topaz_adze;
	public static Item quartz_adze;

	public static Item steel_adze;
	public static Item desh_adze;
	public static Item sticky_desh_adze;
	public static Item titanium_adze_gc;

	public static Item gravitite_adze;
	public static Item holystone_adze;
	public static Item skyroot_adze;
	public static Item valkyrie_adze;
	public static Item zanite_adze;
	
	public static Item mercury_adze;
	public static Item carbon_adze;
	public static Item uranium_adze;
	public static Item palladium_adze;
	public static Item red_gem_adze;
	public static Item magnesium_adze;
	public static Item crystal_adze;
	public static Item white_gem_adze;
	public static Item blue_gem_adze;
	public static Item zinc_adze;
	public static Item tungsten_adze;
	public static Item blue_diamond_adze;
	public static Item red_diamond_adze;
	public static Item purple_diamond_adze;
	public static Item yellow_diamond_adze;
	public static Item green_diamond_adze;
	
	public static Item gobber_adze;
	public static Item nether_adze;
	public static Item end_adze;
	
	public static Item platinum_adze;

	public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);

	public static void init() {

		wooden_adze = new ItemAdze(ToolMaterial.WOOD, 6.0F, -2.4F).setUnlocalizedName("wooden_adze").setRegistryName("wooden_adze").setCreativeTab(SPTabs.tabTools);
		stone_adze = new ItemAdze(ToolMaterial.STONE, 7.0F, -2.4F).setUnlocalizedName("stone_adze").setRegistryName("stone_adze").setCreativeTab(SPTabs.tabTools);
		iron_adze = new ItemAdze(ToolMaterial.IRON, 6.0F, -2.4F).setUnlocalizedName("iron_adze").setRegistryName("iron_adze").setCreativeTab(SPTabs.tabTools);
		gold_adze = new ItemAdze(ToolMaterial.GOLD, 6.0F, -2.4F).setUnlocalizedName("gold_adze").setRegistryName("gold_adze").setCreativeTab(SPTabs.tabTools);
		diamond_adze = new ItemAdze(ToolMaterial.DIAMOND, 5.0F, -2.4F).setUnlocalizedName("diamond_adze").setRegistryName("diamond_adze").setCreativeTab(SPTabs.tabTools);
		bronze_adze = new ItemAdze(SPItems.bronzeToolMaterial, 5.5F, -2.4F).setUnlocalizedName("bronze_adze").setRegistryName("bronze_adze").setCreativeTab(SPTabs.tabTools);
		titanium_adze = new ItemAdze(SPItems.titaniumToolMaterial, 5.5F, -2.4F).setUnlocalizedName("titanium_adze").setRegistryName("titanium_adze").setCreativeTab(SPTabs.tabTools);

		if(!SPCompatibilityManager.isGCLoaded()) {

			if(SPCompatibilityManager.isRailcraftLoaded()) {
				steel_adze = new ItemAdze(ItemMaterials.STEEL_TOOL, 5.5F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);
			} else {
				steel_adze = new ItemAdze(steelToolMaterial, 5.5F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);
			}
		} else {

			steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);

			if(SPCompatibilityManager.isGCPLoaded()) {

				desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F, true).setUnlocalizedName("desh_adze").setRegistryName("desh_adze").setCreativeTab(SPTabs.tabTools);
				sticky_desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F, true).setUnlocalizedName("sticky_desh_adze").setRegistryName("sticky_desh_adze").setCreativeTab(SPTabs.tabTools);
				titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F, true).setUnlocalizedName("titanium_adze_gc").setRegistryName("titanium_adze_gc").setCreativeTab(SPTabs.tabTools);
				
				if(SPCompatibilityManager.isExtraPlanetsLoaded()) {
					
					if(Config.MERCURY && Config.ITEMS_MERCURY)
						mercury_adze = new ItemAdze(ToolMaterial.valueOf("Mercury Material"), 0.0F, -2.4F, true).setUnlocalizedName("mercury_adze").setRegistryName("mercury_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.MERCURY && Config.ITEMS_CARBON)
						carbon_adze = new ItemAdze(ToolMaterial.valueOf("Carbon Material"), 0.0F, -2.4F, true).setUnlocalizedName("carbon_adze").setRegistryName("carbon_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.CERES && Config.ITEMS_URANIUM)
						uranium_adze = new ItemAdze(ToolMaterial.valueOf("Uranium Material"), 0.0F, -2.4F, true).setUnlocalizedName("uranium_adze").setRegistryName("uranium_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.JUPITER && Config.ITEMS_PALLADIUM)
						palladium_adze = new ItemAdze(ToolMaterial.valueOf("Palladium Material"), 0.0F, -2.4F, true).setUnlocalizedName("palladium_adze").setRegistryName("palladium_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.JUPITER && Config.ITEMS_GEM_RED)
						red_gem_adze = new ItemAdze(ToolMaterial.valueOf("Red Gem Material"), 0.0F, -2.4F, true).setUnlocalizedName("red_gem_adze").setRegistryName("red_gem_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.SATURN && Config.ITEMS_MAGNESIUM)
						magnesium_adze = new ItemAdze(ToolMaterial.valueOf("Magnesium Material"), 0.0F, -2.4F, true).setUnlocalizedName("magnesium_adze").setRegistryName("magnesium_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.URANUS && Config.ITEMS_CRYSTAL)
						crystal_adze = new ItemAdze(ToolMaterial.valueOf("Crystal Material"), 0.0F, -2.4F, true).setUnlocalizedName("crystal_adze").setRegistryName("crystal_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.URANUS && Config.ITEMS_GEM_WHITE)
						white_gem_adze = new ItemAdze(ToolMaterial.valueOf("White Gem Material"), 0.0F, -2.4F, true).setUnlocalizedName("white_gem_adze").setRegistryName("white_gem_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.NEPTUNE && Config.ITEMS_GEM_BLUE)
						blue_gem_adze = new ItemAdze(ToolMaterial.valueOf("Blue Gem Material"), 0.0F, -2.4F, true).setUnlocalizedName("blue_gem_adze").setRegistryName("blue_gem_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.NEPTUNE && Config.ITEMS_ZINC)
						zinc_adze = new ItemAdze(ToolMaterial.valueOf("Zinc Material"), 0.0F, -2.4F, true).setUnlocalizedName("zinc_adze").setRegistryName("zinc_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.PLUTO && Config.ITEMS_TUNGSTEN)
						tungsten_adze = new ItemAdze(ToolMaterial.valueOf("Tungsten Material"), 0.0F, -2.4F, true).setUnlocalizedName("tungsten_adze").setRegistryName("tungsten_adze").setCreativeTab(SPTabs.tabTools);
					
					if(Config.KEPLER22B && Config.ITEMS_KEPLER22B && Config.KEPLER_SOLAR_SYSTEMS) {
						
						blue_diamond_adze = new ItemAdze(ToolMaterial.valueOf("Blue Diamond Material"), 0.0F, -2.4F, true).setUnlocalizedName("blue_diamond_adze").setRegistryName("blue_diamond_adze").setCreativeTab(SPTabs.tabTools);
						red_diamond_adze = new ItemAdze(ToolMaterial.valueOf("Red Diamond Material"), 0.0F, -2.4F, true).setUnlocalizedName("red_diamond_adze").setRegistryName("red_diamond_adze").setCreativeTab(SPTabs.tabTools);
						purple_diamond_adze = new ItemAdze(ToolMaterial.valueOf("Purple Diamond Material"), 0.0F, -2.4F, true).setUnlocalizedName("purple_diamond_adze").setRegistryName("purple_diamond_adze").setCreativeTab(SPTabs.tabTools);
						yellow_diamond_adze = new ItemAdze(ToolMaterial.valueOf("Yellow Diamond Material"), 0.0F, -2.4F, true).setUnlocalizedName("yellow_diamond_adze").setRegistryName("yellow_diamond_adze").setCreativeTab(SPTabs.tabTools);
						green_diamond_adze = new ItemAdze(ToolMaterial.valueOf("Green Diamond Material"), 0.0F, -2.4F, true).setUnlocalizedName("green_diamond_adze").setRegistryName("green_diamond_adze").setCreativeTab(SPTabs.tabTools);
					}
				}
			}
		}

		if(!SPCompatibilityManager.isEmeraldMaterialModsLoaded() && SPConfig.enableEmeraldMaterial) {
			emerald_adze = new ItemAdze(SPItems.emeraldToolMaterial, 4.5F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(SPCompatibilityManager.isPI3xLoaded()) {
				emerald_adze = new ItemAdze(EmeraldMaterial.TOOL, 4.0F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isFancyToolsLoaded()) {
				emerald_adze = new ItemAdze(MaterialHandler.emerald, 5.25F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				emerald_adze = new ItemAdze(TriGemsMod.Emerald, 4.0F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isEAOLoaded()) {
				emerald_adze = new ItemAdze(ModItems.emerald, 5.0F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isGACLoaded()) {
				emerald_adze = new ItemAdze(EnumHelper.addToolMaterial("material_emerald", 3, 850, 8.5F, 3.5F, 11), 6.0F, -2.4F, true).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			}
		}

		if(!SPCompatibilityManager.isObsidianMaterialModsLoaded() && SPConfig.enableObsidianMaterial) {
			obsidian_adze = new ItemAdze(SPItems.obsidianToolMaterial, 1.5F, -2.4F, true).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				obsidian_adze = new ItemAdze(MaterialHandler.obsidian, 5.25F, -2.4F, true).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isEAOLoaded()) {
				obsidian_adze = new ItemAdze(ModItems.obsidian, 6.0F, -2.4F, true).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isOAATLoaded()) {
				obsidian_adze = new ItemAdze(OTA_ToolMaterial.obsidianMaterial, 4.0F, -2.4F, true).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isOTAALoaded()) {
				obsidian_adze = new ItemAdze(EnumHelper.addToolMaterial("material_obsidian", 4, 32, 8.0F, 4.0F, 35), 5.0F, -2.4F, true).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			}
		}

		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite) {
			ToolMaterial NETHERITE = EnumHelper.addToolMaterial("NETHERITE", 4, 2031, 9.0F, 4.0F, 9);
			netherite_adze = new ItemFireproofAdze(NETHERITE, 5.0F, -2.4F).setUnlocalizedName("netherite_adze").setRegistryName("netherite_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isNetheriteModLoaded()) {
			netherite_adze = new ItemFireproofAdze(com.flameknightgd.netherite.init.ModItems.MATERIAL_NETHERITE_INGOT, 5.0F, -2.4F).setUnlocalizedName("netherite_adze").setRegistryName("netherite_adze").setCreativeTab(SPTabs.tabTools);
		}

		if(SPCompatibilityManager.isVulcaniteLoaded())
			MoreThanAPickaxeVulcanite.init();

		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.init();

		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			MoreThanAPickaxeGalaxiteOre.init();

		if(SPCompatibilityManager.isTriGemsLoaded()) {
			if(ConfigHandler.enableRuby)
				ruby_adze = new ItemAdze(TriGemsMod.Ruby, 5.0F, -2.4F, true).setUnlocalizedName("ruby_adze").setRegistryName("ruby_adze").setCreativeTab(SPTabs.tabTools);
			if(ConfigHandler.enableSapphire)
				sapphire_adze = new ItemAdze(TriGemsMod.Sapphire, 4.5F, -2.4F, true).setUnlocalizedName("sapphire_adze").setRegistryName("sapphire_adze").setCreativeTab(SPTabs.tabTools);
			if(ConfigHandler.enableTopaz)
				topaz_adze = new ItemAdze(TriGemsMod.Topaz, 5.5F, -2.4F, true).setUnlocalizedName("topaz_adze").setRegistryName("topaz_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isGACLoaded()) {
			ToolMaterial RUBY = EnumHelper.addToolMaterial("material_ruby", 3, 900, 9.0F, 4.0F, 12);
			ruby_adze = new ItemAdze(RUBY, 4.0F, -2.4F, true).setUnlocalizedName("ruby_adze").setRegistryName("ruby_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial SAPPHIRE = EnumHelper.addToolMaterial("material_sapphire", 3, 900, 4.0F, 4.0F, 12);
			sapphire_adze = new ItemAdze(SAPPHIRE, 4.0F, -2.4F, true).setUnlocalizedName("sapphire_adze").setRegistryName("sapphire_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial TOPAZ = EnumHelper.addToolMaterial("material_topaz", 2, 775, 8.0F, 2.7F, 9);
			topaz_adze = new ItemAdze(TOPAZ, 5.3F, -2.4F, true).setUnlocalizedName("topaz_adze").setRegistryName("topaz_adze").setCreativeTab(SPTabs.tabTools);
		}

		if(SPCompatibilityManager.isGACLoaded()) {
			ToolMaterial AMETHYST = EnumHelper.addToolMaterial("material_amethyst", 2, 775, 8.0F, 2.3F, 9);
			amethyst_adze = new ItemAdze(AMETHYST, 5.7F, -2.4F, true).setUnlocalizedName("amethyst_adze").setRegistryName("amethyst_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial QUARTZ = EnumHelper.addToolMaterial("material_quartz", 2, 400, 7.0F, 1.5F, 7);
			quartz_adze = new ItemAdze(QUARTZ, 6.5F, -2.4F, true).setUnlocalizedName("quartz_adze").setRegistryName("quartz_adze").setCreativeTab(SPTabs.tabTools);
		}

		if(SPCompatibilityManager.isAetherLegacyLoaded()) {
			ToolMaterial GRAVITITE = EnumHelper.addToolMaterial("GRAVITITE", ToolMaterial.DIAMOND.getHarvestLevel(), ToolMaterial.DIAMOND.getMaxUses(), ToolMaterial.DIAMOND.getEfficiency(), ToolMaterial.DIAMOND.getAttackDamage(), ToolMaterial.DIAMOND.getEnchantability());
			gravitite_adze = new ItemGravititeAdze(GRAVITITE, 5.0F, -2.4F).setUnlocalizedName("gravitite_adze").setRegistryName("gravitite_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial HOLYSTONE = EnumHelper.addToolMaterial("HOLYSTONE", ToolMaterial.STONE.getHarvestLevel(), ToolMaterial.STONE.getMaxUses(), ToolMaterial.STONE.getEfficiency(), ToolMaterial.STONE.getAttackDamage(), ToolMaterial.STONE.getEnchantability());
			holystone_adze = new ItemHolystoneAdze(HOLYSTONE, 7.0F, -2.4F).setUnlocalizedName("holystone_adze").setRegistryName("holystone_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial SKYROOT = EnumHelper.addToolMaterial("SKYROOT", ToolMaterial.WOOD.getHarvestLevel(), ToolMaterial.WOOD.getMaxUses(), ToolMaterial.WOOD.getEfficiency(), ToolMaterial.WOOD.getAttackDamage(), ToolMaterial.WOOD.getEnchantability());
			skyroot_adze = new ItemAdze(SKYROOT, 6.0F, -2.4F).setUnlocalizedName("skyroot_adze").setRegistryName("skyroot_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial VALKYRIE = EnumHelper.addToolMaterial("VALKYRIE", ToolMaterial.DIAMOND.getHarvestLevel(), ToolMaterial.DIAMOND.getMaxUses(), ToolMaterial.DIAMOND.getEfficiency(), ToolMaterial.DIAMOND.getAttackDamage(), ToolMaterial.DIAMOND.getEnchantability());
			valkyrie_adze = new ItemAdze(VALKYRIE, 5.0F, -2.4F).setUnlocalizedName("valkyrie_adze").setRegistryName("valkyrie_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial ZANITE = EnumHelper.addToolMaterial("ZANITE", ToolMaterial.IRON.getHarvestLevel(), ToolMaterial.IRON.getMaxUses(), ToolMaterial.IRON.getEfficiency(), ToolMaterial.IRON.getAttackDamage(), ToolMaterial.IRON.getEnchantability());
			zanite_adze = new ItemZaniteAdze(ZANITE, 6.0F, -2.4F).setUnlocalizedName("zanite_adze").setRegistryName("zanite_adze").setCreativeTab(SPTabs.tabTools);
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			ToolMaterial MATERIAL_PICKAXE_GLOBOT = EnumHelper.addToolMaterial("material_pickaxe_globot", 3, 4000, 24.0F, 2.0F, 20);
			gobber_adze = new ItemGobberAdze(MATERIAL_PICKAXE_GLOBOT, 9.0F, -2.2F, true).setUnlocalizedName("gobber_adze").setRegistryName("gobber_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial MATERIAL_PICKAXE_GLOBOT2 = EnumHelper.addToolMaterial("material_pickaxe_globot2", 3, 6000, 32.0F, 2.0F, 25);
			nether_adze = new ItemGobberAdze(MATERIAL_PICKAXE_GLOBOT2, 13.0F, -2.2F, true).setUnlocalizedName("nether_adze").setRegistryName("nether_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial MATERIAL_PICKAXE_GLOBOT3 = EnumHelper.addToolMaterial("material_pickaxe_globot3", 10, 6000, 48.0F, 2.0F, 25);
			end_adze = new ItemEndAdze(MATERIAL_PICKAXE_GLOBOT3, 19.0F, -2.2F).setUnlocalizedName("end_adze").setRegistryName("end_adze").setCreativeTab(SPTabs.tabTools);
		}
		
		if (SPCompatibilityManager.isSimplyPlatinumLoaded())
			platinum_adze = new ItemAdze(ModTools.platinumMaterial, 3.0F, -2.4F, true).setUnlocalizedName("platinum_adze").setRegistryName("platinum_adze").setCreativeTab(SPTabs.tabTools);
	}

	public static void register()
	{

		registerItem(wooden_adze);
		registerItem(stone_adze);
		registerItem(iron_adze);
		registerItem(gold_adze);
		registerItem(diamond_adze);
		registerItem(bronze_adze);
		registerItem(titanium_adze);

		registerItem(steel_adze);

		if(SPCompatibilityManager.isGCLoaded() && SPCompatibilityManager.isGCPLoaded()) {

			registerItem(desh_adze);
			registerItem(sticky_desh_adze);
			registerItem(titanium_adze_gc);
			
			if(SPCompatibilityManager.isExtraPlanetsLoaded()) {
				
				if(Config.MERCURY && Config.ITEMS_MERCURY)
					registerItem(mercury_adze);
				
				if(Config.MERCURY && Config.ITEMS_CARBON)
					registerItem(carbon_adze);
				
				if(Config.CERES && Config.ITEMS_URANIUM)
					registerItem(uranium_adze);
				
				if(Config.JUPITER && Config.ITEMS_PALLADIUM)
					registerItem(palladium_adze);
				
				if(Config.JUPITER && Config.ITEMS_GEM_RED)
					registerItem(red_gem_adze);
				
				if(Config.SATURN && Config.ITEMS_MAGNESIUM)
					registerItem(magnesium_adze);
				
				if(Config.URANUS && Config.ITEMS_CRYSTAL)
					registerItem(crystal_adze);
				
				if(Config.URANUS && Config.ITEMS_GEM_WHITE)
					registerItem(white_gem_adze);
				
				if(Config.NEPTUNE && Config.ITEMS_GEM_BLUE)
					registerItem(blue_gem_adze);
				
				if(Config.NEPTUNE && Config.ITEMS_ZINC)
					registerItem(zinc_adze);
				
				if(Config.PLUTO && Config.ITEMS_TUNGSTEN)
					registerItem(tungsten_adze);
				
				if(Config.KEPLER22B && Config.ITEMS_KEPLER22B && Config.KEPLER_SOLAR_SYSTEMS) {
					
					registerItem(blue_diamond_adze);
					registerItem(red_diamond_adze);
					registerItem(purple_diamond_adze);
					registerItem(yellow_diamond_adze);
					registerItem(green_diamond_adze);
				}
			}
			
		}

		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded() || SPConfig.enableEmeraldMaterial) {
			registerItem(emerald_adze);
		}

		if(!SPCompatibilityManager.isEmeraldMaterialModsLoaded() && SPConfig.enableEmeraldMaterial) {
			registerItem(emerald_adze);
		} else if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(!SPCompatibilityManager.isPI3xLoaded() && !SPCompatibilityManager.isFancyToolsLoaded() && SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				registerItem(emerald_adze);
			} else {
				registerItem(emerald_adze);
			}
		}

		if(SPCompatibilityManager.isObsidianMaterialModsLoaded() || SPConfig.enableObsidianMaterial) {
			registerItem(obsidian_adze);
		}

		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite) {
			registerItem(netherite_adze);
		} else if(SPCompatibilityManager.isNetheriteModLoaded()) {
			registerItem(netherite_adze);
		}

		if(SPCompatibilityManager.isVulcaniteLoaded())
			MoreThanAPickaxeVulcanite.register();

		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.register();

		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			MoreThanAPickaxeGalaxiteOre.register();

		if(SPCompatibilityManager.isTriGemsLoaded() ){
			if(ConfigHandler.enableRuby)
				registerItem(ruby_adze);
			if(ConfigHandler.enableSapphire)
				registerItem(sapphire_adze);
			if(ConfigHandler.enableTopaz)
				registerItem(topaz_adze);
		} else if(SPCompatibilityManager.isGACLoaded()) {
			registerItem(ruby_adze);
			registerItem(sapphire_adze);
			registerItem(topaz_adze);
		}

		if(SPCompatibilityManager.isGACLoaded()) {
			registerItem(amethyst_adze);
			registerItem(quartz_adze);
		}

		if(SPCompatibilityManager.isAetherLegacyLoaded()) {
			registerItem(gravitite_adze);
			registerItem(holystone_adze);
			registerItem(skyroot_adze);
			registerItem(valkyrie_adze);
			registerItem(zanite_adze);
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			registerItem(gobber_adze);
			registerItem(nether_adze);
			registerItem(end_adze);
		}
		
		if (SPCompatibilityManager.isSimplyPlatinumLoaded())
			registerItem(platinum_adze);
	}

	public static void registerRenders()
	{
		registerRender(wooden_adze);
		registerRender(stone_adze);
		registerRender(iron_adze);
		registerRender(gold_adze);
		registerRender(diamond_adze);
		registerRender(bronze_adze);
		registerRender(titanium_adze);

		registerRender(steel_adze);

		if(SPCompatibilityManager.isGCLoaded() && SPCompatibilityManager.isGCPLoaded()) {

			registerRender(desh_adze);
			registerRender(sticky_desh_adze);
			registerRender(titanium_adze_gc);
			
			if(SPCompatibilityManager.isExtraPlanetsLoaded()) {
				
				if(Config.MERCURY && Config.ITEMS_MERCURY)
					registerRender(mercury_adze);
				
				if(Config.MERCURY && Config.ITEMS_CARBON)
					registerRender(carbon_adze);
				
				if(Config.CERES && Config.ITEMS_URANIUM)
					registerRender(uranium_adze);
				
				if(Config.JUPITER && Config.ITEMS_PALLADIUM)
					registerRender(palladium_adze);
				
				if(Config.JUPITER && Config.ITEMS_GEM_RED)
					registerRender(red_gem_adze);
				
				if(Config.SATURN && Config.ITEMS_MAGNESIUM)
					registerRender(magnesium_adze);
				
				if(Config.URANUS && Config.ITEMS_CRYSTAL)
					registerRender(crystal_adze);
				
				if(Config.URANUS && Config.ITEMS_GEM_WHITE)
					registerRender(white_gem_adze);
				
				if(Config.NEPTUNE && Config.ITEMS_GEM_BLUE)
					registerRender(blue_gem_adze);
				
				if(Config.NEPTUNE && Config.ITEMS_ZINC)
					registerRender(zinc_adze);
				
				if(Config.PLUTO && Config.ITEMS_TUNGSTEN)
					registerRender(tungsten_adze);
				
				if(Config.KEPLER22B && Config.ITEMS_KEPLER22B && Config.KEPLER_SOLAR_SYSTEMS) {
					
					registerRender(blue_diamond_adze);
					registerRender(red_diamond_adze);
					registerRender(purple_diamond_adze);
					registerRender(yellow_diamond_adze);
					registerRender(green_diamond_adze);
				}
			}
		}

		if(!SPCompatibilityManager.isEmeraldMaterialModsLoaded() && SPConfig.enableEmeraldMaterial) {
			registerRender(emerald_adze);
		} else if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(!SPCompatibilityManager.isPI3xLoaded() && !SPCompatibilityManager.isFancyToolsLoaded() && SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				registerRender(emerald_adze);
			} else {
				registerRender(emerald_adze);
			}
		}

		if(SPCompatibilityManager.isObsidianMaterialModsLoaded() || SPConfig.enableObsidianMaterial) {
			registerRender(obsidian_adze);
		}

		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite) {
			registerRender(netherite_adze);
		} else if(SPCompatibilityManager.isNetheriteModLoaded()) {
			registerRender(netherite_adze);
		}

		if(SPCompatibilityManager.isVulcaniteLoaded())
			MoreThanAPickaxeVulcanite.registerRenders();

		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.registerRenders();

		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			MoreThanAPickaxeGalaxiteOre.registerRenders();

		if(SPCompatibilityManager.isTriGemsLoaded() ){
			if(ConfigHandler.enableRuby)
				registerRender(ruby_adze);
			if(ConfigHandler.enableSapphire)
				registerRender(sapphire_adze);
			if(ConfigHandler.enableTopaz)
				registerRender(topaz_adze);
		} else if(SPCompatibilityManager.isGACLoaded()) {
			registerRender(ruby_adze);
			registerRender(sapphire_adze);
			registerRender(topaz_adze);
		}

		if(SPCompatibilityManager.isGACLoaded()) {
			registerRender(amethyst_adze);
			registerRender(quartz_adze);
		}

		if(SPCompatibilityManager.isAetherLegacyLoaded()) {
			registerRender(gravitite_adze);
			registerRender(holystone_adze);
			registerRender(skyroot_adze);
			registerRender(valkyrie_adze);
			registerRender(zanite_adze);
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			registerRender(gobber_adze);
			registerRender(nether_adze);
			registerRender(end_adze);
		}
		
		if (SPCompatibilityManager.isSimplyPlatinumLoaded())
			registerRender(platinum_adze);
	}

	public static void registerItem(Item item) {

		MoreThanAPickaxe.ITEMS.add(item);
	}

	public static void registerItems(IForgeRegistry<Item> registry) {

		for (Item item : MoreThanAPickaxe.ITEMS)
		{
			registry.register(item);
		}
	}

	public static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
