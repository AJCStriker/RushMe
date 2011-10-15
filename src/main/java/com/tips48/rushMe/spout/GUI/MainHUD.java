package com.tips48.rushMe.spout.GUI;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.RushMe;

public class MainHUD {

	private SpoutPlayer player;
	private InGameHUD hud;
	private WeaponsHUD wHud;

	public MainHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);
		hud = this.player.getMainScreen();

		hud.getArmorBar().setVisible(false);
		hud.getBubbleBar().setVisible(false);
		hud.getHungerBar().setVisible(false);
		hud.getExpBar().setVisible(false);
		hud.getHealthBar().setVisible(false);

		wHud = new WeaponsHUD(this.player);

		hud.attachWidget(RushMe.getInstance(), wHud);

	}

	public void updateHUD() {
		wHud.updateAmmo();
		wHud.updateTeams();
	}

}
