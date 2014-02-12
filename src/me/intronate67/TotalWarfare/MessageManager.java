package me.intronate67.TotalWarfare;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {
	
	private MessageManager(){ }
	
	public enum MessageType {

		INFO(ChatColor.GRAY),
		GOOD(ChatColor.GREEN),
		BAD(ChatColor.RED);

		private ChatColor color;

		MessageType(ChatColor color) {
			this.color = color;
		}

		public ChatColor getColor() {
			return color;
		}
	}
	
	private static MessageManager instance = new MessageManager();
	
	public static MessageManager getInstance(){
		return instance;
	}
	
	private String prefix = ChatColor.BLUE + "[" + ChatColor.GREEN + "TotalWarfare" + ChatColor.BLUE + "] ";
	
	
	public void info(CommandSender s, String msg){
		msg(s, ChatColor.BLUE, msg);
	}
	
	public void msg(CommandSender sender, MessageType type, String... messages) {
		for (String msg : messages) {
			sender.sendMessage(prefix + type.getColor() + msg);
		}
	}
	public void severe(CommandSender s, String msg){
		msg(s, ChatColor.RED, msg);
	}
	
	public void good(CommandSender s, String msg){
		msg(s, ChatColor.GREEN, msg);
	}
	
	private void msg(CommandSender s, ChatColor color, String msg){
		s.sendMessage(prefix + color + msg);
	}
	public void broadcast(MessageType type, String... messages) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			for (String msg : messages) {
				p.sendMessage(prefix + type.getColor() + msg);
			}
		}
	}


}
