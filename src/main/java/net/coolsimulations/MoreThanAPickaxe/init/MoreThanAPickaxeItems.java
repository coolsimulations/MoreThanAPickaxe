package net.coolsimulations.MoreThanAPickaxe.init;

import com.ToMe.trigems.ConfigHandler;
import com.ToMe.trigems.TriGemsMod;
import com.xmods.tools.api.material.OTA_ToolMaterial;

import at.xander.fancytools.handler.MaterialHandler;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import mod.gttiqwt.emeraldobsidian.init.ModItems;
import net.coolsimulations.MoreThanAPickaxe.MoreThanAPickaxe;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemFireproofAdze;
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

			steel_adze = new ItemAdze(steelToolMaterial, 5.5F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);
		} else {

			steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);

			if(SPCompatibilityManager.isGCPLoaded()) {

				desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F).setUnlocalizedName("desh_adze").setRegistryName("desh_adze").setCreativeTab(SPTabs.tabTools);
				sticky_desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F).setUnlocalizedName("sticky_desh_adze").setRegistryName("sticky_desh_adze").setCreativeTab(SPTabs.tabTools);
				titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F).setUnlocalizedName("titanium_adze_gc").setRegistryName("titanium_adze_gc").setCreativeTab(SPTabs.tabTools);
			}
		}
		
		if(!SPCompatibilityManager.isEmeraldMatieralModsLoaded() && SPConfig.enableEmeraldMaterial) {
			emerald_adze = new ItemAdze(SPItems.emeraldToolMaterial, 4.5F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isEmeraldMatieralModsLoaded()) {
			if(SPCompatibilityManager.isPI3xLoaded()) {
				emerald_adze = new ItemAdze(EmeraldMaterial.TOOL, 4.0F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isFancyToolsLoaded()) {
				emerald_adze = new ItemAdze(MaterialHandler.emerald, 5.25F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				emerald_adze = new ItemAdze(TriGemsMod.Emerald, 4.0F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isEAOLoaded()) {
				emerald_adze = new ItemAdze(ModItems.emerald, 5.0F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isGACLoaded()) {
				emerald_adze = new ItemAdze(EnumHelper.addToolMaterial("material_emerald", 3, 850, 8.5F, 3.5F, 11), 6.0F, -2.4F).setUnlocalizedName("emerald_adze").setRegistryName("emerald_adze").setCreativeTab(SPTabs.tabTools);
			}
		}
		
		if(!SPCompatibilityManager.isEmeraldMatieralModsLoaded() && SPConfig.enableObsidianMaterial) {
			obsidian_adze = new ItemAdze(SPItems.obsidianToolMaterial, 1.5F, -2.4F).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isObsidianMatieralModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				obsidian_adze = new ItemAdze(MaterialHandler.obsidian, 5.25F, -2.4F).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isEAOLoaded()) {
				obsidian_adze = new ItemAdze(ModItems.obsidian, 6.0F, -2.4F).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isOAATLoaded()) {
				obsidian_adze = new ItemAdze(OTA_ToolMaterial.obsidianMaterial, 4.0F, -2.4F).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
			} else if(SPCompatibilityManager.isOTAALoaded()) {
				obsidian_adze = new ItemAdze(EnumHelper.addToolMaterial("material_obsidian", 4, 32, 8.0F, 4.0F, 35), 5.0F, -2.4F).setUnlocalizedName("obsidian_adze").setRegistryName("obsidian_adze").setCreativeTab(SPTabs.tabTools);
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
				ruby_adze = new ItemAdze(TriGemsMod.Ruby, 5.0F, -2.4F).setUnlocalizedName("ruby_adze").setRegistryName("ruby_adze").setCreativeTab(SPTabs.tabTools);
			if(ConfigHandler.enableSapphire)
			sapphire_adze = new ItemAdze(TriGemsMod.Sapphire, 4.5F, -2.4F).setUnlocalizedName("sapphire_adze").setRegistryName("sapphire_adze").setCreativeTab(SPTabs.tabTools);
			if(ConfigHandler.enableTopaz)
			topaz_adze = new ItemAdze(TriGemsMod.Topaz, 5.5F, -2.4F).setUnlocalizedName("topaz_adze").setRegistryName("topaz_adze").setCreativeTab(SPTabs.tabTools);
		} else if(SPCompatibilityManager.isGACLoaded()) {
			ToolMaterial RUBY = EnumHelper.addToolMaterial("material_ruby", 3, 900, 9.0F, 4.0F, 12);
			ruby_adze = new ItemAdze(RUBY, 4.0F, -2.4F).setUnlocalizedName("ruby_adze").setRegistryName("ruby_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial SAPPHIRE = EnumHelper.addToolMaterial("material_sapphire", 3, 900, 4.0F, 4.0F, 12);
			sapphire_adze = new ItemAdze(SAPPHIRE, 4.0F, -2.4F).setUnlocalizedName("sapphire_adze").setRegistryName("sapphire_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial TOPAZ = EnumHelper.addToolMaterial("material_topaz", 2, 775, 8.0F, 2.7F, 9);
			topaz_adze = new ItemAdze(TOPAZ, 5.3F, -2.4F).setUnlocalizedName("topaz_adze").setRegistryName("topaz_adze").setCreativeTab(SPTabs.tabTools);
		}
		
		if(SPCompatibilityManager.isGACLoaded()) {
			ToolMaterial AMETHYST = EnumHelper.addToolMaterial("material_amethyst", 2, 775, 8.0F, 2.3F, 9);
			amethyst_adze = new ItemAdze(AMETHYST, 5.7F, -2.4F).setUnlocalizedName("amethyst_adze").setRegistryName("amethyst_adze").setCreativeTab(SPTabs.tabTools);
			ToolMaterial QUARTZ = EnumHelper.addToolMaterial("material_quartz", 2, 400, 7.0F, 1.5F, 7);
			quartz_adze = new ItemAdze(QUARTZ, 6.5F, -2.4F).setUnlocalizedName("quartz_adze").setRegistryName("quartz_adze").setCreativeTab(SPTabs.tabTools);
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
		}
		
		if(SPCompatibilityManager.isEmeraldMatieralModsLoaded() || SPConfig.enableEmeraldMaterial) {
			registerItem(emerald_adze);
		}
		
		if(!SPCompatibilityManager.isEmeraldMatieralModsLoaded() && SPConfig.enableEmeraldMaterial) {
			registerItem(emerald_adze);
		} else if(SPCompatibilityManager.isEmeraldMatieralModsLoaded()) {
			if(!SPCompatibilityManager.isPI3xLoaded() && !SPCompatibilityManager.isFancyToolsLoaded() && SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				registerItem(emerald_adze);
			} else {
				registerItem(emerald_adze);
			}
		}
		
		if(SPCompatibilityManager.isObsidianMatieralModsLoaded() || SPConfig.enableObsidianMaterial) {
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
		}
		
		if(!SPCompatibilityManager.isEmeraldMatieralModsLoaded() && SPConfig.enableEmeraldMaterial) {
			registerRender(emerald_adze);
		} else if(SPCompatibilityManager.isEmeraldMatieralModsLoaded()) {
			if(!SPCompatibilityManager.isPI3xLoaded() && !SPCompatibilityManager.isFancyToolsLoaded() && SPCompatibilityManager.isTriGemsLoaded() && ConfigHandler.enableEmerald) {
				registerRender(emerald_adze);
			} else {
				registerRender(emerald_adze);
			}
		}
		
		if(SPCompatibilityManager.isObsidianMatieralModsLoaded() || SPConfig.enableObsidianMaterial) {
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
