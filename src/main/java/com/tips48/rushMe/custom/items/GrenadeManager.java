package com.tips48.rushMe.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.material.CustomItem;

/**
 * Created by IntelliJ IDEA. User: aidan Date: 11/6/11 Time: 6:27 PM To change
 * this template use File | Settings | File Templates.
 */
public class GrenadeManager {
	
	private static final Set<Grenade> grenades = new HashSet<Grenade>();
	
	private static final Map<String, Set<Grenade>> playerGrenades = new HashMap<String, Set<Grenade>>();
	/**
	 * Creates a gun with the specified specifications
	 * 
	 * @param name
	 *            Name of gun
	 * @param texture
	 *            Online texture URL
	 *  @param type
	 *  			Type of grenade
	 *  @param startAmount
	 *  			Default amount of grenades started with by the player
	 *  @param explosionSize 
	 *  			Size of explosion
	 */
	public static void createGrenade(String name, String texture, GrenadeType type,Integer startAmount, Integer explosionSize, Integer timeBeforeExplosion, Integer damage, Integer stunTime) {

		Grenade grenade = new Grenade(name, texture, type, startAmount, explosionSize, timeBeforeExplosion, damage, stunTime);

		grenades.add(grenade);
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
	 * Gets a gun with the specified name
	 * 
	 * @param name
	 *            Name of gun
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
	 * Gets a set with each gun's name
	 * 
	 * @return a set with each guns name
	 */
	public static Set<String> getGrenadeNames() {
		Set<String> names = new HashSet<String>();
		for (Grenade g : getGrenades()) {
			names.add(g.getName());
		}
		return names;
	}
	
	public static Set<Grenade> getGrenades(Player player) {
		return getGrenades(player.getName());
	}
	
	public static Set<Grenade> getGrenades(String player) {
		return playerGrenades.get(player);
	}

	public static void createGrenades(String player) {
		playerGrenades.put(player, grenades);
	}
	
	public static void createGrenades(Player player) {
		createGrenades(player.getName());
	}
	
}
