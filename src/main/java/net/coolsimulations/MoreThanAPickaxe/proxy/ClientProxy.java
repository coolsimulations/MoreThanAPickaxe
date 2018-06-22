package net.coolsimulations.MoreThanAPickaxe.proxy;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;

public class ClientProxy implements CommonProxy{
	@Override
	public void init(){
		MoreThanAPickaxeItems.registerRenders();
	}

}
