package com.tips48.rushMe.listeners;

import com.tips48.rushMe.custom.GUI.Scoreboard;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.util.RMUtils;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class RMInputListener extends InputListener {
	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		Player p = event.getPlayer();
		if (event.getKey().equals(Keyboard.KEY_R)) {
			if (RMUtils.holdingGun(p)) {
				Gun g = RMUtils.getGun(p);
				g.reload(p);

				// TODO show reloading
			}
		} else if (event.getKey().equals(Keyboard.KEY_TAB)) {
			if (Scoreboard.hasScoreboardOpen(SpoutManager.getPlayer(p))) {
				Scoreboard.remove(SpoutManager.getPlayer(p));
			} else {
				Scoreboard.draw(SpoutManager.getPlayer(p));
			}
		}
	}
}