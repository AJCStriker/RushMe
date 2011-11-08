package com.tips48.rushMe;

import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
	private Location loc1;
	private Location loc2;

	private int doSecondScheduler;
	private int startingScheduler;

	private static Map<String, Arena> locationsNeeded = new HashMap<String, Arena>();

	protected Arena(GameMode gamemode, String name) {
		this.gamemode = gamemode;
		this.name = name;

		teams = new ArrayList<Team>();
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
		Player p = RushMe.getInstance().getServer().getPlayer(player);
		if (p != null) {
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

	protected void handlePlayerJoin(Player player) {
		// TODO
	}

	protected void handlePlayerLeave(Player player) {
		// TODO
	}

	private void doSecond() {
		timeLeft--;
		if (timeLeft == 0) {
			stop();
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

	public Location getLocation1() {
		return loc1;
	}

	public Location getLocation2() {
		return loc2;
	}

	public void setLocation1(Location loc) {
		if (loc1 == null) {
			this.loc1 = loc;
		}
	}

	public void setLocation2(Location loc) {
		if (loc2 == null) {
			this.loc2 = loc;
		}
	}

	public boolean inArena(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();

		return x >= loc1.getBlockX() && x <= loc2.getBlockX()
				&& y >= loc1.getBlockY() && y <= loc2.getBlockY()
				&& z >= loc1.getBlockZ() && z <= loc2.getBlockZ();
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
}
