package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.MessageType;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SetSpawn extends SubCommand{

	public void onCommand(Player p, String[] args){
		
		if (args.length == 0) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "You did not specify an arena ID.");
			return;
		}
			
		int id = -1;
			
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().msg(p, MessageType.BAD, args[0] + " is not a valid number!");
			return;
		}
			
		if (SettingsManager.getArenas().<ConfigurationSection>get("arenas." + id) == null) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "There is no arena with ID " + id + "!");
			return;
		}
		ConfigurationSection rS = SettingsManager.getArenas().createConfigurationSection("arenas." + id + ".heroSpawn");
		ConfigurationSection bS = SettingsManager.getArenas().createConfigurationSection("arenas." + id + ".demonSpawn");

		if(args[1].equalsIgnoreCase("demons")){
			bS.set("world", p.getWorld().getName());
			bS.set("x", p.getLocation().getX());
			bS.set("y", p.getLocation().getY());
			bS.set("z", p.getLocation().getZ());
			SettingsManager.getArenas().set("arenas." + id + ".demonSpawn", bS);

			MessageManager.getInstance().msg(p, MessageType.GOOD, "Set spawn for team: " + args[1] + " in arena: " + id + "!");
			return;
		} 
		if(args[1].equalsIgnoreCase("heroes")){
			rS.set("world", p.getWorld().getName());
			rS.set("x", p.getLocation().getX());
			rS.set("y", p.getLocation().getY());
			rS.set("z", p.getLocation().getZ());
			SettingsManager.getArenas().set("arenas." + id + ".heroSpawn", rS);
		
			MessageManager.getInstance().msg(p, MessageType.GOOD, "Set spawn for team: " + args[1] + " in arena: " + id + "!");
			return;
		}else{
			MessageManager.getInstance().msg(p, MessageType.BAD, "There is no team with the name: " + args[1]);
		}
	}
	
	
	public String name() {
		return "setSpawn";
	}
	
	public String info() {
		return "Set a team spawn";
	}
	
	public String[] aliases() {
		return new String[] { "sS" };
	}
}
