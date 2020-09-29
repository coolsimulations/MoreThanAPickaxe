package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.Collections;

import javax.annotation.Nonnull;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.insane96mcp.carbonado.init.ModItems;
import net.insane96mcp.carbonado.lib.Properties;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class MoreThanAPickaxeJEI extends BlankModPlugin{
	
	@Override
	public void register(@Nonnull IModRegistry registry) {

		if(SPCompatibilityManager.isCarbonadoLoaded())
			if(Properties.config.tools.enableAnvilCrafting)
			registry.addAnvilRecipe(new ItemStack(MoreThanAPickaxeItems.diamond_adze), Collections.singletonList(new ItemStack(ModItems.carbonadoItem, 4)), Collections.singletonList(new ItemStack(MoreThanAPickaxeCarbonado.carbonado_adze)));
		
		if(SPCompatibilityManager.isNoTreePunchingLoaded())
			registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(MoreThanAPickaxeItems.wooden_adze));
	}

}
