package net.coolsimulations.MoreThanAPickaxe.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.coolsimulations.MoreThanAPickaxe.init.MoreThanAPickaxeItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGobberAdze extends ItemAdze {

	public ItemGobberAdze(ToolMaterial material, float damage, float speed) {
		this(material, damage, speed, false);
	}
	
	public ItemGobberAdze(ToolMaterial material, float damage, float speed, boolean unbreakable) {
		super(material, damage, speed, true, unbreakable);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entity, EnumHand hand) {
		
		if (entity instanceof EntityLivingBase) {
			entity.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2400, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 2400, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2400, 0));
		}

		return super.onItemRightClick(world, entity, hand);
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
		if(this != MoreThanAPickaxeItems.gobber_adze)
			target.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 2));
		return super.hitEntity(stack, target, attacker);
	}
	
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 10.0D, 0));
		}

		return multimap;
	}
	
	public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
		super.addInformation(itemstack, world, list, flag);
		if(this == MoreThanAPickaxeItems.gobber_adze)
			list.add(TextFormatting.GOLD + "A better adze");
		if(this == MoreThanAPickaxeItems.nether_adze)
			list.add(TextFormatting.GOLD + "An even better adze");
		list.add(TextFormatting.GREEN + "Right-click for night vision");
		list.add(TextFormatting.GREEN + "Right-click to breath underwater");
		list.add(TextFormatting.GREEN + "Right-click to survive fire");
	}

}
