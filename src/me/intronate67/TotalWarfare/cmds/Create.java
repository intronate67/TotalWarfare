package me.intronate67.TotalWarfare.cmds;

import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.MessageType;
import me.intronate67.TotalWarfare.SettingsManager;

public class Create extends SubCommand{
	
	public void onCommand(Player p, String[] args) {
		int id = ArenaManager.getInstance().getArenas().size() + 1;
		
		SettingsManager.getArenas().createConfigurationSection("arenas." + id);
		SettingsManager.getArenas().set("arenas." + id + ".numPlayers", 10);
		
		MessageManager.getInstance().msg(p, MessageType.GOOD, "Created Arena " + id + "!");
		
		ArenaManager.getInstance().setupArenas();
	}
	
	public String name() {
		return "create";
	}
	
	public String info() {
		return "Create an arena.";
	}
	
	public String[] aliases() {
		return new String[] { "c" };
	}

}
