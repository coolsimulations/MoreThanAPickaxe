package net.coolsimulations.MoreThanAPickaxe.init;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherLootModifier extends LootModifier {

	public static final Supplier<Codec<AetherLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
			.and(ForgeRegistries.ITEMS.getCodec().fieldOf("additional_reward").forGetter(m -> m.itemReward))
			.apply(inst, AetherLootModifier::new)
			));

    private final Item itemReward;

	public AetherLootModifier(LootItemCondition[] conditionsIn, Item reward) {
		super(conditionsIn);
		this.itemReward = reward;
	}

    @NotNull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.add(new ItemStack(this.itemReward));
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}
