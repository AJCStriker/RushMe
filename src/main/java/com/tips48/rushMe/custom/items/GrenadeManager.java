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

package com.tips48.rushMe.custom.items;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.material.CustomItem;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.HashSet;
import java.util.Set;

public class GrenadeManager {

	private static final Set<Grenade> grenades = new HashSet<Grenade>();

	private static final TIntObjectMap<Set<Grenade>> playerGrenades = new TIntObjectHashMap<Set<Grenade>>();

	private GrenadeManager() {

	}

	/**
	 * Creates a gun with the specified specifications
	 * 
	 * @param name
	 *            Name of grenade
	 * @param shortName
	 *            short name of grenade
	 * @param texture
	 *            Online texture URL
	 * @param type
	 *            Type of grenade
	 * @param startAmount
	 *            Default amount of grenades started with by the player
	 * @param explosionSize
	 *            Size of explosion
	 */
	public static Grenade createGrenade(String name, String shortName,
			String texture, GrenadeType type, Integer startAmount,
			Integer explosionSize, Integer timeBeforeExplosion, Integer damage,
			Integer stunTime) {

		Grenade grenade = new Grenade(name, shortName, texture, type,
				startAmount, explosionSize, timeBeforeExplosion, damage,
				stunTime);

		grenades.add(grenade);

		return grenade;
	}

	/**
	 * Gets a set with each gun object
	 * 
	 * @return set with each gun object
	 */
	public static Set<Grenade> getGrenades() {
		return grenades;
	}

	/**
	 * Gets a grenade with the specified name
	 * 
	 * @param name
	 *            Name of grenade
	 * @return {@link Grenade} with the specified name
	 */
	public static Grenade getGrenade(String name) {
		for (Grenade g : getGrenades()) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}

	/**
	 * Gets a grenade from a CustomItem
	 * 
	 * @param item
	 *            {@link CustomItem} object
	 * @return {@link Grenade} from the specified object
	 */
	public static Grenade getGrenade(CustomItem item) {
		for (Grenade g : getGrenades()) {
			if (item.equals(g)) {
				return g;
			}
		}
		return null;
	}

	/**
	 * Gets a set with each grenade's name
	 * 
	 * @return a set with each grenade name
	 */
	public static Set<String> getGrenadeNames() {
		Set<String> names = new HashSet<String>();
		for (Grenade g : getGrenades()) {
			names.add(g.getName());
		}
		return names;
	}

	public static Set<Grenade> getGrenades(Player player) {
		return getGrenades(player.getEntityId());
	}

	public static Set<Grenade> getGrenades(int player) {
		return playerGrenades.get(player);
	}

	public static void createGrenades(int player) {
		playerGrenades.put(player, grenades);
	}

	public static void createGrenades(Player player) {
		createGrenades(player.getEntityId());
	}

}
