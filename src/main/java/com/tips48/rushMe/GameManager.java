package com.tips48.rushMe;

import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.getspout.spoutapi.SpoutManager;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

	private static Set<Arena> games = new HashSet<Arena>();
	private static Set<GameMode> gameModes = new HashSet<GameMode>();
	private static GameMode defaultGameMode = null;

	public static void addToGame(Arena arena, Player player) {
		addToGame(arena, player.getName());
	}

	public static void addToGame(Arena arena, String player) {
		arena.addPlayer(player);
		Player p = RushMe.getInstance().getServer().getPlayer(player);
		if (p != null) {
			arena.handlePlayerJoin(p);
		}
	}

	public static void removeFromGame(Arena arena, Player player) {
		removeFromGame(arena, player.getName());
	}

	public static void removeFromGame(Arena arena, String player) {
		if (arena.hasPlayer(player)) {
			arena.removePlayer(player);
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				arena.handlePlayerLeave(p);
			}
		}
	}

	public static boolean inGame(Player player) {
		return inGame(player.getName());
	}

	public static boolean inGame(String player) {
		for (Arena a : games) {
			for (String s : a.getPlayers()) {
				if (s.equals(player)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Arena getPlayerArena(Player player) {
		return getPlayerArena(player.getName());
	}

	public static Arena getPlayerArena(String player) {
		for (Arena a : games) {
			if (a.hasPlayer(player)) {
				return a;
			}
		}
		return null;
	}

	public static Arena getArena(String name) {
		for (Arena a : games) {
			if (a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	public static void removeArena(Arena a) {
		a.stop();
		games.remove(a);
	}

	public static Arena createArena(String name, GameMode gamemode) {
		Arena a = new Arena(gamemode, name);
		games.add(a);
		return a;
	}

	public static GameMode createGameMode(String name, GameModeType type,
			Integer time, Boolean respawn, Integer respawnTime) {
		GameMode gm = new GameMode(name, type, time, respawn, respawnTime);

		gameModes.add(gm);

		return gm;
	}

	public static GameMode getGameMode(String name) {
		for (GameMode g : gameModes) {
			if (g.getName().equalsIgnoreCase(name)) {
				return g;
			}
		}
		return null;
	}

	public static Set<String> getGameModeNames() {
		Set<String> list = new HashSet<String>();
		for (GameMode g : gameModes) {
			list.add(g.getName());
		}
		return list;
	}

	public static GameMode getDefaultGameMode() {
		return defaultGameMode;
	}

	public static void setDefaultGameMode(GameMode g) {
		if (defaultGameMode == null) {
			defaultGameMode = g;
		}
	}

	protected static PListener getPListener() {
		return new PListener();
	}

	private static class PListener extends PlayerListener {
		public void onPlayerLogin(PlayerLoginEvent event) {
			PlayerData.setDefaults(event.getPlayer());
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
							color = t.equals(team) ? ChatColor.GREEN
									: ChatColor.RED;
						}
						SpoutManager.getAppearanceManager().setPlayerTitle(
								SpoutManager.getPlayer(p), onlinePlayer,
								color + onlinePlayer.getName());
					}
				}
			}
		}
	}
}