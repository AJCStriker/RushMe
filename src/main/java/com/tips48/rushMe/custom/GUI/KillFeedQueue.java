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