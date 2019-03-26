package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.SurvivalPlus.api.SPAchievements;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class MoreThanAPickaxeAchievements {
	
	public static Achievement achievementInstall;
	public static Achievement achievementAdze;
	
	public static void regsiterAchievements() {
		
		achievementInstall = new Achievement("achievement.morethanapickaxe.install", "morethanapickaxe.install", 1, -2, new ItemStack(MoreThanAPickaxeItems.bronze_adze), SPAchievements.achievementInstall).setSpecial();
		achievementInstall.registerStat();
		
		achievementAdze = new Achievement("achievement.morethanapickaxe.adze", "morethanapickaxe.adze", -1, -3, new ItemStack(MoreThanAPickaxeItems.gold_adze), achievementInstall).registerStat();
		
		SPAchievements.spPage.getAchievements().add(achievementInstall);
		SPAchievements.spPage.getAchievements().add(achievementAdze);
	}

}
