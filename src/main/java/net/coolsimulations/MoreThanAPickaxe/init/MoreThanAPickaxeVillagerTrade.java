package net.coolsimulations.MoreThanAPickaxe.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

public class MoreThanAPickaxeVillagerTrade {


	public static void init() {
		
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 1, factories -> {
            factories.add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(MoreThanAPickaxeItems.bronze_adze), 12, 5, 0.05F));
		});
		
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            factories.add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, random.nextInt(2) + 7), EnchantmentHelper.enchantItem(random, new ItemStack(MoreThanAPickaxeItems.titanium_adze), 5 + random.nextInt(15), false), 12, 5, 0.05F));
		});
	}

}
