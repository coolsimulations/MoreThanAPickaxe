package net.coolsimulations.MoreThanAPickaxe.init;

import com.brand.adabraniummod.content.stuff.materials.AdabraniumToolMaterials;
import com.kwpugh.emerald_tools.init.ItemInit;
import com.kwpugh.gobber2.Gobber2;
import com.kwpugh.more_gems.MoreGems;
import com.vanillaenhanced.materials.items.ToolMaterialAmethyst;
import com.vanillaenhanced.materials.items.ToolMaterialObsidian;
import com.vanillaenhanced.materials.items.ToolMaterialRuby;
import com.vanillaenhanced.materials.items.ToolMaterialSapphire;
import com.vanillaenhanced.materials.items.ToolMaterialSteel;

import de.deeprobin.amethyst_mod.AmethystMod;
import de.deeprobin.emeraldmod.EmeraldMod;
import de.deeprobin.ruby_mod.RubyMod;
import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.item.AdzeItemTier;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemEndAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGobberAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGravititeAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemValkyrieAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemZaniteAdze;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.item.SPItemTier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.id.aether.items.tools.AetherToolMaterials;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;

public class MoreThanAPickaxeItems {

	public static Item wooden_adze;
	public static Item stone_adze;
	public static Item iron_adze;
	public static Item gold_adze;
	public static Item diamond_adze;
	public static Item netherite_adze;
	public static Item bronze_adze;
	public static Item titanium_adze;

	public static Item steel_adze;
	public static Item desh_adze;
	public static Item titanium_adze_gc;

	//public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);

	//public static final Tag<Item> STEEL_INGOT = new ItemTags.Wrapper(new ResourceLocation("forge", "ingots/steel"));

	public static Item emerald_adze;
	public static Item obsidian_adze;

	public static Item amethyst_adze;
	public static Item ruby_adze;
	public static Item sapphire_adze;
	public static Item topaz_adze;
	public static Item spinel_adze;
	public static Item quartz_adze;
	
	public static Item citrine_adze;
	public static Item tourmaline_adze;
	public static Item alexandrite_adze;
	public static Item corundum_adze;
	public static Item carbonado_adze;
	public static Item kunzite_adze;
	public static Item moissanite_adze;
	
	public static Item gravitite_adze;
	public static Item zanite_adze;
	public static Item valkyrie_adze;
	
	public static Item adamantium_adze;
	public static Item vibranium_adze;
	public static Item nether_brick_adze;
	
	public static Item gobber_adze;
	public static Item nether_adze;
	public static Item end_adze;

	public static void init() {

		wooden_adze = new ItemAdze(Tiers.WOOD, 6.0F, -2.40F, new FabricItemSettings());
		stone_adze = new ItemAdze(Tiers.STONE, 8.0F, -2.40F, new FabricItemSettings());
		iron_adze = new ItemAdze(Tiers.IRON, 8.0F, -2.40F, new FabricItemSettings());
		gold_adze = new ItemAdze(Tiers.GOLD, 6.0F, -2.40F, new FabricItemSettings());
		diamond_adze = new ItemAdze(Tiers.DIAMOND, 8.0F, -2.40F, new FabricItemSettings());
		netherite_adze = new ItemAdze(Tiers.NETHERITE, 9.0F, -2.40F, new FabricItemSettings().fireproof());
		bronze_adze = new ItemAdze(SPItemTier.bronzeToolMaterial, 7.0F, -2.40F, new FabricItemSettings());
		titanium_adze = new ItemAdze(SPItemTier.titaniumToolMaterial, 8.0F, -2.40F, new FabricItemSettings());
	}
	
	public static void initCompat() {
		
		if(!SPCompatibilityManager.isGCLoaded()) {

			if(SPCompatibilityManager.isVanillaEnhancedLoaded()) {
				steel_adze = new ItemAdze(new ToolMaterialSteel(), 8.0F, -2.40F, new FabricItemSettings());
			} else if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
				steel_adze = new ItemAdze(ItemInit.STEEL_TOOL_MATERIAL, 8.0F, -2.40F, new FabricItemSettings());
			} else {
				steel_adze = new ItemAdze(AdzeItemTier.steelToolMaterial, 8.0F, -2.40F, new FabricItemSettings());
			}
		}

		/**if(SPCompatibilityManager.isGCLoaded()) {

			//steel_adze = new ItemAdze(GCItems.TOOL_STEEL, 4.0F, -2.4F, new FabricItemSettings());

			if(SPCompatibilityManager.isGCPLoaded()) {

				//desh_adze = new ItemAdze(MarsItems.TOOLDESH, 3.5F, -2.4F, new FabricItemSettings());
				//titanium_adze_gc = new ItemAdze(AsteroidsItems.TOOL_TITANIUM, 3.0F, -2.4F, new FabricItemSettings());
			}
		}**/

		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(SPCompatibilityManager.isRobinsEmeraldLoaded()) {
				emerald_adze = new ItemAdze(EmeraldMod.TOOL_MATERIAL, 6.0F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isSimpleEmeraldLoaded()) {
				emerald_adze = new ItemAdze(com.mrsebi123.emeraldmod.util.EmeraldToolMaterial.EMERALD, 6.0F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
				emerald_adze = new ItemAdze(ItemInit.EMERALD_TOOL_MATERIAL, 8.0F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isVanillaEnhancedLoaded()) {
				emerald_adze = new ItemAdze(new com.vanillaenhanced.materials.items.ToolMaterialEmerald(), 7.5F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isEmeraldEquipmentLoaded()) {
				emerald_adze = new ItemAdze(com.exline.emeraldequipment.item.EmeraldToolMaterial.INSTANCE, 7.5F, -2.40F, new FabricItemSettings(), true);	
			}
		}

		if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			if(SPCompatibilityManager.isObsidianEquipmentLoaded()) {
				obsidian_adze = new ItemAdze(com.exline.obsidianequipment.ModToolMaterials.OBSIDIAN, 8.4F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
				obsidian_adze = new ItemAdze(ItemInit.OBSIDIAN_TOOL_MATERIAL, 7.5F, -2.40F, new FabricItemSettings(), true);
			} else if(SPCompatibilityManager.isVanillaEnhancedLoaded()) {
				obsidian_adze = new ItemAdze(new ToolMaterialObsidian(), 8.0F, -2.40F, new FabricItemSettings(), true);
			}
		}
		
		if(SPCompatibilityManager.isRobinsRubyLoaded()) {
			ruby_adze = new ItemAdze(RubyMod.TOOL_MATERIAL, 6.0F, -1.80F, new FabricItemSettings(), true);
		} else if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
			ruby_adze = new ItemAdze(ItemInit.RUBY_TOOL_MATERIAL, 8.0F, -2.40F, new FabricItemSettings(), true);
		} else if (SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			ruby_adze = new ItemAdze(new ToolMaterialRuby(), 7.0F, -2.40F, new FabricItemSettings(), true);
		}
		
		if(SPCompatibilityManager.isMoreGemsLoaded()) {
			sapphire_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.SAPPHIRE_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
		} else if(SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			sapphire_adze = new ItemAdze(new ToolMaterialSapphire(), 7.0F, -2.40F, new FabricItemSettings(), true);
		}
		
		if(SPCompatibilityManager.isEasyEmeraldLoaded()) {
			amethyst_adze = new ItemAdze(ItemInit.AMETHYST_TOOL_MATERIAL, 6.5F, -2.40F, new FabricItemSettings(), true);
		} else if(SPCompatibilityManager.isRobinsAmethystLoaded()) {
			amethyst_adze = new ItemAdze(AmethystMod.TOOL_MATERIAL, 9.0F, -1.80F, new FabricItemSettings(), true);
		} else if(SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			amethyst_adze = new ItemAdze(new ToolMaterialAmethyst(), 7.0F, -2.40F, new FabricItemSettings(), true);
		}
		
		if(SPCompatibilityManager.isMoreGemsLoaded()) {
			topaz_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.TOPAZ_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			citrine_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.CITRINE_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			tourmaline_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.TOURMALINE_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			alexandrite_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.ALEXANDRITE_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			corundum_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.CORUNDUM_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			carbonado_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.CARBONADO_TOOL_MATERIAL, 8.0F, -2.40F, new FabricItemSettings(), true);
			kunzite_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.KUNZITE_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
			moissanite_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.MOISSANITE_TOOL_MATERIAL, 9.0F, -2.40F, new FabricItemSettings(), true);
			spinel_adze = new ItemAdze(com.kwpugh.more_gems.init.ItemInit.SPINEL_TOOL_MATERIAL, 7.0F, -2.40F, new FabricItemSettings(), true);
		}
		
		if(SPCompatibilityManager.isAetherRebornLoaded()) {
			gravitite_adze = new ItemGravititeAdze(AetherToolMaterials.GRAVITITE, 8.0F, -2.40F, new FabricItemSettings());
			zanite_adze = new ItemZaniteAdze(AetherToolMaterials.ZANITE, 8.0F, -2.40F, new FabricItemSettings());
			valkyrie_adze = new ItemValkyrieAdze(AetherToolMaterials.VALKYRIE, 8.0F, -2.40F, new FabricItemSettings());
		}
		
		if(SPCompatibilityManager.isAdabraniumLoaded()) {
			adamantium_adze = new ItemAdze(AdabraniumToolMaterials.ADAMANTIUM, 14.0F, -2.40F, new FabricItemSettings(), true);
			vibranium_adze = new ItemAdze(AdabraniumToolMaterials.VIBRANIUM, 11.0F, -2.40F, new FabricItemSettings(), true);
			nether_brick_adze = new ItemAdze(AdabraniumToolMaterials.NETHER_BRICK, 8.0F, -2.40F, new FabricItemSettings().fireproof(), true);
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			gobber_adze = new ItemGobberAdze(com.kwpugh.gobber2.init.ItemInit.GOBBER_TOOL_MATERIAL, 10.0F, -4.0F + Gobber2.CONFIG.GENERAL.gobberSwordAttackSpeed, new FabricItemSettings());
			nether_adze = new ItemGobberAdze(com.kwpugh.gobber2.init.ItemInit.GOBBER_NETHER_TOOL_MATERIAL, 12.0F, -4.0F + Gobber2.CONFIG.GENERAL.gobberNetherSwordAttackSpeed, new FabricItemSettings());
			end_adze = new ItemEndAdze(com.kwpugh.gobber2.init.ItemInit.GOBBER_END_TOOL_MATERIAL, 15.0F, -4.0F + Gobber2.CONFIG.GENERAL.gobberEndSwordAttackSpeed, new FabricItemSettings());
		}
	}

	public static void register()
	{

		registerItem(wooden_adze, "wooden_adze");
		registerItem(stone_adze, "stone_adze");
		registerItem(iron_adze, "iron_adze");
		registerItem(gold_adze, "gold_adze");
		registerItem(diamond_adze, "diamond_adze");
		registerItem(netherite_adze, "netherite_adze");
		registerItem(bronze_adze, "bronze_adze");
		registerItem(titanium_adze, "titanium_adze");
	}
	
	public static void registerCompat()
	{
		
		if(!SPCompatibilityManager.isGCLoaded()) {

			registerItem(steel_adze, "steel_adze");
		}

		/**if(SPCompatibilityManager.isGCLoaded()) {

			//registerItem(steel_adze, "steel_adze");

			if(SPCompatibilityManager.isGCPLoaded()) {

				//registerItem(desh_adze);
				//registerItem(titanium_adze_gc, "titanium_adze_gc");
			}
		} **/

		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			registerItem(emerald_adze, "emerald_adze");
		}

		if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			registerItem(obsidian_adze, "obsidian_adze");
		}
		
		if(SPCompatibilityManager.isRobinsRubyLoaded() || SPCompatibilityManager.isEasyEmeraldLoaded() || SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			registerItem(ruby_adze, "ruby_adze");
		}
		
		if(SPCompatibilityManager.isMoreGemsLoaded() || SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			registerItem(sapphire_adze, "sapphire_adze");
		}
		
		if(SPCompatibilityManager.isEasyEmeraldLoaded() || SPCompatibilityManager.isRobinsAmethystLoaded() || SPCompatibilityManager.isVanillaEnhancedLoaded()) {
			registerItem(amethyst_adze, "amethyst_adze");
		}
		
		if(SPCompatibilityManager.isMoreGemsLoaded()) {
			registerItem(topaz_adze, "topaz_adze");
			registerItem(citrine_adze, "citrine_adze");
			registerItem(tourmaline_adze, "tourmaline_adze");
			registerItem(alexandrite_adze, "alexandrite_adze");
			registerItem(corundum_adze, "corundum_adze");
			registerItem(carbonado_adze, "carbonado_adze");
			registerItem(kunzite_adze, "kunzite_adze");
			registerItem(moissanite_adze, "moissanite_adze");
			registerItem(spinel_adze, "spinel_adze");
		}
		
		if(SPCompatibilityManager.isAetherRebornLoaded()) {
			registerItem(gravitite_adze, "gravitite_adze");
			registerItem(zanite_adze, "zanite_adze");
			registerItem(valkyrie_adze, "valkyrie_adze");
		}
		
		if(SPCompatibilityManager.isAdabraniumLoaded()) {
			registerItem(adamantium_adze, "adamantium_adze");
			registerItem(vibranium_adze, "vibranium_adze");
			registerItem(nether_brick_adze, "nether_brick_adze");
		}
		
		if(SPCompatibilityManager.isGobberLoaded()) {
			registerItem(gobber_adze, "gobber_adze");
			registerItem(nether_adze, "nether_adze");
			registerItem(end_adze, "end_adze");
		}
	}

	public static void registerItem(Item item, String registryName) {

		Registry.register(Registry.ITEM, new ResourceLocation(Reference.MOD_ID, registryName), item);
	}
}
