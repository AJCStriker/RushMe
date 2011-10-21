package com.tips48.rushMe.teams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.SpoutGUI;
import com.tips48.rushMe.data.PlayerData;

public class Team {
	private Set<String> players = new HashSet<String>();
	private int spawnsLeft;
	private String name;
	private int playerLimit;
	private Set<Location> spawns = new HashSet<Location>();
	private boolean infiniteLives;
	private String prefix;

	public Team(String name, String prefix, int playerLimit) {
		this.name = name;
		this.playerLimit = playerLimit;
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getSpawnsLeft() {
		return spawnsLeft;
	}

	public void addSpawnsLeft() {
		spawnsLeft++;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	public void subtractSpawnsLeft() {
		spawnsLeft--;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	public void setSpawnsLeft(int spawnsLeft) {
		this.spawnsLeft = spawnsLeft;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	public Set<String> getPlayers() {
		return players;
	}

	public void addPlayer(String player) {
		if (playerLimit > players.size()) {
			players.add(player);
		}
	}

	public void removePlayer(String player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}

	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	public void removePlayer(Player player) {
		removePlayer(player.getName());
	}

	public boolean containsPlayer(String player) {
		return players.contains(player);
	}

	public boolean containsPlayer(Player player) {
		return containsPlayer(player.getName());
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public Set<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(Set<Location> spawns) {
		this.spawns = spawns;
	}

	public boolean getInfiniteLives() {
		return infiniteLives;
	}

	public void setInfiniteLives(boolean infiniteLives) {
		this.infiniteLives = infiniteLives;
	}

	public List<String> getByScore() {
		List<String> byScore = new ArrayList<String>();
		int playersOnTeam = players.size();
		Integer[] scores = new Integer[playersOnTeam];
		for (int i = 0; i < playersOnTeam; i++) {
			if (i > 0) {
				scores[i] = getHighestScore(scores[i - 1]);
			} else {
				scores[i] = getHighestScore();
			}
		}
		for (int i = 0; i < playersOnTeam; i++) {
			for (String name : PlayerData.getScores().keySet()) {
				if (!players.contains(name)) {
					continue;
				}
				if (!PlayerData.isActive(name)) {
					continue;
				}
				if (scores[i] == PlayerData.getScore(name)) {
					if (!byScore.contains(name)) {
					byScore.add(i, name);
					}
				}
			}
		}
		
		return byScore;
	}

	private int getHighestScore(int lastScore) {
		int otherScore = 0;
		for (int score : PlayerData.getScores().values()) {
			if (score < lastScore) {
				System.out.println("Score < lastScore");
				if (score > otherScore) {
					System.out.println("Score > otherScore");
				otherScore = score;
				}
			}
		}
		return otherScore;
	}

	private int getHighestScore() {
		int highest = 0;
		for (int score : PlayerData.getScores().values()) {
			if (score > highest) {
				highest = score;
			}
		}
		return highest;
	}
}
