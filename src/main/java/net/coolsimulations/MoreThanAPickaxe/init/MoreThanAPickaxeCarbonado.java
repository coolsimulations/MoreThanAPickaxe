package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.carbonado.init.ModItems;
import net.insane96mcp.carbonado.item.material.ModMaterial;
import net.minecraft.item.Item;

public class MoreThanAPickaxeCarbonado {
	
	public static Item carbonado_adze;
	
	public static void init() {
		
		carbonado_adze = new ItemAdze(ModMaterial.Tools, 6.0F, -2.4F).setUnlocalizedName("carbonado_adze").setRegistryName("carbonado_adze").setCreativeTab(SPTabs.tabTools);
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(carbonado_adze);
	}
	
	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(carbonado_adze);
	}
	
	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeCarbonado.carbonado_adze, ModItems.carbonadoItem);
	}

}
