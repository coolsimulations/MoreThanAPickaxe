package net.coolsimulations.MoreThanAPickaxe.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MoreThanAPickaxeTab extends CreativeTabs{

	public MoreThanAPickaxeTab() {
		super("tabMoreThanAPickaxe");
	}

	@Override
	public Item getTabIconItem() {
		return MoreThanAPickaxeItems.bronze_adze;
	}

}