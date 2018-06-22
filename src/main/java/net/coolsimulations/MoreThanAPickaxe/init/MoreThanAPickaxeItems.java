package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.MoreThanAPickaxe;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoreThanAPickaxeItems {
	
	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;
	
	public static void init() {
		
		wooden_adze = new ItemAdze(ToolMaterial.WOOD, 6.0F, -2.4F).setUnlocalizedName("wooden_adze").setRegistryName("wooden_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		stone_adze = new ItemAdze(ToolMaterial.STONE, 7.0F, -2.4F).setUnlocalizedName("stone_adze").setRegistryName("stone_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		iron_adze = new ItemAdze(ToolMaterial.IRON, 6.0F, -2.4F).setUnlocalizedName("iron_adze").setRegistryName("iron_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		gold_adze = new ItemAdze(ToolMaterial.GOLD, 6.0F, -2.4F).setUnlocalizedName("gold_adze").setRegistryName("gold_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		diamond_adze = new ItemAdze(ToolMaterial.DIAMOND, 5.0F, -2.4F).setUnlocalizedName("diamond_adze").setRegistryName("diamond_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		bronze_adze = new ItemAdze(SPItems.bronzeToolMaterial, 5.5F, -2.4F).setUnlocalizedName("bronze_adze").setRegistryName("bronze_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
		titanium_adze = new ItemAdze(SPItems.titaniumToolMaterial, 5.5F, -2.4F).setUnlocalizedName("titanium_adze").setRegistryName("titanium_adze").setCreativeTab(MoreThanAPickaxe.tabMoreThanAPickaxe);
	}
	
	public static void register()
	{
		
		GameRegistry.register(wooden_adze);
		GameRegistry.register(stone_adze);
		GameRegistry.register(iron_adze);
		GameRegistry.register(gold_adze);
		GameRegistry.register(diamond_adze);
		GameRegistry.register(bronze_adze);
		GameRegistry.register(titanium_adze);
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
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
