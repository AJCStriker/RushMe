package com.tips48.rushMe;

import com.tips48.rushMe.teams.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Arena {

	private final List<Team> teams;
	private final GameMode gamemode;
	private int timeLeft;
	private final int startingTimeLeft;

	private int scheduler;

	public Arena(GameMode gamemode, int startinTimeLeft) {
		this.gamemode = gamemode;
		this.startingTimeLeft = startinTimeLeft;

		teams = new ArrayList<Team>();
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

	private void doSecond() {
		timeLeft--;
		if (timeLeft == 0) {
			stop();
		}
	}

	public void start() {
		timeLeft = startingTimeLeft;
		scheduler = RushMe.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(RushMe.getInstance(), new Runnable() {
			public void run() {
				doSecond();
			}
		}, 0, 20);
	}

	public void stop() {
		RushMe.getInstance().getServer().getScheduler().cancelTask(scheduler);
		scheduler = 0;
	}
}
