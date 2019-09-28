package net.coolsimulations.MoreThanAPickaxe.init;

import java.net.URL;
import java.util.Scanner;

import net.coolsimulations.MoreThanAPickaxe.Reference;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.versions.mcp.MCPVersion;

public class MoreThanAPickaxeUpdateHandler {
	
	private static String latestVersion;
	public static boolean isOld = false;
	public static TranslationTextComponent updateInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/morethanapickaxe/versionchecker114.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				StringTextComponent morethanapickaxe = new StringTextComponent(Reference.MOD_NAME);
				morethanapickaxe.getStyle().setColor(TextFormatting.BLUE);
				
				StringTextComponent MCVersion = new StringTextComponent(MCPVersion.getMCVersion());
				MCVersion.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("sp.update.display3", new Object[] {morethanapickaxe, MCVersion});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("sp.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));
				
			}
			
			if(!latestVersion.equals(Reference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				StringTextComponent sp = new StringTextComponent(Reference.MOD_NAME);
				sp.getStyle().setColor(TextFormatting.BLUE);
				
				StringTextComponent version = new StringTextComponent(latestVersion);
				version.getStyle().setColor(TextFormatting.BLUE);
				
				updateInfo = new TranslationTextComponent("sp.update.display1", new Object[] {sp, version});
				updateInfo.getStyle().setColor(TextFormatting.YELLOW);
				
				updateInfo.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("sp.update.display2")));
				updateInfo.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/more-than-a-pickaxe"));
				
			}
			
		}
	}

}
