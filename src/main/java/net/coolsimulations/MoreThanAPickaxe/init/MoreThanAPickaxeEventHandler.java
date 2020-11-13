package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Timer;
import java.util.TimerTask;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoreThanAPickaxeEventHandler {

	@SubscribeEvent
	public void onplayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		CompoundNBT entityData = player.getPersistentData();

		AdvancementManager manager = player.getServer().getAdvancementManager();
		Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));

		boolean isDone = false;

		Timer timer = new Timer();

		if(install !=null && player.getAdvancements().getProgress(install).hasProgress()) {
			isDone = true;
		}

		if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && SPConfig.disableThanks.get()) {

			entityData.putBoolean("morethanapickaxe.firstJoin", true);

			if(!player.world.isRemote) {

				TranslationTextComponent installInfo = new TranslationTextComponent("advancements.morethanapickaxe.install.display1");
				installInfo.mergeStyle(TextFormatting.GOLD);
				player.func_241151_a_(installInfo, ChatType.SYSTEM, Util.DUMMY_UUID);

			}
		}

		if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck.get() == false) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					player.func_241151_a_(MoreThanAPickaxeUpdateHandler.updateInfo.modifyStyle((style) -> {return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("sp.update.display2"))).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));}), ChatType.SYSTEM, Util.DUMMY_UUID);
					player.func_241151_a_(MoreThanAPickaxeUpdateHandler.updateVersionInfo.modifyStyle((style) -> {return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("sp.update.display2"))).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));}), ChatType.SYSTEM, Util.DUMMY_UUID);
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
