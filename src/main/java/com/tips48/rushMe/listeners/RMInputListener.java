package com.tips48.rushMe.listeners;

import java.util.HashSet;
import java.util.Set;

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
				if (GameManager.getPlayerArena(ply).getPlayerTeam(ply).equals(playerTeam)) {
					playersNotOnTeam.add(ply);
				}
			}
			for (Player player : playersNotOnTeam) {
				PlayerData.setSpotted(player, true);
			}
		}
	}
}