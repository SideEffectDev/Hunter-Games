package me.sideeffect.huntergames;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;
import pw.ender.messagebar.MessageBarSetEvent;



public class PlayerListener implements Listener {
	static HunterGames plugin;

	public PlayerListener(HunterGames instance) {
		plugin = instance;
	}

	static FileManager settings = FileManager.getInstance();
	private static Boolean broadcasted = false;
	public static HashSet<String> zombieList = new HashSet<String>();
	static Player randPlayer = null;
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		if (Game.gameStarted) {
			if (zombieList.contains(player.getName())) {
				giveEffects(e.getPlayer(), "Zombies");
				String currentArena = settings.getData().getString(
						"currentArena");
				Double x = settings.getData().getDouble(
						"Arenas." + currentArena + ".zombies" + ".X");
				Double y = settings.getData().getDouble(
						"Arenas." +currentArena + ".zombies" + ".Y");
				Double z = settings.getData().getDouble(
						"Arenas." +currentArena + ".zombies" + ".Z");
				World w = Bukkit.getServer().getWorld(
						settings.getData().getString(
								"Arenas." +currentArena + ".zombies" + ".W"));
				Location loc = new Location(w, x, y, z);

				e.setRespawnLocation(loc);
				e.getPlayer()
				.sendMessage(
						ChatColor.RED
						+ "You are a zombie. Your goal is to kill every human.");
				setZombie(player);
				giveItems(player, "Zombies");
			}
			if (!zombieList.contains(player.getName())) {
				giveEffects(e.getPlayer(), "Humans");
				String currentArena = settings.getData().getString(
						"currentArena");
				Double x = settings.getData().getDouble(
						"Arenas." + currentArena + ".humans" + ".X");
				Double y = settings.getData().getDouble(
						"Arenas." + currentArena + ".humans" + ".Y");
				Double z = settings.getData().getDouble(
						"Arenas." + currentArena + ".humans" + ".Z");
				World w = Bukkit.getServer().getWorld(
						settings.getData().getString(
								"Arenas." +currentArena + ".humans" + ".W"));
				Location loc = new Location(w, x, y, z);
				e.setRespawnLocation(loc);


			}
		}
		if (!Game.gameStarted) {
			Location loc = new Location(Bukkit.getWorld(settings.getData()
					.getString("Lobby" + "." + "W")), settings.getData()
					.getDouble("Lobby" + "." + "X"), settings.getData()
					.getDouble("Lobby" + "." + "Y"), settings.getData()
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
		removeZombie(player);
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
						HunterGames.gameTime);

				Bukkit.getServer()
				.getScheduler()
				.cancelTask(
						HunterGames.gameTime);
				onlinePlayers.getInventory().setHelmet(null);

				removeEffects(onlinePlayers);
				Game.gameStarted = false;
			}
			if(!Game.gameStarted){
				HunterGames.scoreboard.resetScores(player);
			}
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (Game.gameStarted) {
			if(player != null){
				zombieList.add(player.getName());
			}

		}
		Player Killer = event.getEntity().getKiller();
		Player Killed = event.getEntity();
		if(Game.gameStarted){	
			zombieList.add(Killed.getName());
		}
		if (Game.gameStarted && Game.lobbyStarted == false) {
			if (Killer != null) {
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
						.cancelTask(HunterGames.gameTime);
						Bukkit.getServer().getScheduler()
						.cancelTask(HunterGames.gameTime);
						removeEffects(onlinePlayers);							
						zombieList.clear();
						removeZombie(onlinePlayers);
						onlinePlayers.setScoreboard(HunterGames.manager.getNewScoreboard());
						Game.startGame();
					}
				}
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

			}
		}
		event.setDeathMessage(null);


	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {


		if (Game.gameStarted) {
			String currentArena = settings.getData().getString("currentArena");
			Double x = settings.getData().getDouble(
					"Arenas." + currentArena + ".zombies" + ".X");
			Double y = settings.getData().getDouble(
					"Arenas." + currentArena + ".zombies" + ".Y");
			Double z = settings.getData().getDouble(
					"Arenas." + currentArena + ".zombies" + ".Z");
			World w = Bukkit.getServer().getWorld(
					settings.getData().getString(
							"Arenas." + currentArena + ".zombies" + ".W"));
			Location loc = new Location(w, x, y, z);
			Player player = e.getPlayer();
			player.teleport(loc);

			player.setScoreboard(HunterGames.scoreboard);


		}
		if (!Game.gameStarted && Game.lobbyStarted) {
			Player player = e.getPlayer();
			Location loc = new Location(Bukkit.getWorld(settings.getData()
					.getString("Lobby" + "." + "W")), settings.getData()
					.getDouble("Lobby" + "." + "X"), settings.getData()
					.getDouble("Lobby" + "." + "Y"), settings.getData()
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
	{
		if(zombieList.contains(e.getPlayer().getName())){
			e.setMessage("§l§6Current Map: " + "§c" + HunterGames.currentMap);
		}
		if(!zombieList.contains(e.getPlayer().getName())){
			e.setMessage("§l§6Current Map: " + "§c" + HunterGames.currentMap);
		}
	}
	public static Location Lobby() {

		Location loc = new Location(Bukkit.getWorld(settings.getData()
				.getString("Lobby" + "." + "W")), settings.getData().getDouble(
						"Lobby" + "." + "X"), settings.getData().getDouble(
								"Lobby" + "." + "Y"), settings.getData().getDouble(
										"Lobby" + "." + "Z"));

		return loc;

	}
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e){
		if(!plugin.getConfig().getBoolean("General.MobSpawn")){
			e.setCancelled(true);
		}
	}
	public static void chooseArena() {


		List<String> arenas = new ArrayList<String>();

		try{
			for (String possibleArenas : settings.getData().getConfigurationSection("Arenas").getKeys(false))
			{

				if (settings.getData().contains("Arenas." + possibleArenas + ".zombies") && settings.getData().contains("Arenas." + possibleArenas + ".humans"))
				{
					arenas.add(possibleArenas);
				}
			}
			Random rand = new Random();
			String arena = arenas.get(rand.nextInt(arenas.size()));
			settings.getData().set("currentArena", arena);

			Bukkit.broadcastMessage(HunterGames.P + ChatColor.GRAY
					+ " Next map is: " + ChatColor.GOLD
					+ settings.getData().getString("currentArena"));
			for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
				onlinePlayers.getInventory().clear();

			}

		}catch(Exception e){
			stopGame();
		}

	}
	public static void stopGame(){

		if(!broadcasted){
			Bukkit.broadcastMessage(ChatColor.RED + "There are no availbile" + ChatColor.GOLD + " arenas!");
			broadcasted = true;
		}
		Bukkit.getServer()
		.getScheduler()
		.cancelTask(
				HunterGames.gameTime);
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
			removeZombie(onlinePlayers);
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
					HunterGames.gameTime);


			removeEffects(onlinePlayers);
			PlayerListener.zombieList
			.clear();
			settings.getData().set("currentArena", "Lobby");

			Game.startGame();
		}
	}

	public static void teleportToArena() {
		for(Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()){
			Player randPlayer = Methods.getRandomPlayer();
			if (!PlayerListener.zombieList.contains(randPlayer)) {

				String currentArena = settings.getData().getString("currentArena");
				Location loc = new Location(Bukkit.getWorld(settings.getData()
						.getString("Arenas." + currentArena + ".humans" + "." + "W")), settings.getData()
						.getDouble("Arenas." + currentArena + ".humans" + "." + "X"), settings.getData()
						.getDouble("Arenas." + currentArena + ".humans" + "." + "Y"), settings.getData()
						.getDouble("Arenas." + currentArena + ".humans" + "." + "Z"));

				onlinePlayers.teleport(loc);
				onlinePlayers.getInventory().clear();
				giveItems(onlinePlayers, "Humans");
				randPlayer.getInventory().clear();
				giveItems(randPlayer, "Zombies");

			}

		}
		if (!PlayerListener.zombieList.contains(randPlayer)) {
			String currentArena = settings.getData().getString("currentArena");
			Location loc = new Location(Bukkit.getWorld(settings.getData()
					.getString("Arenas." + currentArena + ".humans" + "." + "W")), settings
					.getData().getDouble(
							"Arenas." + currentArena + ".zombies" + "." + "X"), settings
							.getData().getDouble(
									"Arenas." + currentArena + ".zombies" + "." + "Y"), settings
									.getData().getDouble(
											"Arenas." + currentArena + ".zombies" + "." + "Z"));
			randPlayer.teleport(loc);

		}
	}

	public static void giveItems(Player player, String type) {
		try{
			String items = plugin.getConfig().getString("Kits." + type + ".Items");
			String[] indiItems = items.split(",");
			for(String s : indiItems){
				String[] itemAmounts = s.split("-");
				@SuppressWarnings("deprecation")
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
				+ ChatColor.GOLD + settings.getData().getString("currentArena"));
		giveEffects(randPlayer, "Zombies");
		setZombie(randPlayer);
		Bukkit.broadcastMessage(HunterGames.P + " " + ChatColor.YELLOW
				+ randPlayer.getName() + ChatColor.GRAY + " is infected.");
		randPlayer.sendMessage(HunterGames.P + ChatColor.RED
				+ " You are a zombie. Your goal is to kill everyone human.");

	}
	public static void setZombie(Player player){
		if(plugin.getConfig().getBoolean("ZombieDisguise")){
			try{
				
				HunterGames.api.disguiseToAll(player, new MobDisguise(DisguiseType.ZOMBIE, true));

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void removeZombie(Player player){
		if(plugin.getConfig().getBoolean("ZombieDisguise")){
			try{
				
				HunterGames.api.undisguiseToAll(player);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void giveEffects(Player player, String type) {
		String[] potionEffects = plugin.getConfig().getString("Kits." + type + ".PotionEffects").split(",");
		for(String s : potionEffects){
			String[] potionEffect = s.split("-");
			try {
				int potionId = Integer.parseInt(potionEffect[0]);
				int potionIntensity = Integer.parseInt(potionEffect[1]);
				@SuppressWarnings("deprecation")
				PotionEffectType effect = PotionEffectType.getById(potionId);
				PotionEffect applyEffect = new PotionEffect(effect, 10000, potionIntensity);
				player.addPotionEffect(applyEffect);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
	}
	public static void removeEffects(Player player){
		for(PotionEffect effects : player.getActivePotionEffects()){
			player.removePotionEffect(effects.getType());
		}
	}
}