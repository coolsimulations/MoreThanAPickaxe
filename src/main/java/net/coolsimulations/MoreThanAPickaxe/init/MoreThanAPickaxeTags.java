package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MoreThanAPickaxeTags {
	
	public static class Blocks {
		
		public static final TagKey<Block> MINEABLE_WITH_ADZE = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Reference.MOD_ID, "mineable/adze"));
	}
}
