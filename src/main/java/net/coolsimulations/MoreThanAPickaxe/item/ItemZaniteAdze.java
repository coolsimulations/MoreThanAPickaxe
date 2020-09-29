package net.coolsimulations.MoreThanAPickaxe.item;

import com.gildedgames.the_aether.items.weapons.ItemZaniteSword;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemZaniteAdze extends ItemAdze {
	
	public float[] level = new float[] {2F, 4F, 6F, 8F, 12F};
	public float[] swordLevel = new float[] {3.0F, 4.0F, 5.0F, 6.0F, 7.0F};

	public ItemZaniteAdze(ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
	}
	
	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
		return this.calculateIncrease(stack, this.getStrVsBlock(stack, state));
    }
	
	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
        	if (stack.getItem() instanceof ItemZaniteSword)
        	{
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.calculateSwordIncrease(stack), 0));
        	}

            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
	
	private float calculateSwordIncrease(ItemStack tool)
    {
    	int current = tool.getItemDamage();

		if (isBetween(tool.getMaxDamage(), current, tool.getMaxDamage() - 50))
		{
			return swordLevel[4];
		}
		else if (isBetween(tool.getMaxDamage() - 51, current, tool.getMaxDamage() - 110))
		{
			return swordLevel[3];
		}
		else if (isBetween(tool.getMaxDamage() - 111, current, tool.getMaxDamage() - 200))
		{
			return swordLevel[2];
		}
		else if (isBetween(tool.getMaxDamage() - 201, current, tool.getMaxDamage() - 239))
		{
			return swordLevel[1];
		}
		else
		{
			return swordLevel[0];
		}
    }

    private float calculateIncrease(ItemStack tool, float original)
    {
    	boolean AllowedCalculations = original != 4.0F ? false : true;
    	int current = tool.getItemDamage();

    	if (AllowedCalculations)
    	{
    		if (isBetween(tool.getMaxDamage(), current, tool.getMaxDamage() - 50))
    		{
    			return level[4];
    		}
    		else if (isBetween(tool.getMaxDamage() - 51, current, tool.getMaxDamage() - 110))
    		{
    			return level[3];
    		}
    		else if (isBetween(tool.getMaxDamage() - 111, current, tool.getMaxDamage() - 200))
    		{
    			return level[2];
    		}
    		else if (isBetween(tool.getMaxDamage() - 201, current, tool.getMaxDamage() - 239))
    		{
    			return level[1];
    		}
    		else
    		{
    			return level[0];
    		}
    	}
    	else
    	{
    		return 1.0F;
    	}
    }

    private boolean isBetween(int max, int origin, int min)
    {
    	return origin <= max && origin >= min ? true : false;
    }
    
    public float getStrVsBlock(ItemStack stack, IBlockState block)
	{
    	return this.EFFECTIVE_ON.contains(block.getBlock()) ? 4.0F : 1.0F;
	}

}
