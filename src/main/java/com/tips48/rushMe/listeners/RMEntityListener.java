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
		if (!(e instanceof Player)) {
			return;
		}
		PlayerData.damage((Player) e, event.getDamage());
		event.setCancelled(true);
		if (PlayerData.getHealth((Player) e) <= 0) {
			((Player) e).setHealth(0);
		}
	}

	@Override
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity e = event.getEntity();
		if (!(e instanceof Player)) {
			return;
		}

		// TODO custom health regain
		/*
		 * if(!(PlayerData.isActive((Player) e))) { return; }
		 * PlayerData.heal((Player) e, event.getAmount() * 5);
		 * event.setCancelled(true); }
		 */
	}

}
