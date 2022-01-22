package net.coolsimulations.MoreThanAPickaxe.init;

import com.kwpugh.gobber.init.ModItems;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoreThanAPickaxeGobber {
	
	public static void init() {
		
		ItemStack adze = new ItemStack(MoreThanAPickaxeItems.gobber_adze);
		adze.addEnchantment(Enchantments.SILK_TOUCH, 1);
		
		GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MOD_ID, "gobber_adze_silk"), null, adze, Ingredient.fromItems(MoreThanAPickaxeItems.gobber_adze), Ingredient.fromItems(ModItems.GLOBOT_MEDALLION_SILK));
	}

}
