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

package com.tips48.rushMe.data;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.custom.items.Gun;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class PlayerData {
	private static final TIntIntMap scores = new TIntIntHashMap();
	private static final TIntIntMap kills = new TIntIntHashMap();
	private static final TIntIntMap deaths = new TIntIntHashMap();
	private static final TIntIntMap health = new TIntIntHashMap();
	private static final TIntSet spotted = new TIntHashSet();

	private PlayerData() {

	}

	/**
	 * Utility method
	 * 
	 * @param hurt
	 *            {@link Player} hurt
	 * @param damager
	 *            {@link Player} damagin
	 * @param damage
	 *            Damage inflicted
	 * @param gun
	 *            {@link Gun} object
	 * @see #registerDamage(org.bukkit.entity.Player, org.bukkit.entity.Player,
	 *      int, com.tips48.rushMe.custom.items.Gun)
	 */
	public static void registerDamage(Player hurt, Player damager, int damage,
			Gun gun) {
		registerDamage(hurt.getEntityId(), damager.getEntityId(), damage, gun);
	}

	/**
	 * Registers damage by a player with a gun
	 * 
	 * @param hurt
	 *            Player's name who was hurt
	 * @param damager
	 *            Player's name who was the damager
	 * @param damage
	 *            Damage inflicted
	 * @param gun
	 *            {@link Gun} object
	 */
	public static void registerDamage(int hurt, int damager, int damage, Gun gun) {
		Player hurtP = SpoutManager.getPlayerFromId(hurt);
		Player damagerP = SpoutManager.getPlayerFromId(damager);

		if ((hurtP == null) || (damagerP == null)) {
			setHealth(hurt, damage);
			return;
		}

		MainHUD hurtHud = SpoutGUI.getHudOf(hurtP);
		MainHUD damagerHud = SpoutGUI.getHudOf(damagerP);

		if (!(GameManager.inGame(hurtP))) {
			return;
		}

		setHealth(hurt, damage);

		if ((hurtHud != null) && hurtHud.isActive()) {
			hurtHud.updateHUD();
		}

		if ((damagerHud != null) && damagerHud.isActive()) {
			damagerHud.updateHUD();
			damagerHud.showHit();
		}

		if (getHealth(hurt) <= 0) {
			addDeath(damager);
			SpoutGUI.showKill(damagerP, hurtP, gun);
			damagerHud.getPointQueue().addToQueue("Enemy killed - 100");
			setScore(damagerP, getScore(damagerP) + 100);
		}
		// TODO if keeping gun stats, do here
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return score of specified player
	 * @see #getScore(int)
	 */
	public static int getScore(Player player) {
		return getScore(player.getEntityId());
	}

	/**
	 * Gets the score of the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @return score of specified player
	 */
	public static int getScore(int player) {
		return scores.get(player);
	}

	/**
	 * Gets a map with each players scores
	 * 
	 * @return a {@link TObjectIntMap} with each players scores
	 */
	public static TIntIntMap getScores() {
		return scores;
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param score
	 *            Players new score
	 * @see #setScore(int, Integer)
	 */
	public static void setScore(Player player, Integer score) {
		setScore(player.getEntityId(), score);
	}

	/**
	 * Sets the specified players score
	 * 
	 * @param player
	 *            Player's name
	 * @param score
	 *            Players new score
	 */
	public static void setScore(int player, Integer score) {
		scores.put(player, score);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players score
	 * @see #getKills(int)
	 */
	public static int getKills(Player player) {
		return getKills(player.getEntityId());
	}

	/**
	 * Gets the specified players score
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players score
	 */
	public static int getKills(int player) {
		return kills.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param kill
	 *            New number of kills
	 * @see #setKills(int, Integer)
	 */
	public static void setKills(Player player, Integer kill) {
		setKills(player.getEntityId(), kill);
	}

	/**
	 * Sets the specified player's kills
	 * 
	 * @param player
	 *            Player's name
	 * @param kill
	 *            New number of kills
	 */
	public static void setKills(int player, Integer kill) {
		kills.put(player, kill);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players deaths
	 * @see #getDeaths(int)
	 */
	public static int getDeaths(Player player) {
		return getDeaths(player.getEntityId());
	}

	/**
	 * Gets the specified players deaths
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players deaths
	 */
	public static int getDeaths(int player) {
		return deaths.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param death
	 *            New number of deaths
	 * @see #setDeaths(int, Integer)
	 */
	public static void setDeaths(Player player, Integer death) {
		setDeaths(player.getEntityId(), death);
	}

	/**
	 * Sets the specified player's deaths
	 * 
	 * @param player
	 *            Player's name
	 * @param death
	 *            New number of deaths
	 */
	public static void setDeaths(int player, Integer death) {
		deaths.put(player, death);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #addKill(int)
	 */
	public static void addKill(Player player) {
		addKill(player.getEntityId());
	}

	/**
	 * Adds a kill to a Player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void addKill(int player) {
		kills.put(player, kills.get(player) + 1);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #addDeath(int)
	 */
	public static void addDeath(Player player) {
		addDeath(player.getEntityId());
	}

	/**
	 * Adds a death to a player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void addDeath(int player) {
		deaths.put(player, deaths.get(player) + 1);
	}

	/**
	 * Utiltiy method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players health
	 * @see #getHealth(int)
	 */
	public static int getHealth(Player player) {
		return getHealth(player.getEntityId());
	}

	/**
	 * Gets the specified players health
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players health
	 */
	public static int getHealth(int player) {
		return health.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            New players health
	 * @see #setHealth(int, Integer)
	 */
	public static void setHealth(Player player, Integer h) {
		setHealth(player.getEntityId(), h);
	}

	/**
	 * Sets the specified players health
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            New players health
	 */
	public static void setHealth(int player, Integer h) {
		health.put(player, h);
		Player p = SpoutManager.getPlayerFromId(player);
		if (p != null) {
			MainHUD hud = SpoutGUI.getHudOf(p);
			if (hud != null) {
				hud.updateHUD();
			}
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            Amount to damage player
	 * @see #damage(int, Integer)
	 */
	public static void damage(Player player, Integer h) {
		damage(player.getEntityId(), h);
	}

	/**
	 * Damages the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            Amount to damage player
	 */
	public static void damage(int player, Integer h) {
		int pHealth = health.get(player);
		if ((pHealth - h) >= 0) {
			health.put(player, pHealth - h);
		} else {
			health.put(player, 0);
		}
		Player p = SpoutManager.getPlayerFromId(player);
		if (p != null) {
			MainHUD hud = SpoutGUI.getHudOf(p);
			if (hud != null) {
				hud.updateHUD();
			}
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            Amount to heal
	 * @see #heal(int, Integer)
	 */
	public static void heal(Player player, Integer h) {
		heal(player.getEntityId(), h);
	}

	/**
	 * Heals the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            Amount to heal
	 */
	public static void heal(int player, Integer h) {
		int pHealth = health.get(player);
		if ((pHealth + h) <= 100) {
			health.put(player, pHealth + h);
		} else {
			health.put(player, 100);
		}
		Player p = SpoutManager.getPlayerFromId(player);
		if (p != null) {
			MainHUD hud = SpoutGUI.getHudOf(p);
			if (hud != null) {
				hud.updateHUD();
			}
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #setDefaults(int)
	 */
	public static void setDefaults(Player player) {
		setDefaults(player.getEntityId());
	}

	/**
	 * Sets the defaults for a player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void setDefaults(int player) {
		setDeaths(player, 0);
		setHealth(player, 100);
		setKills(player, 0);
		setScore(player, 0);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #setSpotted(int, boolean)
	 */
	public static void setSpotted(Player player, boolean s) {
		setSpotted(player.getEntityId(), s);
	}

	/**
	 * Sets if the specified player is spotted
	 * 
	 * @param player
	 *            Specified player
	 * @param s
	 *            if the player is spotted
	 */
	public static void setSpotted(int player, boolean s) {
		if (s) {
			spotted.add(player);
		} else {
			if (spotted.contains(player)) {
				spotted.remove(player);
			}
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #isSpotted(int)
	 */
	public static boolean isSpotted(Player player) {
		return isSpotted(player.getEntityId());
	}

	/**
	 * Gets if the specified player is spotted
	 * 
	 * @param player
	 *            Specified player
	 * @return is the specified player is spotted
	 */
	public static boolean isSpotted(int player) {
		return spotted.contains(player);
	}

}
