package com.tips48.rushMe;

import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.SpoutManager;

import java.util.*;

/**
 * Represents an Arena
 */
public class Arena {

	private final List<Team> teams;
	private final GameMode gamemode;
	private int timeLeft;
	private final String name;
	private Set<String> players;
	private boolean started;
	private int startingIn;
	private Vector loc1;
	private Vector loc2;

	private int doSecondScheduler;
	private int startingScheduler;

	private static Map<String, Arena> locationsNeeded = new HashMap<String, Arena>();

	protected Arena(GameMode gamemode, String name) {
		this.gamemode = gamemode;
		this.name = name;

		teams = gamemode.getTeams();

		players = new HashSet<String>();
		started = false;
		startingIn = 600;

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
		return getPlayerTeam(player.getName());
	}

	public Team getPlayerTeam(String player) {
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

	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	public void addPlayer(String player) {
		players.add(player);
		// TODO not random teams
		boolean team = false;
		Random r = new Random();
		while (!team) {
			Team t = teams.get(r.nextInt(teams.size() - 1));
			team = t.addPlayer(player);
		}

		Player p = RushMe.getInstance().getServer().getPlayer(player);
		if (p != null) {
			savedInventories.addInventory(p, p.getInventory());
			p.getInventory().clear();
			SpoutManager.getAppearanceManager().setGlobalSkin(p,
					getPlayerTeam(p).getTexture());
			MainHUD h = SpoutGUI.getHudOf(p);
			if (h != null) {
				h.init();
			}
		}
	}

	public void removePlayer(Player player) {
		removePlayer(player.getName());
	}

	public void removePlayer(String player) {
		if (players.contains(player)) {
			players.remove(player);
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				if (savedInventories.hasInventory(p)) {
					PlayerInventory pi = savedInventories.getInventory(p);
					p.getInventory().setContents(pi.getContents());
					p.getInventory().setArmorContents(pi.getArmorContents());
					savedInventories.removeInventory(p);
				}
				MainHUD h = SpoutGUI.getHudOf(p);
				if (h != null) {
					h.shutdown();
				}
			}
		}
	}

	public boolean hasPlayer(Player player) {
		return hasPlayer(player.getName());
	}

	public boolean hasPlayer(String player) {
		return players.contains(player);
	}

	public Set<String> getPlayers() {
		return players;
	}

	public boolean isStarted() {
		return started;
	}

	public String getTimeBeforeStart() {
		if (started) {
			return "Already started";
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
		for (String s : getPlayers()) {
			Player p = RushMe.getInstance().getServer().getPlayer(s);
			if (p != null) {
				MainHUD hud = SpoutGUI.getHudOf(p);
				if (hud != null) {
					hud.shutdown();
				}
			}
		}
		startingIn = 600;
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
			if (getVector2() != null) {
				GameManager.addArena(this);
			}
		}
	}

	public void setVector2(Vector loc) {
		if (loc2 == null) {
			this.loc2 = loc;
			if (getVector1() != null) {
				GameManager.addArena(this);
			}
		}
	}

	public boolean inArena(Vector loc) {
		double x = loc.getX();
		double z = loc.getZ();

		return (x >= loc1.getBlockX() && x <= loc2.getBlockX() + 1
				&& z >= loc1.getBlockZ() && z <= loc2.getBlockZ() + 1);
	}

	public static boolean hasArena(Player player) {
		return hasArena(player.getName());
	}

	public static boolean hasArena(String player) {
		return locationsNeeded.containsKey(player);
	}

	public static void addArena(Player creator, Arena a) {
		addArena(creator.getName(), a);
	}

	public static void addArena(String creator, Arena a) {
		locationsNeeded.put(creator, a);
	}

	public static Arena getArena(Player p) {
		return getArena(p.getName());
	}

	public static Arena getArena(String p) {
		return locationsNeeded.get(p);
	}

	public static void removeArena(Player p) {
		removeArena(p.getName());
	}

	public static void removeArena(String p) {
		if (locationsNeeded.containsKey(p)) {
			locationsNeeded.remove(p);
		}
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
}
