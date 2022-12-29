package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ItemAdze extends DiggerItem{

	private final float attackDamage;
	private final float attackSpeed;
	private final Tier material;
	private final Multimap<Attribute, AttributeModifier> attribute;
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
	protected static final Map<Block, BlockState> HOE_LOOKUP;
	
	protected final boolean isModded;
	protected final boolean unbreakable;

	public ItemAdze(Tier material, float damage, float speed, Item.Properties builder) {
		this(material, damage, speed, builder, false);
	}
	
	public ItemAdze(Tier material, float damage, float speed, Item.Properties builder, boolean isModded) {
		this(material, damage, speed, builder, isModded, false);
	}

	public ItemAdze(Tier material, float damage, float speed, Item.Properties builder, boolean isModded, boolean unbreakable) {
		super(damage, speed, material, MoreThanAPickaxeTags.Blocks.MINEABLE_WITH_ADZE, builder.stacksTo(1));
		this.material = material;
		this.attackSpeed = speed;
		this.attackDamage = damage;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
		attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		this.attribute = attributeBuilder.build();
		this.isModded = isModded;
		this.unbreakable = unbreakable;
	}

	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if (state.is(Blocks.COBWEB))
		{
			return 15.0F;
		}

		return super.getDestroySpeed(stack, state);
	}

	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {

		if (state.is(Blocks.COBWEB))
		{
			return true;
		}

		return super.isCorrectToolForDrops(stack, state);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {

		boolean success = false;

		if(ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction) || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction)) {
			return true;
		}

		return success;
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity entity) {
		if(!unbreakable)
			return super.hurtEnemy(stack, target, entity);
		else
			return true;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {

		Level world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		Player player = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();

		BlockState iblockstate = world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockPos blockBelowBlockPos = new BlockPos(blockpos.getX(), blockpos.getY() - 1, blockpos.getZ());

		BlockState blockStateBelow = world.getBlockState(blockBelowBlockPos);
		Block blockBelow = blockStateBelow.getBlock();
		BlockPos blockAboveBlockPos = blockpos.above();

		BlockPos blockTwiceBelowBlockPos = blockpos.below(2);

		BlockPos blockTwiceAboveBlockPos = blockpos.above(2);

		Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(context, net.minecraftforge.common.ToolActions.AXE_STRIP, false));
		Optional<BlockState> optional1 = Optional.ofNullable(blockstate.getToolModifiedState(context, net.minecraftforge.common.ToolActions.AXE_SCRAPE, false));
		Optional<BlockState> optional2 = Optional.ofNullable(blockstate.getToolModifiedState(context, net.minecraftforge.common.ToolActions.AXE_WAX_OFF, false));

		Optional<BlockState> optional3 = Optional.empty();
		if (optional.isPresent()) {
			world.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			optional3 = optional;
		} else if (optional1.isPresent()) {
			world.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
			world.levelEvent(player, 3005, blockpos, 0);
			optional3 = optional1;
		} else if (optional2.isPresent()) {
			world.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
			world.levelEvent(player, 3004, blockpos, 0);
			if(player instanceof ServerPlayer) {
				ServerAdvancementManager manager = player.getServer().getAdvancements();
				Advancement wax_off = manager.getAdvancement(new ResourceLocation("minecraft:husbandry/wax_off"));
				AdvancementProgress advancementprogress = ((ServerPlayer) player).getAdvancements().getOrStartProgress(wax_off);
	            if (!advancementprogress.isDone()) {
	               for(String s : advancementprogress.getRemainingCriteria()) {
	            	   ((ServerPlayer) player).getAdvancements().award(wax_off, s);
	               }
	            }
			}
			optional3 = optional2;
		}

		if (optional3.isPresent()) {
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
			}

			world.setBlock(blockpos, optional3.get(), 11);
			world.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, optional3.get()));
			if (!unbreakable && player != null) {
				itemstack.hurtAndBreak(1, player, (p_150686_) -> {
					p_150686_.broadcastBreakEvent(context.getHand());
				});
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
			if (!world.isClientSide()) {
				world.levelEvent((Player)null, 1009, blockpos, 0);
			}

			CampfireBlock.dowse(player, world, blockpos, blockstate);
			world.setBlock(blockpos, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)), 11);

			if (!unbreakable && player != null) {
				context.getItemInHand().hurtAndBreak(1, player, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		if(!context.getPlayer().isCrouching()) {

			BlockState toolModifiedState = world.getBlockState(blockpos).getToolModifiedState(context, ToolActions.HOE_TILL, false);
			Player playerentity = context.getPlayer();
			if(context.getClickedFace() != Direction.DOWN) {

				if(block instanceof BushBlock) {
					BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(iblockstate2 != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative())
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
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
							block.dropResources(iblockstate, world, blockpos);
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
						if(!playerentity.isCreative())
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
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
							block.dropResources(iblockstate, world, blockpos);
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					} else if(iblockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isEmptyBlock(blockAboveBlockPos)) {
						setBlockToPath(context, blockTwiceBelowBlockPos, world);
						if(!playerentity.isCreative()) {
							block.playerDestroy(world, playerentity, blockpos, iblockstate, null, context.getItemInHand());
							block.dropResources(iblockstate, world, blockpos);
						}
						world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
						return InteractionResult.SUCCESS;
					}
				}

				if(world.isEmptyBlock(blockpos.above())) {
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
				world.setBlock(blockpos, iblockstate2, 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), iblockstate2));
				if (!unbreakable && playerentity != null) {
					context.getItemInHand().hurtAndBreak(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
				}
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		return InteractionResult.PASS;
	}

	protected InteractionResult setBlockToPath(UseOnContext context, BlockPos blockpos, Level world) {

		BlockState iblockstate1 = world.getBlockState(blockpos).getToolModifiedState(context, net.minecraftforge.common.ToolActions.SHOVEL_FLATTEN, false);
		if (iblockstate1 != null) {
			Player playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				world.setBlock(blockpos, iblockstate1, 11);
				world.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(playerentity, iblockstate1));
				if (!unbreakable && playerentity != null) {
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

	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment)
	{
		if(enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.DIGGER)
			return true;
		else
			return false;
	}
	
	@Override
	public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {

		checkAdvancement(entity);
	}
	
	protected void checkAdvancement(Entity entity) {
		
		if(isModded) {
			if (entity instanceof ServerPlayer) {
				ServerAdvancementManager manager = entity.getServer().getAdvancements();

				Advancement universal = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/adze"));

				AdvancementProgress advancementprogress = ((ServerPlayer) entity).getAdvancements().getOrStartProgress(universal);
				if (!advancementprogress.isDone()) {
					for(String s : advancementprogress.getRemainingCriteria()) {
						((ServerPlayer) entity).getAdvancements().award(universal, s);
					}
				}
			}
		}
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
		/**final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EquipmentSlotType.MAINHAND)
		{
			multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
			multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, AttributeModifier.Operation.ADDITION));
		}

		return multimap;**/
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attribute : super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
	{
		return stack.getItem() instanceof ItemAdze;
	}

	static {

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
