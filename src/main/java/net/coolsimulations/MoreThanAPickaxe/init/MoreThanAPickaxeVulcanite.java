package net.coolsimulations.MoreThanAPickaxe.init;

import insane96mcp.vulcanite.item.materials.ModMaterial;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.minecraft.item.Item;

public class MoreThanAPickaxeVulcanite {
	
	public static Item vulcanite_adze;
	
	public static void init() {
		
		vulcanite_adze = new ItemAdze(ModMaterial.TOOL_VULCANITE, 6.5F, -2.4F, new Item.Properties()).setRegistryName("vulcanite_adze");
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(vulcanite_adze);
	}
}
