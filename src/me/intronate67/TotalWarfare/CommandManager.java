package me.intronate67.TotalWarfare;

import java.util.ArrayList;
import java.util.Arrays;

import me.intronate67.TotalWarfare.cmds.Create;
import me.intronate67.TotalWarfare.cmds.Delete;
import me.intronate67.TotalWarfare.cmds.ForceStart;
import me.intronate67.TotalWarfare.cmds.ForceStop;
import me.intronate67.TotalWarfare.cmds.Join;
import me.intronate67.TotalWarfare.cmds.Leave;
import me.intronate67.TotalWarfare.cmds.Reload;
import me.intronate67.TotalWarfare.cmds.SetCapture;
import me.intronate67.TotalWarfare.cmds.SetLocation;
import me.intronate67.TotalWarfare.cmds.SubCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{

	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	
	public void setup() {
		commands.add(new Create());
		commands.add(new Delete());
		commands.add(new ForceStart());
		commands.add(new ForceStop());
		commands.add(new Join());
		commands.add(new Leave());
		commands.add(new Reload());
		commands.add(new SetLocation());
		commands.add(new SetCapture());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			MessageManager.getInstance().severe(sender, "Only players can use totalwarfare!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("totalwarfare")) {
			if (args.length == 0) {
				for (SubCommand c : commands) {
					MessageManager.getInstance().info(p, "/totalwarfare " + c.name() + " (" + aliases(c) + ")" + " - " + c.info());
				}
				return true;
			}
			
			SubCommand target = get(args[0]);
			
			if (target == null) {
				MessageManager.getInstance().severe(p, "/totalwarfare " + args[0] + " is not a valid subcommand!");
				return true;
			}
			
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			target.onCommand(p, args);
			
			return true;
		}
		
		return true;
	}
	
	private String aliases(SubCommand cmd) {
		String fin = "";
		
		for (String a : cmd.aliases()) {
			fin += a + " | ";
		}
		
		return fin.substring(0, fin.lastIndexOf(" | "));
	}
	
	private SubCommand get(String name) {
		for (SubCommand cmd : commands) {
			if (cmd.name().equalsIgnoreCase(name)) return cmd;
			for (String alias : cmd.aliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
	
}
