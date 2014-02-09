package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;
import me.intronate67.TotalWarfare.ArenaManager.Team;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerCapture implements Listener{
	
	
	int id = -1;
	Arena a = ArenaManager.getInstance().getArena(id);
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Team pTeam = ArenaManager.getInstance().getArena(id).getTeam(p);
		Location redLoc = e.getPlayer().getLocation();
		Location capture = ArenaManager.getInstance().getArena(id).getCaptureLocation();
		if(a.isStarted()){
			if(redLoc == capture){
				if(e.getPlayer().getInventory().contains(Material.GOLD_INGOT)){
					ArenaManager.getInstance().getArena(id).stop(pTeam);
				}
			}
		}
	}

}
