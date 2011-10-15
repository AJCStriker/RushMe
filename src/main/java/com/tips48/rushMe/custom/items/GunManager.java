package com.tips48.rushMe.custom.items;

import java.util.HashSet;
import java.util.Set;

import org.getspout.spoutapi.material.CustomItem;

public class GunManager {
	private Set<Gun> guns = new HashSet<Gun>();

	public Gun createGun(String name, String texture, int reloadTime,
			int maxClipSize, int maxAmmo, FireType type,
			double timeBetweenFire, boolean bulletsExplode,
			Float explosionSize, int headshotDamage, int bodyDamage) {
		Gun gun = new Gun(name, texture, reloadTime, maxClipSize, maxAmmo,
				type, timeBetweenFire, bulletsExplode, explosionSize,
				headshotDamage, bodyDamage);

		guns.add(gun);

		return gun;
	}

	public Set<Gun> getGuns() {
		return guns;
	}

	public Gun getGun(String name) {
		for (Gun g : getGuns()) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}

	public Gun getGun(CustomItem item) {
		for (Gun g : getGuns()) {
			if (item.equals(g)) {
				return g;
			}
		}
		return null;
	}
}
