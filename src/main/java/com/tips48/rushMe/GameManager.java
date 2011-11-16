package com.tips48.rushMe;

import com.tips48.rushMe.custom.items.GrenadeManager;
import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.getspout.spoutapi.SpoutManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameManager {

	private static Set<Arena> games = new HashSet<Arena>();
	private static Set<GameMode> gameModes = new HashSet<GameMode>();
	private static GameMode defaultGameMode = null;

	private static Set<Arena> notDone = new HashSet<Arena>();

	public static void addToGame(Arena arena, Player player, Team prefered) {
		addToGame(arena, player.getEntityId(), prefered);
	}

	public static void addToGame(Arena arena, int player, Team prefered) {
		GrenadeManager.createGrenades(player);
		arena.addPlayer(player, prefered);
	}

	public static void removeFromGame(Arena arena, Player player) {
		removeFromGame(arena, player.getEntityId());
	}

	public static void removeFromGame(Arena arena, int player) {
		if (arena.hasPlayer(player)) {
			arena.removePlayer(player);
		}
	}

	public static boolean inGame(Player player) {
		return inGame(player.getEntityId());
	}

	public static boolean inGame(int player) {
		for (Arena a : games) {
			for (int s : a.getPlayers().toArray()) {
				if (s == player) {
					return true;
				}
			}
		}
		return false;
	}

	public static Arena getPlayerArena(Player player) {
		return getPlayerArena(player.getEntityId());
	}

	public static Arena getPlayerArena(int player) {
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
		for (int player : a.getPlayers().toArray()) {
			a.removePlayer(player);
		}
		games.remove(a);
	}

	public static Arena createArena(String name, GameMode gamemode, int creator) {
		for (Arena a : notDone) {
			if (a.getCreator() == creator) {
				return null;
			}
		}
		Arena a = new Arena(gamemode, name, creator);
		notDone.add(a);
		return a;
	}

	public static boolean creatingArena(int player) {
		boolean creating = false;
		for (Arena a : notDone) {
			if ((!(creating)) && (!(a.getCompleted()))
					&& (a.getCreator() == player)) {
				creating = true;
			}
		}
		return creating;
	}

	public static boolean creatingArena(Player player) {
		return creatingArena(player.getEntityId());
	}

	public static Arena getCreatingArena(Player player) {
		return getCreatingArena(player.getEntityId());
	}

	public static Arena getCreatingArena(int player) {
		for (Arena a : notDone) {
			if (!a.getCompleted() && a.getCreator() == player) {
				return a;
			}
		}
		return null;
	}

	protected static void addArena(Arena a) {
		games.add(a);
		notDone.remove(a);
	}

	public static GameMode createGameMode(String name, GameModeType type,
			Integer time, Boolean respawn, Integer respawnTime,
			Integer maxPlayers, List<Team> teams) {
		GameMode gm = new GameMode(name, type, time, respawn, respawnTime,
				maxPlayers, teams);

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

	public static Set<String> getArenaNames() {
		Set<String> names = new HashSet<String>();
		for (Arena a : games) {
			names.add(a.getName());
		}
		return names;
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
			for (int player : t.getPlayers().toArray()) {
				Player p = SpoutManager.getPlayerFromId(player);
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