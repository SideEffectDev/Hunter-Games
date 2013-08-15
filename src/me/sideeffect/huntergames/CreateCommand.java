package me.sideeffect.huntergames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateCommand implements CommandExecutor {
	static HunterGames plugin;
	public static List<String> list;

	public CreateCommand(HunterGames instance) {
		plugin = instance;
	}

	static Player randPlayer = null;
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String l,
			String[] args) {
		Player player = (Player) s;

		if (l.equalsIgnoreCase("create")
				&& Methods.hasPermission(player, "huntergames.admin")) {

			if (args.length < 1) {

				player.sendMessage(HunterGames.P + ChatColor.RED + " /Create "
						+ ChatColor.GOLD + "<Arena>");
		}
			List<String> list = plugin.getConfig().getStringList("Arenas.List");

			if (args.length == 1) {
				if (!list.contains(args[0])) {
					player.sendMessage(HunterGames.P + ChatColor.RED
							+ " You created the arena " + ChatColor.GOLD
					+ args[0] + ChatColor.RED + "!");
					if (plugin.getConfig().contains("Arenas.List")) {
						List<String> list1 = plugin.getConfig().getStringList(
								"Arenas.List");
						list1.add(args[0]);
						plugin.getConfig().set("Arenas.List", list1);
					} else {
						List<String> list1 = new ArrayList<String>();
						list1.add(args[0]);
						plugin.getConfig().addDefault("Arenas.List", list1);
					}
				} else {
					s.sendMessage(HunterGames.P + ChatColor.RED
							+ " Sorry! An arena named " + ChatColor.GOLD
							+ args[0] + ChatColor.RED
							+ " is already in the arena list!");
				}

				plugin.saveConfig();

			}

		} else {
			s.sendMessage(HunterGames.P + ChatColor.RED + " You don't have "
					+ ChatColor.GOLD + "permission" + ChatColor.RED
					+ " for this command!");
		}
		return false;
	}

	public static void tpToLobby() {

		Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
				.getString("Lobby" + "." + "W")), plugin.getConfig().getDouble(
						"Lobby" + "." + "X"), plugin.getConfig().getDouble(
								"Lobby" + "." + "Y"), plugin.getConfig().getDouble(
										"Lobby" + "." + "Z"));
		for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			onlinePlayers.teleport(loc);
		}

	}

	public static void chooseArena() {

		List<String> list = plugin.getConfig().getStringList("Arenas.List");
		Random rand = new Random();
		String arena = list.get(rand.nextInt(list.size()));
		plugin.getConfig().set("currentArena", "");
		plugin.getConfig().set("currentArena", arena);
		Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY
				+ " Next map is: " + ChatColor.GOLD
				+ plugin.getConfig().getString("currentArena"));

	}

	public static void teleportToArena() {
		Player randPlayer = Methods.getRandomPlayer();
		if (!EventHandlers.zombieList.contains(randPlayer)) {
			
			String currentArena = plugin.getConfig().getString("currentArena");
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
					.getString(currentArena + ".humans" + "." + "W")), plugin
					.getConfig()
					.getDouble(currentArena + ".humans" + "." + "X"), plugin
					.getConfig()
					.getDouble(currentArena + ".humans" + "." + "Y"), plugin
					.getConfig()
					.getDouble(currentArena + ".humans" + "." + "Z"));
			for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
				onlinePlayers.teleport(loc);
				ItemStack item = new ItemStack(Material.WOOD_SWORD,1);
		        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		        onlinePlayers.getInventory().addItem(item);
			}
		}
		if (!EventHandlers.zombieList.contains(randPlayer)) {
			String currentArena = plugin.getConfig().getString("currentArena");
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
					.getString(currentArena + ".humans" + "." + "W")), plugin
					.getConfig().getDouble(
							currentArena + ".zombies" + "." + "X"), plugin
							.getConfig().getDouble(
									currentArena + ".zombies" + "." + "Y"), plugin
									.getConfig().getDouble(
											currentArena + ".zombies" + "." + "Z"));
			randPlayer.teleport(loc);
		}
	}

	public static void announceStarted() {
		randPlayer = Methods.getRandomPlayer();
		EventHandlers.zombieList.add(randPlayer.getName());
		Bukkit.broadcastMessage(HunterGames.P + ChatColor.RED + " Starting "
				+ ChatColor.GOLD + " infected " + ChatColor.RED + "on map "
				+ ChatColor.GOLD + plugin.getConfig().getString("currentArena"));
		Methods.givePlayerZombieHead(randPlayer);
		Bukkit.broadcastMessage(HunterGames.P + ChatColor.YELLOW
				+ randPlayer.getName() + ChatColor.GRAY + " is infected.");
		randPlayer.sendMessage(HunterGames.P + ChatColor.RED
				+ "You are a zombie. Your goal is to kill everyone human.");

	}
}
