package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
	
	int n = 2;
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (ArenaManager.getInstance().getArena(e.getEntity()) == null) return;
		if(n == 2){
			try {
				wait(40L);
				ArenaManager.getInstance().getArena(e.getEntity()).getSpawn(ArenaManager.getInstance().getArena(e.getEntity()).getTeam(e.getEntity()));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
