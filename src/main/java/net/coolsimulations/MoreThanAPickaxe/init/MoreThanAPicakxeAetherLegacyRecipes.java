package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.the_aether.advancements.AetherAdvancements;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.the_aether.blocks.natural.BlockAetherGrass;
import com.gildedgames.the_aether.blocks.natural.BlockAetherLog;
import com.gildedgames.the_aether.blocks.natural.BlockHolystone;
import com.gildedgames.the_aether.blocks.natural.BlockQuicksoil;
import com.gildedgames.the_aether.blocks.natural.ore.BlockAmbrosiumOre;
import com.gildedgames.the_aether.blocks.util.EnumLogType;
import com.gildedgames.the_aether.entities.bosses.EntityValkyrie;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.dungeon.ItemDungeonKey;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketExtendedAttack;
import com.gildedgames.the_aether.player.PlayerAether;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGravititeAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemZaniteAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thedarkcolour.futuremc.config.FConfig;

public class MoreThanAPicakxeAetherLegacyRecipes {

	@SubscribeEvent
	public void init(Register<AetherEnchantment> event) {

		IForgeRegistry<AetherEnchantment> registry = event.getRegistry();

		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.wooden_adze, 225));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.stone_adze, 550));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.iron_adze, 1750));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.gold_adze, 2500));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.diamond_adze, 5500));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.bronze_adze, 1750));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.titanium_adze, 2750));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.steel_adze, 2050));

		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.gravitite_adze, 5500));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.holystone_adze, 550));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.skyroot_adze, 225));
		registry.register(new AetherEnchantment(MoreThanAPickaxeItems.zanite_adze, 2250));

		if(SPCompatibilityManager.isGCLoaded() && SPCompatibilityManager.isGCPLoaded()) {
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.desh_adze, 3250));
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.sticky_desh_adze, 3250));
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.titanium_adze_gc, 2750));
		}

		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded() || SPConfig.enableEmeraldMaterial)
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.emerald_adze, 3250));

		if(SPCompatibilityManager.isObsidianMaterialModsLoaded() || SPConfig.enableObsidianMaterial)
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.obsidian_adze, 4250));

		if((SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite) || SPCompatibilityManager.isNetheriteModLoaded())
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.netherite_adze, 7250));

		if(SPCompatibilityManager.isVulcaniteLoaded())
			registry.register(new AetherEnchantment(MoreThanAPickaxeVulcanite.vulcanite_adze, 7250));

		if(SPCompatibilityManager.isCarbonadoLoaded())
			registry.register(new AetherEnchantment(MoreThanAPickaxeCarbonado.carbonado_adze, 5750));

		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			registry.register(new AetherEnchantment(MoreThanAPickaxeGalaxiteOre.galaxite_adze, 5750));

		if(SPCompatibilityManager.isTriGemsLoaded() || SPCompatibilityManager.isGACLoaded()) {
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.ruby_adze, 3750));
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.sapphire_adze, 3750));
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.topaz_adze, 3750));
		}

		if(SPCompatibilityManager.isGACLoaded()) {
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.amethyst_adze, 3750));
			registry.register(new AetherEnchantment(MoreThanAPickaxeItems.quartz_adze, 3750));
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void interact(PlayerInteractEvent.LeftClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItemStack();

		handleExtendedReach(player, stack);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initPlayer(EntityJoinWorldEvent event) {

		if(event.getEntity() instanceof EntityPlayer) {
			IPlayerAether instance = AetherAPI.getInstance().get((EntityPlayer) event.getEntity());

			if(!((PlayerAether) instance).extendedReachItems.contains(MoreThanAPickaxeItems.valkyrie_adze)) {
				ArrayList<Item> newList = new ArrayList<Item>();
				for(Item item : ((PlayerAether) instance).extendedReachItems)
					newList.add(item);
				newList.add(MoreThanAPickaxeItems.valkyrie_adze);
				((PlayerAether) instance).extendedReachItems = newList;
			}
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event)
	{
		if (event.player instanceof EntityPlayerMP)
		{
			if (event.crafting.getItem() == MoreThanAPickaxeItems.gravitite_adze)
			{
				AetherAdvancements.GRAV_TOOLSET_TRIGGER.trigger((EntityPlayerMP) event.player);
			}
		}
	}

	@SubscribeEvent
	public void addLoot(LootTableLoadEvent event) {
		if (event.getName().toString().equals(SPCompatibilityManager.AETHER_LEGACY_MODID + ":" + "chests/silver_dungeon_reward")) {
			LootTable inject = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MOD_ID + ":" + "aether_legacy_inject_silver_dungeon_reward"));
			LootPool injectPool = inject.getPool(Reference.MOD_ID);
			event.getTable().addPool(injectPool);
		}
	}

	@SubscribeEvent
	public void onEntityDropLoot(LivingDropsEvent event)
	{
		if (event.getSource() instanceof EntityDamageSource)
		{
			EntityLivingBase entity = event.getEntityLiving();
			EntityDamageSource source = (EntityDamageSource) event.getSource();

			if (source.getImmediateSource() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source.getImmediateSource();
				ItemStack currentItem = player.inventory.getCurrentItem();

				if (currentItem.getItem() == MoreThanAPickaxeItems.skyroot_adze && !(entity instanceof EntityPlayer) && !(entity instanceof EntityWither) && !(entity instanceof EntityValkyrie))
				{
					for (EntityItem items : event.getDrops())
					{
						ItemStack stack = items.getItem();

						if (!(stack.getItem() instanceof ItemDungeonKey) && stack.getItem() != ItemsAether.victory_medal && stack.getItem() != Items.SKULL)
						{
							EntityItem item = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, items.getItem());

							entity.world.spawnEntity(item);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.BreakEvent event) {

		if(event.getState().getBlock() instanceof BlockAetherLog) {
			int size = event.getState().getValue(BlockAetherLog.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if(event.getState().getBlock() instanceof BlockAetherDirt) {
			int size = event.getState().getValue(BlockAetherDirt.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if(event.getState().getBlock() instanceof BlockAetherGrass) {
			int size = event.getState().getValue(BlockAetherGrass.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if(event.getState().getBlock() instanceof BlockAmbrosiumOre) {
			int size = event.getState().getValue(BlockAmbrosiumOre.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if(event.getState().getBlock() instanceof BlockHolystone) {
			int size = event.getState().getValue(BlockHolystone.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if(event.getState().getBlock() instanceof BlockQuicksoil) {
			int size = event.getState().getValue(BlockQuicksoil.double_drop).equals(true) ? 1 : 0;
			doubleDropHelper(size, event.getState(), event.getPlayer(), event.getWorld(), event.getPos());
		}
		if (event.getState().getBlock() instanceof BlockAetherLog && event.getState().getValue(BlockAetherLog.wood_type) == EnumLogType.Oak && (event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemZaniteAdze || event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemGravititeAdze || event.getPlayer().getHeldItemMainhand().getItem()  == MoreThanAPickaxeItems.valkyrie_adze))
        {
			BlockAetherLog.spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(ItemsAether.golden_amber, 1 + new java.util.Random().nextInt(2)));
        }
	}
	
	public void doubleDropHelper(int size, IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		if (player.getHeldItemMainhand().getItem() == MoreThanAPickaxeItems.skyroot_adze)
		{
			for (int i = 0; i < size; ++i)
			{
				state.getBlock().dropBlockAsItem(world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
			}
		}
	}

	public void handleExtendedReach(EntityPlayer player, ItemStack stack) {

		if (stack.getItem() == MoreThanAPickaxeItems.valkyrie_adze)
		{
			Vec3d playerVision = player.getLookVec();
			AxisAlignedBB reachDistance = player.getEntityBoundingBox().grow(10.0D);

			List<Entity> locatedEntities = player.world.getEntitiesWithinAABB(Entity.class, reachDistance);

			Entity found = null;
			double foundLen = 0.0D;

			for (Object o : locatedEntities)
			{
				if (o == player)
				{
					continue;
				}

				Entity ent = (Entity) o;

				if (!ent.canBeCollidedWith() && !(ent instanceof EntityDragon))
				{
					continue;
				}

				Vec3d vec = new Vec3d(ent.posX - player.posX, ent.getEntityBoundingBox().minY + ent.height / 2f - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);
				double len = vec.lengthVector();

				if (len > 8.0F)
				{
					continue;
				}

				vec = vec.normalize();
				double dot = playerVision.dotProduct(vec);

				if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent))
				{
					continue;
				}

				if (foundLen == 0.0 || len < foundLen)
				{
					found = ent;
					foundLen = len;
				}
			}

			if (found != null && player.getRidingEntity() != found)
			{
				stack.damageItem(1, player);

				AetherNetworkingManager.sendToServer(new PacketExtendedAttack(found.getEntityId()));
			}
		}

	}

}
