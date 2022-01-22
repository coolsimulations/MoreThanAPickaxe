package net.coolsimulations.MoreThanAPickaxe.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.id.aether.items.tools.base_tools.ZaniteTool;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class ItemZaniteAdze extends ItemAdze {

	public ItemZaniteAdze(Tier material, float damage, float speed, FabricItemSettings builder) {
		super(material, damage, speed, builder, true);
	}
	
	@Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) + ZaniteTool.calculateSpeedBoost(stack);
    }

}
