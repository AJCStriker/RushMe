package com.tips48.rushMe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;

public class GameManager {
	private static GameMode mode = GameMode.RUSH; // TEMPORARY
	private static Set<PlayerData> playerData = new HashSet<PlayerData>();
	private static List<Team> teams = new ArrayList<Team>();

	public static GameMode getGameMode() {
		return mode;
	}

	public static List<Team> getTeams() {
		return teams;
	}

	public static Team getTeam(String name) {
		for (Team t : getTeams()) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	public static Team getPlayersTeam(String playerName) {
		for (Team t : getTeams()) {
			if (t.containsPlayer(playerName)) {
				return t;
			}
		}
		return null;
	}

	public static Team getPlayersTeam(Player player) {
		return getPlayersTeam(player.getName());
	}

	public static Set<PlayerData> getPlayerData() {
		return playerData;
	}

	public static PlayerData getPlayerData(String player) {
		for (PlayerData d : getPlayerData()) {
			if (d.getName().equalsIgnoreCase(player)) {
				return d;
			}
		}
		return null;
	}

	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getName());
	}

	public static void addPlayerToTeam(Player player, Team team) {
		addPlayerToTeam(player.getName(), team);
	}

	public static void addPlayerToTeam(String player, Team team) {
		team.addPlayer(player);
		teams.add(team);
	}

	public static void removePlayerFromTeam(Player player, Team team) {
		removePlayerFromTeam(player.getName(), team);
	}

	public static void removePlayerFromTeam(String player, Team team) {
		teams.remove(team);
		team.removePlayer(player);
		teams.add(team);
	}

	private static void createPlayerData(String player) {
		PlayerData data = new PlayerData(player);
		playerData.add(data);
	}

	protected static PListener getPListener() {
		return new PListener();
	}

	public static class PListener extends PlayerListener {
		@Override
		public void onPlayerJoin(PlayerJoinEvent event) {
			createPlayerData(event.getPlayer().getName());
		}
	}

	public static void updateNames() {
		for (int i = 0; i < getTeams().size(); i++) {
			Team t = getTeams().get(i);
			for (String player : t.getPlayers()) {
				Player p = RushMe.getInstance().getServer().getPlayer(player);
				if (p != null) {
					for (Player onlinePlayer : RushMe.getInstance().getServer()
							.getOnlinePlayers()) {
						Team team = getPlayersTeam(onlinePlayer);
						if (team == null) {
							SpoutPlayer spoutP = SpoutManager.getPlayer(p);
							SpoutManager.getAppearanceManager().setPlayerTitle(
									spoutP, onlinePlayer,
									ChatColor.WHITE + onlinePlayer.getName());
						} else if (t.equals(team)) {
							SpoutPlayer spoutP = SpoutManager.getPlayer(p);
							SpoutManager.getAppearanceManager().setPlayerTitle(
									spoutP, onlinePlayer,
									ChatColor.GREEN + onlinePlayer.getName());
						} else {
							SpoutPlayer spoutP = SpoutManager.getPlayer(p);
							SpoutManager.getAppearanceManager().setPlayerTitle(
									spoutP, onlinePlayer,
									ChatColor.RED + onlinePlayer.getName());
						}
					}
				}
			}
		}
	}

	public static void createTeams() {
		Team team1 = new Team("Attackers", 12);
		Team team2 = new Team("Defenders", 12);

		teams.add(team1);
		teams.add(team2);
	}
}