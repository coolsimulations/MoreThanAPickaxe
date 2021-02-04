package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.coolsimulations.SurvivalPlus.api.events.ItemAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemAdze extends MiningToolItem implements ItemAccessor {

	protected final float attackDamage;
	protected final float attackSpeed;
	protected final ToolMaterial material;
	protected static final Set<Block> EFFECTIVE_ON;
	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP;
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP;
	protected static final Map<Block, BlockState> HOE_LOOKUP;

	public ItemAdze(ToolMaterial material, float damage, float speed, FabricItemSettings builder) {
		super(damage, speed, material, EFFECTIVE_ON, builder/**.addToolType(net.minecraftforge.common.ToolType.AXE, material.getHarvestLevel()).addToolType(net.minecraftforge.common.ToolType.PICKAXE, material.getHarvestLevel()).addToolType(net.minecraftforge.common.ToolType.SHOVEL, material.getHarvestLevel())**/.group(SPTabs.tabTools).maxCount(1));
		this.material = material;
		this.attackSpeed = speed;
		this.attackDamage = damage;
	}

	public float getMiningSpeed(ItemStack stack, BlockState state)
	{
		Block block = state.getBlock();

		if (block == Blocks.COBWEB)
		{
			return 15.0F;
		}
		else {
			Material material = state.getMaterial();
			return material != Material.WOOD && material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.BAMBOO && material != material.UNDERWATER_PLANT && material != Material.LEAVES && material != Material.GOURD && material != Material.METAL && material != Material.REPAIR_STATION && material != material.STONE ? super.getMiningSpeed(stack, state) : this.miningSpeed;
		}
	}
	
	public boolean isEffectiveOn(BlockState state) {
	      Block block = state.getBlock();
	      int i = this.getMaterial().getMiningLevel();
	      if (block == Blocks.OBSIDIAN) {
	         return i == 3;
	      } else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE && block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK && block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE && block != Blocks.REDSTONE_ORE) {
	         if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE && block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
	            Material material = state.getMaterial();
	            return material == Material.STONE || material == Material.METAL || material == Material.REPAIR_STATION || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK;
	         } else {
	            return i >= 1;
	         }
	      } else {
	         return i >= 2;
	      }
	   }

	/**
	 * Called when this item is used when targetting a Block
	 */
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockpos = context.getBlockPos();
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
			if (!world.isClient) {
				world.setBlockState(blockpos, (BlockState) blockStrip.getDefaultState().with(PillarBlock.AXIS, blockstate.get(PillarBlock.AXIS)), 11);
				if (playerentity != null) {
					context.getStack().damage(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendToolBreakStatus(context.getHand());});
				}
			}

			return ActionResult.SUCCESS;
		}

		if(!context.shouldCancelInteraction()) {

			PlayerEntity playerentity = context.getPlayer();
			if(context.getPlayerFacing() != Direction.DOWN) {
				if (world.isAir(blockpos.up())) {
					setBlockToFarmland(context, blockpos, world);
				}
				
				if(block instanceof PlantBlock) {
					BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					
					if(iblockstate2 != null && world.isAir(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative())
							block.afterBreak(world, playerentity, blockpos, iblockstate, null, context.getStack());
						world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						return ActionResult.SUCCESS;
					}
				}

				if(block instanceof TallPlantBlock) {
					BlockState iblockstate2_below = HOE_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = HOE_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());

					if(iblockstate.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isAir(blockTwiceAboveBlockPos)) {
						setBlockToFarmland(context, blockBelowBlockPos, world);
						block.onBreak(world, blockpos, iblockstate, playerentity);
						return ActionResult.SUCCESS;
					} else if(iblockstate.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isAir(blockAboveBlockPos)) {
						setBlockToFarmland(context, blockTwiceBelowBlockPos, world);
						block.onBreak(world, blockpos, iblockstate, playerentity);
						return ActionResult.SUCCESS;
					}
				}
			}
		} else {
			if(context.getPlayerFacing() != Direction.DOWN) {
				PlayerEntity playerentity = context.getPlayer();
				
				if (world.getBlockState(blockpos.up()).isAir()) {
					setBlockToPath(context, blockpos, world);
				}
				
				if(block instanceof PlantBlock) {
					BlockState iblockstate2 = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());

					if(blockBelow == Blocks.GRASS || iblockstate2 != null && world.isAir(blockAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						if(!playerentity.isCreative())
							block.afterBreak(world, playerentity, blockpos, iblockstate, null, context.getStack());
						world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						return ActionResult.SUCCESS;
					}
				}

				if(block instanceof TallPlantBlock) {
					BlockState iblockstate2_below = SHOVEL_LOOKUP.get(world.getBlockState(blockBelowBlockPos).getBlock());
					BlockState iblockstate2_twice_below = SHOVEL_LOOKUP.get(world.getBlockState(blockTwiceBelowBlockPos).getBlock());
					
					if(iblockstate.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER && iblockstate2_below != null && world.isAir(blockTwiceAboveBlockPos)) {
						setBlockToPath(context, blockBelowBlockPos, world);
						block.onBreak(world, blockpos, iblockstate, playerentity);
						return ActionResult.SUCCESS;
					} else if(iblockstate.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER && iblockstate2_twice_below != null && world.isAir(blockAboveBlockPos)) {
						setBlockToPath(context, blockTwiceBelowBlockPos, world);
						block.onBreak(world, blockpos, iblockstate, playerentity);
						return ActionResult.SUCCESS;
					}
				}

			}
		}

		return ActionResult.PASS;
	}

	protected ActionResult setBlockToFarmland(ItemUsageContext context, BlockPos blockpos, World world) {

		BlockState iblockstate2 = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate2 != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isClient) {
				world.setBlockState(blockpos, iblockstate2, 11);
				if (playerentity != null) {
					context.getStack().damage(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendToolBreakStatus(context.getHand());});
				}
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	protected ActionResult setBlockToPath(ItemUsageContext context, BlockPos blockpos, World world) {

		BlockState iblockstate1 = SHOVEL_LOOKUP.get(world.getBlockState(blockpos).getBlock());
		if (iblockstate1 != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isClient) {
				world.setBlockState(blockpos, iblockstate1, 11);
				if (playerentity != null) {
					context.getStack().damage(1, playerentity, (p_lambda$onItemUse$0_1_) -> {p_lambda$onItemUse$0_1_.sendToolBreakStatus(context.getHand());});
				}
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
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
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		stack.damage(1, attacker, (player) -> {player.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean postMine(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{
		if ((double)state.getHardness(worldIn, pos) != 0.0D)
		{
			stack.damage(2, entityLiving, (player) -> {player.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);});
		}

		return true;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getEnchantability()
	{
		return this.material.getEnchantability();
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		if(enchantment.type == EnchantmentTarget.BREAKABLE || enchantment.type == EnchantmentTarget.WEAPON || enchantment.type == EnchantmentTarget.DIGGER)
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
	public boolean canRepair(ItemStack toRepair, ItemStack repair)
	{

		return this.material.getRepairIngredient().test(repair) || super.canRepair(toRepair, repair);

		/**ItemStack mat = this.material.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);**/
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<String, EntityAttributeModifier> getModifiers(EquipmentSlot equipmentSlot)
	{
		Multimap<String, EntityAttributeModifier> multimap = super.getModifiers(equipmentSlot);

		if (equipmentSlot == EquipmentSlot.MAINHAND)
		{
			multimap.put(EntityAttributes.ATTACK_DAMAGE.getId(), new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Weapon modifier", (double)this.attackDamage, Operation.ADDITION));
			multimap.put(EntityAttributes.ATTACK_SPEED.getId(), new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Weapon modifier", -2.4000000953674316D, Operation.ADDITION));
		}

		return multimap;
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
	public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
		return true;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return false;
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
						Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
						Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND,
						Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER,
						Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
						Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER,
						Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
						Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER,
						Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER});

		BLOCK_STRIPPING_MAP = (new Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
				.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
				.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
				.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
				.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
				.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
				.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
				.build();

		SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

		HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(),
				Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(),
				Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));
	}

}
