package net.coolsimulations.MoreThanAPickaxe;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeEventHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeFuelHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeUpdateHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeVillagerTrade;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPReference;
import net.coolsimulations.SurvivalPlus.api.events.SPLastLoadEvent;
import net.fabricmc.api.ModInitializer;

public class MoreThanAPickaxe implements ModInitializer {
	
	private static MoreThanAPickaxe instance;

	public static MoreThanAPickaxe getInstance()
	{
		return instance;
	}

	@Override
	public void onInitialize() {
		
		MoreThanAPickaxeEventHandler.init();
		SPReference.MOD_ADDON_NAMES.add("morethanapickaxe.name");
		MoreThanAPickaxeUpdateHandler.init();
		MoreThanAPickaxeItems.init();
		MoreThanAPickaxeItems.register();
		
		if(!SPCompatibilityManager.isFabricLoader12Loaded()) {
			load();
		} else {
			SPLastLoadEvent.EVENT.register(() -> {
				load();
			});
		}
		
		MoreThanAPickaxeFuelHandler.init();
		MoreThanAPickaxeVillagerTrade.init();
	}
	
	public static void load() {
		MoreThanAPickaxeItems.initCompat();
		MoreThanAPickaxeItems.registerCompat();
	}

}
