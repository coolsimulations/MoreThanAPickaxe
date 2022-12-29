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
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;

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

		addToTabs();
	}

	public static void load() {
		MoreThanAPickaxeItems.initCompat();
		MoreThanAPickaxeItems.registerCompat();
	}

	private void addToTabs() {
		ItemGroupEvents.modifyEntriesEvent(new ResourceLocation(SPReference.MOD_ID, "tab_tools")).register(content -> {
			content.accept(MoreThanAPickaxeItems.wooden_adze);
			content.accept(MoreThanAPickaxeItems.stone_adze);
			content.accept(MoreThanAPickaxeItems.iron_adze);
			content.accept(MoreThanAPickaxeItems.gold_adze);
			content.accept(MoreThanAPickaxeItems.diamond_adze);
			content.accept(MoreThanAPickaxeItems.netherite_adze);
			content.accept(MoreThanAPickaxeItems.bronze_adze);
			content.accept(MoreThanAPickaxeItems.titanium_adze);
			if(!SPCompatibilityManager.isGCLoaded())
				content.accept(MoreThanAPickaxeItems.steel_adze);
			if(SPCompatibilityManager.isEmeraldMaterialModsLoaded())
				content.accept(MoreThanAPickaxeItems.emerald_adze);
			if(SPCompatibilityManager.isObsidianMaterialModsLoaded())
				content.accept(MoreThanAPickaxeItems.obsidian_adze);
			if(SPCompatibilityManager.isRobinsRubyLoaded() || SPCompatibilityManager.isEasyEmeraldLoaded() || SPCompatibilityManager.isTechRebornLoaded() || SPCompatibilityManager.isEnrichedLoaded())
				content.accept(MoreThanAPickaxeItems.ruby_adze);
			if(SPCompatibilityManager.isMoreGemsLoaded() || SPCompatibilityManager.isTechRebornLoaded() || SPCompatibilityManager.isEnrichedLoaded())
				content.accept(MoreThanAPickaxeItems.sapphire_adze);
			if(SPCompatibilityManager.isEasyEmeraldLoaded() || SPCompatibilityManager.isRobinsAmethystLoaded())
				content.accept(MoreThanAPickaxeItems.amethyst_adze);
			if(SPCompatibilityManager.isEnrichedLoaded())
				content.accept(MoreThanAPickaxeItems.tanzanite_adze);
			if(SPCompatibilityManager.isMoreGemsLoaded()) {
				content.accept(MoreThanAPickaxeItems.topaz_adze);
				content.accept(MoreThanAPickaxeItems.citrine_adze);
				content.accept(MoreThanAPickaxeItems.tourmaline_adze);
				content.accept(MoreThanAPickaxeItems.alexandrite_adze);
				content.accept(MoreThanAPickaxeItems.corundum_adze);
				content.accept(MoreThanAPickaxeItems.carbonado_adze);
				content.accept(MoreThanAPickaxeItems.kunzite_adze);
				content.accept(MoreThanAPickaxeItems.moissanite_adze);
				content.accept(MoreThanAPickaxeItems.spinel_adze);
			}
			if(SPCompatibilityManager.isAetherRebornLoaded()) {
				content.accept(MoreThanAPickaxeItems.gravitite_adze);
				content.accept(MoreThanAPickaxeItems.zanite_adze);
				content.accept(MoreThanAPickaxeItems.valkyrie_adze);
			}
			if(SPCompatibilityManager.isAdabraniumLoaded()) {
				content.accept(MoreThanAPickaxeItems.adamantium_adze);
				content.accept(MoreThanAPickaxeItems.vibranium_adze);
				content.accept(MoreThanAPickaxeItems.nether_brick_adze);
			}
			if(SPCompatibilityManager.isGobberLoaded()) {
				content.accept(MoreThanAPickaxeItems.gobber_adze);
				content.accept(MoreThanAPickaxeItems.nether_adze);
				content.accept(MoreThanAPickaxeItems.end_adze);
			}
			if(SPCompatibilityManager.isLuxoreLoaded())
				content.accept(MoreThanAPickaxeItems.luxore_adze);
			if(SPCompatibilityManager.isTechRebornLoaded())
				content.accept(MoreThanAPickaxeItems.peridot_adze);
		});
	}
}
