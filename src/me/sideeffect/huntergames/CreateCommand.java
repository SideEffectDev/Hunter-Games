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
		
		 if(l.equalsIgnoreCase("create") && Methods.hasPermission(player, "huntergames.admin")){
			 
				if(args.length < 1){
					
				player.sendMessage(HunterGames.P + ChatColor.RED + " /Create " + ChatColor.GOLD + "<Arena>");
				}
				List<String> list = plugin.getConfig().getStringList("Arenas.List");
				
		    if(args.length == 1){	
				if(!list.contains(args[0])){
					player.sendMessage(HunterGames.P + ChatColor.RED + " You created the arena " + ChatColor.GOLD + args[0] + ChatColor.RED + "!");
				if (plugin.getConfig().contains("Arenas.List")) {
		              List<String> list1 = plugin.getConfig().getStringList("Arenas.List");
		              list1.add(args[0]);
		              plugin.getConfig().set("Arenas.List", list1);
		            } else {
		              List<String> list1 = new ArrayList<String>();
		              list1.add(args[0]);
		              plugin.getConfig().addDefault("Arenas.List", list1);
		            }
				}else{
					s.sendMessage(HunterGames.P + ChatColor.RED + " Sorry! An arena named " + ChatColor.GOLD + args[0] + ChatColor.RED + " is already in the arena list!");
				}

		      plugin.saveConfig();
		      
		    	}
			
		    }else{
		    	s.sendMessage(HunterGames.P + ChatColor.RED + " You don't have " + ChatColor.GOLD + "permission" + ChatColor.RED + " for this command!");
		    }
	return false;
	}
}
