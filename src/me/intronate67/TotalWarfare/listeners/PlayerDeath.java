package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener{
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		if(ArenaManager.getInstance().getArena(e.getEntity()) == null) return;
		ArenaManager.getInstance().getArena(e.getEntity()).removePlayer(e.getEntity());
	}
	

}
