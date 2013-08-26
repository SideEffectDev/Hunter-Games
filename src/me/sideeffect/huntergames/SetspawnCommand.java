package me.sideeffect.huntergames;

import java.util.List;

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
		Player player = (Player) s;
		if (l.equalsIgnoreCase("setspawn")
				&& Methods.hasPermission(player, "huntergames.admin")) {

			List<String> list = plugin.getConfig().getStringList("Arenas.List");
			if (args.length == 0) {
				s.sendMessage(HunterGames.P + ChatColor.RED + " /Setspawn "
						+ ChatColor.GOLD + "<Humans / Zombies>" + ChatColor.RED
						+ "<Arena>");
			}
			if (args.length == 1) {
				s.sendMessage(HunterGames.P + ChatColor.RED + " /Setspawn "
						+ ChatColor.GOLD + "<Humans / Zombies>" + ChatColor.RED
						+ "<Arena>");
			}
			if (args.length == 2) {

				String team = args[0].toLowerCase();
				String arena = args[1];
				

				if (team.toLowerCase().equals("humans")
						|| team.toLowerCase().equals("zombies")) {
					player.sendMessage(HunterGames.P + ChatColor.RED
							+ "You created the " + ChatColor.GOLD + team
							+ ChatColor.RED + " spawnpoint for the arena "
							+ ChatColor.GOLD + arena);
					if (list.contains(arena)) {

						Location loc = player.getLocation();
						String w = player.getLocation().getWorld().getName()
								.toString();
						plugin.getConfig().set(arena + "." + team + ".X",
								Double.valueOf(loc.getX()));
						plugin.getConfig().set(arena + "." + team + ".Y",
								Double.valueOf(loc.getY()));
						plugin.getConfig().set(arena + "." + team + ".Z",
								Double.valueOf(loc.getZ()));
						plugin.getConfig().set(arena + "." + team + ".W", w);
						plugin.saveConfig();
					}
					if (!list.contains(arena)) {
						player.sendMessage(HunterGames.P + ChatColor.RED
								+ " The arena " + ChatColor.GOLD + arena
								+ ChatColor.RED + " has not yet been created!");
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
