package me.intronate67.TotalWarfare.cmds;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.ArenaManager.Team;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

public class SetLocation extends SubCommand{
	
	public void onCommand(Player p, String[] args) {
		if (args.length < 2) {
			MessageManager.getInstance().severe(p, "You did not specify enough arguments.");
			return;
		}
		
		int id = -1;
		
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().severe(p, args[0] + " is not a valid number!");
			return;
		}
		
		if (SettingsManager.getInstance().<ConfigurationSection>get(id + "") == null) {
			MessageManager.getInstance().severe(p, "There is no arena with ID " + id + "!");
			return;
		}
		
		Team team = null;
		
		try { team = Team.valueOf(args[1].toUpperCase()); }
		catch (Exception e) { 
			MessageManager.getInstance().severe(p, args[1] + " is not a valid team!");
			return;
		}
		
		ConfigurationSection s = SettingsManager.getInstance().createConfigurationSection(id + "." + team.toString().toLowerCase() + "spawn");
		
		s.set("world", p.getWorld().getName());
		s.set("x", p.getLocation().getX());
		s.set("y", p.getLocation().getY());
		s.set("z", p.getLocation().getZ());
		
		SettingsManager.getInstance().set(id + "." + team.toString().toLowerCase() + "spawn", s);
		
		MessageManager.getInstance().good(p, "Set " + team.toString().toLowerCase() + " spawn!");
	}
	
	public String name() {
		return "setlocation";
	}
	
	public String info() {
		return "Set a team spawn";
	}
	
	public String[] aliases() {
		return new String[] { "s" };
	}
}
