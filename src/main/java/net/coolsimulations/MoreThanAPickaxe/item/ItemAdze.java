package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAdze extends ItemTool{

	protected final float attackDamage;
	protected final Item.ToolMaterial material;
	protected static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH});

	public ItemAdze(ToolMaterial material, float damage, float speed) {
		super(damage, speed, material, EFFECTIVE_ON);
		this.material = material;
		this.maxStackSize = 1;
		this.attackSpeed = speed;
		this.attackDamage = damage;
		this.setHarvestLevel("pickaxe", material.getHarvestLevel());
		this.setHarvestLevel("axe", material.getHarvestLevel());
		this.setHarvestLevel("shovel", material.getHarvestLevel());
		this.setHarvestLevel("mattock", material.getHarvestLevel());
	}

	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();

		if (block == Blocks.WEB)
		{
			return 15.0F;
		}
		else {
			Material material = state.getMaterial();
			return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD && material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
		}
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		Block block = blockIn.getBlock();
		if (block == Blocks.WEB){
			return block == Blocks.WEB;
		}

		if (block == Blocks.SNOW_LAYER){
			return block == Blocks.SNOW_LAYER ? true : block == Blocks.SNOW;
		}

		if (block == Blocks.OBSIDIAN)
		{
			return this.toolMaterial.getHarvestLevel() == 3;
		}
		else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE)
		{
			if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK)
			{
				if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE)
				{
					if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE)
					{
						if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE)
						{
							if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE)
							{
								Material material = blockIn.getMaterial();
								return material == Material.ROCK ? true : (material == Material.IRON ? true : material == Material.ANVIL);
							}
							else
							{
								return this.toolMaterial.getHarvestLevel() >= 2;
							}
						}
						else
						{
							return this.toolMaterial.getHarvestLevel() >= 1;
						}
					}
					else
					{
						return this.toolMaterial.getHarvestLevel() >= 1;
					}
				}
				else
				{
					return this.toolMaterial.getHarvestLevel() >= 2;
				}
			}
			else
			{
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		}
		else
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		ItemStack stack = playerIn.getHeldItem(hand);

		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		BlockPos blockBelowBlockPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		IBlockState blockStateBelow = worldIn.getBlockState(blockBelowBlockPos);
		Block blockBelow = blockStateBelow.getBlock();

		BlockPos blockAboveBlockPos = pos.up();
		IBlockState blockStateAbove = worldIn.getBlockState(blockAboveBlockPos);
		Block blockAbove = blockStateAbove.getBlock();

		BlockPos blockTwiceBelowBlockPos = pos.down(2);
		IBlockState blockStateTwiceBelow = worldIn.getBlockState(blockTwiceBelowBlockPos);
		Block blockTwiceBelow = blockStateTwiceBelow.getBlock();

		BlockPos blockTwiceAboveBlockPos = pos.up(2);

		if(!playerIn.isSneaking()){
			if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
			{
				return EnumActionResult.FAIL;
			}
			else
			{
				int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
				if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

				if (facing != EnumFacing.DOWN)
				{
					if(worldIn.isAirBlock(blockAboveBlockPos))
						return setBlockToFarmland(iblockstate, block, pos, stack, worldIn, playerIn);

					if(block instanceof BlockBush) {

						if(blockBelow == Blocks.GRASS || blockBelow == Blocks.GRASS_PATH || blockBelow instanceof BlockDirt && worldIn.isAirBlock(blockAboveBlockPos)) {
							setBlockToFarmland(blockStateBelow, blockBelow, blockBelowBlockPos, stack, worldIn, playerIn);
							if(!playerIn.isCreative())
								block.harvestBlock(worldIn, playerIn, pos, iblockstate, null, stack);
							this.setBlock(stack, playerIn, worldIn, pos, Blocks.AIR.getDefaultState());
							return EnumActionResult.SUCCESS;
						}
					}

					if(block instanceof BlockDoublePlant) {
						if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.LOWER && blockBelow == Blocks.GRASS || blockBelow == Blocks.GRASS_PATH || blockBelow instanceof BlockDirt && worldIn.isAirBlock(blockTwiceAboveBlockPos)) {
							setBlockToFarmland(blockStateBelow, blockBelow, blockBelowBlockPos, stack, worldIn, playerIn);
							block.onBlockHarvested(worldIn, pos, iblockstate, playerIn);
							return EnumActionResult.SUCCESS;
						} else if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER && blockTwiceBelow == Blocks.GRASS || blockTwiceBelow == Blocks.GRASS_PATH || blockTwiceBelow instanceof BlockDirt && worldIn.isAirBlock(blockAboveBlockPos)) {
							setBlockToFarmland(blockStateTwiceBelow, blockTwiceBelow, blockTwiceBelowBlockPos, stack, worldIn, playerIn);
							block.onBlockHarvested(worldIn, pos, iblockstate, playerIn);
							return EnumActionResult.SUCCESS;
						}
					}
				}

				return EnumActionResult.PASS;
			}
		}else {
			if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
			{
				return EnumActionResult.FAIL;
			}
			else
			{

				if (facing != EnumFacing.DOWN)
				{
					if(worldIn.isAirBlock(blockAboveBlockPos))
						return setBlockToPath(iblockstate, block, pos, stack, worldIn, playerIn);
					
					if(block instanceof BlockBush) {

						if(blockBelow == Blocks.GRASS || blockBelow == Blocks.GRASS_PATH || blockBelow instanceof BlockDirt && worldIn.isAirBlock(blockAboveBlockPos)) {
							setBlockToPath(blockStateBelow, blockBelow, blockBelowBlockPos, stack, worldIn, playerIn);
							if(!playerIn.isCreative())
								block.harvestBlock(worldIn, playerIn, pos, iblockstate, null, stack);
							this.setBlock(stack, playerIn, worldIn, pos, Blocks.AIR.getDefaultState());
							return EnumActionResult.SUCCESS;
						}
					}

					if(block instanceof BlockDoublePlant) {
						if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.LOWER && blockBelow == Blocks.GRASS || blockBelow == Blocks.GRASS_PATH || blockBelow instanceof BlockDirt && worldIn.isAirBlock(blockTwiceAboveBlockPos)) {
							setBlockToPath(blockStateBelow, blockBelow, blockBelowBlockPos, stack, worldIn, playerIn);
							block.onBlockHarvested(worldIn, pos, iblockstate, playerIn);
							return EnumActionResult.SUCCESS;
						} else if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER && blockTwiceBelow == Blocks.GRASS || blockTwiceBelow == Blocks.GRASS_PATH || blockTwiceBelow instanceof BlockDirt && worldIn.isAirBlock(blockAboveBlockPos)) {
							setBlockToPath(blockStateTwiceBelow, blockTwiceBelow, blockTwiceBelowBlockPos, stack, worldIn, playerIn);
							block.onBlockHarvested(worldIn, pos, iblockstate, playerIn);
							return EnumActionResult.SUCCESS;
						}
					}
				}
				else
				{
					return EnumActionResult.PASS;
				}
				
				return EnumActionResult.PASS;
			}
		}
	}

	protected EnumActionResult setBlockToFarmland(IBlockState iblockstate, Block block, BlockPos pos, ItemStack stack, World worldIn, EntityPlayer playerIn) {


		if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
		{
			this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
			return EnumActionResult.SUCCESS;
		}

		if (block instanceof BlockDirt)
		{
			switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT))
			{
			case DIRT:
				this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
				return EnumActionResult.SUCCESS;
			case COARSE_DIRT:
				this.setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
				return EnumActionResult.SUCCESS;
			case PODZOL:
				this.setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	protected EnumActionResult setBlockToPath(IBlockState iblockstate, Block block, BlockPos pos, ItemStack stack, World worldIn, EntityPlayer playerIn) {

		if(block == Blocks.GRASS) {
			IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
			worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if (!worldIn.isRemote)
			{
				worldIn.setBlockState(pos, iblockstate1, 11);
				stack.damageItem(1, playerIn);
			}
		}

		return EnumActionResult.SUCCESS;
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
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
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
		{
			stack.damageItem(2, entityLiving);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
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
		if(enchantment.type == EnumEnchantmentType.BREAKABLE || enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.DIGGER)
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
		ItemStack mat = this.material.getRepairItemStack();
		if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
		return super.getIsRepairable(toRepair, repair);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
		}

		return multimap;
	}
	
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker)
    {
        return stack.getItem() instanceof ItemAdze;
    }

}
