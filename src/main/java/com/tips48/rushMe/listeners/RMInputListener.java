package com.tips48.rushMe.listeners;

import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

import org.bukkit.entity.Player;

import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.util.RMUtils;

public class RMInputListener extends InputListener {
	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		if (event.getKey().equals(Keyboard.KEY_R)) {
			Player p = event.getPlayer();
			if (RMUtils.holdingGun(p)) {
				Gun g = RMUtils.getGun(p);
				g.reload(p);
			}
		}
	}
}