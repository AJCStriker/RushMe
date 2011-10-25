package com.tips48.rushMe;

import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	private static GameMode mode = GameMode.RUSH; // TEMPORARY
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

	protected static PListener getPListener() {
		return new PListener();
	}

	private static class PListener extends PlayerListener {
		public void onPlayerLogin(PlayerLoginEvent event) {
			System.out.println("Activating defaults");
			PlayerData.setDefaults(event.getPlayer());
		}

		public void onPlayerJoin(PlayerJoinEvent event) {
			PlayerData.setActive(event.getPlayer(), true);
			System.out.println("Setting active (true)");
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
						ChatColor color;
						if (team == null) {
							color = ChatColor.WHITE;
						} else {
							color = t.equals(team) ? ChatColor.GREEN : ChatColor.RED;
						}
						SpoutPlayer spoutP = SpoutManager.getPlayer(p);
						SpoutManager.getAppearanceManager().setPlayerTitle(spoutP, onlinePlayer, color + onlinePlayer.getName());
					}
				}
			}
		}
	}

	public static void createTeams() {
		Team team1 = new Team("Attackers", "ATT", 12);
		Team team2 = new Team("Defenders", "DEF", 12);

		teams.add(team1);
		teams.add(team2);
	}
}