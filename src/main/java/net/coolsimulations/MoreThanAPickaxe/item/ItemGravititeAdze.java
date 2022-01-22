package net.coolsimulations.MoreThanAPickaxe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.id.aether.items.tools.base_tools.GravititeTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class ItemGravititeAdze extends ItemAdze {

	public ItemGravititeAdze(Tier material, float damage, float speed, FabricItemSettings builder) {
		super(material, damage, speed, builder.rarity(Rarity.RARE), true);
	}
	
	@Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        return GravititeTool.flipEntity(stack, player, entity, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return GravititeTool.tryFloatBlock(context, super.useOn(context));
    }

}
