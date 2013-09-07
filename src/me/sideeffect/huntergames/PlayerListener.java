package me.sideeffect.huntergames;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pw.ender.messagebar.MessageBarSetEvent;

public class PlayerListener implements Listener {
	static HunterGames plugin;

	public PlayerListener(HunterGames instance) {
		plugin = instance;
	}
	public static HashSet<String> zombieList = new HashSet<String>();
	static Player randPlayer = null;
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		if (Game.gameStarted) {
			if (zombieList.contains(player.getName())) {
				Methods.givePlayerZombieHead(e.getPlayer());
				String currentArena = plugin.getConfig().getString(
						"currentArena");
				Double x = plugin.getConfig().getDouble(
						currentArena + ".zombies" + ".X");
				Double y = plugin.getConfig().getDouble(
						currentArena + ".zombies" + ".Y");
				Double z = plugin.getConfig().getDouble(
						currentArena + ".zombies" + ".Z");
				World w = Bukkit.getServer().getWorld(
						plugin.getConfig().getString(
								currentArena + ".zombies" + ".W"));
				Location loc = new Location(w, x, y, z);

				e.setRespawnLocation(loc);
				e.getPlayer()
				.sendMessage(
						ChatColor.RED
						+ "You are a zombie. Your goal is to kill every human.");

				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90010,
						5));
				player.addPotionEffect(new PotionEffect(
						PotionEffectType.INCREASE_DAMAGE, 90010, 4));

			}
			if (!zombieList.contains(player.getName())) {
				Methods.givePlayerZombieHead(e.getPlayer());
				String currentArena = plugin.getConfig().getString(
						"currentArena");
				Double x = plugin.getConfig().getDouble(
						currentArena + ".humans" + ".X");
				Double y = plugin.getConfig().getDouble(
						currentArena + ".humans" + ".Y");
				Double z = plugin.getConfig().getDouble(
						currentArena + ".humans" + ".Z");
				World w = Bukkit.getServer().getWorld(
						plugin.getConfig().getString(
								currentArena + ".humans" + ".W"));
				Location loc = new Location(w, x, y, z);
				e.setRespawnLocation(loc);


			}
		}
		if (!Game.gameStarted) {
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
					.getString("Lobby" + "." + "W")), plugin.getConfig()
					.getDouble("Lobby" + "." + "X"), plugin.getConfig()
					.getDouble("Lobby" + "." + "Y"), plugin.getConfig()
					.getDouble("Lobby" + "." + "Z"));
			e.setRespawnLocation(loc);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent)e;
		if (((nEvent.getEntity() instanceof Player)) && ((nEvent.getDamager() instanceof Player)))
		{
			Player EntityOne = (Player)nEvent.getEntity();
			Player EntityTwo = (Player)nEvent.getDamager();
			if(!zombieList.contains(EntityOne.getName()) && !zombieList.contains(EntityTwo.getName())){
				e.setCancelled(true);
			}
			if(zombieList.contains(EntityOne.getName()) && zombieList.contains(EntityTwo.getName())){
				e.setCancelled(true);
			}
			if(Game.lobbyStarted){
				e.setCancelled(true);
			}
		}

	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e) {
		if(!e.getPlayer().isOp() || Methods.hasPermission(e.getPlayer(), "huntergames.admin")){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		if(!e.getPlayer().isOp() || Methods.hasPermission(e.getPlayer(), "huntergames.admin")){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityTarget(EntityTargetEvent e) {
		if ((e.getTarget() instanceof Player)) {
			Player target = (Player) e.getTarget();
			if (zombieList.contains(target.getName()))
				e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			if ((Game.gameStarted == true)
					&& (Bukkit.getServer().getOnlinePlayers().length < 2)
					|| !(zombieList.contains(onlinePlayers.getName()))) {

				Game.gameStarted = false;
				player.sendMessage(ChatColor.GREEN
						+ "The game has been stopped.");
				Bukkit.getServer().broadcastMessage(
						HunterGames.P + ChatColor.RED + "Not enough"
								+ ChatColor.GOLD + " players " + ChatColor.RED
								+ "... stopping.");
				Bukkit.getServer()
				.getScheduler()
				.cancelTask(
						HunterGames.timeVote);

				Bukkit.getServer()
				.getScheduler()
				.cancelTask(
						HunterGames.gameTime);
				onlinePlayers.getInventory().setHelmet(null);

				onlinePlayers.removePotionEffect(PotionEffectType.SPEED);
				onlinePlayers
				.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

				Game.gameStarted = false;
			}
			if(!Game.gameStarted){
				HunterGames.scoreboard.resetScores(player);
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {

		Player Killer = event.getEntity().getKiller();
		Player Killed = event.getEntity();
		if (Game.gameStarted && Game.lobbyStarted == false) {
			if (Killer != null) {
				if (zombieList.contains(Killed.getName())) {
					Bukkit.broadcastMessage(ChatColor.DARK_RED
							+ Killer.getName() + ChatColor.YELLOW
							+ " killed the zombie " + ChatColor.GRAY
							+ Killed.getName());
				} else {
					Bukkit.broadcastMessage(ChatColor.DARK_RED
							+ Killer.getName() + ChatColor.YELLOW
							+ " infected " + ChatColor.GRAY
							+ Killed.getName());
				}
			} else {
				Bukkit.broadcastMessage(Killed.getName()
						+ ChatColor.YELLOW + " died.");
				if(Game.gameStarted){	
					zombieList.add(Killed.getName());

				}
			}
		}
		event.setDeathMessage(null);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		

		if (Game.gameStarted) {
			String currentArena = plugin.getConfig().getString("currentArena");
			Double x = plugin.getConfig().getDouble(
					currentArena + ".zombies" + ".X");
			Double y = plugin.getConfig().getDouble(
					currentArena + ".zombies" + ".Y");
			Double z = plugin.getConfig().getDouble(
					currentArena + ".zombies" + ".Z");
			World w = Bukkit.getServer().getWorld(
					plugin.getConfig().getString(
							currentArena + ".zombies" + ".W"));
			Location loc = new Location(w, x, y, z);
			Player player = e.getPlayer();
			player.teleport(loc);

			player.setScoreboard(HunterGames.scoreboard);


		}
		if (!Game.gameStarted) {
			Player player = e.getPlayer();
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
					.getString("Lobby" + "." + "W")), plugin.getConfig()
					.getDouble("Lobby" + "." + "X"), plugin.getConfig()
					.getDouble("Lobby" + "." + "Y"), plugin.getConfig()
					.getDouble("Lobby" + "." + "Z"));
			player.teleport(loc);
		}
		if (Bukkit.getServer().getOnlinePlayers().length < 2) {
			Bukkit.broadcastMessage(HunterGames.P + ChatColor.RESET
					+ ChatColor.RED + " One more" + ChatColor.GOLD + " player"
					+ ChatColor.RED + " needs to join to be able to play!");

		}
		if(Bukkit.getServer().getOnlinePlayers().length >= 2 && !Game.gameStarted){
			Game.startGame();
		}
	}
	@EventHandler
	public void onBarSet(MessageBarSetEvent e)
	{if(zombieList.contains(e.getPlayer().getName())){
		e.setMessage("§l§6Current Map: " + "§c" + HunterGames.currentMap);
	}
	if(!zombieList.contains(e.getPlayer().getName())){
		e.setMessage("§l§6Current Map: " + "§c" + HunterGames.currentMap);
	}
	}
	
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDeathEvent e) {
		Entity entityAttacked = e.getEntity();
		Entity entityAttacker = e.getEntity().getKiller();

		if ((Game.gameStarted) && ((entityAttacker instanceof Player))) {
			Player attacker = (Player) entityAttacker;
			if ((entityAttacked instanceof Player)) {
				Player attacked = (Player) entityAttacked;

				if (zombieList.contains(attacker.getName())) {
					for (Player onlinePlayers : Bukkit.getServer()
							.getOnlinePlayers()) {
						if (zombieList.contains(onlinePlayers.getName())) {
							Game.gameStarted = false;
							Bukkit.getServer().broadcastMessage(
									HunterGames.P + ChatColor.GOLD + " Everyone"
											+ ChatColor.RED
											+ " is dead. Congratulations "
											+ ChatColor.GOLD + "zombies.");

							onlinePlayers.getInventory().setHelmet(null);
							onlinePlayers.getInventory().clear();
							Bukkit.getServer().getScheduler()
							.cancelTask(HunterGames.timeVote);
							Bukkit.getServer().getScheduler()
							.cancelTask(HunterGames.gameTime);
							onlinePlayers
							.removePotionEffect(PotionEffectType.SPEED);
							onlinePlayers
							.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							zombieList.clear();


							onlinePlayers.setScoreboard(HunterGames.manager.getNewScoreboard());


							Game.startGame();
						}
					}
					if (Game.gameStarted)
						zombieList.add(attacked.getName());

				}
			}
		}
	}
	public static void tpToLobby() {

		Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
				.getString("Lobby" + "." + "W")), plugin.getConfig().getDouble(
						"Lobby" + "." + "X"), plugin.getConfig().getDouble(
								"Lobby" + "." + "Y"), plugin.getConfig().getDouble(
										"Lobby" + "." + "Z"));


		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			onlinePlayers.teleport(loc);
		}


	}

	public static void chooseArena() {

		List<String> list = plugin.getConfig().getStringList("Arenas.List");
		Random rand = new Random();
		String arena = list.get(rand.nextInt(list.size()));
		plugin.getConfig().set("currentArena", "");
		plugin.getConfig().set("currentArena", arena);
		Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY
				+ " Next map is: " + ChatColor.GOLD
				+ plugin.getConfig().getString("currentArena"));
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			onlinePlayers.getInventory().clear();

		}


	}
	public static void endGame(){
		for (Player onlinePlayers : Bukkit
				.getServer()
				.getOnlinePlayers()) {

			Bukkit.broadcastMessage(HunterGames.P
					+ ChatColor.GRAY
					+ "Game over! "
					+ ChatColor.GOLD
					+ " Humans win!");
			Game.gameStarted = false;
			onlinePlayers
			.getInventory()
			.setHelmet(
					null);
			onlinePlayers
			.getInventory()
			.clear();
			Bukkit.getServer()
			.getScheduler()
			.cancelTask(
					HunterGames.timeVote);

			Bukkit.getServer()
			.getScheduler()
			.cancelTask(
					HunterGames.gameTime);

			for (PotionEffect effect : onlinePlayers.getActivePotionEffects())
				onlinePlayers.removePotionEffect(effect.getType());
			PlayerListener.zombieList
			.clear();
			plugin.getConfig().set("currentArena", "Lobby");

			Game.startGame();
		}
	}
	public static void teleportToArena() {
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			Player randPlayer = Methods.getRandomPlayer();
			if (!PlayerListener.zombieList.contains(randPlayer)) {

				String currentArena = plugin.getConfig().getString("currentArena");
				Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
						.getString(currentArena + ".humans" + "." + "W")), plugin
						.getConfig()
						.getDouble(currentArena + ".humans" + "." + "X"), plugin
						.getConfig()
						.getDouble(currentArena + ".humans" + "." + "Y"), plugin
						.getConfig()
						.getDouble(currentArena + ".humans" + "." + "Z"));



				onlinePlayers.teleport(loc);
				onlinePlayers.getInventory().clear();
				giveItems();
				ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
				item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
				onlinePlayers.getInventory().addItem(item);
				randPlayer.getInventory().clear();
				giveZombieItems(randPlayer);

			}

		}
		if (!PlayerListener.zombieList.contains(randPlayer)) {
			String currentArena = plugin.getConfig().getString("currentArena");
			Location loc = new Location(Bukkit.getWorld(plugin.getConfig()
					.getString(currentArena + ".humans" + "." + "W")), plugin
					.getConfig().getDouble(
							currentArena + ".zombies" + "." + "X"), plugin
							.getConfig().getDouble(
									currentArena + ".zombies" + "." + "Y"), plugin
									.getConfig().getDouble(
											currentArena + ".zombies" + "." + "Z"));
			randPlayer.teleport(loc);

		}
	}
	public static void giveItems() {
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			try{
				String items = plugin.getConfig().getString("Kits.Humans.Items");
				String[] indiItems = items.split(",");
				for(String s : indiItems){
					String[] itemAmounts = s.split("-");
					ItemStack item = new ItemStack(Integer.valueOf(itemAmounts[0]), Integer.valueOf(itemAmounts[1]));
					onlinePlayers.getInventory().addItem(item);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void giveZombieItems(Player player) {
		try{
			String items = plugin.getConfig().getString("Kits.Zombies.Items");
			String[] indiItems = items.split(",");
			for(String s : indiItems){
				String[] itemAmounts = s.split("-");
				ItemStack item = new ItemStack(Integer.valueOf(itemAmounts[0]), Integer.valueOf(itemAmounts[1]));
				player.getInventory().addItem(item);
			}
		} catch(Exception e){
			e.printStackTrace();
		}

	}
	public static void announceStarted() {
		randPlayer = Methods.getRandomPlayer();
		PlayerListener.zombieList.add(randPlayer.getName());
		Bukkit.broadcastMessage(HunterGames.P + ChatColor.RED + " Starting"
				+ ChatColor.GOLD + " infected " + ChatColor.RED + "on map "
				+ ChatColor.GOLD + plugin.getConfig().getString("currentArena"));
		Methods.givePlayerZombieHead(randPlayer);
		Bukkit.broadcastMessage(HunterGames.P + " " + ChatColor.YELLOW
				+ randPlayer.getName() + ChatColor.GRAY + " is infected.");
		randPlayer.sendMessage(HunterGames.P + ChatColor.RED
				+ " You are a zombie. Your goal is to kill everyone human.");

	}

}