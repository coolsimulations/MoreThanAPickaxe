package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.aether.item.tools.abilities.ZaniteTool;

import net.minecraft.world.item.Tier;

public class ItemZaniteAdze extends ItemAdze implements ZaniteTool {

	public ItemZaniteAdze(Tier material, float damage, float speed, Properties builder) {
		super(material, damage, speed, builder);
	}
}
