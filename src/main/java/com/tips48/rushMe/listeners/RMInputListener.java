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

package com.tips48.rushMe.listeners;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.GUI.Scoreboard;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

import java.util.HashSet;
import java.util.Set;

public class RMInputListener extends InputListener {
	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		Player p = event.getPlayer();
		if (!(GameManager.inGame(p))) {
			return;
		}
		Keyboard key = event.getKey();
		if (key.equals(Keyboard.KEY_R)) {
			if (RMUtils.holdingGun(p)) {
				Gun g = RMUtils.getGun(p);
				g.reload(p);

				// TODO show reloading
			}
		} else if (key.equals(Keyboard.KEY_TAB)) {
			if (Scoreboard.hasScoreboardOpen(SpoutManager.getPlayer(p))) {
				Scoreboard.remove(SpoutManager.getPlayer(p));
			} else {
				Scoreboard.draw(SpoutManager.getPlayer(p));
			}
		} else if (key.equals(Keyboard.KEY_Q)) {
			Set<String> playersSpotted = RMUtils.spot(p);
			Set<Player> players = new HashSet<Player>();
			for (String s : playersSpotted) {
				Player pl = RushMe.getInstance().getServer().getPlayer(s);
				if (pl != null) {
					players.add(pl);
				}
			}
			Set<Player> playersNotOnTeam = new HashSet<Player>();
			Team playerTeam = GameManager.getPlayerArena(p).getPlayerTeam(p);
			for (Player ply : players) {
				if (!(GameManager.inGame(ply))) {
					continue;
				}
				if (GameManager.getPlayerArena(ply).getPlayerTeam(ply)
						.equals(playerTeam)) {
					playersNotOnTeam.add(ply);
				}
			}
			for (Player player : playersNotOnTeam) {
				PlayerData.setSpotted(player, true);
			}
		}
	}
}