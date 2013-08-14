package me.sideeffect.huntergames;


import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HunterGames extends JavaPlugin implements CommandExecutor
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
    saveConfig();
  }

  public void onDisable() {
    Methods.printLine("Version 0.1 Disabled");
  }

  public void registerEvents() {
    getServer().getPluginManager().registerEvents(new EventHandlers(this), this);
  }
  public void loadConfig() {
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
			
		
		}

		saveConfig();
		reloadConfig();
	}
  

}