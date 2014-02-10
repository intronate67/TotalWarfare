package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Leave extends SubCommand{

	public void onCommand(Player p, String[] args) {
		Arena a = ArenaManager.getInstance().getArena(p);
		
		int id = -1;
		if (SettingsManager.getInstance().<ConfigurationSection>get(id + "") == null) {
			MessageManager.getInstance().severe(p, "You are not in a game!");
			return;
		}
		
		a.removePlayer(p, false);
	}
	
	public String name() {
		return "leave";
	}
	
	public String info() {
		return "Leave an arena.";
	}
	
	public String[] aliases() {
		return new String[] { "l" };
	}
}
