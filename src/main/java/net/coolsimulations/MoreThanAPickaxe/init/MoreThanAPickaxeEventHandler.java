package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoreThanAPickaxeEventHandler {
	
	@SubscribeEvent
	public void onplayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
		PlayerEntity player = (PlayerEntity) event.getPlayer();
		CompoundNBT entityData = player.getPersistentData();
		
		if(!entityData.getBoolean("morethanapickaxe.firstJoin")) {
			
			entityData.putBoolean("morethanapickaxe.firstJoin", true);
		
			if(!player.world.isRemote) {
        		
        		TranslationTextComponent installInfo = new TranslationTextComponent("advancements.morethanapickaxe.install.display1");
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
