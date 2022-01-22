package net.coolsimulations.MoreThanAPickaxe.init;

import at.xander.fancytools.FancyToolsMod;
import at.xander.fancytools.config.FancyToolsConfig;
import net.coolsimulations.MoreThanAPickaxe.item.AdzeItemTier;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemEndAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGobberAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.item.SPItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.ForgeRegistries;
import xmods.ota.api.ToolMaterialList;

public class MoreThanAPickaxeItems {

	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item netherite_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;

	public static Item steel_adze;
	//public static Item desh_adze;
	//public static Item titanium_adze_gc;

	//public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);

	//public static final Tag<Item> STEEL_INGOT = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/steel"));
	
	public static Item emerald_adze;
	public static Item obsidian_adze;

	public static Item amethyst_adze;
	public static Item ruby_adze;
	public static Item sapphire_adze;
	public static Item topaz_adze;
	public static Item quartz_adze;
	
	public static Item gobber_adze;
	public static Item nether_adze;
	public static Item end_adze;

	public static void init() {

		wooden_adze = new ItemAdze(Tiers.WOOD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("wooden_adze");
		stone_adze = new ItemAdze(Tiers.STONE, 8.0F, -2.40F, new Item.Properties()).setRegistryName("stone_adze");
		iron_adze = new ItemAdze(Tiers.IRON, 8.0F, -2.40F, new Item.Properties()).setRegistryName("iron_adze");
		gold_adze = new ItemAdze(Tiers.GOLD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("gold_adze");
		diamond_adze = new ItemAdze(Tiers.DIAMOND, 8.0F, -2.40F, new Item.Properties()).setRegistryName("diamond_adze");
		netherite_adze = new ItemAdze(Tiers.NETHERITE, 9.0F, -2.40F, new Item.Properties().fireResistant()).setRegistryName("netherite_adze");
		bronze_adze = new ItemAdze(SPItemTier.bronzeToolMaterial, 7.0F, -2.40F, new Item.Properties()).setRegistryName("bronze_adze");
		titanium_adze = new ItemAdze(SPItemTier.titaniumToolMaterial, 8.0F, -2.40F, new Item.Properties()).setRegistryName("titanium_adze");


		if(!SPCompatibilityManager.isGCLoaded()) {

			steel_adze = new ItemAdze(AdzeItemTier.steelToolMaterial, 8.0F, -2.40F, new Item.Properties()).setRegistryName("steel_adze");
		}

		if(SPCompatibilityManager.isGCLoaded()) {

			//steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);

			if(SPCompatibilityManager.isGCPLoaded()) {

				//desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F).setUnlocalizedName("desh_adze").setRegistryName("desh_adze").setCreativeTab(SPTabs.tabTools);
				//titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F).setUnlocalizedName("titanium_adze_gc").setRegistryName("titanium_adze_gc").setCreativeTab(SPTabs.tabTools);
			}
		}
		
		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				FancyToolsConfig cfg = FancyToolsMod.getInstance().getConfig();
				emerald_adze = new ItemAdze(cfg.getEmerald(), 8.0F, -2.4F, new Item.Properties(), true).setRegistryName("emerald_adze");
			} else if(SPCompatibilityManager.isSimpleEmeraldLoaded()) {
				emerald_adze = new ItemAdze(com.technovision.emeraldmod.util.enums.ModItemTier.EMERALD, 8.0F, -2.4F, new Item.Properties(), true).setRegistryName("emerald_adze");
			} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
				emerald_adze = new ItemAdze(com.kwpugh.easy_emerald.init.ItemInit.EMERALD_TOOL_MATERIAL, 7.0F, -2.4F, new Item.Properties(), true).setRegistryName("emerald_adze");
			} else if (SPCompatibilityManager.isEmeraldEquipmentLoaded()) {
				emerald_adze = new ItemAdze(com.exline.emeraldequipment.items.ModItemTier.EMERALD, 6.5F, -2.4F, new Item.Properties(), true).setRegistryName("emerald_adze");
			}
		}
		
		if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				FancyToolsConfig cfg = FancyToolsMod.getInstance().getConfig();
				obsidian_adze = new ItemAdze(cfg.getObsidian(), 8.0F, -2.4F, new Item.Properties(), true).setRegistryName("obsidian_adze");
			} else if(SPCompatibilityManager.isOAATLoaded()) {
				obsidian_adze = new ItemAdze(ToolMaterialList.obsidian, 8.0F, -2.4F, new Item.Properties(), true).setRegistryName("obsidian_adze");
			} else if (SPCompatibilityManager.isObsidianEquipmentLoaded()) {
				obsidian_adze = new ItemAdze(com.exline.obsidianequipment.items.ModItemTier.OBSIDIAN, 8.4F, -2.4F, new Item.Properties(), true).setRegistryName("obsidian_adze");
			} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
				obsidian_adze = new ItemAdze(com.kwpugh.easy_emerald.init.ItemInit.OBSIDIAN_TOOL_MATERIAL, 7.0F, -2.4F, new Item.Properties(), true).setRegistryName("obsidian_adze");
			}
		}
		
		if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
			ruby_adze = new ItemAdze(com.kwpugh.easy_emerald.init.ItemInit.RUBY_TOOL_MATERIAL, 8.0F, -2.4F, new Item.Properties(), true).setRegistryName("ruby_adze");
		}
		
		if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
			amethyst_adze = new ItemAdze(com.kwpugh.easy_emerald.init.ItemInit.AMETHYST_TOOL_MATERIAL, 9.0F, -2.4F, new Item.Properties(), true).setRegistryName("amethyst_adze");
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			gobber_adze = new ItemGobberAdze(com.kwpugh.gobber2.init.ItemInit.GOBBER_TOOL_MATERIAL, 10.0F, -2.0F, new Item.Properties()).setRegistryName("gobber_adze");
			nether_adze = new ItemGobberAdze(com.kwpugh.gobber2.init.ItemInit.NETHER_GOBBER_TOOL_MATERIAL, 12.0F, -1.8F, new Item.Properties()).setRegistryName("nether_adze");
			end_adze = new ItemEndAdze(com.kwpugh.gobber2.init.ItemInit.END_GOBBER_TOOL_MATERIAL, 15.0F, -1.6F, new Item.Properties()).setRegistryName("end_adze");
		}

	}

	public static void register()
	{

		registerItem(wooden_adze);
		registerItem(stone_adze);
		registerItem(iron_adze);
		registerItem(gold_adze);
		registerItem(diamond_adze);
		registerItem(netherite_adze);
		registerItem(bronze_adze);
		registerItem(titanium_adze);


		if(!SPCompatibilityManager.isGCLoaded()) {

			registerItem(steel_adze);
		}

		if(SPCompatibilityManager.isGCLoaded()) {

			//registerItem(steel_adze);

			if(SPCompatibilityManager.isGCPLoaded()) {

				//registerItem(desh_adze);
				//registerItem(titanium_adze_gc);
			}
		}
		
		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded())
			registerItem(emerald_adze);
		
		if(SPCompatibilityManager.isObsidianMaterialModsLoaded())
			registerItem(obsidian_adze);
		
		if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
			registerItem(ruby_adze);
			registerItem(amethyst_adze);
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			registerItem(gobber_adze);
			registerItem(nether_adze);
			registerItem(end_adze);
		}
		
	}

	public static void registerItem(Item item) {

		ForgeRegistries.ITEMS.register(item);
	}
}
