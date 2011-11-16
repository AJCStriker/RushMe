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

import java.util.HashSet;
import java.util.Set;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.items.Grenade;
import com.tips48.rushMe.custom.items.GrenadeManager;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;

public class WeaponsHUD extends GenericGradient {

	private final SpoutPlayer player;
	private final Label inClip;
	private final Label seperator;
	private final Label left;
	private final Gradient fullHealth;
	private final Gradient health;
	private final Set<Label> grenadeLabels = new HashSet<Label>();

	protected WeaponsHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);

		this.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		this.setBottomColor(new Color(27, 76, 224, 200));
		this.setTopColor(new Color(27, 76, 224, 200));
		this.setHeight(50);
		this.setWidth(140);
		this.setY(-50);
		this.setX(-140);
		this.setPriority(RenderPriority.High);

		inClip = new GenericLabel();
		inClip.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		inClip.setX(-110);
		inClip.setY(-40);
		inClip.setScale(3F);
		inClip.setPriority(RenderPriority.Low);

		seperator = new GenericLabel();
		seperator.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		seperator.setText("/");
		seperator.setX(-75);
		seperator.setY(-33);
		seperator.setScale(2F);
		seperator.setPriority(RenderPriority.Low);

		left = new GenericLabel();
		left.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		left.setY(-33);
		left.setX(-65);
		left.setScale(2F);
		left.setPriority(RenderPriority.Low);

		fullHealth = new GenericGradient();
		fullHealth.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		fullHealth.setY(-70);
		fullHealth.setX(-140);
		fullHealth.setBottomColor(new Color(27, 76, 224, 200));
		fullHealth.setTopColor(new Color(27, 76, 224, 200));
		fullHealth.setHeight(20);
		fullHealth.setWidth(100);
		fullHealth.setPriority(RenderPriority.Low);

		health = new GenericGradient();
		health.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		health.setX(-140);
		health.setY(-70);
		health.setBottomColor(new Color(0, 0, 0));
		health.setTopColor(new Color(0, 0, 0));
		health.setHeight(20);
		health.setPriority(RenderPriority.Lowest);
		updateHealth();

		// TODO health label

	}

	public void init() {
		int y = 40;
		for (Grenade g : GrenadeManager.getGrenades(player)) {
			Label gLabel = new GenericLabel();
			gLabel.setText(g.getShortName() + " x" + g.getAmount());
			gLabel.setY(-y);
			gLabel.setX(-50);
			gLabel.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
			gLabel.setScale(1.3F);
			gLabel.setPriority(RenderPriority.Low);
			player.getMainScreen().attachWidget(RushMe.getInstance(), gLabel);
			grenadeLabels.add(gLabel);
			System.out.println("Created " + g.getName());
			y -= 10;
		}

		player.getMainScreen().attachWidget(RushMe.getInstance(), inClip);
		player.getMainScreen().attachWidget(RushMe.getInstance(), seperator);
		player.getMainScreen().attachWidget(RushMe.getInstance(), left);
		player.getMainScreen().attachWidget(RushMe.getInstance(), health);
		player.getMainScreen().attachWidget(RushMe.getInstance(), fullHealth);
	}

	public void shutdown() {
		for (Label l : grenadeLabels) {
			player.getMainScreen().removeWidget(l);
		}
		grenadeLabels.clear();

		player.getMainScreen().removeWidget(inClip);
		player.getMainScreen().removeWidget(seperator);
		player.getMainScreen().removeWidget(left);
		player.getMainScreen().removeWidget(health);
		player.getMainScreen().removeWidget(fullHealth);
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

		boolean add0 = inClip < 10;

		String inClipString = Integer.toString(inClip);
		if (add0) {
			inClipString = "0" + inClipString;
		}

		this.inClip.setText(inClipColor + inClipString).setDirty(true);

		this.left.setText(ammoColor + Integer.toString(extraAmmo)).setDirty(
				true);
	}

	public void updateHealth() {
		this.health.setWidth(PlayerData.getHealth(player)).setDirty(true);
	}

	public void updateGrenades() {
		for (Grenade g : GrenadeManager.getGrenades(player)) {
			for (Label l : grenadeLabels) {
				if (l.getText().contains(g.getName())) {
					l.setText(g.getShortName() + " x" + g.getAmount())
							.setDirty(true);
				}
			}
		}
	}

}
