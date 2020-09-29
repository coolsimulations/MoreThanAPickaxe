package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.vulcanite.init.ModItems;
import net.insane96mcp.vulcanite.item.material.ModMaterial;
import net.minecraft.item.Item;

public class MoreThanAPickaxeVulcanite {
	
	public static Item vulcanite_adze;
	
	public static void init() {
		
		vulcanite_adze = new ItemAdze(ModMaterial.tool, 4.5F, -2.4F).setUnlocalizedName("vulcanite_adze").setRegistryName("vulcanite_adze").setCreativeTab(SPTabs.tabTools);
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(vulcanite_adze);
	}
	
	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(vulcanite_adze);
	}
	
	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeVulcanite.vulcanite_adze, ModItems.vulcaniteIngotItem);
	}

}
