package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class MoreThanAPickaxeTags {
	
	public static class Blocks {
		
		public static final Tag<Block> MINEABLE_WITH_ADZE = TagRegistry.block(new ResourceLocation(Reference.MOD_ID, "mineable/adze"));
	}
}
