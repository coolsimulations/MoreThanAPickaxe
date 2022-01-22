package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ItemGobberAdze extends ItemAdze {

	public static final Random random = new Random();
	static List<BlockPos> breakList = new ArrayList<BlockPos>();
	BlockPos breakPos;

	public ItemGobberAdze(Tier material, float damage, float speed, Item.Properties builder) {
		this(material, damage, speed, builder, false);
	}
	
	public ItemGobberAdze(Tier material, float damage, float speed, Item.Properties builder, boolean unbreakable) {
		super(material, damage, speed, builder, true, unbreakable);
	}

	@Override
	public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {

		checkAdvancement(entity);

		if (entity instanceof ServerPlayer) {
			ServerAdvancementManager manager = entity.getServer().getAdvancements();

			Advancement paxel = manager.getAdvancement(getGobberLocation("paxel"));
			Advancement piackxe_nether = manager.getAdvancement(getGobberLocation("pickaxe_nether"));
			Advancement piackxe_gobber = manager.getAdvancement(getGobberLocation("pickaxe"));

			grantAdvancement(entity, paxel);
			if(itemStack.getItem() == MoreThanAPickaxeItems.nether_adze)
				grantAdvancement(entity, piackxe_nether);
			if(itemStack.getItem() == MoreThanAPickaxeItems.gobber_adze)
				grantAdvancement(entity, piackxe_gobber);
		}

		if (!level.isClientSide && entity instanceof Player && !breakList.isEmpty() && ((Player)entity).tickCount % 3 == 0) {
			this.breakPos = breakList.get(0);
			level.destroyBlock(this.breakPos, true);
			breakList.remove(0);
		}
	}

	protected static void grantAdvancement(Entity entity, Advancement advancement) {

		AdvancementProgress advancementprogress = ((ServerPlayer) entity).getAdvancements().getOrStartProgress(advancement);
		if (!advancementprogress.isDone()) {
			for(String s : advancementprogress.getRemainingCriteria()) {
				((ServerPlayer) entity).getAdvancements().award(advancement, s);
			}
		}
	}

	protected static ResourceLocation getGobberLocation(String location) {

		return new ResourceLocation(SPCompatibilityManager.GOBBER_MODID, SPCompatibilityManager.GOBBER_MODID + "_" + location);
	}
	
	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(new TranslatableComponent("item.gobber2.gobber2_tree_axe.line1").withStyle(ChatFormatting.GREEN));
    }

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

		ItemStack mainHand = player.getItemInHand(interactionHand);
		if (!level.isClientSide) {
			for (int x = -5; x <= 5; ++x) {
				for (int y = -2; y <= 40; ++y) {
					for (int z = -5; z <= 5; ++z) {
						BlockPos pos = player.blockPosition().offset(x, y, z);
						Block block = level.getBlockState(pos).getBlock();
						if (block != Blocks.NETHER_WART_BLOCK && block != Blocks.WARPED_WART_BLOCK && block != Blocks.SHROOMLIGHT) continue;
						breakList.add(pos);
					}
				}
			}
		}
		return super.use(level, player, interactionHand);
	}

	@Override
	public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {

		if(livingEntity instanceof Player) {
			if (!level.isClientSide && !livingEntity.isCrouching() && blockState.getDestroySpeed(level, blockPos) != 0.0f && !this.attemptFellTree(level, blockPos, (Player) livingEntity)) {
				
				// This forces non-wood blocks to drop their loot table, watch out for exploits
				Block.dropResources(blockState, level, blockPos, null, livingEntity, livingEntity.getMainHandItem());
				
				ItemGobberAdze.attemptBreakNeighbors(level, blockPos, (Player) livingEntity, BlockTags.MINEABLE_WITH_AXE, false);
			}
		}
		if(!unbreakable) {
			return super.mineBlock(itemStack, level, blockState, blockPos, livingEntity);
		} else {
			return true;
		}
	}

	private boolean attemptFellTree(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide) {
			int i;
			ArrayList<BlockPos> logs = new ArrayList<BlockPos>();
			ArrayList<BlockPos> candidates = new ArrayList<BlockPos>();
			candidates.add(pos);
			int leaves = 0;
			for (i = 0; i < candidates.size(); ++i) {
				if (logs.size() > 200) {
					player.displayClientMessage(new TextComponent("Tree too big!"), true);
					return false;
				}
				BlockPos candidate = candidates.get(i);
				Block block = level.getBlockState(candidate).getBlock();
				if (BlockTags.LEAVES.contains(block) || block == Blocks.NETHER_WART_BLOCK || block == Blocks.WARPED_WART_BLOCK) {
					++leaves;
					continue;
				}
				if (logs.size() != 0 && !BlockTags.LOGS.contains(block)) continue;
				logs.add(candidate);
				for (int x = -1; x <= 1; ++x) {
					for (int y = 0; y <= 1; ++y) {
						for (int z = -1; z <= 1; ++z) {
							BlockPos neighbor = candidate.offset(x, y, z);
							if (candidates.contains(neighbor)) continue;
							candidates.add(neighbor);
						}
					}
				}
			}
			if (logs.size() == 0) {
				return false;
			}
			if ((double)leaves >= (double)logs.size() / 6.0) {
				for (i = 0; i < logs.size(); ++i) {
					BlockPos log = (BlockPos)logs.get(i);
					ItemGobberAdze.attemptBreak(level, log, player, BlockTags.MINEABLE_WITH_AXE);
				}
				return true;
			}
		}
		return false;
	}

	public static void attemptBreakNeighbors(Level level, BlockPos blockPos, Player player, Tag<Block> tag, boolean checkHarvestLevel) {
		if (level.isClientSide) {
			return;
		}
		level.setBlockAndUpdate(blockPos, Blocks.GLASS.defaultBlockState());
		BlockHitResult blockHitResult = ItemGobberAdze.calcRay(level, player, ClipContext.Fluid.ANY);
		level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
		if (blockHitResult.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockTrace = blockHitResult;
			Direction face = blockTrace.getDirection();
			for (int a = -1; a <= 1; ++a) {
				for (int b = -1; b <= 1; ++b) {
					if (a == 0 && b == 0) continue;
					BlockPos target = null;
					if (face == Direction.UP || face == Direction.DOWN) {
						target = blockPos.offset(a, 0, b);
					}
					if (face == Direction.NORTH || face == Direction.SOUTH) {
						target = blockPos.offset(a, b, 0);
					}
					if (face == Direction.EAST || face == Direction.WEST) {
						target = blockPos.offset(0, a, b);
					}
					ItemGobberAdze.attemptBreak(level, target, player, tag);
				}
			}
		}
	}

	public static BlockHitResult calcRay(Level level, Player player, ClipContext.Fluid fluidMode) {
		float f = player.getXRot();
		float f1 = player.getYRot();
		Vec3 vec3d = player.getEyePosition(1.0f);
		float f2 = Mth.cos((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
		float f3 = Mth.sin((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
		float f4 = -Mth.cos((float)(-f * ((float)Math.PI / 180)));
		float f5 = Mth.sin((float)(-f * ((float)Math.PI / 180)));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = 3.0;
		Vec3 vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
		return level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
	}

	public static void attemptBreak(Level level, BlockPos blockPos, Player player, Tag<Block> tag) {
		BlockState state = level.getBlockState(blockPos);
		boolean isEffective = tag.contains(state.getBlock());;
		if (player.hasCorrectToolForDrops(state) && isEffective) {
			breakList.add(blockPos);
		}
	}

}
