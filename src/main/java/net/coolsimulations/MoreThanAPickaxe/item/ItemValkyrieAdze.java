package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.id.aether.items.utils.AetherRarity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;

public class ItemValkyrieAdze extends ItemAdze {
	
    private static final UUID REACH_MODIFIER_ID = UUID.fromString("E70BE4E9-B163-4CC1-8848-F860B0BC02FC");
    private static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("7CB7BC58-D3BA-40AE-BC95-F8C38fE144FF");

	public ItemValkyrieAdze(Tier material, float damage, float speed, FabricItemSettings builder) {
		super(material, damage, speed, builder.rarity(AetherRarity.AETHER_LOOT), true);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
		attributeBuilder.putAll(this.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
		attributeBuilder.put(ReachEntityAttributes.REACH, new AttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", 6.0F, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(ReachEntityAttributes.ATTACK_RANGE, new AttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", 4.0F, AttributeModifier.Operation.ADDITION));
		this.attribute = attributeBuilder.build();
	}

}
