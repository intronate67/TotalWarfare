package me.intronate67.TotalWarfare;

import me.intronate67.TotalWarfare.ArenaManager.Team;

import org.bukkit.Location;
import org.bukkit.inventory.PlayerInventory;

public class PlayerData {

	private String player;
	private Team team;
	private PlayerInventory inv;
	private Location loc;
	
	
	public PlayerData(String player, Team team, PlayerInventory inv, Location loc){
		this.player = player;
		this.team = team;
		this.inv = inv;
		this.loc = loc;
	}
	
	public String getPlayerName(){
		return player;
	}
	
	public Team getTeam(){
		return team;
	}
	public PlayerInventory getInventory(){
		return inv;
	}
	public Location getLocation(){
		return loc;
	}
}
