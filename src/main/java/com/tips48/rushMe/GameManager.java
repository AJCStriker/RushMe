package com.tips48.rushMe;

import com.tips48.rushMe.data.PlayerData;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

public class GameManager {

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

/*	public static void updateNames() {
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
	}     */
}