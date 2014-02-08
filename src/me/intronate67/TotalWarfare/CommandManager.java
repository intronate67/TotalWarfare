package me.intronate67.TotalWarfare;

import java.util.ArrayList;
import java.util.Arrays;

import me.intronate67.TotalWarfare.cmds.SubCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{

	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	
	public void setup(){
		//Add all commands.
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.getInstance().severe(sender, "Only Players can use that command!");
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("tw")){
			if(args.length == 0){
				for(SubCommand c : commands){
					MessageManager.getInstance().info(p, "/tw " + c.name() + "(" + aliases(c) + ")" + " - " + c.info()) ;
				}
				return true;
			}
			
			SubCommand target = get(args[0]);
			
			if(target == null){
				MessageManager.getInstance().severe(p, "/tw " + args[0] + " is not a valid subcommand!");
				return true;
			}
			
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			try{
				target.onCommand(p, args);
			}catch(Exception e){
				MessageManager.getInstance().severe(p, "An error has occured: " + e.getCause());
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private String aliases(SubCommand cmd){
		String fin = "";
		
		for(String a : cmd.aliases()){
			fin += a + " | ";
		}
		return fin.substring(0, fin.lastIndexOf("| "));
	}
	
	private SubCommand get(String name){
		for(SubCommand cmd : commands){
			if(cmd.name().equalsIgnoreCase(name)) return cmd;
		}
		return null;
	}
	
}
