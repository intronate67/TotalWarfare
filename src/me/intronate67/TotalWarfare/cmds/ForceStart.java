package me.intronate67.TotalWarfare.cmds;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.Arena.ArenaState;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

public class ForceStart extends SubCommand{

	public void onCommand(Player p, String[] args) {
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
		
		if (a.getState() == ArenaState.STARTED) {
			MessageManager.getInstance().severe(p, "Arena " + id + " has already started!");
			return;
		}
		
		a.start();
		MessageManager.getInstance().good(p, "Force started Arena " + a.getID() + "!");
	}
	
	public String name() {
		return "forcestart";
	}
	
	public String info() {
		return "Force start an arena.";
	}
	
	public String[] aliases() {
		return new String[] { "fstart", "start" };
	}
	
}
