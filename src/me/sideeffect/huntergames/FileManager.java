package me.sideeffect.huntergames;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class FileManager {

	private FileManager() { }
	
	static FileManager instance = new FileManager();
	
	public static FileManager getInstance() {
		return instance;
	}
	
	Plugin p;
	
	FileConfiguration config;
	File cfile;
	
	FileConfiguration data;
	File dfile;
	
	public void setup(Plugin p) {
		cfile = new File(p.getDataFolder(), "config.yml");
		config = p.getConfig();
		
		
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
			
		}
		
		dfile = new File(p.getDataFolder(), "data.yml");
		data = YamlConfiguration.loadConfiguration(dfile);
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
				
				addDefaults();
				
			}
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create " + dfile.getName());
			}
		}
		
		
	}
	
	public FileConfiguration getData() {
		return data;
	}
	
	public void saveData() {
		try {
			data.save(dfile);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save " + dfile.getName());
		}
	}
	
	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(dfile);
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public void saveConfig() {
		try {
			config.save(cfile);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save " + cfile.getName());
		}
	}
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public PluginDescriptionFile getDesc() {
		return p.getDescription();
	}
	public void addDefaults(){
		getData().addDefault("currentArena","Lobby");
		getData().addDefault("Arenas", "");
	}
}