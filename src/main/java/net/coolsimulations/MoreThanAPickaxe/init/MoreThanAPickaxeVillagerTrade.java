package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoreThanAPickaxeVillagerTrade {


	public static void init() {
		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeVillagerTrade());
	}@SubscribeEvent
	public void villagerTrades(VillagerTradesEvent event) {
		Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
		RandomSource random = RandomSource.create();

		if(event.getType() == VillagerProfession.TOOLSMITH) {
			trades.get(1).add(new BasicItemListing(3, new ItemStack(MoreThanAPickaxeItems.bronze_adze.get()), 12, 5));
			trades.get(2).add(new BasicItemListing(random.nextInt(2) + 7, EnchantmentHelper.enchantItem(random, new ItemStack(MoreThanAPickaxeItems.titanium_adze.get()), 5 + random.nextInt(15), false), 12, 5));
		}
	}

}
