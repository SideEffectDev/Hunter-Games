package me.sideeffect.huntergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Game implements Listener

{
	static HunterGames plugin;

	public Game(HunterGames instance) {
		plugin = instance;
	}

	public static int gameTime;
	public static boolean gameStarted = false;
	public static boolean lobbyStarted = false;

	public static void startGame() {

		if (!gameStarted) {

				PlayerListener.chooseArena();
				Location loc = PlayerListener.Lobby();
				for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
					onlinePlayers.setScoreboard(HunterGames.manager.getNewScoreboard());
					onlinePlayers.teleport(loc);
					PlayerListener.removeEffects(onlinePlayers);
				}
			
			lobbyStarted = true;
			HunterGames.gameTime = Bukkit.getServer().getScheduler()
					.scheduleSyncRepeatingTask(HunterGames.me, new Runnable() {
						private int timeleft = HunterGames.lobbyTime;

						@Override
						public void run() {
							if (timeleft != -1) {
								timeleft -= 1;
								for (Player onlinePlayers : Bukkit.getServer()
										.getOnlinePlayers()) {
									onlinePlayers.setLevel(timeleft);

								}

							}
							if (timeleft % 10 == 0 || (timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2
									|| timeleft == 1)) {
								for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
									onlinePlayers.setScoreboard(HunterGames.scoreboard);
								}
								Bukkit.broadcastMessage(HunterGames.P
										+ ChatColor.GRAY
										+ " Game starting in "
										+ ChatColor.GOLD
										+ Methods.getTime(Long
												.valueOf(timeleft)));

								for (Player onlinePlayers : Bukkit.getServer()
										.getOnlinePlayers()) {
									onlinePlayers.setLevel(timeleft);

								}
							}

							else if (timeleft < 1) {

								gameStarted = true;
								lobbyStarted = false;
								PlayerListener.announceStarted();
								PlayerListener.teleportToArena();
								Bukkit.getServer().getScheduler()
								.cancelTask(HunterGames.gameTime);
								Bukkit.getServer().getScheduler()
								.cancelTask(HunterGames.gameTime);
								Bukkit.broadcastMessage(HunterGames.P
										+ ChatColor.GOLD
										+ " 2 Minutes and 30 Seconds "
										+ ChatColor.GRAY
										+ "until the game ends!");

								HunterGames.gameTime = Bukkit
										.getServer()
										.getScheduler()
										.scheduleSyncRepeatingTask(
												HunterGames.me, new Runnable() {

													private int timeleft = HunterGames.timeLimit;

													@Override
													public void run() {
														if (timeleft != -1) {
															timeleft -= 1;
															for (Player onlinePlayers : Bukkit
																	.getServer()
																	.getOnlinePlayers()) {
																onlinePlayers
																.setLevel(timeleft);

															}

															if (timeleft % 30 == 0) {

																Bukkit.broadcastMessage(HunterGames.P
																		+ " "
																		+ ChatColor.GOLD
																		+ Methods
																		.getTime(Long
																				.valueOf(timeleft))
																				+ ChatColor.GRAY
																				+ " until the game ends!");

																for (Player onlinePlayers : Bukkit
																		.getServer()
																		.getOnlinePlayers()) {
																	onlinePlayers
																	.setLevel(timeleft);
																}
															} else if (timeleft == 5 || timeleft == 4 || timeleft == 3 || timeleft == 2
																	|| timeleft == 1) {
																Bukkit.broadcastMessage(HunterGames.P
																		+ " "
																		+ ChatColor.GOLD
																		+ Methods
																		.getTime(Long
																				.valueOf(timeleft))
																				+ ChatColor.GRAY
																				+ " until the game ends!");
															}

															else if (timeleft < 1) {
																PlayerListener.endGame();

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