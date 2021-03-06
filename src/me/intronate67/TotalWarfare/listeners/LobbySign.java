package me.intronate67.TotalWarfare.listeners;

import me.intronate67.TotalWarfare.MessageManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbySign implements Listener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if(!(e.getClickedBlock().getType() == Material.SIGN) && !(e.getClickedBlock().getType() == Material.SIGN_POST)) return;
		
		Sign s = (Sign) e.getClickedBlock().getState();
		
		if(s.getLine(0).equals(ChatColor.BLUE + "TotalWarfare")){
			int id = -1;
			try{ id = Integer.parseInt(s.getLine(2)); }
			catch (Exception ex) {
				MessageManager.getInstance().severe(e.getPlayer(), s.getLine(2) + " is not a valid number!");
				return;
			}
			e.getPlayer().performCommand("totalwarfare join " + id);
		}
		
	}
	@EventHandler
	public void onSignChange(SignChangeEvent e){
		if(e.getLines().length > 0 && !e.getLine(0).equalsIgnoreCase("TotalWarfare")) return;
		if(e.getLines().length < 3){
			e.getBlock().breakNaturally();
			MessageManager.getInstance().severe(e.getPlayer(), "A TotalWarfare sign must have ay least 3 lines!");
			return;
		}
		try{ Integer.parseInt(e.getLine(2)); }
		catch (Exception ex) {
			e.getBlock().breakNaturally();
			MessageManager.getInstance().severe(e.getPlayer(), e.getLine(2) + " is not a valid number!");
			return;
		}
		
		e.setLine(0, ChatColor.BLUE + "TotalWarfare");
	}
	
	//SignFormat
	/**
	 * [TotalWarfare]
	 * Join
	 * #
	 */

}
