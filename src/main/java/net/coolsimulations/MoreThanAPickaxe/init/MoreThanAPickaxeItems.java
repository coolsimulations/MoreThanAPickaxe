package net.coolsimulations.MoreThanAPickaxe.init;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.coolsimulations.MoreThanAPickaxe.MoreThanAPickaxe;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class MoreThanAPickaxeItems {
	
	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;
	
	public static Item steel_adze;
	public static Item desh_adze;
	public static Item titanium_adze_gc;
	
    public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);
	
	public static void init() {
		
		wooden_adze = new ItemAdze(ToolMaterial.WOOD, 6.0F, -2.4F).setUnlocalizedName("wooden_adze").setRegistryName("wooden_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		stone_adze = new ItemAdze(ToolMaterial.STONE, 7.0F, -2.4F).setUnlocalizedName("stone_adze").setRegistryName("stone_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		iron_adze = new ItemAdze(ToolMaterial.IRON, 6.0F, -2.4F).setUnlocalizedName("iron_adze").setRegistryName("iron_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		gold_adze = new ItemAdze(ToolMaterial.GOLD, 6.0F, -2.4F).setUnlocalizedName("gold_adze").setRegistryName("gold_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		diamond_adze = new ItemAdze(ToolMaterial.DIAMOND, 5.0F, -2.4F).setUnlocalizedName("diamond_adze").setRegistryName("diamond_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		bronze_adze = new ItemAdze(SPItems.bronzeToolMaterial, 5.5F, -2.4F).setUnlocalizedName("bronze_adze").setRegistryName("bronze_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		titanium_adze = new ItemAdze(SPItems.titaniumToolMaterial, 5.5F, -2.4F).setUnlocalizedName("titanium_adze").setRegistryName("titanium_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
		
		if(OreDictionary.getOres("ingotSteel").size() > 0) {
			
			if(!SPCompatibilityManager.isGCLoaded()) {
				
				steel_adze = new ItemAdze(steelToolMaterial, 5.5F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
			}
		}
		
		if(SPCompatibilityManager.isGCLoaded()) {
			
			steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F).setUnlocalizedName("steel_adze").setRegistryName("steel_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
			
			if(SPCompatibilityManager.isGCPLoaded()) {
				
				desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F).setUnlocalizedName("desh_adze").setRegistryName("desh_adze").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
				titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F).setUnlocalizedName("titanium_adze_gc").setRegistryName("titanium_adze_gc").setCreativeTab(SPItems.bronze_pickaxe.getCreativeTab());
			}
		}
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
		
		if(OreDictionary.getOres("ingotSteel").size() > 0) {
			
			if(!SPCompatibilityManager.isGCLoaded()) {
				
				GameRegistry.register(steel_adze);
			}
		}
		
		if(SPCompatibilityManager.isGCLoaded()) {
			
			GameRegistry.register(steel_adze);
			
			if(SPCompatibilityManager.isGCPLoaded()) {
				
				GameRegistry.register(desh_adze);
				GameRegistry.register(titanium_adze_gc);
			}
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
		
		if(OreDictionary.getOres("ingotSteel").size() > 0) {
			
			if(!SPCompatibilityManager.isGCLoaded()) {
				
				registerRender(steel_adze);
			}
		}
		
		if(SPCompatibilityManager.isGCLoaded()) {
			
			registerRender(steel_adze);
			
			if(SPCompatibilityManager.isGCPLoaded()) {
				
				registerRender(desh_adze);
				registerRender(titanium_adze_gc);
			}
		}
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
