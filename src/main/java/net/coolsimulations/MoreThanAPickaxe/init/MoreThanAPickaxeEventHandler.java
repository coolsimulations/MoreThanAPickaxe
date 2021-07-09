package net.coolsimulations.MoreThanAPickaxe.init;

import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.events.EntityAccessor;
import net.coolsimulations.SurvivalPlus.api.events.SPPlayerJoinEvent;
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
import net.minecraft.world.InteractionResult;

public class MoreThanAPickaxeEventHandler {

	public static void init() {
		onplayerLogin();
	}

	public static void onplayerLogin()
	{

		SPPlayerJoinEvent.EVENT.register((player, server) -> {
			
			CompoundTag entityData = ((EntityAccessor) player).getPersistentData();

			ServerAdvancementManager manager = server.getAdvancements();
			Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));

			boolean isDone = false;

			Timer timer = new Timer();

			if(install !=null && player.getAdvancements().getOrStartProgress(install).hasProgress()) {
				isDone = true;
			}

			if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && !SPConfig.disableThanks) {

				entityData.putBoolean("morethanapickaxe.firstJoin", true);

				if(!player.level.isClientSide) {

					TranslatableComponent installInfo = new TranslatableComponent("advancements.morethanapickaxe.install.display1");
					installInfo.withStyle(ChatFormatting.GOLD);
					player.sendMessage(installInfo, ChatType.SYSTEM, Util.NIL_UUID);
				}
			}

			if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck == false) {
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo.setStyle(MoreThanAPickaxeUpdateHandler.updateInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"))), ChatType.SYSTEM, Util.NIL_UUID);
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo.setStyle(MoreThanAPickaxeUpdateHandler.updateVersionInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"))), ChatType.SYSTEM, Util.NIL_UUID);
					}
				}, 16000);
			}
			return InteractionResult.PASS;
		});
	}

}
