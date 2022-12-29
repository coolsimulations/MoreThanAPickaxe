package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.aether.item.tools.abilities.HolystoneTool;

import net.minecraft.world.item.Tier;

public class ItemHolystoneAdze extends ItemAdze implements HolystoneTool {

	public ItemHolystoneAdze(Tier material, float damage, float speed, Properties builder) {
		super(material, damage, speed, builder);
	}
}
