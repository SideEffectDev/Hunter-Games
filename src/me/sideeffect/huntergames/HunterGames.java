package me.sideeffect.huntergames;


import java.io.File;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
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
	
	public static String P; 
  public void onEnable()
  {	   
	  loadConfig();
	
    Methods.printLine("Version 0.1 Enabled");
    registerEvents();
    me = this;
    P = getConfig().getString("prefix");
    P.replaceAll("&", "§");
    
    getCommand("create").setExecutor(new CreateCommand(this));
    getCommand("setspawn").setExecutor(new SetspawnCommand(this));
    getCommand("setlobby").setExecutor(new SetlobbyCommand(this));
    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		public void run() {
			if(Game.gameStarted == false && Bukkit.getServer().getOnlinePlayers().length >= 2){
				Game.startGame();
			}
		}
	}, 0, 40);
    
  }
  public void loadConfig(){
	  if (!new File(getDataFolder(), "config.yml").exists())
	      saveDefaultConfig();
	  saveConfig();
	  
  }
  /*public void loadConfig() {
		boolean configFileExistant = false;

		if (new File("plugins/HGInfection").exists()) {
			configFileExistant = true;
		}

		getConfig().options().copyDefaults(true);

		if (!configFileExistant) {
			getConfig().addDefault("prefix",
					("&4HunterGames"));
			getConfig().addDefault("General.timelimit",
					("150"));
			getConfig().addDefault("General.lobbyTime",
					("150"));
			getConfig().addDefault("currentArena",
					("none"));
			getConfig().addDefault("Arenas.List",
					(""));
		
		}

		saveConfig();
		reloadConfig();
	}*/
	public void tpToLobby(){
			String Lobby = "Lobby";
			Location loc = new Location(Bukkit.getWorld(getConfig().getString(Lobby +  "." + ".W")), 
		         getConfig().getDouble(Lobby +  "." + "X"), 
		          getConfig().getDouble(Lobby +  "." + "Y"), 
		          getConfig().getDouble(Lobby +  "." + "Z"));
			for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
				onlinePlayers.teleport(loc);
			}
	}
	public void chooseArena(){
		  
		  List<String> list = getConfig().getStringList("Arenas.List");
		  Random rand = new Random();
		  String arena = list.get(rand.nextInt(list.size()));
		  getConfig().set("currentArena", "");
		  getConfig().set("currentArena", arena);
		  teleportToArena();
	}
	public void teleportToArena(){
			Player randPlayer = Methods.getRandomPlayer();
			if(!EventHandlers.zombieList.contains(randPlayer)){
				String currentArena = getConfig().getString("currentArena");
				Location loc = new Location(Bukkit.getWorld(getConfig().getString(currentArena + ".humans" + "." + ".W")), 
				          getConfig().getDouble(currentArena + ".humans" + "." + "X"), 
				          getConfig().getDouble(currentArena + ".humans" + "." + "Y"), 
				          getConfig().getDouble(currentArena + ".humans" + "." + "Z"));
				for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
					onlinePlayers.teleport(loc);
				}
		}if(!EventHandlers.zombieList.contains(randPlayer)){
				String currentArena = getConfig().getString("currentArena");
				Location loc = new Location(Bukkit.getWorld(getConfig().getString(currentArena + ".humans" + "." + ".W")), 
				          getConfig().getDouble(currentArena + ".zombies" + "." + "X"), 
				          getConfig().getDouble(currentArena + ".zombies" + "." + "Y"), 
				          getConfig().getDouble(currentArena + ".zombies" + "." + "Z"));
				randPlayer.teleport(loc);
			}
	}
  public void onDisable() {
    Methods.printLine("Version 0.1 Disabled");
  }

  public void registerEvents() {
    getServer().getPluginManager().registerEvents(new EventHandlers(this), this);
  }
  
  

}