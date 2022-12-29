package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoreThanAPickaxeFuelHandler {

	@SubscribeEvent
	public static void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event)
	{
		ItemStack fuel = event.getItemStack();

		if(fuel.getItem() == MoreThanAPickaxeItems.wooden_adze.get()) {
			event.setBurnTime(200);
		}

		if (SPCompatibilityManager.isAetherLoaded()) {
			if(fuel.getItem() == MoreThanAPickaxeAether.skyroot_adze.get()) {
				event.setBurnTime(200);
			}
		}
	}

}
