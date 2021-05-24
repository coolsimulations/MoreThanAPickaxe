package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Timer;
import java.util.TimerTask;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.events.EntityAccessor;
import net.coolsimulations.SurvivalPlus.api.events.SPPlayerJoinEvent;
import net.minecraft.advancement.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.MessageType;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class MoreThanAPickaxeEventHandler {

	public static void init() {
		onplayerLogin();
	}

	public static void onplayerLogin()
	{

		SPPlayerJoinEvent.EVENT.register((player, server) -> {
			
			CompoundTag entityData = ((EntityAccessor) player).getPersistentData();

			ServerAdvancementLoader manager = server.getAdvancementLoader();
			Advancement install = manager.get(new Identifier(Reference.MOD_ID, Reference.MOD_ID + "/install"));

			boolean isDone = false;

			Timer timer = new Timer();

			if(install !=null && player.getAdvancementTracker().getProgress(install).isAnyObtained()) {
				isDone = true;
			}

			if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && !SPConfig.disableThanks) {

				entityData.putBoolean("morethanapickaxe.firstJoin", true);

				if(!player.world.isClient) {

					TranslatableText installInfo = new TranslatableText("advancements.morethanapickaxe.install.display1");
					installInfo.formatted(Formatting.GOLD);
					player.sendMessage(installInfo, MessageType.SYSTEM, Util.NIL_UUID);
				}
			}

			if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck == false) {
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo.setStyle(MoreThanAPickaxeUpdateHandler.updateInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"))), MessageType.SYSTEM, Util.NIL_UUID);
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo.setStyle(MoreThanAPickaxeUpdateHandler.updateVersionInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("sp.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"))), MessageType.SYSTEM, Util.NIL_UUID);
					}
				}, 16000);
			}
			return ActionResult.PASS;
		});
	}

}
