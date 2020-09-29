package net.coolsimulations.MoreThanAPickaxe.item;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemFireproofAdze extends ItemAdze {

	public ItemFireproofAdze(ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		FireproofItemLogic.INSTANCE.newUpdate(entity);
		return true;
	}

	public final static class FireproofItemLogic {
		public static final FireproofItemLogic INSTANCE = new FireproofItemLogic();

		public final void newUpdate(EntityItem entity) {
			if (entity.getItem().isEmpty())
			{
				entity.setDead();
			}
			else
			{

				entity.extinguish();
				this.customOnEntityUpdate(entity);
				int var36;
				int pickupDelay = ObfuscationReflectionHelper.getPrivateValue(EntityItem.class, entity, "field_145804_b");
				if (pickupDelay > 0 && pickupDelay != 32767)
				{
					entity.setPickupDelay(--pickupDelay);
					var36 = pickupDelay;
				}

				entity.prevPosX = entity.posX;
				entity.prevPosY = entity.posY;
				entity.prevPosZ = entity.posZ;
				double d0 = entity.motionX;
				double d1 = entity.motionY;
				double d2 = entity.motionZ;

				if (entity.isInsideOfMaterial(Material.LAVA)) {
					this.floatInLava(entity);
				} else if (!entity.hasNoGravity()) {
					entity.motionY -= 0.03999999910593033D;
				}

				if (entity.world.isRemote)
				{
					entity.noClip = false;
				}
				else
				{
					try {
						Method pushOutOfBlocks = ObfuscationReflectionHelper.findMethod(Entity.class, "func_145771_j", boolean.class, double.class, double.class, double.class);
						entity.noClip = (boolean) pushOutOfBlocks.invoke(entity, entity.posX, (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D, entity.posZ);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				try {
					this.newCustomEntityMove(entity, MoverType.SELF, entity.motionX, entity.motionY,
							entity.motionZ);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				boolean flag = (int)entity.prevPosX != (int)entity.posX || (int)entity.prevPosY != (int)entity.posY || (int)entity.prevPosZ != (int)entity.posZ;

				if (flag || entity.ticksExisted % 25 == 0)
				{
					if (!entity.world.isRemote)
					{
						try {
							Method searchForOtherItemsNearby = ObfuscationReflectionHelper.findMethod(EntityItem.class, "func_85054_d", Void.class);
							searchForOtherItemsNearby.invoke(entity);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				float f = 0.98F;

				if (entity.onGround)
				{
					BlockPos underPos = new BlockPos(MathHelper.floor(entity.posX), MathHelper.floor(entity.getEntityBoundingBox().minY) - 1, MathHelper.floor(entity.posZ));
					net.minecraft.block.state.IBlockState underState = entity.world.getBlockState(underPos);
					f = underState.getBlock().getSlipperiness(underState, entity.world, underPos, entity) * 0.98F;
				}

				entity.motionX *= (double)f;
				entity.motionY *= 0.9800000190734863D;
				entity.motionZ *= (double)f;

				if (entity.onGround)
				{
					entity.motionY *= -0.5D;
				}

				if (entity.getAge() != -32768) {
					int tempAge = entity.getAge(); 
					ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, entity, ++tempAge, "field_70292_b");
					var36 = entity.getAge();
				}

				entity.handleWaterMovement();

				if (!entity.world.isRemote)
				{
					double d3 = entity.motionX - d0;
					double d4 = entity.motionY - d1;
					double d5 = entity.motionZ - d2;
					double d6 = d3 * d3 + d4 * d4 + d5 * d5;

					if (d6 > 0.01D)
					{
						entity.isAirBorne = true;
					}
				}

				ItemStack item = entity.getItem();

				if (!entity.world.isRemote && entity.getAge() >= entity.lifespan)
				{
					int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(entity, item);
					if (hook < 0) entity.setDead();
					else          entity.lifespan += hook;
				}
				if (item.isEmpty())
				{
					entity.setDead();
				}
			}
		}

		private final void floatInLava(EntityItem entity) {
			entity.motionX *= 0.95D;
			entity.motionY += entity.motionY < 0.06D ? 5.0E-4D : 0.0D;
			entity.motionZ *= 0.95D;
		}

		private final void customOnEntityUpdate(EntityItem entity) {
			entity.world.profiler.startSection("entityBaseTick");
			if (entity.isRiding()) {
				Entity var10000 = entity.getRidingEntity();
				if (var10000 == null) {
					return;
				}

				if (var10000.isDead) {
					entity.dismountRidingEntity();
				}
			}

			int rideCooldown = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_184245_j");

			if (rideCooldown > 0) {
				ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, entity, rideCooldown += -1, "field_70292_b");
				int var2 = rideCooldown;
			}

			entity.prevDistanceWalkedModified = entity.distanceWalkedModified;
			entity.prevPosX = entity.posX;
			entity.prevPosY = entity.posY;
			entity.prevPosZ = entity.posZ;
			entity.prevRotationPitch = entity.rotationPitch;
			entity.prevRotationYaw = entity.rotationYaw;
			this.updatePortal(entity);
			entity.spawnRunningParticles();
			entity.handleWaterMovement();
			if (entity.posY < -64.0D) {
				try {
					Method outOfWorld = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70076_C", Void.class);
					outOfWorld.invoke(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, false, "field_70148_d");
			entity.world.profiler.endSection();
		}

		private final void updatePortal(EntityItem entity) {
			if (entity.world instanceof WorldServer) {
				boolean inPortal = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_71087_bX");
				int portalCounter = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_82153_h");
				if (inPortal) {
					World var10000 = entity.world;
					MinecraftServer server = var10000.getMinecraftServer();
					int i = entity.getMaxInPortalTime();
					if (server == null) {
						return;
					}

					if (server.getAllowNether() && !entity.isRiding()) {
						int j = portalCounter++;
						if (j >= i) {
							entity.world.profiler.startSection("portal");
							ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, i, "field_82153_h");
							entity.timeUntilPortal = entity.getPortalCooldown();
							WorldProvider var5 = entity.world.provider;
							DimensionType var6 = var5.getDimensionType();
							j = var6.getId() == -1 ? 0 : -1;
							entity.changeDimension(j);
							ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, false, "field_71087_bX");
						}
					}
				} else {
					if (portalCounter > 0) {
						ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, portalCounter -= 4, "field_82153_h");
					}

					if (portalCounter < 0) {
						ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, 0, "field_82153_h");
					}
				}

				try {
					Method decrementTimeUntilPortal = ObfuscationReflectionHelper.findMethod(Entity.class, "func_184173_H", Void.class);
					decrementTimeUntilPortal.invoke(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				entity.world.profiler.endSection();
			}

		}
		
		private final void newCustomEntityMove(EntityItem entity, MoverType type, double x, double y, double z) {
			if (entity.noClip)
	        {
	            entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(x, y, z));
	            entity.resetPositionToBB();
	        }
	        else
	        {
	            if (type == MoverType.PISTON)
	            {
					long pistonDeltasGameTime = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_191506_aJ");
					double[] pistonDeltas = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_191505_aI");
	                long i = entity.world.getTotalWorldTime();

	                if (i != pistonDeltasGameTime)
	                {
	                    Arrays.fill(pistonDeltas, 0.0D);
	                    ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, i, "field_191506_aJ");
	                }

	                if (x != 0.0D)
	                {
	                    int j = EnumFacing.Axis.X.ordinal();
	                    double d0 = MathHelper.clamp(x + pistonDeltas[j], -0.51D, 0.51D);
	                    x = d0 - pistonDeltas[j];
	                    pistonDeltas[j] = d0;

	                    if (Math.abs(x) <= 9.999999747378752E-6D)
	                    {
	                        return;
	                    }
	                }
	                else if (y != 0.0D)
	                {
	                    int l4 = EnumFacing.Axis.Y.ordinal();
	                    double d12 = MathHelper.clamp(y + pistonDeltas[l4], -0.51D, 0.51D);
	                    y = d12 - pistonDeltas[l4];
	                    pistonDeltas[l4] = d12;

	                    if (Math.abs(y) <= 9.999999747378752E-6D)
	                    {
	                        return;
	                    }
	                }
	                else
	                {
	                    if (z == 0.0D)
	                    {
	                        return;
	                    }

	                    int i5 = EnumFacing.Axis.Z.ordinal();
	                    double d13 = MathHelper.clamp(z + pistonDeltas[i5], -0.51D, 0.51D);
	                    z = d13 - pistonDeltas[i5];
	                    pistonDeltas[i5] = d13;

	                    if (Math.abs(z) <= 9.999999747378752E-6D)
	                    {
	                        return;
	                    }
	                }
	            }

	            entity.world.profiler.startSection("move");
	            double d10 = entity.posX;
	            double d11 = entity.posY;
	            double d1 = entity.posZ;

	            boolean isInWeb = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_70134_J");
	            if (isInWeb)
	            {
	            	ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, false, "field_70134_J");
	                x *= 0.25D;
	                y *= 0.05000000074505806D;
	                z *= 0.25D;
	                entity.motionX = 0.0D;
	                entity.motionY = 0.0D;
	                entity.motionZ = 0.0D;
	            }

	            double d2 = x;
	            double d3 = y;
	            double d4 = z;

	            List<AxisAlignedBB> list1 = entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().expand(x, y, z));
	            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox();

	            if (y != 0.0D)
	            {
	                int k = 0;

	                for (int l = list1.size(); k < l; ++k)
	                {
	                    y = ((AxisAlignedBB)list1.get(k)).calculateYOffset(entity.getEntityBoundingBox(), y);
	                }

	                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0D, y, 0.0D));
	            }

	            if (x != 0.0D)
	            {
	                int j5 = 0;

	                for (int l5 = list1.size(); j5 < l5; ++j5)
	                {
	                    x = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(entity.getEntityBoundingBox(), x);
	                }

	                if (x != 0.0D)
	                {
	                    entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
	                }
	            }

	            if (z != 0.0D)
	            {
	                int k5 = 0;

	                for (int i6 = list1.size(); k5 < i6; ++k5)
	                {
	                    z = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(entity.getEntityBoundingBox(), z);
	                }

	                if (z != 0.0D)
	                {
	                    entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0D, 0.0D, z));
	                }
	            }

	            boolean flag = entity.onGround || d3 != y && d3 < 0.0D;

	            if (entity.stepHeight > 0.0F && flag && (d2 != x || d4 != z))
	            {
	                double d14 = x;
	                double d6 = y;
	                double d7 = z;
	                AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
	                entity.setEntityBoundingBox(axisalignedbb);
	                y = (double)entity.stepHeight;
	                List<AxisAlignedBB> list = entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().expand(d2, y, d4));
	                AxisAlignedBB axisalignedbb2 = entity.getEntityBoundingBox();
	                AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
	                double d8 = y;
	                int j1 = 0;

	                for (int k1 = list.size(); j1 < k1; ++j1)
	                {
	                    d8 = ((AxisAlignedBB)list.get(j1)).calculateYOffset(axisalignedbb3, d8);
	                }

	                axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
	                double d18 = d2;
	                int l1 = 0;

	                for (int i2 = list.size(); l1 < i2; ++l1)
	                {
	                    d18 = ((AxisAlignedBB)list.get(l1)).calculateXOffset(axisalignedbb2, d18);
	                }

	                axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
	                double d19 = d4;
	                int j2 = 0;

	                for (int k2 = list.size(); j2 < k2; ++j2)
	                {
	                    d19 = ((AxisAlignedBB)list.get(j2)).calculateZOffset(axisalignedbb2, d19);
	                }

	                axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
	                AxisAlignedBB axisalignedbb4 = entity.getEntityBoundingBox();
	                double d20 = y;
	                int l2 = 0;

	                for (int i3 = list.size(); l2 < i3; ++l2)
	                {
	                    d20 = ((AxisAlignedBB)list.get(l2)).calculateYOffset(axisalignedbb4, d20);
	                }

	                axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
	                double d21 = d2;
	                int j3 = 0;

	                for (int k3 = list.size(); j3 < k3; ++j3)
	                {
	                    d21 = ((AxisAlignedBB)list.get(j3)).calculateXOffset(axisalignedbb4, d21);
	                }

	                axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
	                double d22 = d4;
	                int l3 = 0;

	                for (int i4 = list.size(); l3 < i4; ++l3)
	                {
	                    d22 = ((AxisAlignedBB)list.get(l3)).calculateZOffset(axisalignedbb4, d22);
	                }

	                axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
	                double d23 = d18 * d18 + d19 * d19;
	                double d9 = d21 * d21 + d22 * d22;

	                if (d23 > d9)
	                {
	                    x = d18;
	                    z = d19;
	                    y = -d8;
	                    entity.setEntityBoundingBox(axisalignedbb2);
	                }
	                else
	                {
	                    x = d21;
	                    z = d22;
	                    y = -d20;
	                    entity.setEntityBoundingBox(axisalignedbb4);
	                }

	                int j4 = 0;

	                for (int k4 = list.size(); j4 < k4; ++j4)
	                {
	                    y = ((AxisAlignedBB)list.get(j4)).calculateYOffset(entity.getEntityBoundingBox(), y);
	                }

	                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0D, y, 0.0D));

	                if (d14 * d14 + d7 * d7 >= x * x + z * z)
	                {
	                    x = d14;
	                    y = d6;
	                    z = d7;
	                    entity.setEntityBoundingBox(axisalignedbb1);
	                }
	            }

	            entity.world.profiler.endSection();
	            entity.world.profiler.startSection("rest");
	            entity.resetPositionToBB();
	            entity.collidedHorizontally = d2 != x || d4 != z;
	            entity.collidedVertically = d3 != y;
	            entity.onGround = entity.collidedVertically && d3 < 0.0D;
	            entity.collided = entity.collidedHorizontally || entity.collidedVertically;
	            int j6 = MathHelper.floor(entity.posX);
	            int i1 = MathHelper.floor(entity.posY - 0.20000000298023224D);
	            int k6 = MathHelper.floor(entity.posZ);
	            BlockPos blockpos = new BlockPos(j6, i1, k6);
	            IBlockState iblockstate = entity.world.getBlockState(blockpos);

	            if (iblockstate.getMaterial() == Material.AIR)
	            {
	                BlockPos blockpos1 = blockpos.down();
	                IBlockState iblockstate1 = entity.world.getBlockState(blockpos1);
	                Block block1 = iblockstate1.getBlock();

	                if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate)
	                {
	                    iblockstate = iblockstate1;
	                    blockpos = blockpos1;
	                }
	            }

	            try {
					Method updateFallState = ObfuscationReflectionHelper.findMethod(Entity.class, "func_184231_a", Void.class, double.class, boolean.class, IBlockState.class, BlockPos.class);
					updateFallState.invoke(entity, y, entity.onGround, iblockstate, blockpos);
				} catch (Exception e) {
					e.printStackTrace();
				}

	            if (d2 != x)
	            {
	                entity.motionX = 0.0D;
	            }

	            if (d4 != z)
	            {
	                entity.motionZ = 0.0D;
	            }

	            Block block = iblockstate.getBlock();

	            if (d3 != y)
	            {
	                block.onLanded(entity.world, entity);
	            }

	            try
	            {
	            	try {
						Method doBlockCollisions = ObfuscationReflectionHelper.findMethod(Entity.class, "func_145775_I", Void.class);
						doBlockCollisions.invoke(entity);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            catch (Throwable throwable)
	            {
	                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
	                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
	                entity.addEntityCrashInfo(crashreportcategory);
	                throw new ReportedException(crashreport);
	            }
	            
	            boolean flag1 = entity.isWet();
				if (flag1 && entity.isBurning()) {
					Random rand = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, "field_70146_Z");
					entity.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F,
							1.6F + (rand.nextFloat()
									- rand.nextFloat()) * 0.4F);
				}

	            entity.world.profiler.endSection();
	        }
		}
	}

}
