package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.UUID;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
//import net.doubledoordev.lumberjack.Lumberjack;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

public class MoreThanAPickaxeLumberjack {
	
	/**private HashMultimap<UUID, BlockPos> pointMap = HashMultimap.create();
	private HashMultimap<UUID, BlockPos> nextMap = HashMultimap.create();

	@SubscribeEvent
	public void tickEvent(PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			if (event.side.isServer()) {
				UUID uuid = event.player.getUniqueID();
				if (this.nextMap.containsKey(uuid)
						&& !this.nextMap.get(uuid).isEmpty()) {
					int i = 0;
					UnmodifiableIterator var4 = ImmutableSet.copyOf(
							this.nextMap.get(uuid)).iterator();

					while (var4.hasNext()) {
						BlockPos point = (BlockPos) var4.next();
						((EntityPlayerMP) event.player).interactionManager
								.tryHarvestBlock(point);
						this.nextMap.remove(uuid, point);
						if (i++ > Lumberjack.getTickLimit()) {
							break;
						}
					}

					if (this.pointMap.get(uuid).size() > Lumberjack
							.getTotalLimit()) {
						this.nextMap.removeAll(uuid);
					}

					if (!this.nextMap.containsKey(uuid)
							|| !this.nextMap.get(uuid).isEmpty()) {
						this.pointMap.removeAll(uuid);
					}

				}
			}
		}
	}

	@SubscribeEvent
	public void breakEvent(BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (player != null) {
			UUID uuid = player.getUniqueID();
			IBlockState state = event.getState();
			if (state.getMaterial() == Material.WOOD
					|| Lumberjack.getLeaves()
					&& state.getMaterial() == Material.LEAVES) {
				ItemStack itemStack = player.getHeldItemMainhand();
				if (itemStack != ItemStack.EMPTY
						&& itemStack.getItem() instanceof ItemAdze && !player.isSneaking()) {
					this.pointMap.put(uuid, event.getPos());

					for (int offsetX = -1; offsetX <= 1; ++offsetX) {
						for (int offsetZ = -1; offsetZ <= 1; ++offsetZ) {
							for (int offsetY = -1; offsetY <= 1; ++offsetY) {
								BlockPos newPoint = event.getPos()
										.add(offsetX, offsetY,
												offsetZ);
								if (!this.nextMap.containsEntry(uuid, newPoint)
										&& !this.pointMap.containsEntry(uuid,
												newPoint)) {
									IBlockState newBlockState = event
											.getWorld().getBlockState(newPoint);
									boolean isLeaves = Lumberjack.getLeaves()
											&& newBlockState.getMaterial() == Material.LEAVES;
									if (Lumberjack.getMode() == 0
											&& (isLeaves || newBlockState
													.getBlock() == state
													.getBlock())
											|| Lumberjack.getMode() == 1
											&& (isLeaves || newBlockState
													.getMaterial() == Material.WOOD)) {
										this.nextMap.put(uuid, newPoint);
									}
								}
							}
						}
					}

				}
			}
		}
	}**/

}
