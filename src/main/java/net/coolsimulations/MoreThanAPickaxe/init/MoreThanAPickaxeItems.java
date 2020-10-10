package net.coolsimulations.MoreThanAPickaxe.init;

import com.coliwogg.gemsandcrystals.init.ItemInit.ModItemTier;

import net.coolsimulations.MoreThanAPickaxe.item.AdzeItemTier;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.item.SPItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
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

	public static void init() {

		wooden_adze = new ItemAdze(ItemTier.WOOD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("wooden_adze");
		stone_adze = new ItemAdze(ItemTier.STONE, 8.0F, -2.40F, new Item.Properties()).setRegistryName("stone_adze");
		iron_adze = new ItemAdze(ItemTier.IRON, 8.0F, -2.40F, new Item.Properties()).setRegistryName("iron_adze");
		gold_adze = new ItemAdze(ItemTier.GOLD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("gold_adze");
		diamond_adze = new ItemAdze(ItemTier.DIAMOND, 8.0F, -2.40F, new Item.Properties()).setRegistryName("diamond_adze");
		netherite_adze = new ItemAdze(ItemTier.NETHERITE, 9.0F, -2.40F, new Item.Properties().func_234689_a_()).setRegistryName("netherite_adze");
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
			if(SPCompatibilityManager.isSimpleEmeraldLoaded()) {
				emerald_adze = new ItemAdze(com.technovision.emeraldmod.util.enums.ModItemTier.EMERALD, 8.0F, -2.4F, new Item.Properties()).setRegistryName("emerald_adze");
			} else if(SPCompatibilityManager.isGACLoaded()) {
				emerald_adze = new ItemAdze(ModItemTier.EMERALD, 5.5F, -2.4F, new Item.Properties()).setRegistryName("emerald_adze");
			} else if (SPCompatibilityManager.isStandardMaterialsLoaded()) {
				emerald_adze = new ItemAdze(com.baconbombing.standardmaterials.materials.ModItemTier.EMERALD, 5.0F, -2.4F, new Item.Properties()).setRegistryName("emerald_adze");
			} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
				emerald_adze = new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialList.EMERALD, 7.0F, -2.4F, new Item.Properties()).setRegistryName("emerald_adze");
			}
		}
		
		if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			if(SPCompatibilityManager.isOAATLoaded()) {
				obsidian_adze = new ItemAdze(ToolMaterialList.obsidian, 8.0F, -2.4F, new Item.Properties()).setRegistryName("obsidian_adze");
			} else if (SPCompatibilityManager.isStandardMaterialsLoaded()) {
				obsidian_adze = new ItemAdze(com.baconbombing.standardmaterials.materials.ModItemTier.OBSIDIAN, 5.0F, -2.4F, new Item.Properties()).setRegistryName("obsidian_adze");
			}
		}
		
		if(SPCompatibilityManager.isGACLoaded()) {
			ruby_adze = new ItemAdze(ModItemTier.RUBY, 5.0F, -2.4F, new Item.Properties()).setRegistryName("ruby_adze");
			sapphire_adze = new ItemAdze(ModItemTier.SAPPHIRE, 5.0F, -2.4F, new Item.Properties()).setRegistryName("sapphire_adze");
			topaz_adze = new ItemAdze(ModItemTier.TOPAZ, 5.3F, -2.4F, new Item.Properties()).setRegistryName("topaz_adze");
			amethyst_adze = new ItemAdze(ModItemTier.AMETHYST, 5.7F, -2.4F, new Item.Properties()).setRegistryName("amethyst_adze");
			quartz_adze = new ItemAdze(ModItemTier.QUARTZ, 6.5F, -2.4F, new Item.Properties()).setRegistryName("quartz_adze");			
		} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
			ruby_adze = new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialList.RUBY, 8.0F, -2.4F, new Item.Properties()).setRegistryName("ruby_adze");
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
		
		if(SPCompatibilityManager.isGACLoaded()) {
			registerItem(amethyst_adze);
			registerItem(sapphire_adze);
			registerItem(topaz_adze);
			registerItem(quartz_adze);
		}
		
		if(SPCompatibilityManager.isGACLoaded() || SPCompatibilityManager.isEasyEmeraldLoaded())
			registerItem(ruby_adze);
		
	}

	public static void registerItem(Item item) {

		ForgeRegistries.ITEMS.register(item);
	}
}
