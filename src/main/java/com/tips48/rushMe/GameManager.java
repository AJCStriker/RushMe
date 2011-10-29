package com.tips48.rushMe;

import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.getspout.spoutapi.SpoutManager;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

	private static Map<String, Arena> games = new HashMap<String, Arena>();

	public static void addToGame(Arena arena, Player player) {
		addToGame(arena, player.getName());
	}

	public static void addToGame(Arena arena, String player) {
		games.put(player, arena);
	}

	public static void removeFromGame(Arena arena, Player player) {
		removeFromGame(arena, player.getName());
	}

	public static void removeFromGame(Arena arena, String player) {
		if (games.containsKey(player)) {
			games.remove(player);
		}
	}

	public static Arena getArena(Player player) {
		return getArena(player.getName());
	}

	public static Arena getArena(String player) {
	if (games.containsKey(player)) {
		return games.get(player);
	}
		return null;
	}

	protected static PListener getPListener() {
		return new PListener();
	}

	private static class PListener extends PlayerListener {
		public void onPlayerLogin(PlayerLoginEvent event) {
			PlayerData.setDefaults(event.getPlayer());
		}

		public void onPlayerJoin(PlayerJoinEvent event) {
			PlayerData.setActive(event.getPlayer(), true);
		}
	}

	public static void updateNames(Arena arena) {
		for (int i = 0; i <= arena.getTeams().size(); i++) {
			Team t = arena.getTeams().get(i);
			for (String player : t.getPlayers()) {
				Player p = RushMe.getInstance().getServer().getPlayer(player);
				if (p != null) {
					for (Player onlinePlayer : RushMe.getInstance().getServer()
							.getOnlinePlayers()) {
						if (onlinePlayer == p) {
							continue;
						}
						Team team = arena.getPlayerTeam(onlinePlayer);
						ChatColor color = ChatColor.WHITE;
						if (team != null) {
							color = t.equals(team) ? ChatColor.GREEN : ChatColor.RED;
						}
						SpoutManager.getAppearanceManager().setPlayerTitle(SpoutManager.getPlayer(p), onlinePlayer, color + onlinePlayer.getName());
					}
				}
			}
		}
	}
}