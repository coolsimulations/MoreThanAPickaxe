package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Random;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class MoreThanAPickaxeVillagerTrade {


	public static class ToolSmithLevelOne implements ITradeList {

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 6), new ItemStack(MoreThanAPickaxeItems.bronze_adze)));
		}
	}
	
	public static class ToolSmithLevelTwo implements ITradeList {

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			new EntityVillager.ListEnchantedItemForEmeralds(MoreThanAPickaxeItems.titanium_adze, new PriceInfo(7,9)).addMerchantRecipe(merchant, recipeList, random);
		}
	}

	public static void init() {
		VillagerRegistry.VillagerProfession smith = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith"));
		smith.getCareer(2).addTrade(1, new ToolSmithLevelOne());
		smith.getCareer(2).addTrade(2, new ToolSmithLevelTwo());
	}

}
