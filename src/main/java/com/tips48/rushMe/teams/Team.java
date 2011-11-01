package com.tips48.rushMe.teams;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team {
	private final Set<String> players = new HashSet<String>();
	private int spawnsLeft;
	private final String name;
	private final int playerLimit;
	private List<Location> spawns = new ArrayList<Location>();
	private boolean infiniteLives;
	private final String prefix;

	/**
	 * Creates a team
	 * @param name Team's name
	 * @param prefix Team's preifx
	 * @param playerLimit How many players are allowed on the team
	 */
	public Team(String name, String prefix, int playerLimit) {
		this.name = name;
		this.playerLimit = playerLimit;
		this.prefix = prefix;
	}

	/**
	 * Gets the team's name
	 * @return the teams name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the team's prefix
	 * @return the teams prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Gets how many spawns until the team loses
	 * @return how many spawns left until the team loses
	 */
	public int getSpawnsLeft() {
		return spawnsLeft;
	}

	/**
	 * Adds to the spawns left of the team
	 */
	public void addSpawnsLeft() {
		spawnsLeft++;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	/**
	 * Removes from the spawns left of the team
	 */
	public void subtractSpawnsLeft() {
		spawnsLeft--;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	/**
	 * Sets the spawns left of the team
	 * @param spawnsLeft New spawns left
	 */
	public void setSpawnsLeft(int spawnsLeft) {
		this.spawnsLeft = spawnsLeft;
		for (String player : players) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				SpoutGUI.getHudOf(p).updateHUD();
			}
		}
	}

	/**
	 * Gets a Set with all the players names who are on the team
	 * @return
	 */
	public Set<String> getPlayers() {
		return players;
	}

	/**
	 * Adds the specified player to team
	 * @param player Player's name
	 */
	public void addPlayer(String player) {
		if (playerLimit > players.size()) {
			players.add(player);
		}
	}

	/**
	 * Removes the specified player from the team
	 * @param player Player's name
	 */
	public void removePlayer(String player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}

	/**
	 * Utility method
	 * @see #addPlayer(String)
	 * @param player {@link Player}
	 */
	public void addPlayer(Player player) {
		addPlayer(player.getName());
	}

	/**
	 * Utiltiy method
	 * @param player {@link Player}
	 */
	public void removePlayer(Player player) {
		removePlayer(player.getName());
	}

	/**
	 * Gets if the team contains the specified player
	 * @param player Player's name
	 * @return if the team contains the specified player
	 */
	public boolean containsPlayer(String player) {
		return players.contains(player);
	}

	/**
	 * Utility method
	 * @see #containsPlayer(String)
	 * @param player {@link Player}
	 * @return if the team contains the specified player
	 */
	public boolean containsPlayer(Player player) {
		return containsPlayer(player.getName());
	}

	/**
	 * Gets the limit of players allowed on the team
	 * @return the limit of players allowed on the team
	 */
	public int getPlayerLimit() {
		return playerLimit;
	}

	/**
	 * Gets a list with the spawn Locations
	 * @return a list with the {@link Location}s
	 */
	public List<Location> getSpawns() {
		return spawns;
	}

	/**
	 * Sets the spawn locations
	 * @param spawns List with each spawn {@link Location}
	 */
	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	/**
	 * Adds a spawn to the list of spawns
	 * @param spawn {@link Location} spawn
	 */
	public void addSpawn(Location spawn) {
		spawns.add(spawn);
	}

	/**
	 * Gets if the team has infinite lives
	 * @return if the team has infinite lives
	 */
	public boolean getInfiniteLives() {
		return infiniteLives;
	}

	/**
	 * Sets if the team has infinite lives
	 * @param infiniteLives If the team has infinite lives
	 */
	public void setInfiniteLives(boolean infiniteLives) {
		this.infiniteLives = infiniteLives;
	}

	/**
	 * Gets a list of with each player on the team ranked by score, with 0 as highest
	 * @return a list of with each player on the team by score
	 */
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
				if (!GameManager.inGame(name)) {
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

	/**
	 * Gets the score highest but under the specified score
	 * @param lastScore Limit of score
	 * @return score highest but under the specified score
	 */
	private int getHighestScore(int lastScore) {
		int otherScore = 0;
		for (int score : PlayerData.getScores().values()) {
			if (score < lastScore) {
				if (score > otherScore) {
					otherScore = score;
				}
			}
		}
		return otherScore;
	}

	/**
	 * Gets the highest score
	 * @return the highest score
	 */
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
