package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAdze extends ToolItem{

	private final float attackDamage;
	private final float attackSpeed;
	private final IItemTier material;
	private final Multimap<Attribute, AttributeModifier> attribute;
	private static final Set<Block> EFFECTIVE_ON;
	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP;
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
	protected static final Map<Block, BlockState> HOE_LOOKUP;

	public ItemAdze(IItemTier material, float damage, float speed, Item.Properties builder) {
		super(damage, speed, material, EFFECTIVE_ON, builder.addToolType(net.minecraftforge.common.ToolType.AXE, material.getHarvestLevel()).addToolType(net.minecraftforge.common.ToolType.PICKAXE, material.getHarvestLevel()).addToolType(net.minecraftforge.common.ToolType.SHOVEL, material.getHarvestLevel()).group(SPTabs.tabTools).maxStackSize(1));
		this.material = material;
		this.attackSpeed = speed;
		this.attackDamage = damage;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.<Attribute, AttributeModifier>builder();
		attributeBuilder.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
		this.attribute = attributeBuilder.build();
	}

	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		Block block = state.getBlock();

		if (block == Blocks.COBWEB)
		{
			return 15.0F;
		}
		else {
			Material material = state.getMaterial();
			return material != Material.WOOD && material != Material.field_237214_y_ && material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.BAMBOO && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD && material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
		}
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(BlockState blockIn) {
		Block block = blockIn.getBlock();
		int i = this.getTier().getHarvestLevel();
		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
			return i >= blockIn.getHarvestLevel();
		}

		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.AXE) {
			return i >= blockIn.getHarvestLevel();
		}

		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.SHOVEL) {
			return i >= blockIn.getHarvestLevel();
		}

		Material material = blockIn.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK || block == Blocks.COBWEB;
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockState blockstate = world.getBlockState(blockpos);
		Block blockStrip = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());

		BlockState iblockstate = world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockPos blockBelowBlockPos = new BlockPos(blockpos.getX(), blockpos.getY() - 1, blockpos.getZ());

		BlockState blockStateBelow = world.getBlockState(blockBelowBlockPos);
		Block blockBelow = blockStateBelow.getBlock();
		BlockPos blockAboveBlockPos = blockpos.up();

		BlockPos blockTwiceBelowBlockPos = blockpos.down(2);

		BlockPos blockTwiceAboveBlockPos = blockpos.up(2);


		if (blockStrip != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote) {
				world.setBlockState(blockpos, (BlockState) blockStrip.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
				if (playerentity != null) {
					context.getItem().damageItem(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendBreakAnimation(context.getHand());});
				}
			}

			return ActionResultType.SUCCESS;
		}

		if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
			world.playEvent((PlayerEntity)null, 1009, blockpos, 0);
			world.setBlockState(blockpos, blockstate.with(CampfireBlock.LIT, Boolean.valueOf(false)), 11);
		}

		if(!context.getPlayer().isSneaking()) {

			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
			PlayerEntity playerentity = context.getPlayer();
			if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
			if(context.getFace() != Direction.DOWN) {
				if (world.isAirBlock(blockpos.up())) {
					setBlockToFarmland(context, blockpos, world);
				}

				if(block instanceof BushBlock) {
					BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(iblockstate2 != null && world.isAirBlock(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative())
							block.harvestBlock(world, playerentity, blockpos, iblockstate, null, context.getItem());
						world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						return ActionResultType.SUCCESS;
					}
				}

				if(block instanceof DoublePlantBlock) {
					BlockState iblockstate2_below = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = HOE_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

					if(iblockstate.get(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isAirBlock(blockTwiceAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						block.onBlockHarvested(world, blockpos, iblockstate, playerentity);
						return ActionResultType.SUCCESS;
					} else if(iblockstate.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isAirBlock(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockTwiceBelowBlockPos, world);
						block.onBlockHarvested(world, blockpos, iblockstate, playerentity);
						return ActionResultType.SUCCESS;
					}
				}
			}
		} else {
			if(context.getFace() != Direction.DOWN) {
				PlayerEntity playerentity = context.getPlayer();

				if (world.getBlockState(blockpos.up()).isAir()) {
					setBlockToPath(context, blockpos, world);
				}

				if(block instanceof BushBlock) {
					BlockState iblockstate2 = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(blockBelow == Blocks.GRASS || iblockstate2 != null && world.isAirBlock(blockAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative())
							block.harvestBlock(world, playerentity, blockpos, iblockstate, null, context.getItem());
						world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						return ActionResultType.SUCCESS;
					}
				}

				if(block instanceof DoublePlantBlock) {
					BlockState iblockstate2_below = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = SHOVEL_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

					if(iblockstate.get(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isAirBlock(blockTwiceAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						block.onBlockHarvested(world, blockpos, iblockstate, playerentity);
						return ActionResultType.SUCCESS;
					} else if(iblockstate.get(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isAirBlock(blockAboveBlockPos)) {
						setBlockToPath(context, blockTwiceBelowBlockPos, world);
						block.onBlockHarvested(world, blockpos, iblockstate, playerentity);
						return ActionResultType.SUCCESS;
					}
				}

			}
		}

		return ActionResultType.PASS;
	}

	protected ActionResultType setBlockToFarmland(ItemUseContext context, BlockPos blockpos, World world) {

		BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate2 != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote) {
				world.setBlockState(blockpos, iblockstate2, 11);
				if (playerentity != null) {
					context.getItem().damageItem(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendBreakAnimation(context.getHand());});
				}
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	protected ActionResultType setBlockToPath(ItemUseContext context, BlockPos blockpos, World world) {

		BlockState iblockstate1 = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate1 != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote) {
				world.setBlockState(blockpos, iblockstate1, 11);
				if (playerentity != null) {
					context.getItem().damageItem(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendBreakAnimation(context.getHand());});
				}
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	/**
	 * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
	 */
	public float getAttackDamage()
	{
		return this.material.getAttackDamage();
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		stack.damageItem(1, attacker, (player) -> {player.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
		{
			stack.damageItem(2, entityLiving, (player) -> {player.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
		}

		return true;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return this.material.getEnchantability();
	}
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		if(enchantment.type == EnchantmentType.BREAKABLE || enchantment.type == EnchantmentType.WEAPON || enchantment.type == EnchantmentType.DIGGER)
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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{

		return this.material.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);

		/**ItemStack mat = this.material.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);**/
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot)
	{
		/**final Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EquipmentSlotType.MAINHAND)
		{
			multimap.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
			multimap.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, AttributeModifier.Operation.ADDITION));
		}

		return multimap;**/
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attribute : super.getAttributeModifiers(equipmentSlot);
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker)
    {
        return stack.getItem() instanceof ItemAdze;
    }

	static {

		EFFECTIVE_ON = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL,
				Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
				new Block[]{Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK,
						Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE,
						Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE,
						Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE,
						Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE,
						Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE,
						Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB,
						Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB,
						Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB,
						Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ,
						Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON,
						Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB,
						Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB,
						Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB,
						Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
						Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX,
						Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX,
						Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
						Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
						Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
						Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,
						Blocks.YELLOW_SHULKER_BOX,
						Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS,
						Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD,
						Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD,
						Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG,
						Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN,
						Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON,
						Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON,
						Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE,
						Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE,
						Blocks.field_235358_mQ_, Blocks.field_235359_mR_,
						Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
						Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND,
						Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER,
						Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
						Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER,
						Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
						Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER,
						Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.field_235336_cN_,
						Blocks.NETHER_WART_BLOCK, Blocks.field_235374_mn_, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK,
						Blocks.field_235396_nb_, Blocks.field_235383_mw_, Blocks.SPONGE, Blocks.WET_SPONGE,
						Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES,Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES});

		BLOCK_STRIPPING_MAP = (new Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
				.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
				.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
				.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
				.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
				.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
				.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
				.put(Blocks.field_235368_mh_, Blocks.field_235369_mi_).put(Blocks.field_235370_mj_, Blocks.field_235371_mk_)
				.put(Blocks.field_235377_mq_, Blocks.field_235378_mr_).put(Blocks.field_235379_ms_, Blocks.field_235380_mt_)
				.build();

		SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

		HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(),
				Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(),
				Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));
	}

}
