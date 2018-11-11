package net.coolsimulations.MoreThanAPickaxe.recipes;

import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class MoreThanAPickaxeRecipes {
	
	public static void register() {
	
	addRecipe(new ItemStack(MoreThanAPickaxeItems.wooden_adze), new Object[]{"WWW","WX "," X ", 'X', "stickWood", 'W', "plankWood"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.stone_adze), new Object[]{"SSS","SX "," X ", 'X', "stickWood", 'S', "cobblestone"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.iron_adze), new Object[]{"III","IX "," X ", 'X', "stickWood", 'I', "ingotIron"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.gold_adze), new Object[]{"GGG","GX "," X ", 'X', "stickWood", 'G', "ingotGold"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.diamond_adze), new Object[]{"DDD","DX "," X ", 'X', "stickWood", 'D', "gemDiamond"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.bronze_adze), new Object[]{"BBB","BX "," X ", 'X', "stickWood", 'B', "ingotBronze"});
	addRecipe(new ItemStack(MoreThanAPickaxeItems.titanium_adze), new Object[]{"TTT","TX "," X ", 'X', "stickWood", 'T', "ingotTitanium"});
	
	if(OreDictionary.getOres("ingotSteel").size() > 0) {
		
		if(!SPCompatibilityManager.isGCLoaded()) {
			
			addRecipe(new ItemStack(MoreThanAPickaxeItems.steel_adze), new Object[]{"SSS","SX "," X ", 'X', "stickWood", 'T', "ingotSteel"});
		}
	}
	
	if(SPCompatibilityManager.isGCLoaded()) {
		
		addRecipe(new ItemStack(MoreThanAPickaxeItems.steel_adze), new Object[]{"SSS","SX "," X ", 'X', "stickWood", 'S', "compressedSteel"});
		
		if(SPCompatibilityManager.isGCPLoaded()) {
			
			addRecipe(new ItemStack(MoreThanAPickaxeItems.desh_adze), new Object[]{"DDD","DX "," X ", 'X', new ItemStack(MarsItems.marsItemBasic, 1, 1), 'D', "ingotDesh"});
			addRecipe(new ItemStack(MoreThanAPickaxeItems.titanium_adze_gc), new Object[]{"TTT","TX "," X ", 'X', "stickWood", 'T', new ItemStack(AsteroidsItems.basicItem, 1, 9)});
		}
	}

	}
	
	public static void addRecipe(ItemStack result, Object[] obj)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, obj));
    }
	
}
