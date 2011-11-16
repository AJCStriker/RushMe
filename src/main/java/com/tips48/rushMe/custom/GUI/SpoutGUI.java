/*
 * This file is part of RushMe.
 *
 * RushMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RushMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class SpoutGUI {

	private static final TIntObjectMap<MainHUD> huds = new TIntObjectHashMap<MainHUD>();

	private SpoutGUI() {

	}

	public static void showKill(Player killer, Player killed, Gun weapon) {
		Arena a = GameManager.getPlayerArena(killer);
		Team pTeam = a.getPlayerTeam(killer);
		Team other = null;
		for (Team t : a.getTeams()) {
			if (t != pTeam) {
				other = t;
			}
		}
		for (int i : pTeam.getPlayers().toArray()) {
			Player p = SpoutManager.getPlayerFromId(i);
			if (p != null) {
				MainHUD hud = getHudOf(p);
				if (hud != null) {
					hud.getKillFeedQueue().addToQueue(
							ChatColor.GREEN + killer.getDisplayName()
									+ ChatColor.WHITE + "[" + weapon.getName()
									+ "]" + ChatColor.RED
									+ killed.getDisplayName());
				}
			}
		}
		for (int i : other.getPlayers().toArray()) {
			Player p = SpoutManager.getPlayerFromId(i);
			if (p != null) {
				MainHUD hud = getHudOf(p);
				if (hud != null) {
					hud.getKillFeedQueue().addToQueue(
							ChatColor.RED + killer.getDisplayName()
									+ ChatColor.WHITE + "[" + weapon.getName()
									+ "]" + ChatColor.GREEN
									+ killed.getDisplayName());
				}
			}
		}
	}

	public static MainHUD getHudOf(Player player) {
		return getHudOf(player.getEntityId());
	}

	public static MainHUD getHudOf(int player) {
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
			huds.put(event.getPlayer().getEntityId(), hud);
		}
	}
}
