package me.intronate67.TotalWarfare.cmds;

import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

public class Create extends SubCommand{
	
	public void onCommand(Player p, String[] args){
		int id = ArenaManager.getInstance().getArenas().size() + 1;
		SettingsManager.getInstance().createConfigurationSection(id + "");
		MessageManager.getInstance().good(p, "Created Arena: " + id + "!");
	}
	
	public String name(){
		return "create";
	}
	public String info(){
		return "Create an arena.";
	}
	
	public String[] aliases(){
		return new String[]{ "c" };
	}

}
