package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class MoreThanAPickaxeEventHandler {
	
	@SubscribeEvent
	public void onplayerLogin(PlayerLoggedInEvent event)
    {
		EntityPlayer player = (EntityPlayer) event.getPlayer();
		NBTTagCompound entityData = player.getEntityData();
		
		if(!entityData.getBoolean("morethanapickaxe.firstJoin")) {
			
			entityData.setBoolean("morethanapickaxe.firstJoin", true);
		
			if(!player.world.isRemote) {
        		
        		TextComponentTranslation installInfo = new TextComponentTranslation("advancements.morethanapickaxe.install.display1");
        		installInfo.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(installInfo);
        		
        	}
		}
        
        if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck.get() == false) {
        	player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo);
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
