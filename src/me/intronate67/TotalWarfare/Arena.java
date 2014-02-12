package me.intronate67.TotalWarfare;

import java.util.ArrayList;
import java.util.Random;

import me.intronate67.TotalWarfare.ArenaManager.Team;
import me.intronate67.TotalWarfare.MessageManager.MessageType;
import me.intronate67.TotalWarfare.SettingsManager;
import me.intronate67.TotalWarfare.listeners.SignManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Arena {
	
	public enum ArenaState { DISABLED, WAITING, COUNTING_DOWN, STARTED; }

	private int id, taskID, numPlayers, currentPlayers = 0;
	protected ArenaState state = ArenaState.DISABLED;
	private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	private ArrayList<Sign> signs;
	private Location spawnPoint, heroSpawn, demonSpawn;
	Random rand = new Random();
	
	protected Arena(int id) {

		this.id = id;
		this.players = new ArrayList<PlayerData>();
		this.numPlayers = SettingsManager.getArenas().get("arenas." + id + ".numPlayers");
		
		ConfigurationSection s = SettingsManager.getArenas().get("arenas." + id + ".spawn");
		ConfigurationSection rS = SettingsManager.getArenas().get("arenas." + id + "heroSpawn");
		ConfigurationSection bS = SettingsManager.getArenas().get("arenas." + id + "demonSpawn");
		
		spawnPoint = LocationUtil.locationFromConfig(s, true);
		heroSpawn = LocationUtil.locationFromConfig(rS, true);
		demonSpawn = LocationUtil.locationFromConfig(bS, true);
		
		state = ArenaState.WAITING;
		
		this.signs = SignManager.getSigns(this);
	}
	
	public Location getSpawn(Team team){
		switch(team){
			case HEROES: return heroSpawn;
			case DEMONS: return demonSpawn;
			default: return null;
		}
	}
	
	public void setTeam(Team team1){
		int n = rand.nextInt(2) + 1;
		if(n == 1){
			setTeam(Team.DEMONS);
			
		}
			setTeam(Team.HEROES);
	}

	public Team getTeam(Player p){
		return getData(p).getTeam();
	}
	public int getID() {
		return id;
	}
	
	public ArenaState getState() {
		return state;
	}
	
	private Team getTeamWithLessPlayers() {
		int red = 0, blue = 0;
		for (PlayerData pd : players) {
			if (pd.getTeam() == Team.HEROES) red++;
			else blue++;
		}
		if (red > blue) return Team.DEMONS;
		else return Team.HEROES;
	}
	
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	public void addPlayer(Player p) {
		
		players.add(new PlayerData(p.getName(), getTeamWithLessPlayers(), p.getInventory().getContents(), p.getInventory().getArmorContents() , p.getLocation()));
		if (currentPlayers >= numPlayers) {
			MessageManager.getInstance().severe(p, "There are too many players.");
			return;
		}
		
		if (spawnPoint == null) {
			MessageManager.getInstance().severe(p, "Spawn point not yet set.");
			return;
		}
		
		p.getInventory().clear();
		//Default class below.
		p.teleport(spawnPoint);
		p.setGameMode(GameMode.SURVIVAL);
		currentPlayers++;
		
		MessageManager.getInstance().info(p, "You have joined arena " + getID() + " and are on the " + ChatColor.valueOf(getData(p).getTeam().toString()) + getData(p).getTeam().toString().toLowerCase() + ChatColor.BLUE + " team!");
		for (Sign s : signs) {
			s.setLine(2, currentPlayers + "");
		}
	}
	
	public void spawnTele(Player p){
		if(players.contains(p) != true){
			return;
		}
		getSpawn(getData(p).getTeam());
	}
	
	public void removePlayer(Player p, boolean lost) {
		PlayerData pd = getData(p);
		
		p.getInventory().clear();
		for (ItemStack i : pd.getContents()) if (i != null) p.getInventory().addItem(i);
		p.getInventory().setArmorContents(pd.getArmorContents());
		
		p.teleport(pd.getLocation());
		
		p.setScoreboard(null);
		
		players.remove(pd);
		
		
		if (lost) {
			MessageManager.getInstance().info(p, "You lost the game.");
		}
	}
	
	public void start() {
		this.state = ArenaState.COUNTING_DOWN;
		for (Sign s : signs) {
			s.setLine(3, getState().toString());
			s.update();
		}
		
		final Countdown c = new Countdown(30, "Game starting in %t seconds!", this, 30, 20, 10, 5, 4, 3, 2, 1);
		this.taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TotalWarfare.getPlugin(), new Runnable() {
			public void run() {
				if (!c.isDone) {
					c.run();
					state = ArenaState.STARTED;
					for (Sign s : signs) {
						s.setLine(3, getState().toString());
						s.update();
					}
				}
				else Bukkit.getServer().getScheduler().cancelTask(taskID);
				
			}
		}, 0, 20);
	}
	
	
	private PlayerData getData(Player p) {
		for (PlayerData pd : players) {
			if (pd.getPlayerName().equalsIgnoreCase(p.getPlayer().getName())) return pd;
		}
		return null;
	}
	public void stop(Player winner) {
		MessageManager.getInstance().broadcast(MessageType.INFO, winner != null ? winner.getName() + " won the game!" : "The game was ended.");
		for (PlayerData pd : players) {
			Player p = Bukkit.getServer().getPlayer(pd.getPlayerName());
			removePlayer(p, true);
			this.state = ArenaState.WAITING;
		}
	}

	
	class Countdown implements Runnable {
		
		public boolean isDone = false;
		public int timer;
		public String msg;
		public Arena a;
		public ArrayList<Integer> countingNums;
		public Player p;
		
		public Countdown(int start, String msg, Arena a, int... countingNums) {
			this.timer = start;
			this.msg = msg;
			this.a = a;
			this.countingNums = new ArrayList<Integer>();
			for (int i : countingNums) this.countingNums.add(i);
		}
		
		public void run() {
			
			if (timer == 0) {
				a.sendMessage(MessageType.GOOD, "The game has begun!");
				a.state = ArenaState.STARTED;
				isDone = true;
				if(players.contains(p.getName()) != true){
					return;
				}
				a.spawnTele(p);
				return;
				
			}
			
			if (countingNums.contains(timer)) {
				a.sendMessage(MessageType.INFO, msg.replaceAll("%t", timer + ""));
			}
			
			timer--;
		}
	}
	
	public boolean containsPlayer(Player p) {
		return getData(p) != null;
	}
	
	protected void sendMessage(MessageType type, String... messages) {
		for (@SuppressWarnings("unused") PlayerData d : players) MessageManager.getInstance().broadcast(MessageType.INFO, messages);
	}

}
