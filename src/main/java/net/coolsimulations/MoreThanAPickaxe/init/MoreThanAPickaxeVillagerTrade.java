package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Random;

import net.coolsimulations.SurvivalPlus.api.recipes.SPBasicTrade;
import net.coolsimulations.SurvivalPlus.api.recipes.SPEnchantTrade;
import net.coolsimulations.SurvivalPlus.api.recipes.SPTradeRecipes;
import net.coolsimulations.SurvivalPlus.api.recipes.SPTradeRecipes.VillagerLevel;
import net.minecraft.item.ItemStack;
import net.minecraft.village.VillagerProfession;

public class MoreThanAPickaxeVillagerTrade {


	public static void init() {
		
		Random random = new Random();

		SPTradeRecipes.addBasicRecipe(VillagerProfession.TOOLSMITH, VillagerLevel.getIDByLevel(1), new SPBasicTrade(3, new ItemStack(MoreThanAPickaxeItems.bronze_adze), 12, 5));
		SPTradeRecipes.addBasicRecipe(VillagerProfession.TOOLSMITH, VillagerLevel.getIDByLevel(2), new SPEnchantTrade(random.nextInt(2) + 7, MoreThanAPickaxeItems.titanium_adze, 12, 5));
	}

}
