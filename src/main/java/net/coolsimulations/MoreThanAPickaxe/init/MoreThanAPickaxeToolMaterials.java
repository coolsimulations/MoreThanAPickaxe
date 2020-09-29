package net.coolsimulations.MoreThanAPickaxe.init;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPBlocks;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thedarkcolour.futuremc.config.FConfig;

public class MoreThanAPickaxeToolMaterials {
	
	public static void init() {
		
		if(SPCompatibilityManager.isEmeraldMatieralModsLoaded() || SPConfig.enableEmeraldMaterial)
			setMaterial(MoreThanAPickaxeItems.emerald_adze, Items.EMERALD);
		if(SPCompatibilityManager.isObsidianMatieralModsLoaded() || SPConfig.enableObsidianMaterial)
			setMaterial(MoreThanAPickaxeItems.obsidian_adze, Blocks.OBSIDIAN);
		if(SPCompatibilityManager.isFutureMCLoaded() && FConfig.INSTANCE.getNetherUpdate().netherite)
			setMaterial(MoreThanAPickaxeItems.netherite_adze, Item.REGISTRY.getObject(new ResourceLocation(SPCompatibilityManager.FUTURE_MC_MODID, "netherite_ingot")));
		else if(SPCompatibilityManager.isNetheriteModLoaded())
			setMaterial(MoreThanAPickaxeItems.netherite_adze, com.flameknightgd.netherite.init.ModItems.NETHERITE_INGOT);
		if(SPCompatibilityManager.isVulcaniteLoaded())
			MoreThanAPickaxeVulcanite.registerToolMaterial();
		if(SPCompatibilityManager.isCarbonadoLoaded())
			MoreThanAPickaxeCarbonado.registerToolMaterial();
		if(SPCompatibilityManager.isGalaxiteOreLoaded())
			MoreThanAPickaxeGalaxiteOre.registerToolMaterial();
			
		if(SPCompatibilityManager.isTriGemsLoaded() || SPCompatibilityManager.isGACLoaded()) {
			setMaterial(MoreThanAPickaxeItems.ruby_adze, SPBlocks.ruby);
			setMaterial(MoreThanAPickaxeItems.sapphire_adze, SPBlocks.sapphire);
			setMaterial(MoreThanAPickaxeItems.topaz_adze, SPBlocks.topaz);
		}
		if(SPCompatibilityManager.isGACLoaded()) {
			setMaterial(MoreThanAPickaxeItems.amethyst_adze, SPBlocks.amethyst);
			setMaterial(MoreThanAPickaxeItems.quartz_adze, Items.QUARTZ);
		}
		if(SPCompatibilityManager.isAetherLegacyLoaded()) {
			setMaterial(MoreThanAPickaxeItems.gravitite_adze, BlocksAether.enchanted_gravitite);
			setMaterial(MoreThanAPickaxeItems.holystone_adze, BlocksAether.holystone);
			setMaterial(MoreThanAPickaxeItems.skyroot_adze, BlocksAether.skyroot_plank);
			setMaterial(MoreThanAPickaxeItems.zanite_adze, ItemsAether.zanite_gemstone);
		}
	}
	
	public static void setMaterial(Item adze, Item repair) {
		
		setMaterial(adze, new ItemStack(repair));
	}
	
	public static void setMaterial(Item adze, Block repair) {
		
		setMaterial(adze, new ItemStack(repair));
	}
	
	public static void setMaterial(Item adze, ItemStack repair) {
		
		if(!(adze instanceof ItemAdze))
			throw new RuntimeException("Can not set tool repair material because item is not an Adze");
		
		String name = (((ItemAdze) adze).getToolMaterialName());
		if(ToolMaterial.valueOf(name).getRepairItemStack().isEmpty()) {
			ToolMaterial.valueOf(name).setRepairItem(repair);
		}
	}

}
