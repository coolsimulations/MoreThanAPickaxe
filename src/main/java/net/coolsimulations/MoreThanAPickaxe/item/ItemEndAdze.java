package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.List;

import com.gildedgames.the_aether.AetherConfig;
import com.google.common.collect.Multimap;
import com.kwpugh.gobber.init.ModItems;
import com.kwpugh.gobber.util.EnableUtil;
import com.mjr.extraplanets.Config;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import micdoodle8.mods.galacticraft.planets.venus.ConfigManagerVenus;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class ItemEndAdze extends ItemGobberAdze {

	public ItemEndAdze(ToolMaterial material, float damage, float speed) {
		super(material, damage, speed, true);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		ItemStack stack = playerIn.getHeldItem(hand);

		if(!EnableUtil.isEnabled(stack)) {
			return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		} else {
			if(!worldIn.isRemote) {

				IBlockState iblockstate = worldIn.getBlockState(pos);
				Block block = iblockstate.getBlock();

				BlockPos torchPos;
				boolean isWallTorch = false;
				switch(facing) {
				case DOWN:
					return EnumActionResult.FAIL;
				case UP:
					torchPos = new BlockPos(pos.getX(), pos.getY() +1, pos.getZ());
					break;
				case NORTH:
					torchPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() -1);
					isWallTorch = true;
					break;
				case SOUTH:
					torchPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() +1);
					isWallTorch = true;
					break;
				case WEST:
					torchPos = new BlockPos(pos.getX() -1, pos.getY(), pos.getZ());
					isWallTorch = true;
					break;
				case EAST:
					torchPos = new BlockPos(pos.getX() +1, pos.getY(), pos.getZ());
					isWallTorch = true;
					break;
				default:
					return EnumActionResult.FAIL;
				}

				if(block instanceof BlockDoublePlant) {

					if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.LOWER && !(worldIn.isAirBlock(pos.down()))) {
						if(!playerIn.isCreative()) {
							block.harvestBlock(worldIn, playerIn, pos, iblockstate, null, stack);
						}
						worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
						return placeTorch(worldIn, playerIn, facing, pos.down(), pos, false);
					} else if(iblockstate.getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER && !(worldIn.isAirBlock(pos.down(2)))) {
						if(!playerIn.isCreative()) {
							block.onBlockHarvested(worldIn, pos, iblockstate, playerIn);
						}
						worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 11);
						return placeTorch(worldIn, playerIn, facing, pos.down(2), pos.down(1), false);
					}
				}

				if(block instanceof BlockBush) {

					if(!(worldIn.isAirBlock(pos.down()))) {
						if(!playerIn.isCreative()) {
							block.harvestBlock(worldIn, playerIn, pos, iblockstate, null, stack);
						}
						worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
						return placeTorch(worldIn, playerIn, facing, pos.down(), pos, false);
					}
				}

				return placeTorch(worldIn, playerIn, facing, pos, torchPos, isWallTorch);
			}

			return EnumActionResult.SUCCESS;
		}
	}

	protected EnumActionResult placeTorch(World worldIn, EntityPlayer playerIn, EnumFacing facing, BlockPos pos, BlockPos torchPos, boolean isWallTorch) {

		IBlockState state = worldIn.getBlockState(pos);
		Block block = worldIn.getBlockState(pos).getBlock();

		if(block == Blocks.TORCH) {
			return EnumActionResult.FAIL;
		}

		if(worldIn.isAirBlock(torchPos) || ((worldIn.getBlockState(torchPos).getBlock() instanceof BlockLiquid && worldIn.getBlockState(torchPos).getValue(BlockLiquid.LEVEL) == 0) || (worldIn.getBlockState(torchPos).getBlock() instanceof IFluidBlock && ((IFluidBlock) worldIn.getBlockState(torchPos)).getFilledPercentage(worldIn, torchPos) >= 0.875F	))) {

			if(state.isSideSolid(worldIn, torchPos, facing)) {

				IBlockState torch = Blocks.TORCH.getDefaultState();

				if(worldIn.provider.getDimension() == -1) {
					if(SPCompatibilityManager.isFutureMCLoaded())
						torch = Block.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.FUTURE_MC_MODID, "soul_fire_torch")).getDefaultState();
				}

				if(SPCompatibilityManager.isGCLoaded()) {
					if(worldIn.provider.getDimension() == ConfigManagerCore.idDimensionMoon)
						torch = Block.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.GCCORE_MODID, "glowstone_torch")).getDefaultState();
					else if(SPCompatibilityManager.isGCPLoaded()) {
						if(worldIn.provider.getDimension() == ConfigManagerMars.dimensionIDMars || worldIn.provider.getDimension() == ConfigManagerAsteroids.dimensionIDAsteroids || worldIn.provider.getDimension() == ConfigManagerVenus.dimensionIDVenus)
							torch = Block.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.GCCORE_MODID, "glowstone_torch")).getDefaultState();
						else if(SPCompatibilityManager.isExtraPlanetsLoaded()) {
							if(worldIn.provider.getDimension() == Config.CALLISTO_ID
									|| worldIn.provider.getDimension() == Config.CERES_ID
									|| worldIn.provider.getDimension() == Config.DEIMOS_ID
									|| worldIn.provider.getDimension() == Config.ERIS_ID
									|| worldIn.provider.getDimension() == Config.EUROPA_ID
									|| worldIn.provider.getDimension() == Config.GANYMEDE_ID
									|| worldIn.provider.getDimension() == Config.IAPETUS_ID
									|| worldIn.provider.getDimension() == Config.IO_ID
									|| worldIn.provider.getDimension() == Config.JUPITER_ID
									|| worldIn.provider.getDimension() == Config.MERCURY_ID
									|| worldIn.provider.getDimension() == Config.NEPTUNE_ID
									|| worldIn.provider.getDimension() == Config.OBERON_ID
									|| worldIn.provider.getDimension() == Config.PHOBOS_ID
									|| worldIn.provider.getDimension() == Config.PLUTO_ID
									|| worldIn.provider.getDimension() == Config.RHEA_ID
									|| worldIn.provider.getDimension() == Config.SATURN_ID
									|| worldIn.provider.getDimension() == Config.TITAN_ID
									|| worldIn.provider.getDimension() == Config.TITANIA_ID
									|| worldIn.provider.getDimension() == Config.TRITON_ID
									|| worldIn.provider.getDimension() == Config.URANUS_ID)
								torch = Block.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.GCCORE_MODID, "glowstone_torch")).getDefaultState();
						}	
					}
				}

				if(SPCompatibilityManager.isAetherLegacyLoaded()) {
					if(worldIn.provider.getDimension() == AetherConfig.dimension.aether_dimension_id)
						torch = Block.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.AETHER_LEGACY_MODID, "ambrosium_torch")).getDefaultState();
				}

				if(isWallTorch) {
					worldIn.setBlockState(torchPos, torch.withProperty(BlockTorch.FACING, facing), 11);
					worldIn.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 8.0F, (float) (0.7F + (Math.random()*0.3D)));
				} else
				{
					worldIn.setBlockState(torchPos, torch, 11);
					worldIn.playSound(null, playerIn.getPosition(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 8.0F, (float) (0.7F + (Math.random()*0.3D)));
				}
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack itemStack = player.getHeldItem(hand);
		
		if (!world.isRemote) {
			if(player.isSneaking()) {
				EnableUtil.changeEnabled(player, hand);
				player.sendMessage(new TextComponentString("Status changed!"));
			} else {
				ItemArrow itemarrow = (ItemArrow)Items.ARROW;
				EntityArrow persistentProjectileEntity = itemarrow.createArrow(world, new ItemStack(Items.ARROW), player);
	            float arrowVelocity = 60.0F;
	            persistentProjectileEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, arrowVelocity, 0.0F);
	            persistentProjectileEntity.setDamage(arrowVelocity);
	            
				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemStack);
				if (k > 0) {
					persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) k * 0.5D + 0.5D);
				}

				int l = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemStack);
				if (l > 0) {
					persistentProjectileEntity.setKnockbackStrength(l);
				}

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemStack) > 0 || EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemStack) > 0) {
					persistentProjectileEntity.setFire(100);
				}
	            
	            world.spawnEntity(persistentProjectileEntity);
	            persistentProjectileEntity.pickupStatus = PickupStatus.DISALLOWED;
			}
		}
		
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		if(enchantment.type == EnumEnchantmentType.BOW && enchantment != Enchantments.INFINITY)
			return true;
		else
			return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 20.0D, 0));
		}

		return multimap;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(0, entityLiving);
		}

		stack.damageItem(0, entityLiving);
		return true;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (target instanceof EntityWitherSkeleton) {
			target.setDead();
			target.setDropItemsWhenDead(false);
			target.dropItem(ModItems.FRESH_WITHER_SKULL, 1);
			return true;
		}

		return super.hitEntity(stack, target, attacker);
	}

	public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
		list.add(TextFormatting.GOLD + "An unbreakable adze");
		super.addInformation(itemstack, world, list, flag);
		list.add(TextFormatting.GOLD + "Right-click to place a torch");
		list.add(TextFormatting.GOLD + "It never runs out of torches!!");
		list.add(TextFormatting.GOLD + "Fires deadly arrows with great speed and precision");
		list.add(TextFormatting.RED + "Torch placing ability (" + EnableUtil.isEnabled(itemstack) + ")");
	}

}
