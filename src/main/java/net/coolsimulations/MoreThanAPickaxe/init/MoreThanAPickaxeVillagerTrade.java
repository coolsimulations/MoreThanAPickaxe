package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.List;
import java.util.Random;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoreThanAPickaxeVillagerTrade {


	public static void init() {
		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeVillagerTrade());
	}@SubscribeEvent
	public void villagerTrades(VillagerTradesEvent event) {
		Int2ObjectMap<List<ITrade>> trades = event.getTrades();
		Random random = new Random();

		if(event.getType() == VillagerProfession.TOOLSMITH) {
			trades.get(1).add(new BasicTrade(3, new ItemStack(MoreThanAPickaxeItems.bronze_adze), 12, 5));
			trades.get(2).add(new BasicTrade(random.nextInt(2) + 7, EnchantmentHelper.addRandomEnchantment(random, new ItemStack(MoreThanAPickaxeItems.titanium_adze), 5 + random.nextInt(15), false), 12, 5));
		}
	}

}
