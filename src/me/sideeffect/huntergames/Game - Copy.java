package me.sideeffect.huntergames;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class Game
  implements Listener
  
{ 
	
    public static int timeVote;
	public static boolean gameStarted = false;
	public static boolean lobbyStarted = true;

		
	public static void startGame(){
	
		    	
		     

		
		      
		      if (!gameStarted) {
		    	  
<<<<<<< HEAD
		    	  HunterGames hg = new HunterGames();
		    	  hg.tpToLobby();
		    	  gameStarted = true;
		    	  Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY + " Game starting in " + ChatColor.GOLD + "45 seconds!");
		    		 
=======
		    			//Methods.startZombies = 0;
		    		//Methods.startHumans = Bukkit.getOnlinePlayers().length;
		    		 gameStarted = true;
		    		 Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY + " Game starting in " + ChatColor.GOLD + "45 seconds!");
		    		 for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
		    			 String Lobby = "Lobby";
				    		Location loc = new Location(Bukkit.getWorld(plugin.getConfig().getString(Lobby +  "." + ".W")), 
				  		          plugin.getConfig().getDouble(Lobby +  "." + "X"), 
				  		          plugin.getConfig().getDouble(Lobby +  "." + "Y"), 
				  		          plugin.getConfig().getDouble(Lobby +  "." + "Z"));
	                     onlinePlayers.teleport(loc);
	                      
	                      onlinePlayers.setGameMode(GameMode.ADVENTURE);
	                      }
>>>>>>> 04e3fbd5bf06c79d9c3a0a153ed79713b389d708
		    	HunterGames.timeVote = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(HunterGames.me, new Runnable()
		          {  
		    		
		    	
		              private int timeleft = 45;
		              
		              @
		              Override
		              public void run()
		              {
		                  if (timeleft != -1)
		                  {
		                      timeleft -= 1;
		                      for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			                      onlinePlayers.setLevel(timeleft);
			                      
		
			                      }
		                      
		                      }
		                      if (timeleft == 40 ||timeleft == 30 || timeleft == 20 || timeleft == 10 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
		                      {
		                          
		                    	  Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY + " Game starting in " + ChatColor.GOLD + Methods.getTime(Long.valueOf(timeleft)));
		                                  
		                                  for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
		                                	  onlinePlayers.setLevel(timeleft);
		                                	 
		                                  }
		                      }
		                     
		                      else if (timeleft < 1){
		                    	  HunterGames hg = new HunterGames();
		                    	  hg.chooseArena();
		                    	  lobbyStarted = false;
		                    	  Methods.startGame();	 
						    	  Methods.announceStarted(); 
						    	  gameStarted = true;
						    	  Bukkit.getServer().getScheduler().cancelTask(HunterGames.timeVote);
						    	 
						    	  
		                          Bukkit.getServer().getScheduler().cancelTask(HunterGames.gameTime);
						    	  Bukkit.broadcastMessage(HunterGames.P + ChatColor.GOLD + " 2 Minutes and 30 Seconds " + ChatColor.GRAY + "until the game ends!");
							    	  HunterGames.gameTime = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(HunterGames.me, new Runnable()
							          {
							              private int timeleft = 150;
							              
							              @
							              Override
							              public void run()
							              {
							                  if (timeleft != -1)
							                  {
								                      timeleft -= 1;
								                      for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
									                      onlinePlayers.setLevel(timeleft);
								                      
								                      }
							                      
							                      if (timeleft == 150 || timeleft == 120 || timeleft == 60 || timeleft == 30 || timeleft == 15 || timeleft == 10 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
							                      {
							                          
							                    	  
							                    	  Bukkit.broadcastMessage(HunterGames.P + ChatColor.GOLD + Methods.getTime(Long.valueOf(timeleft)) + ChatColor.GRAY + " until the game ends!");;
							                                  for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
							                                	  onlinePlayers.setLevel(timeleft);
							                                  }
							                      }
							                     if(timeleft == 90){
							                    	 Bukkit.broadcastMessage(HunterGames.P + ChatColor.GOLD + " 1 Minute and 30 Seconds " + ChatColor.GRAY + "until the game ends!");
							                     }
							                     
							                      else if (timeleft < 1)
							                      {for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
								                    	 
								                    	  Bukkit.broadcastMessage(HunterGames.P  + ChatColor.GRAY + "Game over! " + ChatColor.GOLD + " Humans win!");
								                    	  gameStarted = false;
								                    	  Bukkit.getServer().getScheduler().cancelTask(HunterGames.timeVote);
												    	  
								                          Bukkit.getServer().getScheduler().cancelTask(HunterGames.gameTime);
								                    	  
								                          //Bukkit.getServer().broadcastMessage(str);
								                          onlinePlayers.removePotionEffect(PotionEffectType.SPEED);
								                          onlinePlayers.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								                          EventHandlers.zombieList.clear();
								                          startGame();
							                      }
											             
											    	  
											    	  
							                      		}
							                      
							    	  
							                  		}
							                     }
							          	}, 0L, 20L);	  
		                      }
		                  }
		              
		    }, 0L, 20L);
		        
		      

		    
		      }
		      
	}	
}
