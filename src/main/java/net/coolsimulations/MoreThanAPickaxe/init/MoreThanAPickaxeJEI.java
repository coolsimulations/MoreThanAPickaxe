package net.coolsimulations.MoreThanAPickaxe.init;

import javax.annotation.Nonnull;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class MoreThanAPickaxeJEI implements IModPlugin{
	
	@Override
	public void register(@Nonnull IModRegistry registry) {

		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.initJEI(registry);
		
		if(SPCompatibilityManager.isNoTreePunchingLoaded())
			registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(MoreThanAPickaxeItems.wooden_adze));
	}

}
