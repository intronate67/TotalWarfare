package me.intronate67.TotalWarfare;

import java.util.ArrayList;

import me.intronate67.TotalWarfare.ArenaManager.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Arena extends JavaPlugin{
	
	private boolean started = false;
	private int id;
	protected ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	
	private Scoreboard sb;
	private Objective o;
	private Score heroes, demons;
	private Location redspawn, bluespawn;
	private Location capturePoint;
	
	protected Arena(int id){
		this.id = id;
		
		ConfigurationSection conf = SettingsManager.getInstance().get(id + "");
		
		this.redspawn = getLocation(conf.getConfigurationSection("redspawn"));
		this.bluespawn = getLocation(conf.getConfigurationSection( "bluespawn"));
		
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
	public Location getCaptureLocation(){
		return (Location) this.getConfig().getConfigurationSection(a.getID() + "." + "capturePoint").getValues(true);
	
	}
	
	public int getID(){
		return id;
	}
	
	public boolean isStarted(){
		return started;
	}
	
	public void setStarted(boolean started){
		this.started = started;
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
		//Heroes default class paladin.
		// TODO: Add other hero Classes.
		//Sword
		ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
		ItemMeta ironMeta = ironSword.getItemMeta();
		ironMeta.setDisplayName("Holy Light");
		ironSword.setItemMeta(ironMeta);
		ironSword.addEnchantment(Enchantment.DAMAGE_UNDEAD, 1);
		//Armor
		//Helmet
		ItemStack lHelm = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta lHelmMeta = (LeatherArmorMeta)lHelm.getItemMeta();
		lHelmMeta.setColor(Color.WHITE);
		//Chestplate
		ItemStack lChest = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lChestMeta = (LeatherArmorMeta)lChest.getItemMeta();
		lChestMeta.setColor(Color.WHITE);
		//Leggings
		ItemStack lLegs = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta lLegsMeta = (LeatherArmorMeta)lLegs.getItemMeta();
		lLegsMeta.setColor(Color.WHITE);
		//Boots
		ItemStack lBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta lBootsMeta = (LeatherArmorMeta)lBoots.getItemMeta();
		lBootsMeta.setColor(Color.WHITE);
		//Demon default class Brute.
		//Sword
		ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
		ItemMeta stoneMeta = stoneSword.getItemMeta();
		stoneMeta.setDisplayName("Gnarly Claw");
		stoneSword.setItemMeta(stoneMeta);
		stoneSword.addEnchantment(Enchantment.DURABILITY, 10);
		//Armor
		ItemStack iChest = new ItemStack(Material.IRON_CHESTPLATE);
		
		players.add(new PlayerData(p.getName(), getTeamWithLessPlayers(), p.getInventory(), p.getLocation()));
		for(PlayerData pd : players){
			if(pd.getTeam() == Team.RED){
				p.getInventory().clear();
				p.getInventory().addItem(ironSword);
				p.getInventory().setHelmet(lHelm);
				p.getInventory().setChestplate(lChest);
				p.getInventory().setLeggings(lLegs);
				p.getInventory().setBoots(lBoots);
			}else{
				p.getInventory().clear();
				p.getActivePotionEffects().add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, 600));
				p.getInventory().addItem(stoneSword);
				p.getInventory().setChestplate(iChest);
			}
		}
		
		p.teleport(getSpawn(getData(p).getTeam()));
		
		p.setScoreboard(sb);
		
		if(players.size() >= 2) start();
	}
	
	public void removePlayer(Player p){
		PlayerData pd = getData(p);
		p.getInventory().clear();
		p.getInventory().addItem(pd.getInventory().getContents());
		p.getInventory().setArmorContents(pd.getInventory().getArmorContents());
		
		p.teleport(pd.getLocation());
		
		p.setScoreboard(null);
		
		players.remove(pd);
		
		MessageManager.getInstance().info(p, "You lost the game.");
		msg(p.getName() + " lost the game.");
	}
	
	public void start(){
		msg("Game starting in 30 seconds!");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SettingsManager.getInstance().getPlugin(), new Runnable(){
			public void run(){
				Arena.this.started = true;
				msg("Good luck!");
			}
		}, 30 * 20);
	}
	
	public void stop(Team pTeam){
		msg(pTeam != null ? pTeam.name() + " won the game!" : "The game was ended.");
		for(PlayerData pd : players){
			Player p = Bukkit.getServer().getPlayer(pd.getPlayerName());
			p.getInventory().clear();
			p.getInventory().addItem(pd.getInventory().getContents());
			p.getInventory().setArmorContents(pd.getInventory().getArmorContents());
			
			p.teleport(pd.getLocation());
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
