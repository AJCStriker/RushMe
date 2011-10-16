package com.tips48.rushMe;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.spout.GUI.MainHUD;

public class SpoutGUI {

	private static Map<String, MainHUD> huds = new HashMap<String, MainHUD>();

	@Deprecated
	public static void showKill(Player killer, LivingEntity killed,
			String weapon) {
		final SpoutPlayer sp = SpoutManager.getPlayer(killer);
		final Label l = new GenericLabel();
		l.setAnchor(WidgetAnchor.BOTTOM_CENTER);
		l.setAlign(WidgetAnchor.CENTER_CENTER);
		l.setY(-45);
		String killedName = null;
		if (killed instanceof Player) {
			killedName = ((Player) killed).getDisplayName();
		} else {
			killedName = killed.getClass().getInterfaces()[0].getSimpleName();
		}
		l.setText(ChatColor.GREEN + killer.getName() + ChatColor.WHITE + " ["
				+ weapon + "] " + ChatColor.RED + killedName);
		sp.getMainScreen().attachWidget(RushMe.getInstance(), l);
		RushMe.getInstance().getServer().getScheduler()
				.scheduleSyncDelayedTask(RushMe.getInstance(), new Runnable() {
					public void run() {
						sp.getMainScreen().removeWidget(l);
					}
				}, 20 * 5);
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

	protected static PListener getPListener() {
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
