package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Timer;
import java.util.TimerTask;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoreThanAPickaxeEventHandler {

	@SubscribeEvent
	public void onplayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		ServerPlayer player = (ServerPlayer) event.getPlayer();
		CompoundTag entityData = player.getPersistentData();

		ServerAdvancementManager manager = player.getServer().getAdvancements();
		Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));

		boolean isDone = false;

		Timer timer = new Timer();

		if(install !=null && player.getAdvancements().getOrStartProgress(install).hasProgress()) {
			isDone = true;
		}

		if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && SPConfig.disableThanks.get()) {

			entityData.putBoolean("morethanapickaxe.firstJoin", true);

			if(!player.level.isClientSide) {

				TranslatableComponent installInfo = new TranslatableComponent("advancements.morethanapickaxe.install.display1");
				installInfo.withStyle(ChatFormatting.GOLD);
				player.sendMessage(installInfo, ChatType.SYSTEM, Util.NIL_UUID);

			}
		}

		if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck.get() == false) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));}), ChatType.SYSTEM, Util.NIL_UUID);
					player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));}), ChatType.SYSTEM, Util.NIL_UUID);
				}
			}, 16000);
		}
	}

	/**@SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();

		if(SPCompatibilityManager.isGCLoaded()) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "steel_adze"));	
		}
    }**/

}
