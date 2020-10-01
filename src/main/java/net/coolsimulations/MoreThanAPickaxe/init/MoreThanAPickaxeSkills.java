package net.coolsimulations.MoreThanAPickaxe.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import codersafterdark.reskillable.base.LevelLockHandler;
import net.coolsimulations.SurvivalPlus.api.SPItems;
import net.coolsimulations.SurvivalPlus.api.item.SPItemAxe;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.actors.threadpool.Arrays;

public class MoreThanAPickaxeSkills {
	
	protected static List<String> skills = new ArrayList<String>();
	public static boolean isNotBlacklist;
	
	public static void initReskillable(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "reskillable.cfg"));
		
		String desc = "Set requirements for items in this list. Each entry is composed of the item key and the requirements\nThe item key is in the simple mod:item_id format. Optionally, it can be in mod:item_id:metadata, if you want to match metadata.\nThe requirements are in a comma separated list, each in a key|value format. For example, to make an iron pickaxe require 5 mining\nand 5 building, you'd use the following string:\n\"minecraft:iron_pickaxe=mining|5,building|5\"\n\nItem usage can also be locked behind an advancement, by using adv|id. For example, to make the elytra require the \"Acquire Hardware.\" advancement\nyou'd use the following string:\n\"minecraft:elytra=adv|minecraft:story/smelt_iron\"\n\nSkill requirements and advancements can be mixed and matched, so you can make an item require both, if you want.\nYou can also lock placed blocks from being used or broken, in the same manner.\n\nLocks defined here apply to all the following cases: Right clicking an item, placing a block, breaking a block, using a block that's placed,\nleft clicking an item, using an item to break any block, and equipping an armor item.\n\nYou can lock entire mods by just using their name as the left argument. You can then specify specific items to not be locked,\nby defining their lock in the normal way. If you want an item to not be locked in this way, use \"none\" after the =";
		Property itemsList = config.get(Configuration.CATEGORY_GENERAL, "Skill Locks", LevelLockHandler.DEFAULT_SKILL_LOCKS, desc);
		
		List<String> locksList = new ArrayList<String>(Arrays.asList(itemsList.getStringList()));
		skills = locksList;
		
		Property blacklist = config.get(Configuration.CATEGORY_GENERAL, "Enable Automatic SurvivalPlus Entries", true, "SurvivalPlus automatically adds it's values based on the set Iron ones, turn this off to remove SurvivalPlus entries from the Skill Locks list");
		isNotBlacklist = blacklist.getBoolean(true);

		addItemDynamic(locksList, MoreThanAPickaxeItems.iron_adze, "gathering", 0, "attack", "mining", "farming", "iron", 5);
		addItemDynamic(locksList, MoreThanAPickaxeItems.gold_adze, "gathering", 0, "attack", "mining", "farming", "gold", 5);
		addItemDynamic(locksList, MoreThanAPickaxeItems.diamond_adze, "gathering", 0, "attack", "mining", "farming", "diamond", 16);
		addItemDynamic(locksList, MoreThanAPickaxeItems.bronze_adze, "gathering", 0, "attack", "mining", "farming", "bronze", 3);
		addItemDynamic(locksList, MoreThanAPickaxeItems.titanium_adze, "gathering", 0, "attack", "mining", "farming", "titanium", 7);
		
		itemsList.set(skills.toArray(new String[0]));;

		config.save();
	}
	
	protected static void addItem(List<String> originalList, Item item, String typeOne, int levelOne, String typeTwo, int levelTwo, String typeThree, int levelThree, String typeFour, int levelFour) {
		
		if(!checkList(originalList, item) && isNotBlacklist) {
			skills.add(item.getRegistryName() + ":*=reskillable:" + typeOne + "|" + levelOne + ",reskillable:" + typeTwo + "|" + levelTwo + ",reskillable:" + typeThree + "|" + levelThree + ",reskillable:" + typeFour + "|" + levelFour);
		}	
	}
	
	protected static void addItemDynamic(List<String> originalList, Item item, String typeOne, int levelDifference, String typeTwo, String typeThree, String typeFour, String material, int backupDefault) {
		
		addItem(originalList, item, typeOne, getLevelFromType(originalList, typeOne, levelDifference, material, backupDefault), typeTwo, getLevelFromType(originalList, typeTwo, levelDifference, material, backupDefault), typeThree, getLevelFromType(originalList, typeThree, levelDifference, material, backupDefault), typeFour, getLevelFromType(originalList, typeFour, levelDifference, material, backupDefault));
	}
	
	protected static int getLevelFromType(List<String> originalList, String type, int levelDifference, String material, int backupDefault) {
		
		if(type.toLowerCase() == "gathering")
		{
			int originalShovelLevel = getIntFromMaterial(originalList, Items.IRON_SHOVEL, material);
			int finalShovelInt;
			
			if((originalShovelLevel + levelDifference) < 0)
				finalShovelInt = backupDefault;
			else
				finalShovelInt = originalShovelLevel + levelDifference;
			
			int originalAxeLevel = getIntFromMaterial(originalList, Items.IRON_AXE, material);
			int finalAxeInt;
			
			if((originalAxeLevel + levelDifference) < 0)
				finalAxeInt = backupDefault;
			else
				finalAxeInt = originalAxeLevel + levelDifference;
			
			if(finalShovelInt > finalAxeInt)
				return finalShovelInt;
			else
				return finalAxeInt;
			
		} else if (type.toLowerCase() == "attack") {
			int originalLevel = getIntFromMaterial(originalList, Items.IRON_SWORD, material);
			int finalInt;
			
			if((originalLevel + levelDifference) < 0)
				finalInt = backupDefault;
			else
				finalInt = originalLevel + levelDifference;
			return finalInt;
		} else if (type.toLowerCase() == "mining") {
			int originalLevel = getIntFromMaterial(originalList, Items.IRON_PICKAXE, material);
			int finalInt;
			
			if((originalLevel + levelDifference) < 0)
				finalInt = backupDefault;
			else
				finalInt = originalLevel + levelDifference;
			return finalInt;
		} else if (type.toLowerCase() == "farming") {
			int originalLevel = getIntFromMaterial(originalList, Items.IRON_HOE, material);
			int finalInt;
			
			if((originalLevel + levelDifference) < 0)
				finalInt = backupDefault;
			else
				finalInt = originalLevel + levelDifference;
			return finalInt;
		} else {
			return backupDefault;
		}
		
	}
	
	protected static boolean checkList(List<String> list, Item item) {
		
		for(int i = 0; i < list.size(); i++ ) {
			
			if(list.get(i).contains(item.getRegistryName().toString()))
				return true;
		}
		
		return false;
	}
	
	protected static String checkListString(List<String> list, Item item) {
		
		for(int i = 0; i < list.size(); i++ ) {
			
			if(list.get(i).contains(item.getRegistryName().toString()))
				return list.get(i);
		}
		
		return "";
	}
	
	protected static int getIntFromString(String skill, Item checkedItem) {
		
		if(StringUtils.countMatches(skill, "reskillable") == 1) {
			String edited = removeItem(removeSkill(removePrefix(skill)), checkedItem);
			return Integer.parseInt(edited);
		}
		
		if(StringUtils.countMatches(skill, "reskillable") == 2) {
			String edited = removeItem(removeSkill(removePrefix(skill)), checkedItem);
			if(edited.length() == 2) {
				int first = Character.getNumericValue((edited.charAt(0)));
				int second = Character.getNumericValue((edited.charAt(1)));
				if(first == second)
					return first;
				else
					return first < second ? second : second < first ? first : 1;
			} else if(edited.length() == 3) {
				int first = Character.getNumericValue(edited.charAt(0)) + Character.getNumericValue(edited.charAt(1));
				int second = Character.getNumericValue(edited.charAt(1)) + Character.getNumericValue(edited.charAt(2));
				if(first == second)
					return first;
				else
					return first < second ? second : second < first ? first : 1;
			} else if(edited.length() == 4) {
				int first = Character.getNumericValue(edited.charAt(0)) + Character.getNumericValue(edited.charAt(1));
				int second = Character.getNumericValue(edited.charAt(2)) + Character.getNumericValue(edited.charAt(3));
				if(first == second)
					return first;
				else
					return first < second ? second : second < first ? first : 1;
			}
		}
		
		return 1;
	}
	
	protected static String removePrefix(String skill) {
		
		String edited = skill;
		
		edited = edited.replace(":*=reskillable:", "");
		edited = edited.replace("|", "");
		if(edited.contains(",reskillable:"))
			edited = edited.replace(",reskillable:", "");
		
		return edited;
	}
	
	protected static String removeItem(String skill, Item item) {
		
		String edited = skill;
		
		edited = edited.replace(item.getRegistryName().toString(), "");
		
		return edited;
	}
	
	protected static String removeSkill(String skill) {
		
		String edited = skill;
		
		if(edited.contains("gathering"))
			edited = edited.replace("gathering", "");
		if(edited.contains("mining"))
			edited = edited.replace("mining", "");
		if(edited.contains("farming"))
			edited = edited.replace("farming", "");
		if(edited.contains("attack"))
			edited = edited.replace("attack", "");
		if(edited.contains("defense"))
			edited = edited.replace("defense", "");
		if(edited.contains("agility"))
			edited = edited.replace("agility", "");
		if(edited.contains("magic"))
			edited = edited.replace("magic", "");
		if(edited.contains("building"))
			edited = edited.replace("building", "");
		
		return edited;
	}
	
	protected static int getIntFromMaterial(List<String> list, Item item, String material) {
		
		if(material.toLowerCase() == "iron") {
			if(item instanceof ItemSpade && checkList(list, Items.IRON_SHOVEL)) {
				String shovel = checkListString(list, Items.IRON_SHOVEL);
				return getIntFromString(shovel, Items.IRON_SHOVEL);
			}
			
			if((item instanceof SPItemAxe || item instanceof ItemAxe || (item instanceof ItemTool && item.getRegistryName().getResourcePath().contains("axe") && !item.getRegistryName().getResourcePath().contains("pickaxe"))) && checkList(list, Items.IRON_AXE)) {
				String shovel = checkListString(list, Items.IRON_AXE);
				return getIntFromString(shovel, Items.IRON_AXE);
			}
			
			if(item instanceof ItemSword && checkList(list, Items.IRON_SWORD)) {
				String shovel = checkListString(list, Items.IRON_SWORD);
				return getIntFromString(shovel, Items.IRON_SWORD);
			}
			
			if(item instanceof ItemPickaxe && checkList(list, Items.IRON_PICKAXE)) {
				String shovel = checkListString(list, Items.IRON_PICKAXE);
				return getIntFromString(shovel, Items.IRON_PICKAXE);
			}
			
			if(item instanceof ItemHoe && checkList(list, Items.IRON_HOE)) {
				String shovel = checkListString(list, Items.IRON_HOE);
				return getIntFromString(shovel, Items.IRON_HOE);
			}
		} else if(material.toLowerCase() == "gold") {
			if(item instanceof ItemSpade && checkList(list, Items.GOLDEN_SHOVEL)) {
				String shovel = checkListString(list, Items.GOLDEN_SHOVEL);
				return getIntFromString(shovel, Items.GOLDEN_SHOVEL);
			}
			
			if((item instanceof SPItemAxe || item instanceof ItemAxe || (item instanceof ItemTool && item.getRegistryName().getResourcePath().contains("axe") && !item.getRegistryName().getResourcePath().contains("pickaxe"))) && checkList(list, Items.GOLDEN_AXE)) {
				String shovel = checkListString(list, Items.GOLDEN_AXE);
				return getIntFromString(shovel, Items.GOLDEN_AXE);
			}
			
			if(item instanceof ItemSword && checkList(list, Items.GOLDEN_SWORD)) {
				String shovel = checkListString(list, Items.GOLDEN_SWORD);
				return getIntFromString(shovel, Items.GOLDEN_SWORD);
			}
			
			if(item instanceof ItemPickaxe && checkList(list, Items.GOLDEN_PICKAXE)) {
				String shovel = checkListString(list, Items.GOLDEN_PICKAXE);
				return getIntFromString(shovel, Items.GOLDEN_PICKAXE);
			}
			
			if(item instanceof ItemHoe && checkList(list, Items.GOLDEN_HOE)) {
				String shovel = checkListString(list, Items.GOLDEN_HOE);
				return getIntFromString(shovel, Items.GOLDEN_HOE);
			}
		} else if(material.toLowerCase() == "diamond") {
			if(item instanceof ItemSpade && checkList(list, Items.DIAMOND_SHOVEL)) {
				String shovel = checkListString(list, Items.DIAMOND_SHOVEL);
				return getIntFromString(shovel, Items.DIAMOND_SHOVEL);
			}
			
			if((item instanceof SPItemAxe || item instanceof ItemAxe || (item instanceof ItemTool && item.getRegistryName().getResourcePath().contains("axe") && !item.getRegistryName().getResourcePath().contains("pickaxe"))) && checkList(list, Items.DIAMOND_AXE)) {
				String shovel = checkListString(list, Items.DIAMOND_AXE);
				return getIntFromString(shovel, Items.DIAMOND_AXE);
			}
			
			if(item instanceof ItemSword && checkList(list, Items.DIAMOND_SWORD)) {
				String shovel = checkListString(list, Items.DIAMOND_SWORD);
				return getIntFromString(shovel, Items.DIAMOND_SWORD);
			}
			
			if(item instanceof ItemPickaxe && checkList(list, Items.DIAMOND_PICKAXE)) {
				String shovel = checkListString(list, Items.DIAMOND_PICKAXE);
				return getIntFromString(shovel, Items.DIAMOND_PICKAXE);
			}
			
			if(item instanceof ItemHoe && checkList(list, Items.DIAMOND_HOE)) {
				String shovel = checkListString(list, Items.DIAMOND_HOE);
				return getIntFromString(shovel, Items.DIAMOND_HOE);
			}
		} else if(material.toLowerCase() == "bronze") {
			if(item instanceof ItemSpade && checkList(list, SPItems.bronze_shovel)) {
				String shovel = checkListString(list, SPItems.bronze_shovel);
				return getIntFromString(shovel, SPItems.bronze_shovel);
			}
			
			if((item instanceof SPItemAxe || item instanceof ItemAxe || (item instanceof ItemTool && item.getRegistryName().getResourcePath().contains("axe") && !item.getRegistryName().getResourcePath().contains("pickaxe"))) && checkList(list, SPItems.bronze_axe)) {
				String shovel = checkListString(list, SPItems.bronze_axe);
				return getIntFromString(shovel, SPItems.bronze_axe);
			}
			
			if(item instanceof ItemSword && checkList(list, SPItems.bronze_sword)) {
				String shovel = checkListString(list, SPItems.bronze_sword);
				return getIntFromString(shovel, SPItems.bronze_sword);
			}
			
			if(item instanceof ItemPickaxe && checkList(list, SPItems.bronze_pickaxe)) {
				String shovel = checkListString(list, SPItems.bronze_pickaxe);
				return getIntFromString(shovel, SPItems.bronze_pickaxe);
			}
			
			if(item instanceof ItemHoe && checkList(list, SPItems.bronze_hoe)) {
				String shovel = checkListString(list, SPItems.bronze_hoe);
				return getIntFromString(shovel, SPItems.bronze_hoe);
			}
		} else if(material.toLowerCase() == "titanium") {
			if(item instanceof ItemSpade && checkList(list, SPItems.titanium_shovel)) {
				String shovel = checkListString(list, SPItems.titanium_shovel);
				return getIntFromString(shovel, SPItems.titanium_shovel);
			}
			
			if((item instanceof SPItemAxe || item instanceof ItemAxe || (item instanceof ItemTool && item.getRegistryName().getResourcePath().contains("axe") && !item.getRegistryName().getResourcePath().contains("pickaxe"))) && checkList(list, SPItems.titanium_axe)) {
				String shovel = checkListString(list, SPItems.titanium_axe);
				return getIntFromString(shovel, SPItems.titanium_axe);
			}
			
			if(item instanceof ItemSword && checkList(list, SPItems.titanium_sword)) {
				String shovel = checkListString(list, SPItems.titanium_sword);
				return getIntFromString(shovel, SPItems.titanium_sword);
			}
			
			if(item instanceof ItemPickaxe && checkList(list, SPItems.titanium_pickaxe)) {
				String shovel = checkListString(list, SPItems.titanium_pickaxe);
				return getIntFromString(shovel, SPItems.titanium_pickaxe);
			}
			
			if(item instanceof ItemHoe && checkList(list, SPItems.titanium_hoe)) {
				String shovel = checkListString(list, SPItems.titanium_hoe);
				return getIntFromString(shovel, SPItems.titanium_hoe);
			}
		}
		
		return 1;
	}

}
