package net.coolsimulations.MoreThanAPickaxe.init;

import java.net.URL;
import java.util.Scanner;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.TextComponent;

public class MoreThanAPickaxeUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslatableComponent updateInfo = null;
	public static TextComponent updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/morethanapickaxe-fabric/versionchecker118.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/morethanapickaxe-fabric/updateinfo118.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersionInfo = s.nextLine();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TranslatableComponent morethanapickaxe = new TranslatableComponent("morethanapickaxe.name");
				morethanapickaxe.withStyle(ChatFormatting.BLUE);
				
				TextComponent MCVersion = new TextComponent(SharedConstants.getCurrentVersion().getName());
				MCVersion.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent("sp.update.display3", new Object[] {morethanapickaxe, MCVersion});
				updateInfo.withStyle(ChatFormatting.YELLOW);
				
				//updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2")));
				//updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"));
				
			}
			
			if(!latestVersion.equals(Reference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TranslatableComponent morethanapickaxe = new TranslatableComponent("morethanapickaxe.name");
				morethanapickaxe.withStyle(ChatFormatting.BLUE);
				
				TextComponent version = new TextComponent(latestVersion);
				version.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent("sp.update.display1", new Object[] {morethanapickaxe, version});
				updateInfo.withStyle(ChatFormatting.YELLOW);
				
				//updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2")));
				//updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"));
				
				if(latestVersionInfo != null) {
					
					updateVersionInfo = new TextComponent(latestVersionInfo);
					updateVersionInfo.withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD);
					
					//updateVersionInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("sp.update.display2")));
					//updateVersionInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/more-than-a-pickaxe-fabric"));
					
				}
				
			}
			
		}
	}

}
