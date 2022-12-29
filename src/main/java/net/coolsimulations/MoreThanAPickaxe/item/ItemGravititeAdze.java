package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.aether.item.tools.abilities.GravititeTool;

import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemGravititeAdze extends ItemAdze implements GravititeTool {

	public ItemGravititeAdze(Tier material, float damage, float speed, Properties builder) {
		super(material, damage, speed, builder);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		ItemStack itemStack = context.getItemInHand();
		BlockState blockState = level.getBlockState(blockPos);
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		return !this.floatBlock(level, blockPos, itemStack, blockState, player, hand)
				? super.useOn(context)
				: InteractionResult.sidedSuccess(level.isClientSide());
	}
	
	@Override
	public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {

		checkAdvancement(entity);

		if (entity instanceof ServerPlayer) {
			ServerAdvancementManager manager = entity.getServer().getAdvancements();

			Advancement gravitite = manager.getAdvancement(new ResourceLocation(SPCompatibilityManager.AETHER_MODID, "gravitite_tools"));

			AdvancementProgress advancementprogress = ((ServerPlayer) entity).getAdvancements().getOrStartProgress(gravitite);
			if (!advancementprogress.isDone()) {
				for(String s : advancementprogress.getRemainingCriteria()) {
					((ServerPlayer) entity).getAdvancements().award(gravitite, s);
				}
			}
		}
	}
}
