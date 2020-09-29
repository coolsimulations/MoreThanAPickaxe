package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import micdoodle8.mods.galacticraft.planets.mars.blocks.BlockCreeperEgg;
import micdoodle8.mods.galacticraft.planets.mars.blocks.BlockSlimelingEgg;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.coolsimulations.MoreThanAPickaxe.MoreThanAPickaxe;
import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemDenseAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.SPReference;
import net.insane96mcp.carbonado.init.ModItems;
import net.insane96mcp.carbonado.lib.Properties;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import thedarkcolour.futuremc.config.FConfig;
import thedarkcolour.futuremc.recipe.smithing.SmithingRecipe;
import thedarkcolour.futuremc.recipe.smithing.SmithingRecipes;

public class MoreThanAPickaxeEventHandler {

	@SubscribeEvent
	public void onplayerLogin(PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.player;
		NBTTagCompound entityData = player.getEntityData();

		AdvancementManager manager = player.getServer().getAdvancementManager();
		Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));

		boolean isDone = false;
		
		Timer timer = new Timer();

		if(install !=null && player.getAdvancements().getProgress(install).hasProgress()) {
			isDone = true;
		}

		if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && !SPConfig.disableThanks) {

			entityData.setBoolean("morethanapickaxe.firstJoin", true);

			if(!player.world.isRemote) {

				TextComponentTranslation installInfo = new TextComponentTranslation("advancements.morethanapickaxe.install.display1");
				installInfo.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(installInfo);

			}
		}

		if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck == false) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					player.sendMessage(MoreThanAPickaxeUpdateHandler.updateInfo);
					player.sendMessage(MoreThanAPickaxeUpdateHandler.updateVersionInfo);
				}
			}, 16000);

		}
	}
	
	@SubscribeEvent
	public void anvilRecipe(AnvilUpdateEvent event) {
		if(SPCompatibilityManager.isCarbonadoLoaded()) {
			
			ItemStack left = event.getLeft();
			ItemStack right = event.getRight();
			ItemStack output = null;
			int carbonadoAmount = 4;
			
			if (Properties.config.tools.enableAnvilCrafting && left.getItem().equals(MoreThanAPickaxeItems.diamond_adze) && right.getItem().equals(ModItems.carbonadoItem) && right.getCount() >= carbonadoAmount) {
				
				output = new ItemStack(MoreThanAPickaxeCarbonado.carbonado_adze);
				NBTTagCompound tags = left.getTagCompound();
				output.setTagCompound(tags);
				event.setOutput(output);
				event.setMaterialCost(carbonadoAmount);
				
				int cost = 0;
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
				for (Enchantment enchantment : enchantments.keySet()) {
					int lvl = enchantments.get(enchantment);
					int baseCost = 0;
					switch (enchantment.getRarity())
	                {
	                    case COMMON:
	                    	baseCost = 1;
	                        break;
	                    case UNCOMMON:
	                    	baseCost = 2;
	                        break;
	                    case RARE:
	                    	baseCost = 4;
	                        break;
	                    case VERY_RARE:
	                    	baseCost = 8;
	                }
					cost += baseCost * lvl;
				}
				cost *= 0.5f;
				event.setCost(MathHelper.clamp(cost, 1, 39));
			}
		}
	}
	
	/**
	 * Warning! Below are spoilers for how to make a Sticky Desh Pickaxe in Galacticraft Planets
	 */
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if(SPCompatibilityManager.isGCPLoaded()) {
			ItemStack itemStack = event.getPlayer().getHeldItemMainhand();
			Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
			if(block instanceof BlockCreeperEgg && itemStack.getItem() == MoreThanAPickaxeItems.sticky_desh_adze) {
				block.harvestBlock(event.getWorld(), event.getPlayer(), event.getPos(), event.getWorld().getBlockState(event.getPos()), null, itemStack);
			}
			if(block instanceof BlockSlimelingEgg) {
				if(itemStack.getItem() instanceof ItemAdze || itemStack.getItem() instanceof ItemDenseAdze || itemStack.getItem() instanceof ItemPickaxe) {
					event.getWorld().setBlockToAir(event.getPos());
					event.getPlayer().addStat(StatList.getBlockStats(block));
					event.getPlayer().addExhaustion(0.025F);
		            block.dropBlockAsItem(event.getWorld(), event.getPos(), event.getState().getBlock().getStateFromMeta(event.getState().getBlock().getMetaFromState(event.getState()) % 3), 0);
				}
				if(itemStack.getItem() == MarsItems.deshPickaxe) {
					if(itemStack.isItemEnchanted() && EnchantmentHelper.getEnchantments(itemStack).containsKey(Enchantments.SILK_TOUCH)) {
						ItemStack sticky_desh = new ItemStack(MarsItems.deshPickSlime);
						sticky_desh.setTagCompound(itemStack.getTagCompound());
						if (ItemStack.areItemStacksEqual(event.getPlayer().getHeldItemOffhand(), itemStack))
						{
							event.getPlayer().setHeldItem(EnumHand.OFF_HAND, sticky_desh);
						}
						else
						{
							event.getPlayer().setHeldItem(EnumHand.MAIN_HAND, sticky_desh);
						}
					}
				}
				if(itemStack.getItem() == MoreThanAPickaxeItems.desh_adze) {
					if(itemStack.isItemEnchanted() && EnchantmentHelper.getEnchantments(itemStack).containsKey(Enchantments.SILK_TOUCH)) {
						ItemStack sticky_desh = new ItemStack(MoreThanAPickaxeItems.sticky_desh_adze);
						sticky_desh.setTagCompound(itemStack.getTagCompound());
						if (ItemStack.areItemStacksEqual(event.getPlayer().getHeldItemOffhand(), itemStack))
						{
							event.getPlayer().setHeldItem(EnumHand.OFF_HAND, sticky_desh);
						}
						else
						{
							event.getPlayer().setHeldItem(EnumHand.MAIN_HAND, sticky_desh);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockClick(PlayerInteractEvent.LeftClickBlock event) {
		if(SPCompatibilityManager.isGCPLoaded()) {
			Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
			if((block instanceof BlockCreeperEgg) && (event.getItemStack().getItem() == MoreThanAPickaxeItems.sticky_desh_adze || event.getItemStack().getItem() == MarsItems.deshPickSlime)) {
				event.setUseItem(event.getUseItem().ALLOW);
				block.setHardness(0.2F);
			} else if((block instanceof BlockCreeperEgg) && (event.getItemStack().getItem() != MoreThanAPickaxeItems.sticky_desh_adze || event.getItemStack().getItem() != MarsItems.deshPickSlime)) {
				event.setUseItem(event.getUseItem().DENY);
				block.setHardness(block.getPlayerRelativeBlockHardness(event.getWorld().getBlockState(event.getPos()), event.getEntityPlayer(), event.getWorld(), event.getPos()));
			}
			if((block instanceof BlockSlimelingEgg) && (event.getItemStack().getItem() instanceof ItemAdze || event.getItemStack().getItem() instanceof ItemDenseAdze || event.getItemStack().getItem() instanceof ItemPickaxe)) {
				event.setUseItem(event.getUseItem().ALLOW);
			} else if((block instanceof BlockSlimelingEgg) && !(event.getItemStack().getItem() instanceof ItemAdze || event.getItemStack().getItem() instanceof ItemDenseAdze || event.getItemStack().getItem() instanceof ItemPickaxe)) {
				event.setUseItem(event.getUseItem().DENY);
			}
		}
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		MoreThanAPickaxeItems.registerItems(event.getRegistry());
	}

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event)
	{
		for(Item item : MoreThanAPickaxe.ITEMS) {
			MoreThanAPickaxeItems.registerRenders();
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();

		if(SPCompatibilityManager.isGCLoaded()) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "steel_adze"));	
		}
		
		if(SPCompatibilityManager.isNoTreePunchingLoaded()) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "wooden_adze"));
			modRegistry.remove(new ResourceLocation(SPCompatibilityManager.NO_TREE_PUNCHING_MODID + ":" + "tools/iron_mattock"));
			modRegistry.remove(new ResourceLocation(SPCompatibilityManager.NO_TREE_PUNCHING_MODID + ":" + "tools/gold_mattock"));
			modRegistry.remove(new ResourceLocation(SPCompatibilityManager.NO_TREE_PUNCHING_MODID + ":" + "tools/diamond_mattock"));
			modRegistry.remove(new ResourceLocation(SPCompatibilityManager.NO_TREE_PUNCHING_MODID + ":" + "bronze_mattock"));
			modRegistry.remove(new ResourceLocation(SPCompatibilityManager.NO_TREE_PUNCHING_MODID + ":" + "steel_mattock"));
			modRegistry.remove(new ResourceLocation(SPReference.MOD_ID + ":" + "titanium_mattock"));
			modRegistry.remove(new ResourceLocation(SPReference.MOD_ID + ":" + "desh_mattock"));
		}
		
		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "netherite_adze"));
			SmithingRecipes.INSTANCE.getRecipes().add(new SmithingRecipe(new ItemStack(MoreThanAPickaxeItems.diamond_adze), new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.FUTURE_MC_MODID, "netherite_ingot"))), new ItemStack(MoreThanAPickaxeItems.netherite_adze)));
		}
	}

}
