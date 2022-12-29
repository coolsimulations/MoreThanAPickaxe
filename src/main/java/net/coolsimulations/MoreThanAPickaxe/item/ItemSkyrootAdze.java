package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.aether.item.tools.abilities.SkyrootTool;

import net.minecraft.world.item.Tier;

public class ItemSkyrootAdze extends ItemAdze implements SkyrootTool {

	public ItemSkyrootAdze(Tier material, float damage, float speed, Properties builder) {
		super(material, damage, speed, builder);
	}
}
