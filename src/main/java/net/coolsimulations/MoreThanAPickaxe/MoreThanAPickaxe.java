package net.coolsimulations.MoreThanAPickaxe;

import java.util.ArrayList;
import java.util.List;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeAchievements;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeEventHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeUpdateHandler;
import net.coolsimulations.MoreThanAPickaxe.proxy.CommonProxy;
import net.coolsimulations.MoreThanAPickaxe.recipes.MoreThanAPickaxeRecipes;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = Reference.DEPENDENCIES, updateJSON = "https://coolsimulations.net/mcmods/morethanapickaxe/versionchecker.json")
public class MoreThanAPickaxe {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@Mod.Instance(Reference.MOD_ID)
	public static MoreThanAPickaxe instance;
	
	public static List<Achievement> achievements = new ArrayList<Achievement>();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		System.out.println("Pre Init");
		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeEventHandler());
		MoreThanAPickaxeUpdateHandler.init();
		MoreThanAPickaxeItems.init();
		MoreThanAPickaxeItems.register();
		MoreThanAPickaxeAchievements.regsiterAchievements();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		System.out.println("Init");
		proxy.init();
		MoreThanAPickaxeRecipes.register();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("Post Init");
		
	}
}
