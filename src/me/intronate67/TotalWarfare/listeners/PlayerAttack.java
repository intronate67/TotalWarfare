package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAttack implements Listener{
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			if(ArenaManager.getInstance().getArena(p) != null) e.setCancelled(true);
		}
	}

}
