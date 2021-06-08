package net.coolsimulations.MoreThanAPickaxe.init;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public class MoreThanAPickaxeFuelHandler {
	
	public static void init() {
		
		FuelRegistry.INSTANCE.add(MoreThanAPickaxeItems.wooden_adze, 200);
	}

}
