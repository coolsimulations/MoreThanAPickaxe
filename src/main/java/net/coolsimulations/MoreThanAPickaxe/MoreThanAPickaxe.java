package net.coolsimulations.MoreThanAPickaxe;

import java.util.ArrayList;
import java.util.List;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPicakxeAetherLegacyRecipes;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeEventHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeFuelHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeHammerTime;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeLumberjack;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeSkills;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeToolMaterials;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeUpdateHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeVillagerTrade;
import net.coolsimulations.MoreThanAPickaxe.proxy.CommonProxy;
import net.coolsimulations.MoreThanAPickaxe.recipes.MoreThanAPickaxeSmeltingRecipes;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPReference;
import net.coolsimulations.SurvivalPlus.api.compat.SPCompatRecipeManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = Reference.DEPENDENCIES, updateJSON = "https://coolsimulations.net/mcmods/morethanapickaxe/versionchecker.json")
public class MoreThanAPickaxe {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.Instance(Reference.MOD_ID)
	public static MoreThanAPickaxe instance;

	public static final List<Item> ITEMS = new ArrayList<Item>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Pre Init");
		SPReference.MOD_ADDON_NAMES.add("morethanapickaxe.name");
		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeEventHandler());
		MoreThanAPickaxeUpdateHandler.init();
		MoreThanAPickaxeItems.init();
		MoreThanAPickaxeItems.register();
		MoreThanAPickaxeVillagerTrade.init();

		if(SPCompatibilityManager.isHammerTimeLoaded()) {
			MoreThanAPickaxeHammerTime.init();
		}

		if(SPCompatibilityManager.isLumberjackLoaded()) {
			MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeLumberjack());
		}
		
		if(SPCompatibilityManager.isReskillableLoaded()) {
			MoreThanAPickaxeSkills.initReskillable(event);
		}
		
		if(SPCompatibilityManager.isAetherLegacyLoaded())
			MinecraftForge.EVENT_BUS.register(new MoreThanAPicakxeAetherLegacyRecipes());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		System.out.println("Init");
		proxy.init();
		MoreThanAPickaxeToolMaterials.init();
		GameRegistry.registerFuelHandler(new MoreThanAPickaxeFuelHandler());
		MoreThanAPickaxeSmeltingRecipes.register();
		SPCompatRecipeManager.futureRecipeManager.addOreDictionarySmithingRecipe(new ItemStack(MoreThanAPickaxeItems.diamond_adze), "ingotNetherite", new ItemStack(MoreThanAPickaxeItems.netherite_adze));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("Post Init");

	}
}
