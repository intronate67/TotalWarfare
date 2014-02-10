package me.intronate67.TotalWarfare;

import java.util.ArrayList;

import me.intronate67.TotalWarfare.ArenaManager.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Arena extends JavaPlugin{
	
	public enum ArenaState { DISABLED, WAITING, COUNTING_DOWN, STARTED; }
	public boolean isHeroes = false;
	private boolean cd = false;
	private int id;
	protected ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	
	private Scoreboard sb;
	private Objective o;
	private Score heroes, demons;
	private Location redspawn, bluespawn;
	private Location capturePoint;
	protected ArenaState state = ArenaState.DISABLED;
	
	protected Arena(int id){
		this.id = id;
		
		ConfigurationSection conf = SettingsManager.getInstance().get(id + "");
		
		this.redspawn = getLocation(conf.getConfigurationSection("redspawn"));
		this.bluespawn = getLocation(conf.getConfigurationSection( "bluespawn"));
		this.capturePoint = getCaptureLocation(conf.getConfigurationSection("capturePoint"));
		
		state = ArenaState.WAITING;
		sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		o = sb.registerNewObjective("Team Scores", "dummy");
		heroes = o.getScore(Bukkit.getServer().getOfflinePlayer(ChatColor.RED + "Heroes"));
		demons = o.getScore(Bukkit.getServer().getOfflinePlayer(ChatColor.RED + "Demons"));
	}
	Arena a = ArenaManager.getInstance().getArena(id);
	
	private Location getLocation(ConfigurationSection path){
		return new Location(
				Bukkit.getServer().getWorld("world"),
				path.getDouble("x"),
				path.getDouble("y"),
				path.getDouble("z"));
	}
	public Location getCaptureLocation(ConfigurationSection path){
		return new Location(
				Bukkit.getServer().getWorld("captureWorld"),
				path.getDouble("captureX"),
				path.getDouble("captureY"),
				path.getDouble("captureZ"));
	}
	public ArenaState getState(){
		return state;
	}
	
	public int getID(){
		return id;
	}
	
	
	public boolean isHeroes(){
		return isHeroes;
	}
	public void setHeroes(boolean isHeroes){
		this.isHeroes = isHeroes;
	}
	
	public Location getSpawn(Team team){
		switch(team){
			case RED: return redspawn;
			case BLUE: return bluespawn;
			default: return null;
		}
	}
	public Location getCapture(){
		return capturePoint;
	}
	
	public Team getTeam(Player p){
		return getData(p).getTeam();
	}
	
	public void addPlayer(Player p){
		players.add(new PlayerData(p.getName(), getTeamWithLessPlayers(), p.getInventory().getContents(), p.getLocation()));
		
		p.getInventory().clear();
		p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 1));
		p.teleport(getSpawn(getData(p).getTeam()));
		
		p.setScoreboard(sb);
		
		p.setGameMode(GameMode.SURVIVAL);
		p.setFlying(false);
		
		MessageManager.getInstance().info(p, "You have joined arena " + getID() + " and are on the " + ChatColor.valueOf(getData(p).getTeam().toString()) + getData(p).getTeam().toString().toLowerCase() + ChatColor.YELLOW + " team!");
		
		if (players.size() >= 2 && !cd) start();
	}
	
	public void removePlayer(Player p, boolean b){
		players.add(new PlayerData(p.getName(), getTeamWithLessPlayers(), p.getInventory().getContents(), p.getLocation()));
		
		p.getInventory().clear();
		p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 1));
		// Could add colored armor.
		
		p.teleport(getSpawn(getData(p).getTeam()));
		
		p.setScoreboard(sb);
		
		p.setGameMode(GameMode.SURVIVAL);
		p.setFlying(false);
		
		MessageManager.getInstance().info(p, "You have joined arena " + getID() + " and are on the " + ChatColor.valueOf(getData(p).getTeam().toString()) + getData(p).getTeam().toString().toLowerCase() + ChatColor.YELLOW + " team!");
		
		if (players.size() >= 2 && !cd) start();
	}
	
	public void start(){
		this.state = ArenaState.COUNTING_DOWN;
		cd = true;
		msg("Game starting in 30 seconds!");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SettingsManager.getInstance().getPlugin(), new Runnable(){
			public void run(){
				Arena.this.cd = false;
				msg("Good luck!");
			}
		}, 30 * 20);
		state = ArenaState.STARTED;
	}
	
	public void stop(Team pTeam){
		msg(pTeam != null ? pTeam.name() + " won the game!" : "The game was ended.");
		for (PlayerData pd : players) {
			Player p = Bukkit.getServer().getPlayer(pd.getPlayerName());
			removePlayer(p, true);
		}
	}
	
	public void addDeath(Player p){
		Team t = getTeam(p);
		if(t == Team.RED) demons.setScore(demons.getScore() + 1);
		else heroes.setScore(heroes.getScore() + 1);
	}
	
	private void msg(String msg){
		for(PlayerData pd : players){
			Player p = Bukkit.getServer().getPlayer(pd.getPlayerName());
			MessageManager.getInstance().info(p, msg);
		}
	}
	
	private Team getTeamWithLessPlayers(){
		int red = 0, blue = 0;
		for(PlayerData pd : players){
			if(pd.getTeam() == Team.RED) red ++;
			else blue++;
		}
		if(red > blue) return Team.BLUE;
		else return Team.RED;
	}
	
	public boolean containsPlayer(Player p){
		return getData(p) != null;
	}
	private PlayerData getData(Player p){
		for(PlayerData pd : players){
			if(pd.getPlayerName().equalsIgnoreCase(p.getName())) return pd;
		}
		return null;
	}
}
