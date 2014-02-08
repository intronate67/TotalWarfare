package me.intronate67.TotalWarfare.cmds;

import org.bukkit.entity.Player;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;

public class ForceStart extends SubCommand{

	public void onCommand(Player p, String[] args){
		if(args.length == 0){
			MessageManager.getInstance().severe(p, "You must specify an arena ID.");
			return;
		}
		int id = -1;
		try{ id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().severe(p, args[0] + " is not a valid arena!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if(a == null){
			MessageManager.getInstance().severe(p, "There is no arena with ID " + id + "!");
			return;
		}
		
		if(a.isStarted()){
			MessageManager.getInstance().severe(p, "Arena " + id + " has not yet started!");
			return;
		}
		
		a.stop(null);
		MessageManager.getInstance().good(p, "Force stopped Arena " + a.getID() + "!");
	}
	
	public String name(){
		return "forcestop";
	}
	
	public String info(){
		return "Force stop an arena.";
	}
	
	public String[] aliases(){
		return new String[] { "fstop", "stop" };
	}
	
}
