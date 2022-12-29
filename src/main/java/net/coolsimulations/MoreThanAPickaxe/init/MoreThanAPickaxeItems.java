package net.coolsimulations.MoreThanAPickaxe.init;

import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.kwpugh.gobber2.lists.tiers.ToolMaterialTiers;

import at.xander.fancytools.FancyToolsMod;
import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.item.AdzeItemTier;
import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemEndAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGobberAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGravititeAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemHolystoneAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemSkyrootAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemValkyrieAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemZaniteAdze;
import net.coolsimulations.SurvivalPlus.api.item.SPItemTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xmods.ota.api.ToolMaterialList;

public class MoreThanAPickaxeItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_STEEL = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_EMERALD_FANCY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_EMERALD_SIMPLE = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_EMERALD_EASY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_EMERALD_EQUIPMENT = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_OBSIDIAN_FANCY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_OBSIDIAN_OAAT = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_OBSIDIAN_EQUIPMENT = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_OBSIDIAN_EASY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_EASY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_GOBBER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS_AETHER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	
	public static final RegistryObject<Item> wooden_adze = ITEMS.register("wooden_adze", () -> new ItemAdze(Tiers.WOOD, 6.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> stone_adze = ITEMS.register("stone_adze", () -> new ItemAdze(Tiers.STONE, 8.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> iron_adze = ITEMS.register("iron_adze", () -> new ItemAdze(Tiers.IRON, 8.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> gold_adze = ITEMS.register("gold_adze", () -> new ItemAdze(Tiers.GOLD, 6.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> diamond_adze = ITEMS.register("diamond_adze", () -> new ItemAdze(Tiers.DIAMOND, 8.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> netherite_adze = ITEMS.register("netherite_adze", () -> new ItemAdze(Tiers.NETHERITE, 9.0F, -2.40F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> bronze_adze = ITEMS.register("bronze_adze", () -> new ItemAdze(SPItemTier.bronzeToolMaterial, 7.0F, -2.40F, new Item.Properties()));
	public static final RegistryObject<Item> titanium_adze = ITEMS.register("titanium_adze", () -> new ItemAdze(SPItemTier.titaniumToolMaterial, 8.0F, -2.40F, new Item.Properties()));

	public static final RegistryObject<Item> steel_adze = ITEMS_STEEL.register("steel_adze", () -> new ItemAdze(AdzeItemTier.steelToolMaterial, 8.0F, -2.40F, new Item.Properties()));
	//public static final RegistryObject<Item> desh_adze;
	//public static final RegistryObject<Item> titanium_adze_gc;

	//public static ToolMaterial steelToolMaterial = EnumHelper.addToolMaterial("steel_adze", 2, 500, 7, 2.5F, 9);

	//public static final TagKey<Item> STEEL_INGOT = new ItemTags.create(new ResourceLocation("forge", "ingots/steel"));
	
	public static final RegistryObject<Item> emerald_adze = RegistryObject.create(new ResourceLocation(Reference.MOD_ID, "emerald_adze"), ForgeRegistries.ITEMS);
	public static final RegistryObject<Item> obsidian_adze = RegistryObject.create(new ResourceLocation(Reference.MOD_ID, "obsidian_adze"), ForgeRegistries.ITEMS);
	
	public static final RegistryObject<Item> emerald_adze_fancy = ITEMS_EMERALD_FANCY.register("emerald_adze", () -> new ItemAdze(FancyToolsMod.getInstance().getConfig().getEmerald(), 8.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> emerald_adze_simple = ITEMS_EMERALD_SIMPLE.register("emerald_adze", () -> new ItemAdze(com.technovision.emeraldmod.util.enums.ModItemTier.EMERALD, 8.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> emerald_adze_easy = ITEMS_EMERALD_EASY.register("emerald_adze", () -> new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialTiers.EMERALD, 7.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> emerald_adze_equipment = ITEMS_EMERALD_EQUIPMENT.register("emerald_adze", () -> new ItemAdze(com.exline.emeraldequipment.items.ModItemTier.EMERALD, 8.5F, -2.4F, new Item.Properties(), true));
	
	public static final RegistryObject<Item> obsidian_adze_fancy = ITEMS_OBSIDIAN_FANCY.register("obsidian_adze", () -> new ItemAdze(FancyToolsMod.getInstance().getConfig().getObsidian(), 8.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> obsidian_adze_oaat = ITEMS_OBSIDIAN_OAAT.register("obsidian_adze", () -> new ItemAdze(ToolMaterialList.obsidian, 8.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> obsidian_adze_equipment = ITEMS_OBSIDIAN_EQUIPMENT.register("obsidian_adze", () -> new ItemAdze(com.exline.obsidianequipment.items.ModItemTier.OBSIDIAN, 8.4F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> obsidian_adze_easy = ITEMS_OBSIDIAN_EASY.register("obsidian_adze", () -> new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialTiers.OBSIDIAN, 7.0F, -2.4F, new Item.Properties(), true));
	
	public static final RegistryObject<Item> ruby_adze = ITEMS_EASY.register("ruby_adze", () -> new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialTiers.RUBY, 8.0F, -2.4F, new Item.Properties(), true));
	public static final RegistryObject<Item> amethyst_adze = ITEMS_EASY.register("amethyst_adze", () -> new ItemAdze(com.kwpugh.easy_emerald.lists.ToolMaterialTiers.AMETHYST, 9.0F, -2.4F, new Item.Properties(), true));

	//public static final RegistryObject<Item> sapphire_adze;
	//public static final RegistryObject<Item> topaz_adze;
	//public static final RegistryObject<Item> quartz_adze;
	
	public static final RegistryObject<Item> gravitite_adze = ITEMS_AETHER.register("gravitite_adze", () -> new ItemGravititeAdze(AetherItemTiers.GRAVITITE, 8.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> holystone_adze = ITEMS_AETHER.register("holystone_adze", () -> new ItemHolystoneAdze(AetherItemTiers.HOLYSTONE, 8.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> skyroot_adze = ITEMS_AETHER.register("skyroot_adze", () -> new ItemSkyrootAdze(AetherItemTiers.SKYROOT, 6.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> zanite_adze = ITEMS_AETHER.register("zanite_adze", () -> new ItemZaniteAdze(AetherItemTiers.ZANITE, 8.0F, -1.0F, new Item.Properties()));
	public static final RegistryObject<Item> valkyrie_adze = ITEMS_AETHER.register("valkyrie_adze", () -> new ItemValkyrieAdze(AetherItemTiers.VALKYRIE, 9.0F, -0.3F, new Item.Properties()));
	
	public static final RegistryObject<Item> gobber_adze = ITEMS_GOBBER.register("gobber_adze", () -> new ItemGobberAdze(ToolMaterialTiers.OVERWORLD_GOBBER, 10.0F, -2.0F, new Item.Properties()));
	public static final RegistryObject<Item> nether_adze = ITEMS_GOBBER.register("nether_adze", () -> new ItemGobberAdze(ToolMaterialTiers.NETHER_GOBBER, 12.0F, -1.8F, new Item.Properties()));
	public static final RegistryObject<Item> end_adze = ITEMS_GOBBER.register("end_adze", () -> new ItemEndAdze(ToolMaterialTiers.END_GOBBER, 15.0F, -1.6F, new Item.Properties()));
}
