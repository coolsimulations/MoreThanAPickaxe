package net.coolsimulations.MoreThanAPickaxe.init;

import java.net.URL;
import java.util.Scanner;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;

public class MoreThanAPickaxeUpdateHandler {
	
	private static String latestVersion;
	public static boolean isOld = false;
	public static TextComponentTranslation updateInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/morethanapickaxe/versionchecker111.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponentString morethanapickaxe = new TextComponentString(Reference.MOD_NAME);
				morethanapickaxe.getStyle().setColor(TextFormatting.BLUE);
				
				TextComponentString MCVersion = new TextComponentString(MinecraftForge.MC_VERSION);
				MCVersion.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TextComponentTranslation("sp.update.display3", new Object[] {morethanapickaxe, MCVersion});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("sp.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));
				
			}
			
			if(!latestVersion.equals(Reference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponentString sp = new TextComponentString(Reference.MOD_NAME);
				sp.getStyle().setColor(TextFormatting.BLUE);
				
				TextComponentString version = new TextComponentString(latestVersion);
				version.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TextComponentTranslation("sp.update.display1", new Object[] {sp, version});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("sp.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));
				
			}
			
		}
	}

}
