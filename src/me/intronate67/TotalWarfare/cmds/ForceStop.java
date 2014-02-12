package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.Arena.ArenaState;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.MessageType;

import org.bukkit.entity.Player;

public class ForceStop extends SubCommand{

	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "You must specify an arena ID.");
			return;
		}
		
		int id = -1;
		
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().msg(p, MessageType.BAD, args[0] + " is not a valid number!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if (a == null) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "There is no arena with ID " + id + "!");
			return;
		}
		
		if (a.getState() != ArenaState.STARTED) {
			MessageManager.getInstance().msg(p, MessageType.BAD, "Arena " + id + " is not running!");
			return;
		}
		
		a.stop(p.getPlayer());
		MessageManager.getInstance().msg(p, MessageType.GOOD, "Force stopped arena " + a.getID() + "!");
	}
	
	public String name() {
		return "forcestop";
	}
	
	public String info() {
		return "Force stop an arena.";
	}
	
	public String[] aliases() {
		return new String[] { "fstop", "stop" };
	}
	
}
