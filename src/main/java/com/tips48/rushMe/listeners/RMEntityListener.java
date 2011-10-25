package com.tips48.rushMe.listeners;

import com.tips48.rushMe.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RMEntityListener extends EntityListener {

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (e instanceof Player) {
			if (!(PlayerData.isActive((Player) e))) {
				return;
			}
			PlayerData.damage((Player) e, event.getDamage());
			event.setCancelled(true);
			if (PlayerData.getHealth((Player) e) <= 0) {
				((Player) e).setHealth(0);
			}
		}
	}

	@Override
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (e instanceof Player) {
			event.setCancelled(true);
		}

		// TODO health regain
		/*
				   * if(!(PlayerData.isActive((Player) e))) { return; }
				   * PlayerData.heal((Player) e, event.getAmount() * 5);
				   * event.setCancelled(true); }
				   */
	}

}
