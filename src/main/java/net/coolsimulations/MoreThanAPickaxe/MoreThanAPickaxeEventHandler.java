package net.coolsimulations.MoreThanAPickaxe;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class MoreThanAPickaxeEventHandler {
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
    {
		MoreThanAPickaxeItems.registerItems(event.getRegistry());
    }

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event)
    {
        for(Item item : MoreThanAPickaxe.ITEMS) {
        	MoreThanAPickaxeItems.registerRenders();
        }
    }
	
	@SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
		
		if(SPCompatibilityManager.isGCLoaded()) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "steel_adze"));	
		}
    }

}
