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

import com.tips48.rushMe.RushMe;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MapHUD extends GenericGradient {

	private final SpoutPlayer player;
	private final Label team1;
	private final Label team2;
	private final Gradient mcomSeperator;

	protected MapHUD(Player player) {
		this.player = SpoutManager.getPlayer(player);

		this.setBottomColor(new Color(27, 76, 224, 200));
		this.setTopColor(new Color(27, 76, 224, 200));
		this.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		this.setHeight(100);
		this.setWidth(70);
		this.setY(-100);
		this.setPriority(RenderPriority.High);

		mcomSeperator = new GenericGradient();
		mcomSeperator.setBottomColor(new Color(0, 0, 0));
		mcomSeperator.setTopColor(new Color(0, 0, 0));
		mcomSeperator.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		mcomSeperator.setY(-75);
		mcomSeperator.setWidth(70);
		mcomSeperator.setHeight(1);
		mcomSeperator.setPriority(RenderPriority.Low);

		team1 = new GenericLabel();
		team1.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		team1.setY(-70);
		team1.setPriority(RenderPriority.Low);

		team2 = new GenericLabel();
		team2.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		team2.setY(-62);
		team2.setPriority(RenderPriority.Low);

		updateTeams();
	}

	public void init() {
		this.player.getMainScreen().attachWidget(RushMe.getInstance(), team1);
		this.player.getMainScreen().attachWidget(RushMe.getInstance(), team2);
		this.player.getMainScreen().attachWidget(RushMe.getInstance(),
				mcomSeperator);
	}

	public void shutdown() {
		player.getMainScreen().removeWidget(team1);
		player.getMainScreen().removeWidget(team2);
		player.getMainScreen().removeWidget(mcomSeperator);
	}

	public void updateTeams() {
		/*
		 * Team team = GameManager.getPlayersTeam(player);
		 * 
		 * String name; String left; ChatColor teamColor;
		 * 
		 * String enemyName; String enemyLeft; ChatColor enemyColor;
		 * 
		 * if (team != null) { name = team.getPrefix(); teamColor =
		 * ChatColor.BLUE; if (team.getInfiniteLives()) { left = "Infinity"; }
		 * else { left = Integer.toString(team.getSpawnsLeft()); } Team other =
		 * null; for (Team t : GameManager.getTeams()) { if (t != team) { other
		 * = t; } } if (other != null) { if (other.getInfiniteLives()) {
		 * enemyLeft = "Infinity"; } else { enemyLeft =
		 * Integer.toString(other.getSpawnsLeft()); } enemyName =
		 * other.getPrefix(); enemyColor = ChatColor.RED; } else { enemyLeft =
		 * "Unknown"; enemyName = "Unknown"; enemyColor = ChatColor.WHITE; } }
		 * else { name = "Unknown"; teamColor = ChatColor.WHITE; enemyName =
		 * "Unknown"; enemyColor = ChatColor.WHITE; enemyLeft = "Unknown"; left
		 * = "Unknown"; }
		 * 
		 * team1.setText(teamColor + name + ChatColor.WHITE + "           " +
		 * left) .setDirty(true); team2.setText( enemyColor + enemyName +
		 * ChatColor.WHITE + "           " + enemyLeft).setDirty(true);
		 */
	}

}
