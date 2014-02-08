package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;

import org.bukkit.entity.Player;

public class Leave extends SubCommand{

	public void onCommand(Player p, String[] args){
		
		Arena a = ArenaManager.getInstance().getArena(p);
		if(a == null){
			MessageManager.getInstance().severe(p, "You are are not in a game!");
			return;
		}
		
		a.removePlayer(p);
	}
	
	public String name(){
		return "leave";
	}
	
	public String info(){
		return "Leave an arena";
	}
	
	public String[] aliases(){
		return new String[]{ "l" };
	}
}
