package me.sideeffect.huntergames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
	static HunterGames plugin;

	public HelpCommand(HunterGames instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		Player p = (Player)s;
		if(s instanceof Player){
		if (!Methods.hasPermission(p, "huntergames.admin") && l.equalsIgnoreCase("help")) {
			String help = plugin.getConfig().getString("Help");
			String hlp = help.replaceAll("&", "§");
			p.sendMessage(hlp);
			}
		}else{
			s.sendMessage(plugin.getConfig().getString("Help").replaceAll("&", "§"));
		}
		return false;
	}

}
