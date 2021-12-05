package net.coolsimulations.MoreThanAPickaxe.recipes;

import ic2.api.item.IC2Items;
import mods.railcraft.common.items.RailcraftItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.coolsimulations.SurvivalPlus.api.compat.SPCompatRecipeManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoreThanAPickaxeSmeltingRecipes {
	
	public static void register() {
		
		GameRegistry.addSmelting(MoreThanAPickaxeItems.iron_adze, new ItemStack(Items.IRON_NUGGET), 0.1F);
		GameRegistry.addSmelting(MoreThanAPickaxeItems.gold_adze, new ItemStack(Items.GOLD_NUGGET), 0.1F);
		GameRegistry.addSmelting(MoreThanAPickaxeItems.bronze_adze, new ItemStack(SPItems.bronze_nugget), 0.1F);
		GameRegistry.addSmelting(MoreThanAPickaxeItems.titanium_adze, new ItemStack(SPItems.titanium_nugget), 0.1F);
		
		SPCompatRecipeManager.futureRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.iron_adze), new ItemStack(Items.IRON_NUGGET));
		SPCompatRecipeManager.futureRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.gold_adze), new ItemStack(Items.GOLD_NUGGET));
		SPCompatRecipeManager.futureRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.bronze_adze), new ItemStack(SPItems.bronze_nugget));
		SPCompatRecipeManager.futureRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.titanium_adze), new ItemStack(SPItems.titanium_nugget));
		
		if(SPConfig.enableReplaceSmelting && SPCompatibilityManager.isIc2Loaded()) {
			ItemStack steel = IC2Items.getItem("ingot", "steel");
			steel.setCount(4);
			
			SPCompatRecipeManager.railcraftRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.iron_adze), steel, 1);
		} else if(SPCompatibilityManager.isRailcraftLoaded()) {
			SPCompatRecipeManager.railcraftRecipeManager.addBlastFurnaceRecipe(new ItemStack(MoreThanAPickaxeItems.iron_adze), new ItemStack(RailcraftItems.INGOT.item(), 4), 1);
		}
	}

}
