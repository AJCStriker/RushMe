package com.tips48.rushMe;

import com.tips48.rushMe.teams.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Represents an Arena
 */
public class Arena {

	private final List<Team> teams;
	private final GameMode gamemode;
	private int timeLeft;
	private final int time;
	private final String name;
	private Set<String> players;
	private boolean started;

	private int scheduler;

	protected Arena(GameMode gamemode, int time, String name) {
		this.gamemode = gamemode;
		this.time = time;
		this.name = name;

		teams = new ArrayList<Team>();
		players = new HashSet<String>();
		started = false;
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

	protected void onPlayerJoin(Player player) {
		// TODO teleport
		// TODO block
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
		timeLeft = time;
		scheduler = RushMe.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(RushMe.getInstance(), new Runnable() {
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
		RushMe.getInstance().getServer().getScheduler().cancelTask(scheduler);
		scheduler = 0;
		started = false;
	}
}
