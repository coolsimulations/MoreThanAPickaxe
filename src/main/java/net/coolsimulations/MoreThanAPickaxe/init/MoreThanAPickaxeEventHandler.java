package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Timer;
import java.util.TimerTask;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.events.EntityAccessor;
import net.coolsimulations.SurvivalPlus.api.events.SPPlayerJoinEvent;
import net.minecraft.advancement.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

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
					installInfo.getStyle().setColor(Formatting.GOLD);
					player.sendMessage(installInfo);

				}
			}

			if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck == false) {
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo);
						player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo);
					}
				}, 16000);
			}
			return ActionResult.PASS;
		});
	}

}
