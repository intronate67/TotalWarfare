package me.intronate67.TotalWarfare;

import me.intronate67.TotalWarfare.ArenaManager.Team;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PlayerData {

	private String player;
	private Team team;
	private ItemStack[] contents;
	private Location loc;
	
	public PlayerData(String player, Team team, ItemStack[] contents, Location loc) {
		this.player = player;
		this.team = team;
		this.contents = contents;
		this.loc = loc;
	}
	
	public String getPlayerName() {
		return player;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public ItemStack[] getContents() {
		return contents;
	}
	
	public Location getLocation() {
		return loc;
	}
}
