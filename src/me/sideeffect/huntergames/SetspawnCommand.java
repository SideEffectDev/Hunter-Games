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
		if(l.equalsIgnoreCase("setspawn")){
			  Player player = (Player)s;
			String team = args[0];
			String arena = args[1];;
			
			//List<String> List = plugin.plugin.plugin.getConfig().getStringList("Motd");
			//for (String s : List){
			if(args.length < 2 && args.length >= 1){
				player.sendMessage(HunterGames.P + ChatColor.GOLD + "/setspawn <Humans / Red> <Zombies>");
			}if(args.length == 2){
				
				
				
	            
	                
	            
				//if(s.toLowerCase().contains(arena.toLowerCase())){
				
					if(team.toLowerCase().equals("humans") || team.toLowerCase().equals("zombies")){
						player.sendMessage(HunterGames.P + "A spawn has been created at this location named " + arena + " for team " + team );
					
					
					//this.liste = plugin.plugin.getConfig().getStringList("Arenas.List");
		            //this.liste.add(arena);
			        Location loc = player.getLocation();
			        String w = player.getLocation().getWorld().getName().toString();
			        plugin.getConfig().set(arena + "." + team + ".X", Double.valueOf(loc.getX()));
			        plugin.getConfig().set(arena + "." + team + ".Y", Double.valueOf(loc.getY()));
			        plugin.getConfig().set(arena + "." + team + ".Z", Double.valueOf(loc.getZ()));
			        plugin.getConfig().set(arena + "." + team + ".W", w);
			        plugin.saveConfig();
			        
					}
					
			//}
			
			} //else{ //if(!s.toLowerCase().contains(arena.toLowerCase())){
					//player.sendMessage(Tdm.P + ChatColor.RED + "There is no arena with that name!");
				
			//}
		//}
	    
		
	    }
		return false;
	}

}
