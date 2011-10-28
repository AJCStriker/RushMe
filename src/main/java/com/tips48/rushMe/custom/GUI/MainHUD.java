package com.tips48.rushMe.custom.GUI;

import com.tips48.rushMe.RushMe;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MainHUD {

	private final SpoutPlayer player;
	private final InGameHUD hud;
	private final MapHUD mHud;
	private final WeaponsHUD wHud;

	private boolean active;

	public MainHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);
		hud = this.player.getMainScreen();
		mHud = new MapHUD(this.player);
		wHud = new WeaponsHUD(this.player);
	}

	public void init() {

		if (isActive()) {
			return;
		}

		hud.getArmorBar().setVisible(false);
		hud.getBubbleBar().setVisible(false);
		hud.getHungerBar().setVisible(false);
		hud.getExpBar().setVisible(false);
		hud.getHealthBar().setVisible(false);

		mHud.init();

		wHud.init();

		hud.attachWidget(RushMe.getInstance(), mHud);
		hud.attachWidget(RushMe.getInstance(), wHud);

		updateHUD();

		active = true;
	}

	public void shutdown() {

		if (!isActive()) {
			return;
		}

		hud.getArmorBar().setVisible(true);
		hud.getBubbleBar().setVisible(true);
		hud.getHungerBar().setVisible(true);
		hud.getExpBar().setVisible(true);
		hud.getHealthBar().setVisible(true);

		mHud.shutdown();
		hud.removeWidget(mHud);
		wHud.shutdown();
		hud.removeWidget(wHud);

		active = false;
	}

	public void updateHUD() {
		wHud.updateAmmo();
		mHud.updateTeams();
		wHud.updateHealth();
	}

	public boolean isActive() {
		return active;
	}

}
