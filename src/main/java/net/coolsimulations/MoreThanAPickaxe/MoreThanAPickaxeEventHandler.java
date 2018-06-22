package net.coolsimulations.MoreThanAPickaxe;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

}
