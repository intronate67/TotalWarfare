package me.intronate67.TotalWarfare.cmds;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;
import me.intronate67.TotalWarfare.Arena.ArenaState;

import org.bukkit.entity.Player;

public class Delete extends SubCommand{
	
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			MessageManager.getInstance().severe(p, "You must specify an arena number!");
			return;
		}
		
		int id = -1;
		
		try { id = Integer.parseInt(args[0]); }
		catch (Exception e) {
			MessageManager.getInstance().severe(p, args[0] + " is not a valid number!");
			return;
		}
		
		Arena a = ArenaManager.getInstance().getArena(id);
		
		if (a == null) {
			MessageManager.getInstance().severe(p, "There is no arena with ID " + id + "!");
			return;
		}
		
		if (a.getState() == ArenaState.STARTED) {
			MessageManager.getInstance().severe(p, "Arena " + id + " is ingame!");
			return;
		}
		
		SettingsManager.getInstance().set(id + "", null);

		ArenaManager.getInstance().setup();
	}
	
	public String name() {
		return "delete";
	}
	
	public String info() {
		return "Delete an arena.";
	}
	
	public String[] aliases() {
		return new String[] { "d" };
	}

}
