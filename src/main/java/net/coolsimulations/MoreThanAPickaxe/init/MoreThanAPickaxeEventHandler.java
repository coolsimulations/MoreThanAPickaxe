package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import insane96mcp.vulcanite.setup.ModItems;
import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class MoreThanAPickaxeEventHandler {

	public List<ItemEntity> fireProofItems = new ArrayList<ItemEntity>();

	@SubscribeEvent
	public void onplayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		CompoundNBT entityData = player.getPersistentData();

		AdvancementManager manager = player.getServer().getAdvancementManager();
		Advancement install = manager.getAdvancement(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID + "/install"));

		boolean isDone = false;

		Timer timer = new Timer();

		if(install !=null && player.getAdvancements().getProgress(install).hasProgress()) {
			isDone = true;
		}

		if(!entityData.getBoolean("morethanapickaxe.firstJoin") && !isDone && SPConfig.disableThanks.get()) {

			entityData.putBoolean("morethanapickaxe.firstJoin", true);

			if(!player.world.isRemote) {

				TranslationTextComponent installInfo = new TranslationTextComponent("advancements.morethanapickaxe.install.display1");
				installInfo.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(installInfo);

			}
		}

		if(MoreThanAPickaxeUpdateHandler.isOld == true && SPConfig.disableUpdateCheck.get() == false) {
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
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if(SPCompatibilityManager.isVulcaniteLoaded()) {
			if (right.getItem().equals(ModItems.VULCANITE_NUGGET.get()) && left.getItem().equals(MoreThanAPickaxeItems.iron_adze) && right.getCount() >= Math.round(4 * 9 * .25f)) {

				int nuggetCost = Math.round(4 * 9 * .25f);

				String itemName = left.getItem().getRegistryName().getPath();
				ItemStack output = new ItemStack(MoreThanAPickaxeVulcanite.vulcanite_adze);
				CompoundNBT tags = left.getTag();
				output.setTag(tags);
				event.setOutput(output);
				event.setMaterialCost(nuggetCost);

				int minCost = ModList.get().isLoaded("charm") ? 0 : 1;

				int cost = minCost;

				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
				for (Enchantment enchantment : enchantments.keySet()) {
					int lvl = enchantments.get(enchantment);
					int baseCost = 0;
					switch (enchantment.getRarity()) {
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
				cost *= 0.25f;

				event.setCost(MathHelper.clamp(cost, minCost, 39));
			}
		}
		if(SPCompatibilityManager.isCarbonadoLoaded()) {
			if (right.getItem().equals(insane96mcp.carbonado.setup.ModItems.CARBONADO.get()) && left.getItem().equals(MoreThanAPickaxeItems.diamond_adze) && right.getCount() >= 4) {

				ItemStack output = new ItemStack(MoreThanAPickaxeCarbonado.carbonado_adze);
				CompoundNBT tags;
				if (left.getTag() != null)
					tags = left.getTag().copy();
				else
					tags = new CompoundNBT();
				tags.putInt("Damage", 0);
				output.setTag(tags);
				event.setOutput(output);
				event.setMaterialCost(4);

				int minCost = ModList.get().isLoaded("charm") ? 0 : 1;

				int cost = minCost;

				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
				for (Enchantment enchantment : enchantments.keySet()) {
					int lvl = enchantments.get(enchantment);
					int baseCost = 0;
					switch (enchantment.getRarity()) {
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
				cost *= 0.667f;

				event.setCost(MathHelper.clamp(cost, minCost, 39));
			}
		}
	}

	@SubscribeEvent
	public void itemDropped(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof ItemEntity && (SPCompatibilityManager.isExtendedNetherBackportLoaded() || SPCompatibilityManager.isFutureMCLoaded())) {
			ItemEntity itemEntity = (ItemEntity) entity;
			if (itemEntity.getItem().getItem() == MoreThanAPickaxeItems.netherite_adze) {
				itemEntity.setInvulnerable(true);
				this.fireProofItems.add(itemEntity);
			}
		}
	}

	@SubscribeEvent
	public void tick(WorldTickEvent event) {
		if(SPCompatibilityManager.isExtendedNetherBackportLoaded() || SPCompatibilityManager.isFutureMCLoaded()) {
			try {
				List<ItemEntity> toRemove = new ArrayList<ItemEntity>();
				Iterator<ItemEntity> var3 = this.fireProofItems.iterator();

				while (var3.hasNext()) {
					ItemEntity item = (ItemEntity) var3.next();
					if (item.isAlive()) {
						if (item.isInLava()) {
							item.addVelocity(0.0D, 0.025D, 0.0D);
						}
					} else {
						toRemove.add(item);
					}
				}

				this.fireProofItems.removeAll(toRemove);
			} catch (ConcurrentModificationException var5) {
				var5.printStackTrace();
			}
		}
	}


	/**@SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();

		if(SPCompatibilityManager.isGCLoaded()) {
			modRegistry.remove(new ResourceLocation(Reference.MOD_ID + ":" + "steel_adze"));	
		}
    }**/

}
