package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Collections;

import javax.annotation.Nonnull;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.carbonado.init.ModItems;
import net.insane96mcp.carbonado.item.material.ModMaterial;
import net.insane96mcp.carbonado.lib.Properties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MoreThanAPickaxeCarbonado {
	
	public static Item carbonado_adze;
	
	public static void init() {
		
		carbonado_adze = new ItemAdze(ModMaterial.Tools, 6.0F, -2.4F, true).setUnlocalizedName("carbonado_adze").setRegistryName("carbonado_adze").setCreativeTab(SPTabs.tabTools);
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(carbonado_adze);
	}
	
	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(carbonado_adze);
	}
	
	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeCarbonado.carbonado_adze, ModItems.carbonadoItem);
	}
	
	public static void initJEI(@Nonnull IModRegistry registry) {
		
		if(Properties.config.tools.enableAnvilCrafting) {
			AnvilRecipeWrapper anvilRecipe = new AnvilRecipeWrapper(Collections.singletonList(new ItemStack(MoreThanAPickaxeItems.diamond_adze)), Collections.singletonList(new ItemStack(ModItems.carbonadoItem, 4)), Collections.singletonList(new ItemStack(MoreThanAPickaxeCarbonado.carbonado_adze)));
			registry.addRecipes(Collections.singletonList(anvilRecipe), VanillaRecipeCategoryUid.ANVIL);
		}
	}

}
