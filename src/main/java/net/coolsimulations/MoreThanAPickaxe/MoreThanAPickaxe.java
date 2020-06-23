package net.coolsimulations.MoreThanAPickaxe;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeEventHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeHammerTime;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeLumberjack;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeUpdateHandler;
import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeVillagerTrade;
import net.coolsimulations.MoreThanAPickaxe.proxy.ClientProxy;
import net.coolsimulations.MoreThanAPickaxe.proxy.CommonProxy;
import net.coolsimulations.SurvivalPlus.api.SPCompatibilityManager;
import net.coolsimulations.SurvivalPlus.api.SPReference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(value = Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MoreThanAPickaxe {

	public static CommonProxy proxy = (CommonProxy) DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	private static MoreThanAPickaxe instance;

	public static MoreThanAPickaxe getInstance()
	{
		return instance;
	}

	public MoreThanAPickaxe() {

		MinecraftForge.EVENT_BUS.register(new MoreThanAPickaxeEventHandler());
		SPReference.MOD_ADDON_NAMES.add("morethanapickaxe.name");
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

	}

}
