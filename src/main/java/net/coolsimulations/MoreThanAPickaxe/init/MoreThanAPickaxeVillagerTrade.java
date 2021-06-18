package net.coolsimulations.MoreThanAPickaxe.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class MoreThanAPickaxeVillagerTrade {


	public static void init() {
		
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(MoreThanAPickaxeItems.bronze_adze), 12, 5, 0.05F));
		});
		
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, random.nextInt(2) + 7), EnchantmentHelper.enchant(random, new ItemStack(MoreThanAPickaxeItems.titanium_adze), 5 + random.nextInt(15), false), 12, 5, 0.05F));
		});
	}

}
