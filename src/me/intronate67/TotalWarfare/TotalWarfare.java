package me.intronate67.TotalWarfare;

import me.intronate67.TotalWarfare.listeners.ArmorRemove;
import me.intronate67.TotalWarfare.listeners.BlockBreak;
import me.intronate67.TotalWarfare.listeners.LobbySign;
import me.intronate67.TotalWarfare.listeners.PlayerAttack;
import me.intronate67.TotalWarfare.listeners.PlayerDeath;
import me.intronate67.TotalWarfare.listeners.PlayerLeave;
import me.intronate67.TotalWarfare.listeners.PlayerLoseHunger;
import me.intronate67.TotalWarfare.listeners.SignManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TotalWarfare extends JavaPlugin{
	
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(new SignManager(), this);
		
		ArenaManager.getInstance().setupArenas();
		
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
		
	}
	public void onDisable(){
		for(Arena a : ArenaManager.getInstance().getArenas()){
			a.stop(null);
		}
	}
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("TotalWarfare");
	}

}
