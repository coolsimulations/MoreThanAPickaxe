package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.item.ItemDenseAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.minecraft.item.Item;

import com.ewyboy.bibliotheca.common.loaders.ItemLoader;

public class MoreThanAPickaxeHammerTime {
	
	public static void init() {
		ItemLoader.init(SPCompatibilityManager.HAMMER_TIME_MODID, Items.class);
	}
	
	public static class Items {

		public static ItemDenseAdze denseAdzeWood = new ItemDenseAdze(Item.ToolMaterial.WOOD);
		public static ItemDenseAdze denseAdzeStone = new ItemDenseAdze(Item.ToolMaterial.STONE);
		public static ItemDenseAdze denseAdzeIron = new ItemDenseAdze(Item.ToolMaterial.IRON);
		public static ItemDenseAdze denseAdzeGold = new ItemDenseAdze(Item.ToolMaterial.GOLD);
		public static ItemDenseAdze denseAdzeDiamond = new ItemDenseAdze(Item.ToolMaterial.DIAMOND);
		public static ItemDenseAdze denseAdzeBronze = new ItemDenseAdze(SPItems.bronzeToolMaterial);
		public static ItemDenseAdze denseAdzeTitanium = new ItemDenseAdze(SPItems.titaniumToolMaterial);
	}

}
