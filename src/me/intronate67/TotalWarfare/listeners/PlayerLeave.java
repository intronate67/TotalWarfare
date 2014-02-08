package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.Arena;
import me.intronate67.TotalWarfare.ArenaManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener{
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		Arena a = ArenaManager.getInstance().getArena(e.getPlayer());
		if(a == null) return;
		a.removePlayer(e.getPlayer());
	}

}
