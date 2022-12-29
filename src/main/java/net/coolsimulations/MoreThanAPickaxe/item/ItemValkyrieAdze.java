package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class ItemValkyrieAdze extends ItemAdze implements ValkyrieTool {

	public ItemValkyrieAdze(Tier material, float damage, float speed, Properties builder) {
		super(material, damage, speed, builder);
	}
	
	@Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.extendReachModifier(super.getAttributeModifiers(slot, stack), slot);
    }
}
