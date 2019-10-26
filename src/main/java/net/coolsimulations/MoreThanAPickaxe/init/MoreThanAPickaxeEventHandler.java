package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MoreThanAPickaxeEventHandler {
	
	@SubscribeEvent
	public void onplayerLogin(PlayerLoggedInEvent event)
    {
		EntityPlayerMP player = (EntityPlayerMP) event.getPlayer();
		NBTTagCompound entityData = player.getEntityData();
		
		AdvancementManager manager = player.getServer().getAdvancementManager();
		Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));
		
		boolean isDone = false;
		
		if(install !=null && player.getAdvancements().getProgress(install).hasProgress()) {
			isDone = true;
		}
		
		if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone) {
			
			entityData.setBoolean("morethanapickaxe.firstJoin", true);
		
			if(!player.world.isRemote) {
        		
        		TextComponentTranslation installInfo = new TextComponentTranslation("advancements.morethanapickaxe.install.display1");
        		installInfo.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(installInfo);
        		
        	}
		}
        
        if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck.get() == false) {
        	player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo);
        	player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo);
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
