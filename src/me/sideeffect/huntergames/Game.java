package me.sideeffect.huntergames;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class Game
implements Listener

{ 
	static HunterGames plugin;

	public Game(HunterGames instance) {
		plugin = instance;
	}  
	public static int timeVote;
	public static boolean gameStarted = false;
	public static boolean lobbyStarted = false;


	public static void startGame(){






		if (!gameStarted) {

			CreateCommand.chooseArena();
			CreateCommand.tpToLobby();
			lobbyStarted = true;
			

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
					if (timeleft == 200 ||timeleft == 190 ||timeleft == 180 ||timeleft == 170 ||timeleft == 160 ||timeleft == 150 ||timeleft == 140 ||timeleft == 130 ||timeleft == 120 ||timeleft == 110 ||timeleft == 100 ||timeleft == 90 ||timeleft == 80 ||timeleft == 70 ||timeleft == 60 ||timeleft == 50 ||timeleft == 40 ||timeleft == 30 || timeleft == 20 || timeleft == 10 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
					{

						Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY + " Game starting in " + ChatColor.GOLD + Methods.getTime(Long.valueOf(timeleft)));

						for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
							onlinePlayers.setLevel(timeleft);

						}
					}

					else if (timeleft < 1){


						lobbyStarted = false;
						Methods.startGame();	 
						CreateCommand.announceStarted(); 
						gameStarted = true;
						Bukkit.getServer().getScheduler().cancelTask(HunterGames.timeVote);
						CreateCommand.teleportToArena();

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

									if (timeleft == 390 ||timeleft == 360 ||timeleft == 330 ||timeleft == 300 ||timeleft == 270 ||timeleft == 240 ||timeleft == 210 ||timeleft == 180 ||timeleft == 150 || timeleft == 120 || timeleft == 60 || timeleft == 30 || timeleft == 15 || timeleft == 10 || timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2 || timeleft == 1)
									{


										Bukkit.broadcastMessage(HunterGames.P + " " + ChatColor.GOLD + Methods.getTime(Long.valueOf(timeleft)) + ChatColor.GRAY + " until the game ends!");;
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
										onlinePlayers.getInventory().setHelmet(null);
										onlinePlayers.getInventory().clear();
										Bukkit.getServer().getScheduler().cancelTask(HunterGames.timeVote);

										Bukkit.getServer().getScheduler().cancelTask(HunterGames.gameTime);


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