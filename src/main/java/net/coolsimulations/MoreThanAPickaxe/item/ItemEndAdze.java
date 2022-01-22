package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.kwpugh.gobber2.config.GobberConfigBuilder;
import com.kwpugh.gobber2.util.EnableUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class ItemEndAdze extends ItemGobberAdze implements Wearable {

	public ItemEndAdze(Tier material, float damage, float speed, Item.Properties builder) {
		super(material, damage, speed, builder, true);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		ItemStack stack = context.getItemInHand();

		if(!EnableUtil.isEnabled(stack)) {
			return super.useOn(context);
		} else {
			if(!context.getLevel().isClientSide()) {

				Level world = context.getLevel();
				Player player = context.getPlayer();
				BlockPos blockpos = context.getClickedPos();
				BlockState iblockstate = world.getBlockState(blockpos);
				Block block = iblockstate.getBlock();

				BlockPos torchPos;
				boolean isWallTorch = false;
				switch(context.getClickedFace()) {
				case DOWN:
					return InteractionResult.FAIL;
				case UP:
					torchPos = new BlockPos(blockpos.getX(), blockpos.getY() +1, blockpos.getZ());
					break;
				case NORTH:
					torchPos = new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ() -1);
					isWallTorch = true;
					break;
				case SOUTH:
					torchPos = new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ() +1);
					isWallTorch = true;
					break;
				case WEST:
					torchPos = new BlockPos(blockpos.getX() -1, blockpos.getY(), blockpos.getZ());
					isWallTorch = true;
					break;
				case EAST:
					torchPos = new BlockPos(blockpos.getX() +1, blockpos.getY(), blockpos.getZ());
					isWallTorch = true;
					break;
				default:
					return InteractionResult.FAIL;
				}

				if(block instanceof DoublePlantBlock) {

					if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && !(world.isEmptyBlock(blockpos.below()))) {
						if(!player.isCreative()) {
							block.playerDestroy(world, player, blockpos, iblockstate, null, context.getItemInHand());
							block.dropResources(iblockstate, world, blockpos);
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return placeTorch(context, blockpos.below(), blockpos, false);
					} else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && !(world.isEmptyBlock(blockpos.below(2)))) {
						if(!player.isCreative()) {
							block.playerDestroy(world, player, blockpos.below(), iblockstate, null, context.getItemInHand());
							block.dropResources(world.getBlockState(blockpos.below()), world, blockpos.below());
						}
						world.setBlock(blockpos.below(), Blocks.AIR.defaultBlockState(), 11);
						return placeTorch(context, blockpos.below(2), blockpos.below(1), false);
					}
				}

				if(block instanceof BushBlock) {

					if(!(world.isEmptyBlock(blockpos.below()))) {
						if(!player.isCreative()) {
							block.playerDestroy(world, player, blockpos, iblockstate, null, context.getItemInHand());
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return placeTorch(context, blockpos.below(), blockpos, false);
					}
				}

				return placeTorch(context, blockpos, torchPos, isWallTorch);
			}

			return InteractionResult.SUCCESS;
		}
	}

	protected InteractionResult placeTorch(UseOnContext context, BlockPos pos, BlockPos torchPos, boolean isWallTorch) {

		BlockState state = context.getLevel().getBlockState(pos);
		Block block = context.getLevel().getBlockState(pos).getBlock();

		if(block == Blocks.TORCH || block == Blocks.WALL_TORCH || block == Blocks.SOUL_TORCH || block == Blocks.SOUL_WALL_TORCH) {
			return InteractionResult.FAIL;
		}

		if(context.getLevel().getBlockState(torchPos).isAir() || context.getLevel().getBlockState(torchPos).getFluidState().isSource()) {

			if(state.isSolidRender(context.getLevel(), pos)) {

				BlockState torch;
				BlockState wall_torch;

				if(context.getLevel().dimension() == Level.NETHER) {
					torch = Blocks.SOUL_TORCH.defaultBlockState();
					wall_torch = Blocks.SOUL_WALL_TORCH.defaultBlockState();
				} else {
					torch = Blocks.TORCH.defaultBlockState();
					wall_torch = Blocks.WALL_TORCH.defaultBlockState();
				}

				if(isWallTorch) {
					context.getLevel().setBlockAndUpdate(torchPos, wall_torch.setValue(HorizontalDirectionalBlock.FACING, context.getClickedFace()));
					context.getLevel().playSound(null, context.getPlayer().blockPosition(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 8.0F, (float) (0.7F + (Math.random()*0.3D)));
				} else
				{
					context.getLevel().setBlockAndUpdate(torchPos, torch);
					context.getLevel().playSound(null, context.getPlayer().blockPosition(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 8.0F, (float) (0.7F + (Math.random()*0.3D)));
				}
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

		ItemStack itemStack = player.getItemInHand(interactionHand);

		boolean hasQuickUse = itemStack.getEnchantmentTags().toString().contains("quickuse");
		
		if(!hasQuickUse)
		{
			player.getCooldowns().addCooldown(this, GobberConfigBuilder.SNIPER_SWORD_COOLDOWN.get());
		}

		if(!level.isClientSide) {

			if(player.isCrouching()) {
				EnableUtil.changeEnabled(player, interactionHand);
				player.displayClientMessage(new TranslatableComponent("item.gobber2.gobber2_paxel_stars.line4", EnableUtil.isEnabled(itemStack)).withStyle(ChatFormatting.RED), true);
			} else {
				
				ArrowItem itemarrow = (ArrowItem)Items.ARROW;
				AbstractArrow persistentProjectileEntity = itemarrow.createArrow(level, new ItemStack(Items.ARROW), player);
	            float arrowVelocity = 60.0F;
	            persistentProjectileEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, arrowVelocity, 0.0F);
	            persistentProjectileEntity.setBaseDamage(arrowVelocity);
	            
				int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
				if (k > 0) {
					persistentProjectileEntity.setBaseDamage(persistentProjectileEntity.getBaseDamage() + (double) k * 0.5D + 0.5D);
				}

				int l = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
				if (l > 0) {
					persistentProjectileEntity.setKnockback(l);
				}

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0 || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemStack) > 0) {
					persistentProjectileEntity.setSecondsOnFire(100);
				}
	            
	            level.addFreshEntity(persistentProjectileEntity);
	            persistentProjectileEntity.pickup = Pickup.DISALLOWED;
			}
		}

		return super.use(level, player, interactionHand);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment)
	{
		if(enchantment.category == EnchantmentCategory.BOW && enchantment != Enchantments.INFINITY_ARROWS)
			return true;
		else
			return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level world, Player player)
	{
		if(world.isClientSide) return;

		if(GobberConfigBuilder.END_GOBBER_TOOLS_UNBREAKABLE.get())
		{
			stack.getOrCreateTag().putBoolean("Unbreakable", true);
		}
	}

	@Override
	public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {

		checkAdvancement(entity);

		if (entity instanceof ServerPlayer) {
			ServerAdvancementManager manager = entity.getServer().getAdvancements();

			Advancement paxel = manager.getAdvancement(getGobberLocation("paxel"));
			Advancement paxel_end = manager.getAdvancement(getGobberLocation("paxel_end"));
			Advancement paxel_stars = manager.getAdvancement(getGobberLocation("paxel_stars"));
			Advancement pickaxe_end = manager.getAdvancement(getGobberLocation("pickaxe_end"));
			Advancement sword_end = manager.getAdvancement(getGobberLocation("sword_end"));
			Advancement sword_sniper = manager.getAdvancement(getGobberLocation("sword_sniper"));

			grantAdvancement(entity, paxel);
			grantAdvancement(entity, paxel_end);
			grantAdvancement(entity, paxel_stars);
			grantAdvancement(entity, pickaxe_end);
			grantAdvancement(entity, sword_end);
			grantAdvancement(entity, sword_sniper);
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(new TranslatableComponent("item.gobber2.gobber2_tree_axe.line1").withStyle(ChatFormatting.YELLOW));
		list.add(new TranslatableComponent("item.gobber2.gobber2_paxel_stars.line2").withStyle(ChatFormatting.YELLOW));
		list.add(new TranslatableComponent("item.gobber2.gobber2_paxel_stars.line3").withStyle(ChatFormatting.YELLOW));
		list.add(new TranslatableComponent("item.gobber2.gobber2_sword_sniper.line2").withStyle(ChatFormatting.YELLOW));
		list.add(new TranslatableComponent("item.gobber2.gobber2_paxel_stars.line4", EnableUtil.isEnabled(itemStack)).withStyle(ChatFormatting.RED));
	}
}
