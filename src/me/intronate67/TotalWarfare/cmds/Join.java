package me.intronate67.TotalWarfare.cmds;

import java.util.Random;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.Arena.ArenaState;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.MessageType;

import org.bukkit.entity.Player;

public class Join extends SubCommand{

	Random rand = new Random();
	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(p) != null) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "You are already in an arena!");
			return;
		}
		
		if (args.length == 0) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "You must specify an arena number!");
			return;
		}
		
		int id = -1;
		
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) { MessageManager.getInstance().msg(p, MessageType.BAD, args[0] + " is not a number!"); }
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if (a == null) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "That arena doesn't exist!");
			return;
		}
		
		if (a.getState() == ArenaState.DISABLED || a.getState() == ArenaState.STARTED) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "That arena is " + a.getState().toString().toLowerCase() + "!");
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
