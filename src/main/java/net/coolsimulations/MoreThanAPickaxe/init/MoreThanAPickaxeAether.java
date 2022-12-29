package net.coolsimulations.MoreThanAPickaxe.init;

import com.gildedgames.aether.item.combat.AetherItemTiers;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.coolsimulations.MoreThanAPickaxe.item.ItemGravititeAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemHolystoneAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemSkyrootAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemValkyrieAdze;
import net.coolsimulations.MoreThanAPickaxe.item.ItemZaniteAdze;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreThanAPickaxeAether {
	
	public static final DeferredRegister<Item> ITEMS_AETHER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	
	public static final RegistryObject<Item> gravitite_adze = ITEMS_AETHER.register("gravitite_adze", () -> new ItemGravititeAdze(AetherItemTiers.GRAVITITE, 8.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> holystone_adze = ITEMS_AETHER.register("holystone_adze", () -> new ItemHolystoneAdze(AetherItemTiers.HOLYSTONE, 8.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> skyroot_adze = ITEMS_AETHER.register("skyroot_adze", () -> new ItemSkyrootAdze(AetherItemTiers.SKYROOT, 6.0F, -1.5F, new Item.Properties()));
	public static final RegistryObject<Item> zanite_adze = ITEMS_AETHER.register("zanite_adze", () -> new ItemZaniteAdze(AetherItemTiers.ZANITE, 8.0F, -1.0F, new Item.Properties()));
	public static final RegistryObject<Item> valkyrie_adze = ITEMS_AETHER.register("valkyrie_adze", () -> new ItemValkyrieAdze(AetherItemTiers.VALKYRIE, 9.0F, -0.3F, new Item.Properties()));

}
