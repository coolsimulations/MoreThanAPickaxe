package net.coolsimulations.MoreThanAPickaxe.init;

import insane96mcp.carbonado.item.material.ModMaterial;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.minecraft.item.Item;

public class MoreThanAPickaxeCarbonado {
	
	public static Item carbonado_adze;
	
	public static void init() {
		
		carbonado_adze = new ItemAdze(ModMaterial.TOOLS_CARBONADO, 5.0F, -2.4F, new Item.Properties()).setRegistryName("carbonado_adze");
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(carbonado_adze);
	}
}
