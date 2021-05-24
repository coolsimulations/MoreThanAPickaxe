package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.function.Supplier;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;

public enum AdzeItemTier implements ToolMaterial {
    steelToolMaterial(2, 500, 7.0F, 2.5F, 9, () -> {
    	return Ingredient.fromTag(ItemTags.getTagGroup().getTag(new Identifier("c", "steel_ingots")));
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    AdzeItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new Lazy(repairMaterialIn);
    }

    public int getDurability() {
        return this.maxUses;
    }

    public float getMiningSpeedMultiplier() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}