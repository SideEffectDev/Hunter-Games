package me.sideeffect.huntergames;



import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
	static HunterGames plugin;

	public SetspawnCommand(HunterGames instance) {
	plugin = instance;
	}  
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		Player player = (Player)s;
		if(l.equalsIgnoreCase("setspawn") && Methods.hasPermission(player, "huntergames.admin")){
			  
			String team = args[0];
			String arena = args[1];;
			
			
			if(args.length < 2 && args.length >= 1){
				player.sendMessage(HunterGames.P + ChatColor.RED  + " /Setspawn" + ChatColor.GOLD + "<Humans / Zombies> + ChatColor.RED + <Arena>");
			}if(args.length == 2){
				
				
				
	            
	                
	            
				
				
					if(team.toLowerCase().equals("humans") || team.toLowerCase().equals("zombies")){
						player.sendMessage(HunterGames.P + ChatColor.RED +  "You created the " + ChatColor.GOLD +  team + ChatColor.RED + " spawnpoint for the arena " + ChatColor.GOLD + arena);
					
					
					
			        Location loc = player.getLocation();
			        String w = player.getLocation().getWorld().getName().toString();
			        plugin.getConfig().set(arena + "." + team + ".X", Double.valueOf(loc.getX()));
			        plugin.getConfig().set(arena + "." + team + ".Y", Double.valueOf(loc.getY()));
			        plugin.getConfig().set(arena + "." + team + ".Z", Double.valueOf(loc.getZ()));
			        plugin.getConfig().set(arena + "." + team + ".W", w);
			        plugin.saveConfig();
			        
					}
					
			
			
			} 
	    
		
	    }else {
	    	s.sendMessage(ChatColor.RED + " You don't have " + ChatColor.GOLD + "permission" + ChatColor.RED + " for this command!");
	    	
	    }
		return false;
	}

}
