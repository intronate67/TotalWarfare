package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SetCapture extends SubCommand{

	public void onCommand(Player p, String[] args){
		if(args.length < 1){
			MessageManager.getInstance().severe(p, "You did not specify enough arguments!");
			return;
		}
		
		int id = -1;
		try{ id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().severe(p, args[0] + " is not a valid arena!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if(SettingsManager.getArenas().<ConfigurationSection>get(id + "") == null){
			MessageManager.getInstance().severe(p, "There is no arena with ID " + id + "!");
			return;
		}

		
		ConfigurationSection s = SettingsManager.getArenas().createConfigurationSection(a.getID() + "." + "capturePoint");
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		s.set("captureWorld", p.getWorld().getName());
		s.set("captureX", x);
		s.set("captureY", y);
		s.set("captureZ", z);
		
		MessageManager.getInstance().good(p, "Set capture point for arena " + a.getID());
	}
	public String name(){
		return "setcapture";
	}
	public String info(){
		return "Set a capture point";
	}
	public String[] aliases(){
		return new String[] { "sc" };
	}
	
}
