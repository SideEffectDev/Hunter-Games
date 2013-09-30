package me.sideeffect.huntergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Methods

{	
	static HunterGames plugin;

	public Methods(HunterGames instance) {
		plugin = instance;
	}


	static Player randPlayer = null;
	public static String lastchoice;

	public static void printLine(String message) {
		System.out.println(ChatColor.GOLD + "[" + ChatColor.BLACK + ">"
				+ ChatColor.WHITE + "Hunter" + ChatColor.DARK_RED + "Games"
				+ ChatColor.BLACK + "<" + ChatColor.GOLD + "]"
				+ ChatColor.RESET + message);
	}



	public static String getTime(long Time) {
		String times = null;
		long time = Time;
		long seconds = time;
		long minutes = seconds / 60;

		seconds %= 60;
		if (seconds == 0) {
			if (minutes <= 1){
				times = minutes + " Minute";
			}if(seconds == 0 && minutes == 0){
				times = seconds + " Seconds";
			}
			else{
				times = minutes + " Minutes";
			}
		} else if (minutes == 0) {
			if (seconds <= 1)
				times = seconds + " Second";
			else
				times = seconds + " Seconds";
		}

		else 
			times = minutes + " Minutes and " + seconds + " Seconds";

		return times;

	}
	public static boolean hasPermission(Player player, String permission) {
		if (player.hasPermission(permission)) {
			return true;
		}
		return false;
	}

	public static Player getRandomPlayer() {
		Player[] player = Bukkit.getOnlinePlayers();
		Player randomPlayer = player[new java.util.Random()
		.nextInt(player.length - 1)];
		return randomPlayer;
	}

	

	public static void removeEffects(Player player) {

		for (PotionEffect effect : player.getActivePotionEffects()){
			player.removePotionEffect(effect.getType());
		}

	}

}