package net.coolsimulations.MoreThanAPickaxe.init;

import com.ToMe.trigems.ConfigHandler;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.items.ExtraPlanets_Items;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPBlocks;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thedarkcolour.futuremc.config.FConfig;

public class MoreThanAPickaxeToolMaterials {

	public static void init() {

		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded() || SPConfig.enableEmeraldMaterial)
			setMaterial(MoreThanAPickaxeItems.emerald_adze, Items.EMERALD);
		if(SPCompatibilityManager.isObsidianMaterialModsLoaded() || SPConfig.enableObsidianMaterial)
			setMaterial(MoreThanAPickaxeItems.obsidian_adze, Blocks.OBSIDIAN);
		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite)
			setMaterial(MoreThanAPickaxeItems.netherite_adze, Item.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.FUTURE_MC_MODID, "netherite_ingot")));
		else if(SPCompatibilityManager.isNetheriteModLoaded())
			setMaterial(MoreThanAPickaxeItems.netherite_adze, com.flameknightgd.netherite.init.ModItems.NETHERITE_INGOT);
		if(SPCompatibilityManager.isVulcaniteLoaded())
			MoreThanAPickaxeVulcanite.registerToolMaterial();
		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.registerToolMaterial();
		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			MoreThanAPickaxeGalaxiteOre.registerToolMaterial();

		if(SPCompatibilityManager.isTriGemsLoaded()) {
			if(ConfigHandler.enableRuby)
				setMaterial(MoreThanAPickaxeItems.ruby_adze, SPBlocks.ruby);
			if(ConfigHandler.enableSapphire)
				setMaterial(MoreThanAPickaxeItems.sapphire_adze, SPBlocks.sapphire);
			if(ConfigHandler.enableTopaz)
				setMaterial(MoreThanAPickaxeItems.topaz_adze, SPBlocks.topaz);
		} else if(SPCompatibilityManager.isGACLoaded()) {
			setMaterial(MoreThanAPickaxeItems.ruby_adze, SPBlocks.ruby);
			setMaterial(MoreThanAPickaxeItems.sapphire_adze, SPBlocks.sapphire);
			setMaterial(MoreThanAPickaxeItems.topaz_adze, SPBlocks.topaz);
		}

		if(SPCompatibilityManager.isGACLoaded()) {
			setMaterial(MoreThanAPickaxeItems.amethyst_adze, SPBlocks.amethyst);
			setMaterial(MoreThanAPickaxeItems.quartz_adze, Items.QUARTZ);
		}
		if(SPCompatibilityManager.isAetherLegacyLoaded()) {
			setMaterial(MoreThanAPickaxeItems.gravitite_adze, BlocksAether.enchanted_gravitite);
			setMaterial(MoreThanAPickaxeItems.holystone_adze, BlocksAether.holystone);
			setMaterial(MoreThanAPickaxeItems.skyroot_adze, BlocksAether.skyroot_plank);
			setMaterial(MoreThanAPickaxeItems.zanite_adze, ItemsAether.zanite_gemstone);
		}

		if(SPCompatibilityManager.isExtraPlanetsLoaded()) {
			if(SPCompatibilityManager.isExtraPlanetsLoaded()) {

				if(Config.MERCURY && Config.ITEMS_MERCURY)
					setMaterial(MoreThanAPickaxeItems.mercury_adze, ExtraPlanets_Items.INGOT_MERCURY);

				if(Config.MERCURY && Config.ITEMS_CARBON)
					setMaterial(MoreThanAPickaxeItems.carbon_adze, new ItemStack(ExtraPlanets_Items.TIER_4_ITEMS, 1, 5));

				if(Config.CERES && Config.ITEMS_URANIUM)
					setMaterial(MoreThanAPickaxeItems.uranium_adze, ExtraPlanets_Items.INGOT_URANIUM);

				if(Config.JUPITER && Config.ITEMS_PALLADIUM)
					setMaterial(MoreThanAPickaxeItems.palladium_adze, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 5));

				if(Config.JUPITER && Config.ITEMS_GEM_RED)
					setMaterial(MoreThanAPickaxeItems.red_gem_adze, new ItemStack(ExtraPlanets_Items.TIER_5_ITEMS, 1, 8));

				if(Config.SATURN && Config.ITEMS_MAGNESIUM)
					setMaterial(MoreThanAPickaxeItems.magnesium_adze, new ItemStack(ExtraPlanets_Items.TIER_6_ITEMS, 1, 5));

				if(Config.URANUS && Config.ITEMS_CRYSTAL)
					setMaterial(MoreThanAPickaxeItems.crystal_adze, new ItemStack(ExtraPlanets_Items.TIER_7_ITEMS, 1, 5));

				if(Config.URANUS && Config.ITEMS_GEM_WHITE)
					setMaterial(MoreThanAPickaxeItems.white_gem_adze, new ItemStack(ExtraPlanets_Items.TIER_7_ITEMS, 1, 7));

				if(Config.NEPTUNE && Config.ITEMS_GEM_BLUE)
					setMaterial(MoreThanAPickaxeItems.blue_gem_adze, new ItemStack(ExtraPlanets_Items.TIER_8_ITEMS, 1, 6));

				if(Config.NEPTUNE && Config.ITEMS_ZINC)
					setMaterial(MoreThanAPickaxeItems.zinc_adze, new ItemStack(ExtraPlanets_Items.TIER_8_ITEMS, 1, 5));

				if(Config.PLUTO && Config.ITEMS_TUNGSTEN)
					setMaterial(MoreThanAPickaxeItems.tungsten_adze, new ItemStack(ExtraPlanets_Items.TIER_9_ITEMS, 1, 5));

				if(Config.KEPLER22B && Config.ITEMS_KEPLER22B && Config.KEPLER_SOLAR_SYSTEMS) {

					setMaterial(MoreThanAPickaxeItems.blue_diamond_adze, ExtraPlanets_Items.TIER_11_ITEMS);
					setMaterial(MoreThanAPickaxeItems.red_diamond_adze, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 1));
					setMaterial(MoreThanAPickaxeItems.purple_diamond_adze, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 2));
					setMaterial(MoreThanAPickaxeItems.yellow_diamond_adze, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 3));
					setMaterial(MoreThanAPickaxeItems.green_diamond_adze, new ItemStack(ExtraPlanets_Items.TIER_11_ITEMS, 1, 4));
				}
			}
		}
	}

	public static void setMaterial(Item adze, Item repair) {

		setMaterial(adze, new ItemStack(repair));
	}

	public static void setMaterial(Item adze, Block repair) {

		setMaterial(adze, new ItemStack(repair));
	}

	public static void setMaterial(Item adze, ItemStack repair) {

		if(!(adze instanceof ItemAdze))
			throw new RuntimeException("Can not set tool repair material because item is not an Adze");

		String name = (((ItemAdze) adze).getToolMaterialName());
		if(ToolMaterial.valueOf(name).getRepairItemStack().isEmpty()) {
			ToolMaterial.valueOf(name).setRepairItem(repair);
		}
	}

}
