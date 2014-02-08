package me.intronate67.TotalWarfare.cmds;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	public abstract void onCommand(Player p, String[] args);
	
	public abstract String name();
	
	public abstract String info();
	
	public abstract String[] aliases();

}
