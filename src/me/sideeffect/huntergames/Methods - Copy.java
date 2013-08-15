package me.sideeffect.huntergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

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
    System.out.println(ChatColor.GOLD + "[" + ChatColor.BLACK + ">" + ChatColor.WHITE + "Hunter" + ChatColor.DARK_RED + "Games" + ChatColor.BLACK + "<" + ChatColor.GOLD + "]" + ChatColor.RESET +  message);
  }
  
  
  public static String getTime(Long Time)
  {
      String times = null;
      Long time = Time;
      Long seconds = time;
      long minutes = seconds / 60;
      seconds %= 60;
      if (seconds == 0)
      {
          if (minutes <= 1) times = minutes + " Minute";
          else times = minutes + " Minutes";
      }
      else if (minutes == 0)
      {
          if (seconds <= 1) times = seconds + " Second";
          else times = seconds + " Seconds";
      }
      else
      {
          times = minutes + " Minutes " + seconds + " Seconds";
      }
      return times;
  }
  public static boolean hasPermission(Player player, String permission) {
    if (player.hasPermission(permission)) {
      return true;
    }
    return false;
  }
	
  public static void announceStarted()
  {		
	  randPlayer = getRandomPlayer();
	    EventHandlers.zombieList.add(randPlayer.getName());
	   
	    givePlayerZombieHead(randPlayer);
	    Bukkit.broadcastMessage(HunterGames.P + ChatColor.YELLOW + randPlayer.getName() + ChatColor.GRAY + " is infected.");
	    randPlayer.sendMessage(HunterGames.P + ChatColor.RED + "You are a zombie. Your goal is to kill everyone human.");
	    
	  }

  public static Player getRandomPlayer() {
    Player[] player = Bukkit.getOnlinePlayers();
    Player randomPlayer = player[new java.util.Random().nextInt(player.length - 1)];
    return randomPlayer;
  }

  public static void givePlayerZombieHead(Player player) {
	    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
	    head.setDurability((short)2);
	    player.getInventory().setHelmet(head);

	    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90010, 5));
	    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 90010, 4));
	  }
  public static void waitUntilGameStarts(){
	  
  }
 
  

public static void startGame() {
	
	
	
	for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
    Game.gameStarted = true;
    




ScoreboardManager manager = Bukkit.getScoreboardManager();
Scoreboard board = manager.getNewScoreboard();
Objective objective = board.registerNewObjective("Players", "dummy");

objective.setDisplaySlot(DisplaySlot.SIDEBAR);


objective.setDisplayName("Players");
Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Zombies:")); //Get a fake offline player

score.setScore(EventHandlers.zombieList.size());
int jason = EventHandlers.zombieList.size();

Score score1 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Humans:")); //Get a fake offline player

score1.setScore(Bukkit.getServer().getOnlinePlayers().length - jason);
onlinePlayers.setScoreboard(board);

   
    
     
    
    
    
  }
	
}



}