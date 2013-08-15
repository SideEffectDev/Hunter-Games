package me.sideeffect.huntergames;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;

public class EventHandlers
  implements Listener
{
	static HunterGames plugin;

	public EventHandlers(HunterGames instance) {
	plugin = instance;
	}  
  public static int Zombies;
  public static HashSet<String> zombieList = new HashSet<String>();

 

  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerRespawn(PlayerRespawnEvent e)
  { Player player = e.getPlayer();
    if (Game.gameStarted){
    	if(zombieList.contains(player.getName())){
      Methods.givePlayerZombieHead(e.getPlayer());
      String currentArena = plugin.getConfig().getString("currentArena");
      Double x = plugin.getConfig().getDouble(currentArena +".zombies"+ ".X");
	    Double y = plugin.getConfig().getDouble(currentArena +".zombies"+ ".Y");
	    Double z = plugin.getConfig().getDouble(currentArena +".zombies"+ ".Z");
	    World w = Bukkit.getServer().getWorld(plugin.getConfig().getString(currentArena + ".zombies" + ".W"));
	    Location loc = new Location(w, x, y, z);
      e.setRespawnLocation(loc);
      e.getPlayer().sendMessage(ChatColor.RED + "You are a zombie. Your goal is to kill everyone human.");
      
    	}
    }
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onBlockPlace(BlockPlaceEvent e) {
    if (zombieList.contains(e.getPlayer().getName()))
      e.setCancelled(true);
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onBlockBreak(BlockBreakEvent e) {
    if (zombieList.contains(e.getPlayer().getName()))
      e.setCancelled(true);
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onEntityTarget(EntityTargetEvent e)
  {
    if ((e.getTarget() instanceof Player)) {
      Player target = (Player)e.getTarget();
      if (zombieList.contains(target.getName()))
        e.setCancelled(true);
    }
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerQuit(PlayerQuitEvent e)
   {
     Player player = e.getPlayer();
				for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
    if ((Game.gameStarted == true) && 
       (Bukkit.getServer().getOnlinePlayers().length < 2)) {
				
      Game.gameStarted = false;
      player.sendMessage(ChatColor.GREEN + "The game has been stopped.");
       Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Not enough players, stopping.");
       for (Player onlinePlayers1 : Bukkit.getServer().getOnlinePlayers()) {
         onlinePlayers1.removePotionEffect(PotionEffectType.SPEED);
         onlinePlayers1.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
      		} Game.gameStarted = false;
				}  

                   else if(!zombieList.contains(onlinePlayers.getName())){
	 				Game.gameStarted = false; 
	        player.sendMessage(ChatColor.GREEN + "The game has been stopped.");
	      Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Not enough players, stopping.");
					}
				}
    
  }
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerDeath(PlayerDeathEvent event) {

    Player Killer = event.getEntity().getKiller();
    Player TheOneKilled = event.getEntity();
    if (Game.gameStarted && Game.lobbyStarted == false) {
      if (Killer != null) {
          if (zombieList.contains(TheOneKilled.getName())) {
            Bukkit.broadcastMessage(ChatColor.DARK_RED + Killer.getName() + ChatColor.YELLOW + " killed the zombie " + ChatColor.GRAY + TheOneKilled.getName());
          } else {
                  Bukkit.broadcastMessage(ChatColor.DARK_RED + Killer.getName() + ChatColor.YELLOW + " infected " + ChatColor.GRAY + TheOneKilled.getName());
          }
      } else {
                Bukkit.broadcastMessage(TheOneKilled.getName() + ChatColor.YELLOW + " died.");
          }
    }
    event.setDeathMessage(null);
}
  @EventHandler
  /*    */   public void onPlayerJoin(PlayerJoinEvent e) {
  			   if(Game.gameStarted){
  				   String currentArena = plugin.getConfig().getString("currentArena");
  				 Double x = plugin.getConfig().getDouble(currentArena +".zombies"+ ".X");
  			    Double y = plugin.getConfig().getDouble(currentArena +".zombies"+ ".Y");
  			    Double z = plugin.getConfig().getDouble(currentArena +".zombies"+ ".Z");
  			    World w = Bukkit.getServer().getWorld(plugin.getConfig().getString(currentArena + ".zombies" + ".W"));
  			    Location loc = new Location(w, x, y, z);
  			    Player player = e.getPlayer();
  			    player.teleport(loc);
  			   }
  			 if(Bukkit.getServer().getOnlinePlayers().length >= 2){
  				Game.startGame();
  			 }if(Bukkit.getServer().getOnlinePlayers().length< 2){
  				 Bukkit.broadcastMessage(HunterGames.P + ChatColor.RESET + ChatColor.RED + " One more player needs to join to be able to play!");
  				 
  			 }
  	
  			   
  			   
  			 
  			 
  		   
  	}
  @EventHandler(priority=EventPriority.NORMAL)
  public void onEntityDeath(EntityDeathEvent e) {
    Entity entityAttacked = e.getEntity();
    Entity entityAttacker = e.getEntity().getKiller();

    if ((Game.gameStarted) && 
      ((entityAttacker instanceof Player))) {
      Player attacker = (Player)entityAttacker;
      if ((entityAttacked instanceof Player)) {
        Player attacked = (Player)entityAttacked;

        if (zombieList.contains(attacker.getName()))
        {
          for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
            if (zombieList.contains(onlinePlayers.getName())) {
              Game.gameStarted = false;
              Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Everyone is dead. Congratulations zombies.");
              String str = zombieList.toString();
              Bukkit.getServer().getScheduler().cancelTask(HunterGames.timeVote);
              Bukkit.getServer().getScheduler().cancelTask(HunterGames.gameTime);
              Bukkit.getServer().broadcastMessage(str);
              onlinePlayers.removePotionEffect(PotionEffectType.SPEED);
              onlinePlayers.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
              zombieList.clear();
              Game.startGame();
            }
          }
          if (Game.gameStarted)
            zombieList.add(attacked.getName());
          	int startingZombies = 1;
          	Zombies = startingZombies + 1;
          	
            
         
          
        }
      }
    }
  }
}