package com.tips48.rushMe.custom.GUI;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.teams.Team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.SpoutManager;

import java.util.HashMap;
import java.util.Map;

public class SpoutGUI {

	private static final Map<String, MainHUD> huds = new HashMap<String, MainHUD>();

	private SpoutGUI() {

	}

	public static void showKill(Player killer, Player killed,
			Gun weapon) {
		Arena a = GameManager.getPlayerArena(killer);
		Team pTeam = a.getPlayerTeam(killer);
		Team other = null;
		for (Team t :a.getTeams()) {
			if (t != pTeam) {
				other = t;
			}
		}
		for (int i : pTeam.getPlayers().toArray()) {
			Player p = SpoutManager.getPlayerFromId(i);
			if (p != null) {
				MainHUD hud = getHudOf(p);
				if (hud != null) {
					hud.getKillFeedQueue().addToQueue(ChatColor.GREEN + killer.getDisplayName() + ChatColor.WHITE + "[" + weapon.getName() + "]" + ChatColor.RED + killed.getDisplayName());
				}
			}
		}
		for (int i : other.getPlayers().toArray()) {
			Player p = SpoutManager.getPlayerFromId(i);
			if (p != null) {
				MainHUD hud = getHudOf(p);
				if (hud != null) {
					hud.getKillFeedQueue().addToQueue(ChatColor.RED + killer.getDisplayName() + ChatColor.WHITE + "[" + weapon.getName() + "]" + ChatColor.GREEN + killed.getDisplayName());
				}
			}
		}
	}

	public static MainHUD getHudOf(Player player) {
		return getHudOf(player.getName());
	}

	public static MainHUD getHudOf(String player) {
		if (huds.containsKey(player)) {
			return huds.get(player);
		}
		return null;
	}

	public static PListener getPListener() {
		return new PListener();
	}

	private static class PListener extends PlayerListener {
		@Override
		public void onPlayerJoin(PlayerJoinEvent event) {
			MainHUD hud = new MainHUD(event.getPlayer());
			huds.put(event.getPlayer().getName(), hud);
		}
	}
}
