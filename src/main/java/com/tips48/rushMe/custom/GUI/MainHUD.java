package com.tips48.rushMe.custom.GUI;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MainHUD {

	private final SpoutPlayer player;
	private final InGameHUD hud;
	private final MapHUD mHud;
	private final WeaponsHUD wHud;
	private final Label timeLabel;
	private final PointQueue pQueue;
	private final KillFeedQueue kQueue;

	private int schedulerId;

	private boolean active;

	public MainHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);
		hud = this.player.getMainScreen();
		mHud = new MapHUD(this.player);
		wHud = new WeaponsHUD(this.player);
		pQueue = new PointQueue(this.player, 4);
		kQueue = new KillFeedQueue(this.player, 4);
		timeLabel = new GenericLabel();
		timeLabel.setScale(1.3F);
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

		player.getMainScreen().attachWidget(RushMe.getInstance(), timeLabel);

		schedulerId = RushMe
				.getInstance()
				.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(RushMe.getInstance(),
						new Runnable() {
							public void run() {
								if (GameManager.inGame(player)) {
									Arena a = GameManager
											.getPlayerArena(player);
									if (a.isStarted()) {
										timeLabel
												.setText(
														"Time left: "
																+ RMUtils
																		.parseIntForMinute(a
																				.getTimeLeft()))
												.setDirty(true);
									} else {
										timeLabel
												.setText(
														"Time till start: "
																+ a.getTimeBeforeStart())
												.setDirty(true);
									}
								}
							}
						}, 0, 20);

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

		RushMe.getInstance().getServer().getScheduler().cancelTask(schedulerId);
		hud.removeWidget(timeLabel);
		schedulerId = 0;

		active = false;
	}

	public void updateHUD() {
		if (!(active)) {
			return;
		}
		wHud.updateAmmo();
		mHud.updateTeams();
		wHud.updateHealth();
		wHud.updateGrenades();
	}

	public PointQueue getPointQueue() {
		return pQueue;
	}

	public KillFeedQueue getKillFeedQueue() {
		return kQueue;
	}

	public boolean isActive() {
		return active;
	}

}
