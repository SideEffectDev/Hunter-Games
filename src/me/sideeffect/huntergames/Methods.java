package me.sideeffect.huntergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Methods

{	
	static HunterGames plugin;

	public Methods(HunterGames instance) {
		plugin = instance;
	}
	
	public int jason = 5;
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
			times = minutes + " Minutes "+ "and " + seconds + " Seconds";
		
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

	public static void givePlayerZombieHead(Player player) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
		head.setDurability((short) 2);
		player.getInventory().setHelmet(head);

		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90010,
				5));
		player.addPotionEffect(new PotionEffect(
				PotionEffectType.INCREASE_DAMAGE, 90010, 4));
		
		
	}
	HunterGames hg = new HunterGames();
	int joey = hg.lTime;
	

}