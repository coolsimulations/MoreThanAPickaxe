package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.AdzeItemTier;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.item.SPItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.registries.ForgeRegistries;

public class MoreThanAPickaxeItems {

	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;

	public static Item steel_adze;
	//public static Item desh_adze;
	//public static Item titanium_adze_gc;

	//public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);

	//public static final Tag<Item> STEEL_INGOT = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/steel"));

	public static void init() {

		wooden_adze = new ItemAdze(ItemTier.WOOD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("wooden_adze");
		stone_adze = new ItemAdze(ItemTier.STONE, 7.0F, -2.40F, new Item.Properties()).setRegistryName("stone_adze");
		iron_adze = new ItemAdze(ItemTier.IRON, 6.0F, -2.40F, new Item.Properties()).setRegistryName("iron_adze");
		gold_adze = new ItemAdze(ItemTier.GOLD, 6.0F, -2.40F, new Item.Properties()).setRegistryName("gold_adze");
		diamond_adze = new ItemAdze(ItemTier.DIAMOND, 5.0F, -2.40F, new Item.Properties()).setRegistryName("diamond_adze");
		bronze_adze = new ItemAdze(SPItemTier.bronzeToolMaterial, 5.5F, -2.40F, new Item.Properties()).setRegistryName("bronze_adze");
		titanium_adze = new ItemAdze(SPItemTier.titaniumToolMaterial, 5.5F, -2.40F, new Item.Properties()).setRegistryName("titanium_adze");


		if(!SPCompatibilityManager.isGCLoaded()) {

			steel_adze = new ItemAdze(AdzeItemTier.steelToolMaterial, 5.5F, -2.40F, new Item.Properties()).setRegistryName("steel_adze");
		}

		if(SPCompatibilityManager.isGCLoaded()) {

			//steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPTabs.tabTools);

			if(SPCompatibilityManager.isGCPLoaded()) {

				//desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F).setUnlocalizedName("desh_adze").setRegistryName("desh_adze").setCreativeTab(SPTabs.tabTools);
				//titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F).setUnlocalizedName("titanium_adze_gc").setRegistryName("titanium_adze_gc").setCreativeTab(SPTabs.tabTools);
			}
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
	}

	public static void registerItem(Item item) {

		ForgeRegistries.ITEMS.register(item);
	}
}
