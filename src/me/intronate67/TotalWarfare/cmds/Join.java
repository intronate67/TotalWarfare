package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Join extends SubCommand{
	
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(p) != null) {
			MessageManager.getInstance().severe(p, "You are already in a game!");
			return;
		}
		
		if (args.length == 0) {
			MessageManager.getInstance().severe(p, "You must specify an arena ID.");
			return;
		}
		
		int id = -1;
		
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().severe(p, args[0] + " is not a valid number!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if (SettingsManager.getInstance().<ConfigurationSection>get(id + "") == null) {
			MessageManager.getInstance().severe(p, "There is no arena with ID " + id + "!");
			return;
		}
		a.addPlayer(p);
	}
	
	public String name() {
		return "join";
	}
	
	public String info() {
		return "Join a game.";
	}
	
	public String[] aliases() {
		return new String[] { "j" };
	}
}
