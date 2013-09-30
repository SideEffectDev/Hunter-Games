package me.sideeffect.huntergames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {
	static FileManager settings = FileManager.getInstance();
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		Player player = (Player) s;
		String arena = args[0];
		if (l.equalsIgnoreCase("create")
				&& (Methods.hasPermission(player, "huntergames.admin")) || player.isOp()) {
						player.sendMessage(HunterGames.P + ChatColor.RED
								+ " You created the arena " + ChatColor.GOLD
								+ args[0] + ChatColor.RED + "!");
							settings.getData().set("Arenas." + arena , "" );
							settings.saveData();
					
		} else {
			s.sendMessage(HunterGames.P + ChatColor.RED + " You don't have "
					+ ChatColor.GOLD + "permission" + ChatColor.RED
					+ " for this command!");
		}
		return false;
	}


}
