package me.sideeffect.huntergames;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class HunterGames extends JavaPlugin {
	public static Plugin me;
	public static ScoreboardManager manager;
	public static Scoreboard scoreboard;
	public static Objective objective;
	public static String Prefix;
	public static String P;
	public static String Map;
	public static String currentMap;
	public static int timeVote;
	public static int gameTime;
	public int tLimit = 150;
	public int lTime = 45;
	public int timeLimit;
	public int lobbyTime;


	public void onDisable() {
		Methods.printLine("Version 0.1 Disabled");
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
		HunterGames.scoreboard.resetScores(onlinePlayers);
		}
	}

	public void onEnable() {

		loadConfig();
		for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			onlinePlayers.removePotionEffect(PotionEffectType.SPEED);
			onlinePlayers.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		}
		Methods.printLine("Version 0.1 Enabled");
		registerEvents();
		me = this;
		Map = getConfig().getString("currentArena");
		currentMap = Map;
		Prefix = getConfig().getString("prefix");
		P = Prefix.replaceAll("&", "§");
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("HunterGames", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		lTime = getConfig().getInt("General.lobbyTime");
		tLimit = getConfig().getInt("General.timeLimit");
		lobbyTime = lTime;
		timeLimit = tLimit;
		getCommand("create").setExecutor(new CreateCommand(this));
		getCommand("setspawn").setExecutor(new SetspawnCommand(this));
		getCommand("setlobby").setExecutor(new SetlobbyCommand(this));
		getCommand("help").setExecutor(new HelpCommand(this));
		getCommand("?").setExecutor(new HelpCommand(this));
		Bukkit.getServer().getScheduler()
		.scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (Game.lobbyStarted == false
						&& Game.gameStarted == false
						&& Bukkit.getServer().getOnlinePlayers().length >= 2) {
					Game.startGame();
				}
			}
		}, 20, 40);
		Bukkit.getServer().getScheduler()
		.scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {

				HunterGames.objective.setDisplayName("§7§l" + HunterGames.currentMap);
				Score humans = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Humans:"));
				Score zombies = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Zombies:"));
				zombies.setScore(PlayerListener.zombieList.size());
				humans.setScore(Bukkit.getServer().getOnlinePlayers().length - PlayerListener.zombieList.size());


			}
		}, 20, 40);
	}

	public void loadConfig() {
		if (!new File(getDataFolder(), "config.yml").exists())
			saveDefaultConfig();
		saveConfig();

	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this),
				this);
	}

}