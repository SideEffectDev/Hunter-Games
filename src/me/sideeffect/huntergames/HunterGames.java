package me.sideeffect.huntergames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import de.robingrether.idisguise.api.DisguiseAPI;
import pw.ender.messagebar.MessageBarSetEvent;

public class HunterGames extends JavaPlugin {
	public static Plugin me;
	public static ScoreboardManager manager;
	public static Scoreboard scoreboard;
	public static Objective objective;
	public static String Prefix;
	public static String P;
	public static String Map;
	public static String currentMap;
	public static int gameTime;
	public int tLimit = 150;
	public int lTime = 45;
	public static int timeLimit;
	public static int lobbyTime;
	public static DisguiseAPI api;
	static FileManager settings = FileManager.getInstance();	
	public void onDisable() {
		Methods.printLine("Version" + this.getDescription().getVersion() + "Disabled");
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			HunterGames.scoreboard.resetScores(onlinePlayers);
		}
		settings.getData().set("currentArena", "Lobby");
		PlayerListener.zombieList.clear();
	}

	public void onEnable() {

		loadConfigurationFiles();
		for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			PlayerListener.removeEffects(onlinePlayers);	
		}
		api = getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
		settings.getData().set("currentArena", "Lobby");
		PluginDescriptionFile file = this.getDescription();
		Methods.printLine("Version " + file.getVersion() +  " Enabled");
		registerEvents();
		me = this;
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
		getCommand("create").setExecutor(new CreateCommand());
		getCommand("setspawn").setExecutor(new SetspawnCommand());
		getCommand("setlobby").setExecutor(new SetlobbyCommand());
		getCommand("help").setExecutor(new HelpCommand(this));
		Bukkit.getServer().getScheduler()
		.scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Map = settings.getData().getString("currentArena");
				currentMap = Map;
				String prefix = getConfig().getString("General.ScoreboardPrefix").replaceAll("&", "§");
				HunterGames.objective.setDisplayName(prefix);
				Score humans = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Humans:"));
				Score zombies = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Zombies:"));
				zombies.setScore(PlayerListener.zombieList.size());
				humans.setScore(Bukkit.getServer().getOnlinePlayers().length - PlayerListener.zombieList.size());
				for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
					MessageBarSetEvent Msg = new MessageBarSetEvent(onlinePlayers);
					Msg.setMessage(currentMap);
				}
				if(settings.getData().contains("Arenas")){
					List<String> arenas = new ArrayList<String>();
					for (String possibleArenas : settings.getData().getConfigurationSection("Arenas").getKeys(false))
					{

						if (settings.getData().contains("Arenas." + possibleArenas + ".zombies") && settings.getData().contains("Arenas." + possibleArenas + ".humans"))
						{
							arenas.add(possibleArenas);
						}
					}
					if (Game.lobbyStarted == false
							&& Game.gameStarted == false
							&& Bukkit.getServer().getOnlinePlayers().length >= 2 && arenas.size() > 0 && settings.getData().contains("Lobby")) {
						Game.startGame();
					}
				}


			}
		}, 0, 20);
	}

	public void loadConfigurationFiles() {
		this.saveDefaultConfig();
		settings.setup(this);
	}




	public void registerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(this),this);
	}

}