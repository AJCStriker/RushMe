package com.tips48.rushMe;

import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.SpoutManager;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.util.*;

/**
 * Represents an Arena
 */
public class Arena {

	private final List<Team> teams;
	private final GameMode gamemode;
	private int timeLeft;
	private final String name;
	private TIntSet players;
	private boolean started;
	private int startingIn;
	private Vector loc1;
	private Vector loc2;

	private int doSecondScheduler;
	private int startingScheduler;

	private int creator;

	// private static Map<String, Arena> locationsNeeded = new HashMap<String,
	// Arena>();

	protected Arena(GameMode gamemode, String name, int creator) {
		this.gamemode = gamemode;
		this.name = name;
		this.creator = creator;

		teams = gamemode.getTeams();

		players = new TIntHashSet();
		started = false;

		startingIn = -1;
	}

	public void startCountdownTillStart(int s) {
		this.startingIn = s;
		startingScheduler = RushMe
				.getInstance()
				.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(RushMe.getInstance(),
						new Runnable() {
							public void run() {
								startingIn--;
								if (startingIn <= 0) {
									start();
								}
							}
						}, 0, 20);
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Team getTeam(String name) {
		for (Team t : getTeams()) {
			if (t.getName().contains(name)) {
				return t;
			}
		}
		return null;
	}

	public Team getPlayerTeam(Player player) {
		return getPlayerTeam(player.getEntityId());
	}

	public Team getPlayerTeam(int player) {
		for (Team t : getTeams()) {
			if (t.containsPlayer(player)) {
				return t;
			}
		}
		return null;
	}

	public GameMode getGameMode() {
		return gamemode;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public String getName() {
		return name;
	}

	public void addPlayer(Player player, Team prefered) {
		addPlayer(player.getEntityId(), prefered);
	}

	public void addPlayer(int player, Team prefered) {
		players.add(player);
		boolean team = false;
		if (prefered != null) {
			team = prefered.addPlayer(player);
		}
		Random r = new Random();
		while (!team) {
			Team t = teams.get(r.nextInt(teams.size() - 1));
			team = t.addPlayer(player);
		}

		Player p = SpoutManager.getPlayerFromId(player);
		if (p != null) {
			savedInventories.addInventory(p, p.getInventory());
			p.getInventory().clear();
			RMUtils.giveAllGuns(p);
			savedGamemodes.addGameMode(p, p.getGameMode());
			p.setGameMode(org.bukkit.GameMode.SURVIVAL);
			SpoutManager.getAppearanceManager().setGlobalSkin(p,
					getPlayerTeam(p).getTexture());
			MainHUD h = SpoutGUI.getHudOf(p);
			if (h != null) {
				h.init();
			}
		}
	}

	public void removePlayer(Player player) {
		removePlayer(player.getEntityId());
	}

	public void removePlayer(int player) {
		if (players.contains(player)) {
			players.remove(player);
			Player p = SpoutManager.getPlayerFromId(player);
			if (p != null) {
				if (savedInventories.hasInventory(p)) {
					PlayerInventory pi = savedInventories.getInventory(p);
					p.getInventory().setContents(pi.getContents());
					p.getInventory().setArmorContents(pi.getArmorContents());
					savedInventories.removeInventory(p);
				}
				if (savedGamemodes.hasGameMode(p)) {
					p.setGameMode(savedGamemodes.getGameMode(p));
					savedGamemodes.removeGameMode(p);
				}
				MainHUD h = SpoutGUI.getHudOf(p);
				if (h != null) {
					h.shutdown();
				}
			}
		}
	}

	public boolean hasPlayer(Player player) {
		return hasPlayer(player.getEntityId());
	}

	public boolean hasPlayer(int player) {
		return players.contains(player);
	}

	public TIntSet getPlayers() {
		return players;
	}

	public boolean isStarted() {
		return started;
	}

	public String getTimeBeforeStart() {
		if (started) {
			return "Already started";
		}
		if (startingIn == -1) {
			return "Not starting";
		}
		return RMUtils.parseIntForMinute(startingIn);
	}

	private void doSecond() {
		timeLeft--;
		if (timeLeft == 0) {
			stop();
		}
		boolean gameWon = false;
		for (Team team : teams) {
			if (gameWon) {
				team.doWon();
				return;
			}
			if (team.getSpawnsLeft() == 0 && !team.getInfiniteLives()) {
				stop();
				team.doLost();
				gameWon = true;
			}
		}
	}

	public int getCreator() {
		return creator;
	}

	public void start() {
		if (started) {
			return;
		}
		RushMe.getInstance().getServer().getScheduler()
				.cancelTask(startingScheduler);
		startingScheduler = 0;
		startingIn = 0;
		timeLeft = gamemode.getTime();
		doSecondScheduler = RushMe
				.getInstance()
				.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(RushMe.getInstance(),
						new Runnable() {
							public void run() {
								doSecond();
							}
						}, 0, 20);
		started = true;
	}

	public void stop() {
		if (!started) {
			return;
		}
		RushMe.getInstance().getServer().getScheduler()
				.cancelTask(doSecondScheduler);
		doSecondScheduler = 0;
		for (int s : getPlayers().toArray()) {
			Player p = SpoutManager.getPlayerFromId(s);
			if (p != null) {
				MainHUD hud = SpoutGUI.getHudOf(p);
				if (hud != null) {
					hud.shutdown();
				}
			}
		}
		startCountdownTillStart(60);
		started = false;
	}

	public Vector getVector1() {
		return loc1;
	}

	public Vector getVector2() {
		return loc2;
	}

	public void setVector1(Vector loc) {
		if (loc1 == null) {
			this.loc1 = loc;
			if (loc2 != null) {
				GameManager.addArena(this);
			}
		}
	}

	public void setVector2(Vector loc) {
		if (loc2 == null) {
			this.loc2 = loc;
			if (loc1 != null) {
				GameManager.addArena(this);
			}
		}
	}

	public boolean inArena(Vector loc) {
		final double x = loc.getX();
		final double z = loc.getZ();
		return x >= loc1.getBlockX() && x < loc2.getBlockX() + 1
				&& z >= loc1.getBlockZ() && z < loc2.getBlockZ() + 1;
	}

	public boolean getCompleted() {
		return (loc1 != null) && (loc2 != null);
	}

	private static class savedInventories {
		private static final Map<String, PlayerInventory> inventories = new HashMap<String, PlayerInventory>();

		protected static PlayerInventory getInventory(String player) {
			return inventories.get(player);
		}

		protected static PlayerInventory getInventory(Player player) {
			return getInventory(player.getName());
		}

		protected static boolean hasInventory(Player player) {
			return hasInventory(player.getName());
		}

		protected static boolean hasInventory(String player) {
			return inventories.containsKey(player);
		}

		protected static void removeInventory(Player player) {
			removeInventory(player.getName());
		}

		protected static void removeInventory(String player) {
			inventories.remove(player);
		}

		protected static void addInventory(Player player, PlayerInventory pi) {
			addInventory(player.getName(), pi);
		}

		protected static void addInventory(String player, PlayerInventory pi) {
			inventories.put(player, pi);
		}

	}

	private static class savedGamemodes {
		private static final Map<String, org.bukkit.GameMode> gamemodes = new HashMap<String, org.bukkit.GameMode>();

		protected static org.bukkit.GameMode getGameMode(String player) {
			return gamemodes.get(player);
		}

		protected static org.bukkit.GameMode getGameMode(Player player) {
			return getGameMode(player.getName());
		}

		protected static boolean hasGameMode(Player player) {
			return hasGameMode(player.getName());
		}

		protected static boolean hasGameMode(String player) {
			return gamemodes.containsKey(player);
		}

		protected static void removeGameMode(Player player) {
			removeGameMode(player.getName());
		}

		protected static void removeGameMode(String player) {
			gamemodes.remove(player);
		}

		protected static void addGameMode(Player player, org.bukkit.GameMode g) {
			addGameMode(player.getName(), g);
		}

		protected static void addGameMode(String player, org.bukkit.GameMode g) {
			gamemodes.put(player, g);
		}

	}
}
