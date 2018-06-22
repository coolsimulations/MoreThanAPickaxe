package net.coolsimulations.MoreThanAPickaxe;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MoreThanAPickaxeTab extends CreativeTabs{

	public MoreThanAPickaxeTab() {
		super("tabMoreThanAPickaxe");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(MoreThanAPickaxeItems.bronze_adze);
	}

}