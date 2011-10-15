package com.tips48.rushMe.spout.GUI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;

public class WeaponsHUD extends GenericGradient {

	private SpoutPlayer player;
	private GenericLabel ammo;
	private GenericLabel team1;
	private GenericLabel team2;

	public WeaponsHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);

		this.setBottomColor(new Color(27, 76, 224, 200));
		this.setTopColor(new Color(27, 76, 224, 200));
		this.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		this.setHeight(50);
		this.setWidth(140);
		this.setY(-50);
		this.setPriority(RenderPriority.High);

		ammo = new GenericLabel();
		ammo.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		ammo.setY(-8);
		ammo.setPriority(RenderPriority.Low);
		updateAmmo();

		team1 = new GenericLabel();
		team1.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		team1.setY(-45);
		team1.setPriority(RenderPriority.Low);

		team2 = new GenericLabel();
		team2.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		team2.setY(-45);
		team2.setX(70);
		team2.setPriority(RenderPriority.Low);

		updateTeams();

		this.player.getMainScreen().attachWidget(RushMe.getInstance(), ammo);
		this.player.getMainScreen().attachWidget(RushMe.getInstance(), team1);
		this.player.getMainScreen().attachWidget(RushMe.getInstance(), team2);
	}

	public void updateAmmo() {
		int inClip;
		int extraAmmo;

		if (!RMUtils.holdingGun(player)) {
			inClip = 0;
			extraAmmo = 0;
		} else {
			Gun g = RMUtils.getGun(player);
			inClip = g.getLoadedInClip();
			extraAmmo = g.getAmmo();
		}

		ChatColor inClipColor = inClip > 0 ? ChatColor.WHITE : ChatColor.RED;
		ChatColor ammoColor = extraAmmo > 0 ? ChatColor.WHITE : ChatColor.RED;

		ammo.setText(
				"Ammo: " + inClipColor + Integer.toString(inClip)
						+ ChatColor.WHITE + "/" + ammoColor
						+ Integer.toString(extraAmmo)).setDirty(true);
	}

	public void updateTeams() {
		Team team = GameManager.getPlayersTeam(player);

		String name;
		String left;
		ChatColor teamColor;

		String enemyName;
		String enemyLeft;
		ChatColor enemyColor;

		if (team != null) {
			name = team.getName();
			teamColor = ChatColor.BLUE;
			if (team.getInfiniteLives()) {
				left = "Infinity";
			} else {
				left = Integer.toString(team.getSpawnsLeft());
			}
			Team other = null;
			for (Team t : GameManager.getTeams()) {
				if (t != team) {
					other = t;
				}
			}
			if (other != null) {
				if (other.getInfiniteLives()) {
					enemyLeft = "Infinity";
				} else {
					enemyLeft = Integer.toString(other.getSpawnsLeft());
				}
				enemyName = other.getName();
				enemyColor = ChatColor.RED;
			} else {
				enemyLeft = "Unknown";
				enemyName = "Unknown";
				enemyColor = ChatColor.WHITE;
			}
		} else {
			name = "Unknown";
			teamColor = ChatColor.WHITE;
			enemyName = "Unknown";
			enemyColor = ChatColor.WHITE;
			enemyLeft = "Unknown";
			left = "Unknown";
		}

		team1.setText(teamColor + name + ChatColor.WHITE + ": " + left)
				.setDirty(true);
		team2.setText(
				enemyColor + enemyName + ChatColor.WHITE + ": " + enemyLeft)
				.setDirty(true);
	}

}
