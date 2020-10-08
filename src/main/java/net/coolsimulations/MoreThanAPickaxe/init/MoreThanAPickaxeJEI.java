package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import insane96mcp.carbonado.setup.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class MoreThanAPickaxeJEI implements IModPlugin {

	private static final ResourceLocation UID = new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID);

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		final IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
		
		List<Object> recipes = new ArrayList<>();
		
		if(SPCompatibilityManager.isCarbonadoLoaded())
			recipes.add(factory.createAnvilRecipe(new ItemStack(MoreThanAPickaxeItems.diamond_adze), Collections.singletonList(new ItemStack(ModItems.CARBONADO.get(), 4)), Collections.singletonList(new ItemStack(MoreThanAPickaxeCarbonado.carbonado_adze))));
		if(SPCompatibilityManager.isVulcaniteLoaded())
			recipes.add(factory.createAnvilRecipe(new ItemStack(MoreThanAPickaxeItems.iron_adze), Collections.singletonList(new ItemStack(insane96mcp.vulcanite.setup.ModItems.VULCANITE_NUGGET.get(), Math.round(4 * 9 * .25f))), Collections.singletonList(new ItemStack(MoreThanAPickaxeVulcanite.vulcanite_adze))));

		registration.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
	}

}
