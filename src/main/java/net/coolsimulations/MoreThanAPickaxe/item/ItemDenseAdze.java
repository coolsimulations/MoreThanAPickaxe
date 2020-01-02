package net.coolsimulations.MoreThanAPickaxe.item;

import gnu.trove.set.hash.THashSet;

import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import net.coolsimulations.SurvivalPlus.api.SPBlocks;
import net.coolsimulations.SurvivalPlus.api.blocks.SPBlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.ewyboy.bibliotheca.common.interfaces.IItemRenderer;
import com.ewyboy.hammertime.common.items.ItemBaseTool;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ItemDenseAdze extends ItemBaseTool implements IItemRenderer {
	
	public static class TreeChopTask {
        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;
        public Queue<BlockPos> blocks = Lists.newLinkedList();
        public Set<BlockPos> visited = new THashSet();

        public TreeChopTask(ItemStack tool, BlockPos start, EntityPlayer player, int blocksPerTick) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;
            this.blocks.add(start);
        }

        @SubscribeEvent
        public void chopChop(TickEvent.WorldTickEvent event) {
            if (event.side.isClient()) {
                this.finish();
                return;
            }
            if (event.world.provider.getDimension() != this.world.provider.getDimension()) {
                return;
            }
            int left = this.blocksPerTick;
            while (left > 0) {
                if (this.tool.getItemDamage() >= this.tool.getMaxDamage()) {
                    this.player.getHeldItem(this.player.swingingHand).shrink(1);
                    this.finish();
                    break;
                }
                if (this.blocks.isEmpty()) {
                    this.finish();
                    return;
                }
                BlockPos pos = this.blocks.remove();
                if (!this.visited.add(pos) || !ItemDenseAdze.isLog(this.world, pos)) continue;
                for (EnumFacing facing : new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}) {
                    BlockPos pos2 = pos.offset(facing);
                    if (this.visited.contains((Object)pos2)) continue;
                    this.blocks.add(pos2);
                }
                for (int x = 0; x < 3; ++x) {
                    for (int z = 0; z < 3; ++z) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if (this.visited.contains((Object)pos2)) continue;
                        this.blocks.add(pos2);
                    }
                }
                this.world.destroyBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
                this.tool.damageItem(1, (EntityLivingBase)this.player);
                --left;
            }
        }

        private void finish() {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
	
	public static final Set<Block> EFFECTIVE_ON;

	public ItemDenseAdze(ToolMaterial toolMaterial) {
		super(toolMaterial, EFFECTIVE_ON);
	}
	
	public boolean onBlockDestroyed(ItemStack stack, World world,
			IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
	}

	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos,
			EntityPlayer player) {
		if(isLog(player.world, pos)) {
			return fellTree(itemstack, pos, player);
		} else {
			return false;
		}
	}

	public static boolean detectTree(World world, BlockPos origin) {
		BlockPos pos = null;
		Stack<BlockPos> candidates = new Stack();
		candidates.add(origin);

		while (true) {
			BlockPos candidate;
			do {
				do {
					if (candidates.isEmpty()) {
						if (pos == null) {
							return false;
						}

						int d = 3;
						int o = -1;
						int leaves = 0;

						for (int x = 0; x < d; ++x) {
							for (int y = 0; y < d; ++y) {
								for (int z = 0; z < d; ++z) {
									BlockPos leaf = pos.add(o + x, o
											+ y, o + z);
									IBlockState state = world
											.getBlockState(leaf);
									if (state.getBlock().isLeaves(state,
											world, leaf)) {
										++leaves;
										if (leaves >= 5) {
											return true;
										}
									}
								}
							}
						}

						return false;
					}

					candidate = (BlockPos) candidates.pop();
				} while (pos != null
						&& candidate.getY() <= pos.getY());
			} while (!isLog(world, candidate));

			for (pos = candidate.up(); isLog(world, pos); pos = pos
					.up()) {
				;
			}

			candidates.add(pos.north());
			candidates.add(pos.east());
			candidates.add(pos.south());
			candidates.add(pos.west());
		}
	}

	private static boolean isLog(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().isWood(world, pos);
	}
	
	private static boolean isLeaves(World world, BlockPos pos) {
		
		return world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos);
	}

	public static boolean fellTree(ItemStack itemstack, BlockPos start,
			EntityPlayer player) {
		if (player.getEntityWorld().isRemote) {
			return true;
		} else {
			MinecraftForge.EVENT_BUS.register(new TreeChopTask(itemstack,
					start, player, 1));
			return true;
		}
	}
	
	public boolean canHarvestBlock(IBlockState blockIn) {
		Block block = blockIn.getBlock();
		if (block == Blocks.OBSIDIAN) {
			return this.toolMaterial.getHarvestLevel() == 3;
		} else if (block != Blocks.DIAMOND_BLOCK
				&& block != Blocks.DIAMOND_ORE) {
			if (block != Blocks.EMERALD_ORE
					&& block != Blocks.EMERALD_BLOCK) {
				if (block != Blocks.GOLD_BLOCK
						&& block != Blocks.GOLD_ORE) {
					if (block != Blocks.IRON_BLOCK
							&& block != Blocks.IRON_ORE) {
						if (block != Blocks.LAPIS_BLOCK
								&& block != Blocks.LAPIS_ORE) {
							if (block != Blocks.REDSTONE_ORE
									&& block != Blocks.LIT_REDSTONE_ORE) {
								if (block != SPBlocks.copper_ore
										&& block != SPBlocks.copper_block) {
									if (block != SPBlocks.tin_ore
											&& block != SPBlocks.tin_block) {
										if (block != SPBlocks.titanium_ore
												&& block != SPBlocks.titanium_block) {
											Material material = blockIn.getMaterial();
											return material == Material.ROCK ? true : (material == Material.IRON ? true : material == Material.ANVIL) || (blockIn.getBlock() instanceof IPlantable || blockIn.getBlock() instanceof IGrowable || EFFECTIVE_ON.contains(blockIn.getBlock()));
										}else {
											return this.toolMaterial.getHarvestLevel() >= 1;
										}
									}else {
										return this.toolMaterial.getHarvestLevel() >= 1;
									}
								}else {
									return this.toolMaterial.getHarvestLevel() >= 2;
								}
							}else {
								return this.toolMaterial.getHarvestLevel() >= 2;
							}
						} else {
							return this.toolMaterial.getHarvestLevel() >= 1;
						}
					} else {
						return this.toolMaterial.getHarvestLevel() >= 1;
					}
				} else {
					return this.toolMaterial.getHarvestLevel() >= 2;
				}
			} else {
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		} else {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
	}
	
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		if(!player.isSneaking()){
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
		         return EnumActionResult.FAIL;
		      } else {
		         IBlockState iblockstate = worldIn.getBlockState(pos);
		         Block block = iblockstate.getBlock();
		         if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
		            if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
		               this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
		               return EnumActionResult.SUCCESS;
		            }
		            
		            if(iblockstate.getBlock() instanceof BlockDirt) {
		            	switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT))
		            	{
		            	case DIRT:
		            		this.setBlock(itemstack, player, worldIn, pos, Blocks.FARMLAND.getDefaultState());
		            		return EnumActionResult.SUCCESS;
		            	case COARSE_DIRT:
		            		this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
		            		return EnumActionResult.SUCCESS;
		            	case PODZOL:
		            		this.setBlock(itemstack, player, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
		            		return EnumActionResult.SUCCESS;
		            	}
		            }
		         }

		         return EnumActionResult.PASS;
		      }
		} else {
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
				return EnumActionResult.FAIL;
			} else {
				IBlockState iblockstate = worldIn.getBlockState(pos);
				Block block = iblockstate.getBlock();
				if (facing != EnumFacing.DOWN
						&& worldIn.getBlockState(pos.up())
						.getMaterial() == Material.AIR
						&& block == Blocks.GRASS) {
					IBlockState blockState = Blocks.GRASS_PATH.getDefaultState();
					worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN,
							SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!worldIn.isRemote) {
						worldIn.setBlockState(pos, blockState, 11);
						itemstack.damageItem(1, player);
					}

					return EnumActionResult.SUCCESS;
				} else {
					return EnumActionResult.PASS;
				}
			}
		}
	}

	private void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state) {
	      worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
	      if (!worldIn.isRemote) {
	         worldIn.setBlockState(pos, state, 11);
	         stack.damageItem(1, player);
	      }

	   }

	static {
		EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.LOG,
				Blocks.LOG2, Blocks.PLANKS,
				Blocks.CRAFTING_TABLE, Blocks.CHEST,
				Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE,
				Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL,
				Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
				Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL,
				Blocks.GOLD_BLOCK, Blocks.GOLD_ORE,
				Blocks.ICE, Blocks.IRON_BLOCK,
				Blocks.IRON_ORE, Blocks.LAPIS_BLOCK,
				Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE,
				Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK,
				Blocks.PACKED_ICE, Blocks.RAIL,
				Blocks.REDSTONE_ORE, Blocks.SANDSTONE,
				Blocks.RED_SANDSTONE, Blocks.STONE,
				Blocks.STONE_SLAB, Blocks.STONE_BUTTON,
				Blocks.STONE_PRESSURE_PLATE,
				SPBlocks.copper_ore, SPBlocks.copper_block,
				SPBlocks.tin_ore, SPBlocks.tin_block,
				SPBlocks.titanium_ore, SPBlocks.titanium_block,
				Blocks.CLAY, Blocks.DIRT,
				Blocks.FARMLAND, Blocks.GRASS,
				Blocks.GRAVEL, Blocks.MYCELIUM,
				Blocks.SAND, Blocks.SNOW,
				Blocks.SNOW_LAYER, Blocks.SOUL_SAND,
				Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER,
				Blocks.TALLGRASS, Blocks.BROWN_MUSHROOM,
				Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM,
				Blocks.RED_MUSHROOM_BLOCK, Blocks.LEAVES,
				Blocks.LEAVES2, Blocks.CHORUS_FLOWER,
				Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER,
				Blocks.WHEAT, Blocks.CHORUS_PLANT,
				Blocks.DOUBLE_PLANT});
	}

}
