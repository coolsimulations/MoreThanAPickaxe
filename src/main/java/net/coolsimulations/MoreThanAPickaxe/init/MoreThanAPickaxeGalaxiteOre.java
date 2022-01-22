package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.ArrayList;
import java.util.List;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.galaxite.init.ModItems;
import net.insane96mcp.galaxite.item.material.ModMaterial;
import net.insane96mcp.galaxite.events.PlayerBreakSpeed;
import net.insane96mcp.galaxite.events.LivingHurt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class MoreThanAPickaxeGalaxiteOre {
	
	public static Item galaxite_adze;
	
	public static void init() {
		
		galaxite_adze = new ItemAdze(ModMaterial.Tool, 6.5F, -2.4F, true).setUnlocalizedName("galaxite_adze").setRegistryName("galaxite_adze").setCreativeTab(SPTabs.tabTools);
		registerLivingList();
		registerBreakList();
	}
	
	public static void register() {
		MoreThanAPickaxeItems.registerItem(galaxite_adze);
	}
	
	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(galaxite_adze);
	}
	
	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeGalaxiteOre.galaxite_adze, ModItems.galaxite);
	}
	
	public static void registerLivingList() {

		ItemStack[] galaxiteTools = ObfuscationReflectionHelper.getPrivateValue(LivingHurt.class, null, "galaxiteTools");
		List<ItemStack> newList = new ArrayList<ItemStack>();

		for(ItemStack stack : galaxiteTools)
			newList.add(stack);

		if(!newList.contains(new ItemStack(galaxite_adze)))
			newList.add(new ItemStack(galaxite_adze));

		ObfuscationReflectionHelper.setPrivateValue(LivingHurt.class, null, newList.toArray(new ItemStack[0]), "galaxiteTools");
	}
	
	public static void registerBreakList() {

		ItemStack[] validTools = ObfuscationReflectionHelper.getPrivateValue(PlayerBreakSpeed.class, null, "validEfficencyBoost");
		List<ItemStack> newList = new ArrayList<ItemStack>();

		for(ItemStack stack : validTools)
			newList.add(stack);

		if(!newList.contains(new ItemStack(galaxite_adze)))
			newList.add(new ItemStack(galaxite_adze));

		ObfuscationReflectionHelper.setPrivateValue(PlayerBreakSpeed.class, null, newList.toArray(new ItemStack[0]), "validEfficencyBoost");
	}

}
