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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.RushMe;

public class KillFeedQueue {

	private final SpoutPlayer player;

	private List<Widget> onScreenQueue;

	private int maxOnScreen;

	public KillFeedQueue(Player player, int maxOnScreen) {
		this.player = SpoutManager.getPlayer(player);
		this.maxOnScreen = maxOnScreen;

		this.onScreenQueue = new ArrayList<Widget>();
	}

	public void addToQueue(String s) {
		if (onScreenQueue.size() == maxOnScreen) {
			remove(onScreenQueue.get(0));
			moveAllDown(10);
		}
		final Label l = new GenericLabel();
		int y = 4;
		for (int i = onScreenQueue.size(); i >= 0; i--) {
			y = y + 10;
		}
		l.setY(y);
		l.setAnchor(WidgetAnchor.TOP_RIGHT);
		l.setText(s);
		l.setX(GenericLabel.getStringWidth(s) + 4);

		onScreenQueue.add(l);
		player.getMainScreen().attachWidget(RushMe.getInstance(), l);

		RushMe.getInstance().getServer().getScheduler()
				.scheduleSyncDelayedTask(RushMe.getInstance(), new Runnable() {
					public void run() {
						remove(l);
					}
				}, 3000);
	}

	private void moveAllDown(int by) {
		for (Widget w : onScreenQueue) {
			w.setY(w.getY() - by).setDirty(true);
		}
	}

	private void remove(Widget w) {
		if (onScreenQueue.contains(w)) {
			player.getMainScreen().removeWidget(w);
			onScreenQueue.remove(w);
		}
	}

}