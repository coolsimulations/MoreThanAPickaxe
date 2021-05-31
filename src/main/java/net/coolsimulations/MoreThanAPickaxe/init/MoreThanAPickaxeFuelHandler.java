package net.coolsimulations.MoreThanAPickaxe.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class MoreThanAPickaxeFuelHandler implements IFuelHandler{
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		
		if(fuel.getItem() == MoreThanAPickaxeItems.wooden_adze) {
			return 200;
		}
		
		return 0;
	}

}
