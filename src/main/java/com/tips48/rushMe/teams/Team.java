package com.tips48.rushMe.teams;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.SpoutGUI;

public class Team {
	private Set<String> players = new HashSet<String>();
	private int spawnsLeft;
	private String name;
	private int playerLimit;
	private Set<Location> spawns = new HashSet<Location>();
	private boolean infiniteLives;

	public Team(String name, int playerLimit) {
		this.name = name;
		this.playerLimit = playerLimit;
	}

	public String getName() {
		return name;
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
}
