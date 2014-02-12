package me.intronate67.TotalWarfare.cmds;

import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;

public class Reload extends SubCommand{
	public void onCommand(Player p, String[] args) {
		ArenaManager.getInstance().setupArenas();
		MessageManager.getInstance().good(p, "Reloaded!");
	}
	
	public String name() {
		return "reload";
	}
	
	public String info() {
		return "Reload arenas.";
	}
	
	public String[] aliases() {
		return new String[] { "r" };
	}
}
