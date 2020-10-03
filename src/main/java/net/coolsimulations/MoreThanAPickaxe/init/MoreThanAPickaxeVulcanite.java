package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.ArrayList;
import java.util.List;

import net.coolsimulations.MoreThanAPickaxe.item.ItemAdze;
import net.coolsimulations.SurvivalPlus.api.SPTabs;
import net.insane96mcp.vulcanite.events.HarvestDrops;
import net.insane96mcp.vulcanite.events.LivingHurt;
import net.insane96mcp.vulcanite.events.PlayerBreakSpeed;
import net.insane96mcp.vulcanite.init.ModItems;
import net.insane96mcp.vulcanite.item.material.ModMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class MoreThanAPickaxeVulcanite {

	public static Item vulcanite_adze;

	public static void init() {

		vulcanite_adze = new ItemAdze(ModMaterial.tool, 4.5F, -2.4F).setUnlocalizedName("vulcanite_adze").setRegistryName("vulcanite_adze").setCreativeTab(SPTabs.tabTools);
		registerHarvestList();
		registerLivingList();
		registerBreakList();
	}

	public static void register() {
		MoreThanAPickaxeItems.registerItem(vulcanite_adze);
	}

	public static void registerRenders() {
		MoreThanAPickaxeItems.registerRender(vulcanite_adze);
	}

	public static void registerToolMaterial() {
		MoreThanAPickaxeToolMaterials.setMaterial(MoreThanAPickaxeVulcanite.vulcanite_adze, ModItems.vulcaniteIngotItem);
	}

	public static void registerHarvestList() {

		ItemStack[] validTools = ObfuscationReflectionHelper.getPrivateValue(HarvestDrops.class, null, "validTools");
		List<ItemStack> newList = new ArrayList<ItemStack>();

		for(ItemStack stack : validTools)
			newList.add(stack);

		if(!newList.contains(new ItemStack(vulcanite_adze)))
			newList.add(new ItemStack(vulcanite_adze));

		ObfuscationReflectionHelper.setPrivateValue(HarvestDrops.class, null, newList.toArray(new ItemStack[0]), "validTools");
	}

	public static void registerLivingList() {

		ItemStack[] vulcaniteWeapons = ObfuscationReflectionHelper.getPrivateValue(LivingHurt.class, null, "vulcaniteWeapons");
		List<ItemStack> newList = new ArrayList<ItemStack>();

		for(ItemStack stack : vulcaniteWeapons)
			newList.add(stack);

		if(!newList.contains(new ItemStack(vulcanite_adze)))
			newList.add(new ItemStack(vulcanite_adze));

		ObfuscationReflectionHelper.setPrivateValue(LivingHurt.class, null, newList.toArray(new ItemStack[0]), "vulcaniteWeapons");
	}

	public static void registerBreakList() {

		ItemStack[] validTools = ObfuscationReflectionHelper.getPrivateValue(PlayerBreakSpeed.class, null, "validEfficencyBoost");
		List<ItemStack> newList = new ArrayList<ItemStack>();

		for(ItemStack stack : validTools)
			newList.add(stack);

		if(!newList.contains(new ItemStack(vulcanite_adze)))
			newList.add(new ItemStack(vulcanite_adze));

		ObfuscationReflectionHelper.setPrivateValue(PlayerBreakSpeed.class, null, newList.toArray(new ItemStack[0]), "validEfficencyBoost");
	}

}
