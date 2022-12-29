package net.coolsimulations.MoreThanAPickaxe;

import com.mojang.serialization.Codec;

import net.coolsimulations.MoreThanAPickaxe.init.AetherLootModifier;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeEventHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeLumberjack;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeUpdateHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeVillagerTrade;
import net.coolsimulations.MoreThanAPickaxe.proxy.ClientProxy;
import net.coolsimulations.MoreThanAPickaxe.proxy.CommonProxy;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPReference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(value = Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoreThanAPickaxe {

	public static CommonProxy proxy = (CommonProxy) DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	private static MoreThanAPickaxe instance;

	public static MoreThanAPickaxe getInstance()
	{
		return instance;
	}

	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_AETHER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<Codec<AetherLootModifier>> valkyrie_adze = LOOT_MODIFIER_AETHER.register("valkyrie_adze", AetherLootModifier.CODEC);

	public MoreThanAPickaxe() {

		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeEventHandler());
		SPReference.MOD_ADDON_NAMES.add("morethanapickaxe.name");
		MoreThanAPickaxeUpdateHandler.init();
		MoreThanAPickaxeItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		if(!SPCompatibilityManager.isGCLoaded())
			MoreThanAPickaxeItems.ITEMS_STEEL.register(FMLJavaModLoadingContext.get().getModEventBus());
		if(SPCompatibilityManager.isEmeraldMaterialModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				MoreThanAPickaxeItems.ITEMS_EMERALD_FANCY.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if(SPCompatibilityManager.isSimpleEmeraldLoaded()) {
				MoreThanAPickaxeItems.ITEMS_EMERALD_SIMPLE.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
				MoreThanAPickaxeItems.ITEMS_EMERALD_EASY.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if (SPCompatibilityManager.isEmeraldEquipmentLoaded()) {
				MoreThanAPickaxeItems.ITEMS_EMERALD_EQUIPMENT.register(FMLJavaModLoadingContext.get().getModEventBus());
			}
		}
		if(SPCompatibilityManager.isObsidianMaterialModsLoaded()) {
			if(SPCompatibilityManager.isFancyToolsLoaded()) {
				MoreThanAPickaxeItems.ITEMS_OBSIDIAN_FANCY.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if(SPCompatibilityManager.isOAATLoaded()) {
				MoreThanAPickaxeItems.ITEMS_OBSIDIAN_OAAT.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if (SPCompatibilityManager.isObsidianEquipmentLoaded()) {
				MoreThanAPickaxeItems.ITEMS_OBSIDIAN_EQUIPMENT.register(FMLJavaModLoadingContext.get().getModEventBus());
			} else if (SPCompatibilityManager.isEasyEmeraldLoaded()) {
				MoreThanAPickaxeItems.ITEMS_OBSIDIAN_EASY.register(FMLJavaModLoadingContext.get().getModEventBus());
			}
		}
		if(SPCompatibilityManager.isEasyEmeraldLoaded())
			MoreThanAPickaxeItems.ITEMS_EASY.register(FMLJavaModLoadingContext.get().getModEventBus());
		if(SPCompatibilityManager.isGobberLoaded())
			MoreThanAPickaxeItems.ITEMS_GOBBER.register(FMLJavaModLoadingContext.get().getModEventBus());
		if(SPCompatibilityManager.isAetherLoaded()) {
			MoreThanAPickaxeItems.ITEMS_AETHER.register(FMLJavaModLoadingContext.get().getModEventBus());
			LOOT_MODIFIER_AETHER.register(FMLJavaModLoadingContext.get().getModEventBus());
		}
		MoreThanAPickaxeVillagerTrade.init();

		proxy.init();

		if(SPCompatibilityManager.isLumberjackLoaded()) {
			MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeLumberjack());
		}

	}

}
