package me.sideeffect.huntergames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetlobbyCommand implements CommandExecutor {
	static HunterGames plugin;

	public SetlobbyCommand(HunterGames instance) {
	plugin = instance;
	}  
		@Override
		public boolean onCommand(CommandSender s, Command cmd, String l,
				String[] args) {
			Player player = (Player)s;
		if(Methods.hasPermission(player, "huntergames.admin")){
				if(l.equalsIgnoreCase("setlobby") && args.length == 0){
					
					s.sendMessage(HunterGames.P + ChatColor.GOLD + " The " + ChatColor.RED + "Lobby" + ChatColor.GOLD + " SpawnPoint has been set!");
					Location loc = player.getLocation();
			        String w = player.getLocation().getWorld().getName().toString();
			        plugin.getConfig().set("Lobby" + "." +  "X", Double.valueOf(loc.getX()));
			        plugin.getConfig().set("Lobby" + "." +  "Y", Double.valueOf(loc.getY()));
			        plugin.getConfig().set("Lobby" + "." +  "Z", Double.valueOf(loc.getZ()));
			        plugin.getConfig().set("Lobby" + "." +  "W", w);
			        plugin.saveConfig();
				}else{
					s.sendMessage(HunterGames.P + ChatColor.RED + " Something went wrong!");
				}
			
			}else{
				s.sendMessage(HunterGames.P + ChatColor.RED + " You don't have " + ChatColor.GOLD + "permission" + ChatColor.RED + " for this command!");
			}
		return false;
		}

}
