package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeTags;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.coolsimulations.SurvivalPlus.api.events.ItemAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class ItemAdze extends DiggerItem implements ItemAccessor {

	protected final float attackDamage;
	protected final float attackSpeed;
	protected final Tier material;
	protected Multimap<Attribute, AttributeModifier> attribute;
	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP;
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
	protected static final Map<Block, BlockState> HOE_LOOKUP;

	protected final boolean isModded;
	protected final boolean unbreakable;

	public ItemAdze(Tier material, float damage, float speed, FabricItemSettings builder) {
		this(material, damage, speed, builder, false);
	}
	
	public ItemAdze(Tier material, float damage, float speed, FabricItemSettings builder, boolean isModded) {
		this(material, damage, speed, builder, isModded, false);
	}

	public ItemAdze(Tier material, float damage, float speed, FabricItemSettings builder, boolean isModded, boolean unbreakable) {
		super(damage, speed, material, MoreThanAPickaxeTags.Blocks.MINEABLE_WITH_ADZE, builder.group(SPTabs.tabTools).maxCount(1));
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

	public boolean isCorrectToolForDrops(BlockState state) {

		if (state.is(Blocks.COBWEB))
		{
			return true;
		}

		return super.isCorrectToolForDrops(state);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity entity) {
		if(!unbreakable)
			return super.hurtEnemy(stack, target, entity);
		else
			return true;
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
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

		Optional<BlockState> optional = Optional.ofNullable((Block) BLOCK_STRIPPING_MAP.get(blockstate.getBlock())).map(state -> state.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockstate.getValue(RotatedPillarBlock.AXIS)));
		Optional<BlockState> optional1 = WeatheringCopper.getPrevious(blockstate);
		Optional<BlockState> optional2 = Optional.ofNullable((Block) ((BiMap) HoneycombItem.WAX_OFF_BY_BLOCK.get()).get(blockstate.getBlock())).map(state -> state.withPropertiesOf(blockstate));

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
			if (player instanceof ServerPlayer) {
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

			CampfireBlock.dowse(context.getPlayer(), world, blockpos, blockstate);
			world.setBlock(blockpos, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)), 11);

			if (!unbreakable && player != null) {
				context.getItemInHand().hurtAndBreak(1, player, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.broadcastBreakEvent(context.getHand());});
			}

			return InteractionResult.sidedSuccess(world.isClientSide);
		}

		if(SPCompatibilityManager.isAetherRebornLoaded()) {
			if (world.getBlockState(blockpos).getBlock() == Registry.BLOCK.get(new ResourceLocation(SPCompatibilityManager.AETHER_REBORN_MODID, "golden_oak_log")) && !world.isClientSide) {
				ServerLevel server = (ServerLevel) world;
				LootTable supplier = server.getServer().getLootTables().get(new ResourceLocation(SPCompatibilityManager.AETHER_REBORN_MODID, "gameplay/golden_oak_log_strip"));
				List<ItemStack> items = supplier.getRandomItems(new LootContext.Builder(server)
						.withParameter(LootContextParams.BLOCK_STATE, world.getBlockState(blockpos))
						.withParameter(LootContextParams.ORIGIN, Vec3.atLowerCornerOf(blockpos))
						.withParameter(LootContextParams.TOOL, context.getItemInHand())
						.create(LootContextParamSets.BLOCK)
						);
				Vec3 offsetDirection = context.getClickLocation();
				for (ItemStack item : items) {
					ItemEntity itemEntity = new ItemEntity(context.getLevel(), offsetDirection.x, offsetDirection.y, offsetDirection.z, item);
					world.addFreshEntity(itemEntity);
				}
				world.setBlock(blockpos, Registry.BLOCK.get(new ResourceLocation(SPCompatibilityManager.AETHER_REBORN_MODID, "stripped_golden_oak_log")).defaultBlockState(), 11);
			}
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
				if(world.getBlockState(blockpos).getBlock() == Blocks.ROOTED_DIRT) {
					Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(Items.HANGING_ROOTS));
				}
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

		BlockState iblockstate1 = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
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

	@Override
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
				.put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD).put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG)
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
