package me.sideeffect.huntergames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
	static HunterGames plugin;

	public HelpCommand(HunterGames instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		if (l.equalsIgnoreCase("help") || l.equalsIgnoreCase("?")) {
			String help = plugin.getConfig().getString("Help");
			String hlp = help.replaceAll("&", "§");
			s.sendMessage(hlp);

		}
		return false;
	}

}
