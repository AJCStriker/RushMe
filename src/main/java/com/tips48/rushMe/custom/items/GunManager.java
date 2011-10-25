package com.tips48.rushMe.custom.items;

import org.getspout.spoutapi.material.CustomItem;

import java.util.HashSet;
import java.util.Set;

public class GunManager {
	private Set<Gun> guns = new HashSet<Gun>();

	public Gun createGun(String name, String texture, Integer reloadTime,
	                     Boolean autoReload, Integer maxClipSize, Integer maxAmmo,
	                     Double timeBetweenFire, Boolean bulletsExplode,
	                     Float explosionSize, Double entityExplosionRadius,
	                     Integer headshotDamage, Integer bodyDamage, Double recoilBack,
	                     Float recoilVertical, Float recoilHorizontal) {

		Gun gun = new Gun(name, texture, reloadTime, autoReload, maxClipSize,
				maxAmmo, timeBetweenFire, bulletsExplode, explosionSize,
				entityExplosionRadius, headshotDamage, bodyDamage, recoilBack,
				recoilVertical, recoilHorizontal);

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

	public Set<String> getGunNames() {
		Set<String> names = new HashSet<String>();
		for (Gun g : getGuns()) {
			names.add(g.getName());
		}
		return names;
	}
}
