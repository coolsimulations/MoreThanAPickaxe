package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPAchievements;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MoreThanAPickaxeEventHandler {
	
	@SubscribeEvent
	public void onplayerLogin(PlayerLoggedInEvent event)
    {
		EntityPlayer player = (EntityPlayer) event.player;
		
		if (!player.hasAchievement(MoreThanAPickaxeAchievements.achievementInstall))
        {
            player.addStat(MoreThanAPickaxeAchievements.achievementInstall);
            
            if(!player.world.isRemote) {
            	
            	TextComponentTranslation installInfo = new TextComponentTranslation("achievement.morethanapickaxe.install.display1");
            	installInfo.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(installInfo);

            }
        }
        
        if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck == false) {
        	player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo);
        }
    }
	
	@SubscribeEvent
	public void onCrafted(ItemCraftedEvent event)
	{
		EntityPlayer player = (EntityPlayer) event.player;
		Item item = event.crafting.getItem();
		
		if(item instanceof ItemAdze) {
			
			if(!player.hasAchievement(MoreThanAPickaxeAchievements.achievementAdze)) {
				player.addStat(MoreThanAPickaxeAchievements.achievementAdze);
			}
		}
	}


}
