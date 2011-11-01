package com.tips48.rushMe.custom.items;

import org.getspout.spoutapi.material.CustomItem;

import java.util.HashSet;
import java.util.Set;

public class GunManager {
	private final Set<Gun> guns = new HashSet<Gun>();

	/**
	 * Creates a gun with the specified specifications
	 * @param name Name of gun
	 * @param texture Online texture URL
	 * @param reloadTime Time between reloads
	 * @param autoReload If gun auto reloads
	 * @param maxClipSize Max clip size
	 * @param maxAmmo Max ammo of gun
	 * @param timeBetweenFire Time between firing
	 * @param bulletsExplode If Bullets explode
	 * @param explosionSize Size of explosion (Can be null if bullets don't explode)
	 * @param entityDamageDistance Distance in which entities get damaged by explosions (Can be null if bullets don't explode)
	 * @param headshotDamage Damage of a head shot (Can be null if bullets explode)
	 * @param bodyDamage Damage of a body shot (Can be null if bullets explode)
	 * @param recoilBack Recoil moving the player back (Negative if the player should move forward)
	 * @param recoilVertical Recoil moving the player's gun up (Negative if the player's gun should go down)
	 * @param recoilHorizontal Recoil moving the player's gun to the right (Negative if the player's gun should go to the left)
	 * @return {@link Gun} object with the specified specifications
	 */
	public Gun createGun(String name, String texture, Integer reloadTime,
	                     Boolean autoReload, Integer maxClipSize, Integer maxAmmo,
	                     Double timeBetweenFire, Boolean bulletsExplode,
	                     Float explosionSize, Double entityDamageDistance,
	                     Integer headshotDamage, Integer bodyDamage, Double recoilBack,
	                     Float recoilVertical, Float recoilHorizontal) {

		Gun gun = new Gun(name, texture, reloadTime, autoReload, maxClipSize,
				maxAmmo, timeBetweenFire, bulletsExplode, explosionSize,
				entityDamageDistance, headshotDamage, bodyDamage, recoilBack,
				recoilVertical, recoilHorizontal);

		guns.add(gun);

		return gun;
	}

	/**
	 * Gets a set with each gun object
	 * @return set with each gun object
	 */
	public Set<Gun> getGuns() {
		return guns;
	}

	/**
	 * Gets a gun with the specified name
	 * @param name Name of gun
	 * @return {@link Gun} with the specified name
	 */
	public Gun getGun(String name) {
		for (Gun g : getGuns()) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}

	/**
	 * Gets a gun from a CustomItem
	 * @param item {@link CustomItem} object
	 * @return {@link Gun} from the specified object
	 */
	public Gun getGun(CustomItem item) {
		for (Gun g : getGuns()) {
			if (item.equals(g)) {
				return g;
			}
		}
		return null;
	}

	/**
	 * Gets a set with each gun's name
	 * @return a set with each guns name
	 */
	public Set<String> getGunNames() {
		Set<String> names = new HashSet<String>();
		for (Gun g : getGuns()) {
			names.add(g.getName());
		}
		return names;
	}
}
