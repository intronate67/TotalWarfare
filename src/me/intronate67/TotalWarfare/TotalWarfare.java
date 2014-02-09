package me.intronate67.TotalWarfare;

import me.intronate67.TotalWarfare.listeners.ArmorRemove;
import me.intronate67.TotalWarfare.listeners.BlockBreak;
import me.intronate67.TotalWarfare.listeners.LobbySign;
import me.intronate67.TotalWarfare.listeners.PlayerAttack;
import me.intronate67.TotalWarfare.listeners.PlayerCapture;
import me.intronate67.TotalWarfare.listeners.PlayerDeath;
import me.intronate67.TotalWarfare.listeners.PlayerLeave;
import me.intronate67.TotalWarfare.listeners.PlayerLoseHunger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TotalWarfare extends JavaPlugin{
	
	public void onEnable(){
		ArenaManager.getInstance().setup();
		
		CommandManager cm = new CommandManager();
		cm.setup();
		getCommand("tw").setExecutor(cm);
		
		Bukkit.getServer().getPluginManager().registerEvents(new ArmorRemove(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LobbySign(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerAttack(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerLoseHunger(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerCapture(), this);
		
	}

}
