package net.coolsimulations.MoreThanAPickaxe.init;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class MoreThanAPickaxeAchievements {
	
	public static AchievementPage moreThanAPickaxePage;
	public static Achievement achievementInstall;
	public static Achievement achievementAdze;
	
	public static void regsiterAchievements() {
		
		achievementInstall = new Achievement("achievement.morethanapickaxe.install", "morethanapickaxe.install", 0, 0, new ItemStack(MoreThanAPickaxeItems.bronze_adze), null).setSpecial();
		achievementInstall.registerStat();
		
		achievementAdze = new Achievement("achievement.morethanapickaxe.adze", "morethanapickaxe.adze", 1, 2, new ItemStack(MoreThanAPickaxeItems.gold_adze), achievementInstall).registerStat();
		
		moreThanAPickaxePage = new AchievementPage(Reference.MOD_NAME, achievementInstall, achievementAdze);
	}
	
	public static void registerPage()
	{
		AchievementPage.registerAchievementPage(moreThanAPickaxePage);
	}

}
