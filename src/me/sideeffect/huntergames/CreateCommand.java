package me.sideeffect.huntergames;



import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {
	static HunterGames plugin;
	public static List<String> list;
	public CreateCommand(HunterGames instance) {
	plugin = instance;
	}  
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l, String[]args){
		  Player player = (Player)s;
		 if(l.equalsIgnoreCase("create")){
				if(args.length < 1){
				player.sendMessage(HunterGames.P + "/create <arena>");
				}
		    if(args.length == 1){	
				
				if (plugin.getConfig().contains("Arenas.List")) {
		              List<String> list = plugin.getConfig().getStringList("Arenas.List");
		              list.add(args[0]);
		              plugin.getConfig().set("Arenas.List", list);
		            } else {
		              List<String> list = new ArrayList<String>();
		              list.add(args[0]);
		              plugin.getConfig().addDefault("Arenas.List", list);
		            }
		     

		      plugin.saveConfig();
		      player.sendMessage(HunterGames.P + ChatColor.BLUE + "An Arena has been created called: " + ChatColor.DARK_RED + args[0]);
		    	}
			
		    }		
	return false;
	}
}
