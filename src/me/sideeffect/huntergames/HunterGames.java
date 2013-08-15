package me.sideeffect.huntergames;


import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HunterGames extends JavaPlugin 
{

	public static Scoreboard playingBoard;
	public static Objective voteList;
	public static Objective playingList;
	public static int timeVote;
	public static int gameTime;
	public static Plugin me;
	public static String Prefix;
	public static String P;

	public void onDisable() {
		Methods.printLine("Version 0.1 Disabled");
	}
	public void onEnable()
	{	   
		loadConfig();
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			onlinePlayers.removePotionEffect(PotionEffectType.SPEED);
			onlinePlayers.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		}
		Methods.printLine("Version 0.1 Enabled");
		registerEvents();
		me = this;
		Prefix = getConfig().getString("prefix");
		P = Prefix.replaceAll("&", "§");

		getCommand("create").setExecutor(new CreateCommand(this));
		getCommand("setspawn").setExecutor(new SetspawnCommand(this));
		getCommand("setlobby").setExecutor(new SetlobbyCommand(this));
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if(Game.lobbyStarted == false && Game.gameStarted == false && Bukkit.getServer().getOnlinePlayers().length >= 2){
					Game.startGame();
				}
			}
		}, 20, 40);

	}
	public void loadConfig(){
		if (!new File(getDataFolder(), "config.yml").exists())
			saveDefaultConfig();
		saveConfig();

	}




	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new EventHandlers(this), this);
	}



}