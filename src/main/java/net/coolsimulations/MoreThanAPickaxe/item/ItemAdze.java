package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeTags;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.coolsimulations.SurvivalPlus.api.events.ItemAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class ItemAdze extends DiggerItem implements ItemAccessor {

	protected final float attackDamage;
	protected final float attackSpeed;
	protected final Tier material;
	private final Multimap<Attribute, AttributeModifier> attribute;
	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP;
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
	protected static final Map<Block, BlockState> HOE_LOOKUP;

	public ItemAdze(Tier material, float damage, float speed, FabricItemSettings builder) {
		super(damage, speed, material, MoreThanAPickaxeTags.Blocks.MINEABLE_WITH_ADZE, builder.group(SPTabs.tabTools).maxCount(1));
		this.material = material;
		this.attackSpeed = speed;
		this.attackDamage = damage;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
		attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		this.attribute = attributeBuilder.build();
	}
	
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if (state.is(Blocks.COBWEB))
		{
			return 15.0F;
		}
		
		return super.getDestroySpeed(stack, state);
	}
	
	public boolean isCorrectToolForDrops(BlockState state) {
		
		if (state.is(Blocks.COBWEB))
		{
			return true;
		}
		
		return super.isCorrectToolForDrops(state);
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		Block blockStrip = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());

		BlockState iblockstate = world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockPos blockBelowBlockPos = new BlockPos(blockpos.getX(), blockpos.getY() - 1, blockpos.getZ());

		BlockState blockStateBelow = world.getBlockState(blockBelowBlockPos);
		Block blockBelow = blockStateBelow.getBlock();
		BlockPos blockAboveBlockPos = blockpos.above();

		BlockPos blockTwiceBelowBlockPos = blockpos.below(2);

		BlockPos blockTwiceAboveBlockPos = blockpos.above(2);


		if (blockStrip != null) {
			Player playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				world.setBlock(blockpos, (BlockState) blockStrip.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockstate.getValue(RotatedPillarBlock.AXIS)), 11);
				if (playerentity != null) {
					context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
				}
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
			world.levelEvent((Player)null, 1009, blockpos, 0);
			world.setBlock(blockpos, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)), 11);
			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		if(!context.getPlayer().isCrouching()) {

			Player playerentity = context.getPlayer();
			if(context.getClickedFace() != Direction.DOWN) {
				if(block instanceof BushBlock) {
					BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(iblockstate2 != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					}
				}

				if(block instanceof DoublePlantBlock) {
					BlockState iblockstate2_below = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = HOE_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

					if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isEmptyBlock(blockTwiceAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
							block.dropResources(iblockstate, world, blockpos);
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					} else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockTwiceBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					}
				}
				
				if (world.isEmptyBlock(blockpos.above())) {
					return setBlockToFarmland(context, blockpos, world);
				}
			}

			return InteractionResult.PASS;
		} else {
			if(context.getClickedFace() != Direction.DOWN) {
				Player playerentity = context.getPlayer();

				if(block instanceof BushBlock) {
					BlockState iblockstate2 = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(blockBelow == Blocks.GRASS || iblockstate2 != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerWillDestroy(world, blockpos, iblockstate, playerentity);
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					}
				}

				if(block instanceof DoublePlantBlock) {
					BlockState iblockstate2_below = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = SHOVEL_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

					if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isEmptyBlock(blockTwiceAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					} else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToPath(context, blockTwiceBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					}
				}
				
				if (world.getBlockState(blockpos.above()).isAir()) {
					return setBlockToPath(context, blockpos, world);
				}

			}

			return InteractionResult.PASS;
		}
	}

	protected InteractionResult setBlockToFarmland(UseOnContext context, BlockPos blockpos, Level world) {

		BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate2 != null) {
			Player playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				if(world.getBlockState(blockpos).getBlock() == Blocks.ROOTED_DIRT) {
					Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(Items.HANGING_ROOTS));
				}
				world.setBlock(blockpos, iblockstate2, 11);
				if (playerentity != null) {
					context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
				}
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		return InteractionResult.PASS;
	}

	protected InteractionResult setBlockToPath(UseOnContext context, BlockPos blockpos, Level world) {

		BlockState iblockstate1 = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate1 != null) {
			Player playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				world.setBlock(blockpos, iblockstate1, 11);
				if (playerentity != null) {
					context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
				}
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		return InteractionResult.PASS;
	}

	/**
	 * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
	 */
	public float getAttackDamageBonus()
	{
		return this.material.getAttackDamageBonus();
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getEnchantmentValue()
	{
		return this.material.getEnchantmentValue();
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment)
	{
		if(enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.DIGGER)
			return true;
		else
			return false;
	}


	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName()
	{
		return this.material.toString();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
	{

		return this.material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);

		/**ItemStack mat = this.material.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);**/
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot)
	{
		/**Multimap<EntityAttribute, EntityAttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EquipmentSlot.MAINHAND)
		{
			multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, Operation.ADDITION));
			multimap.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.4000000953674316D, Operation.ADDITION));
		}

		return multimap;**/
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attribute : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
	{
		return stack.getItem() instanceof ItemAdze;
	}

	@Override
	public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
		return false;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, Player player) {
		return true;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
		return false;
	}

	static {

		BLOCK_STRIPPING_MAP = (new Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
				.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
				.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
				.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
				.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
				.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
				.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
				.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
				.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
				.build();

		SHOVEL_LOOKUP = Maps.newHashMap((new Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState())
				.put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState())
				.put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState())
				.put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState())
				.put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState())
				.put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState()).build());

		HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(),
				Blocks.DIRT_PATH, Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(),
				Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState(), Blocks.ROOTED_DIRT, Blocks.DIRT.defaultBlockState()));
	}

}
