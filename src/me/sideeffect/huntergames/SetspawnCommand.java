package me.sideeffect.huntergames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
	
	static FileManager settings = FileManager.getInstance();

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		
		Player player = (Player) s;
		if (l.equalsIgnoreCase("setspawn")
				&& (Methods.hasPermission(player, "huntergames.admin")) || player.isOp()) {

			
			if (args.length == 0 || args.length == 1) {
				s.sendMessage(HunterGames.P + ChatColor.RED + " /Setspawn "
						+ ChatColor.GOLD + "<Humans / Zombies> " + ChatColor.RED
						+ "<Arena>");
			}
			if (args.length == 2) {

				String team = args[0].toLowerCase();
				String arena = args[1];
				

				if (team.toLowerCase().equals("humans")
						|| team.toLowerCase().equals("zombies")) {
				
					if (settings.getData().contains("Arenas." + arena)) {
						
						Location loc = player.getLocation();
						String w = player.getLocation().getWorld().getName()
								.toString();
						settings.getData().set("Arenas." + arena + "." + team + ".X",
								Double.valueOf(loc.getX()));
						settings.getData().set("Arenas." + arena + "." + team + ".Y",
								Double.valueOf(loc.getY()));
						settings.getData().set("Arenas." + arena + "." + team + ".Z",
								Double.valueOf(loc.getZ()));
						settings.getData().set("Arenas." + arena + "." + team + ".W", w);
						settings.saveData();
						player.sendMessage(HunterGames.P + ChatColor.RED
								+ "You created the " + ChatColor.GOLD + team
								+ ChatColor.RED + " spawnpoint for the arena "
								+ ChatColor.GOLD + arena);
					}
				}
			}

		} else {
			s.sendMessage(HunterGames.P + ChatColor.RED + " You don't have "
					+ ChatColor.GOLD + "permission" + ChatColor.RED
					+ " for this command!");

		}
		return false;
	}

}
