package me.intronate67.TotalWarfare;

import org.bukkit.plugin.java.JavaPlugin;

public class TotalWarfare extends JavaPlugin{
	
	public void onEnable(){
		ArenaManager.getInstance().setup();
		
		CommandManager cm = new CommandManager();
		cm.setup();
		getCommand("tw").setExecutor(cm);
	}

}
