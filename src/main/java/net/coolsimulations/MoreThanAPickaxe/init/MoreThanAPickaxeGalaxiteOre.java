package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.galaxite.init.ModItems;
import net.insane96mcp.galaxite.item.material.ModMaterial;
import net.minecraft.item.Item;

public class MoreThanAPickaxeGalaxiteOre {
	
	public static Item galaxite_adze;
	
	public static void init() {
		
		galaxite_adze = new ItemAdze(ModMaterial.Tool, 6.5F, -2.4F).setUnlocalizedName("galaxite_adze").setRegistryName("galaxite_adze").setCreativeTab(SPTabs.tabTools);
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(galaxite_adze);
	}
	
	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(galaxite_adze);
	}
	
	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeGalaxiteOre.galaxite_adze, ModItems.galaxite);
	}

}
