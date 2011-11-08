package com.tips48.rushMe.data;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.util.RMUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {
	private static final TObjectIntMap<String> scores = new TObjectIntHashMap<String>();
	private static final TObjectIntMap<String> kills = new TObjectIntHashMap<String>();
	private static final TObjectIntMap<String> deaths = new TObjectIntHashMap<String>();
	private static final TObjectIntMap<String> health = new TObjectIntHashMap<String>();
	private static final Map<String, Boolean> active = new HashMap<String, Boolean>();

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
		registerDamage(hurt.getName(), damager.getName(), damage, gun);
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
	@SuppressWarnings("deprecation")
	public static void registerDamage(String hurt, String damager, int damage,
			Gun gun) {
		Player hurtP = RushMe.getInstance().getServer().getPlayer(hurt);
		Player damagerP = RushMe.getInstance().getServer().getPlayer(damager);

		MainHUD hurtHud = SpoutGUI.getHudOf(hurt);
		MainHUD damagerHud = SpoutGUI.getHudOf(damager);

		if (!(GameManager.inGame(hurt))) {
			return;
		}

		setHealth(hurt, damage);
		
		if (hurtP == null || damagerP == null) {
			return;
		}

		if (hurtHud != null && hurtHud.isActive()) {
			hurtHud.updateHUD();
		}
		
		if (damagerHud != null && damagerHud.isActive()) {
			damagerHud.updateHUD();
		}

		if (getHealth(hurt) <= 0) {
			hurtP.setHealth(0);
			addDeath(damager);
			SpoutGUI.showKill(damagerP, hurtP, gun.getName());
		}
		// TODO if keeping gun stats, do here
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return score of specified player
	 * @see #getScore(String)
	 */
	public static int getScore(Player player) {
		return getScore(player.getName());
	}

	/**
	 * Gets the score of the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @return score of specified player
	 */
	public static int getScore(String player) {
		return scores.get(player);
	}

	/**
	 * Gets a map with each players scores
	 * 
	 * @return a {@link TObjectIntMap} with each players scores
	 */
	public static TObjectIntMap<String> getScores() {
		return scores;
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param score
	 *            Players new score
	 * @see #setScore(String, Integer)
	 */
	public static void setScore(Player player, Integer score) {
		setScore(player.getName(), score);
	}

	/**
	 * Sets the specified players score
	 * 
	 * @param player
	 *            Player's name
	 * @param score
	 *            Players new score
	 */
	public static void setScore(String player, Integer score) {
		scores.put(player, score);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players score
	 * @see #getKills(String)
	 */
	public static int getKills(Player player) {
		return getKills(player.getName());
	}

	/**
	 * Gets the specified players score
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players score
	 */
	public static int getKills(String player) {
		return kills.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param kill
	 *            New number of kills
	 * @see #setKills(String, Integer)
	 */
	public static void setKills(Player player, Integer kill) {
		setKills(player.getName(), kill);
	}

	/**
	 * Sets the specified player's kills
	 * 
	 * @param player
	 *            Player's name
	 * @param kill
	 *            New number of kills
	 */
	public static void setKills(String player, Integer kill) {
		kills.put(player, kill);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players deaths
	 * @see #getDeaths(String)
	 */
	public static int getDeaths(Player player) {
		return getDeaths(player.getName());
	}

	/**
	 * Gets the specified players deaths
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players deaths
	 */
	public static int getDeaths(String player) {
		return deaths.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param death
	 *            New number of deaths
	 * @see #setDeaths(String, Integer)
	 */
	public static void setDeaths(Player player, Integer death) {
		setDeaths(player.getName(), death);
	}

	/**
	 * Sets the specified player's deaths
	 * 
	 * @param player
	 *            Player's name
	 * @param death
	 *            New number of deaths
	 */
	public static void setDeaths(String player, Integer death) {
		deaths.put(player, death);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #addKill(String)
	 */
	public static void addKill(Player player) {
		addKill(player.getName());
	}

	/**
	 * Adds a kill to a Player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void addKill(String player) {
		kills.put(player, kills.get(player) + 1);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #addDeath(String)
	 */
	public static void addDeath(Player player) {
		addDeath(player.getName());
	}

	/**
	 * Adds a death to a player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void addDeath(String player) {
		deaths.put(player, deaths.get(player) + 1);
	}

	/**
	 * Utiltiy method
	 * 
	 * @param player
	 *            {@link Player}
	 * @return specified players health
	 * @see #getHealth(String)
	 */
	public static int getHealth(Player player) {
		return getHealth(player.getName());
	}

	/**
	 * Gets the specified players health
	 * 
	 * @param player
	 *            Player's name
	 * @return specified players health
	 */
	public static int getHealth(String player) {
		return health.get(player);
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            New players health
	 * @see #setHealth(String, Integer)
	 */
	public static void setHealth(Player player, Integer h) {
		setHealth(player.getName(), h);
	}

	/**
	 * Sets the specified players health
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            New players health
	 */
	public static void setHealth(String player, Integer h) {
		health.put(player, h);
		MainHUD hud = SpoutGUI.getHudOf(player);
		if (hud != null) {
			hud.updateHUD();
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            Amount to damage player
	 * @see #damage(String, Integer)
	 */
	public static void damage(Player player, Integer h) {
		damage(player.getName(), h);
	}

	/**
	 * Damages the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            Amount to damage player
	 */
	public static void damage(String player, Integer h) {
		int pHealth = health.get(player);
		if (pHealth - h >= 0) {
			health.put(player, pHealth - h);
		} else {
			health.put(player, 0);
		}
		MainHUD hud = SpoutGUI.getHudOf(player);
		if (hud != null) {
			hud.updateHUD();
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @param h
	 *            Amount to heal
	 * @see #heal(String, Integer)
	 */
	public static void heal(Player player, Integer h) {
		heal(player.getName(), h);
	}

	/**
	 * Heals the specified player
	 * 
	 * @param player
	 *            Player's name
	 * @param h
	 *            Amount to heal
	 */
	public static void heal(String player, Integer h) {
		int pHealth = health.get(player);
		if (pHealth + h <= 100) {
			health.put(player, pHealth + h);
		} else {
			health.put(player, 100);
		}
		MainHUD hud = SpoutGUI.getHudOf(player);
		if (hud != null) {
			hud.updateHUD();
		}
	}

	/**
	 * Utility method
	 * 
	 * @param player
	 *            {@link Player}
	 * @see #setDefaults(String)
	 */
	public static void setDefaults(Player player) {
		setDefaults(player.getName());
	}

	/**
	 * Sets the defaults for a player
	 * 
	 * @param player
	 *            Player's name
	 */
	public static void setDefaults(String player) {
		setDeaths(player, 0);
		setHealth(player, 100);
		setKills(player, 0);
		setScore(player, 0);
	}

	/**
	 * Sets if the specified player is currently active
	 * 
	 * @param player
	 *            Player's name
	 * @param a
	 *            If the player is active
	 * @deprecated Use GameManager
	 */
	@Deprecated
	public static void setActive(String player, boolean a) {
		if (a) {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				MainHUD hud = SpoutGUI.getHudOf(p);
				if (hud != null) {
					hud.init();
					// TODO give player guns
				}
			}
			active.put(player, true);
		} else {
			Player p = RushMe.getInstance().getServer().getPlayer(player);
			if (p != null) {
				MainHUD hud = SpoutGUI.getHudOf(p);
				if (hud != null) {
					hud.shutdown();
				}
				RMUtils.clearInventoryOfGuns(p);
			}
			active.put(player, false);
		}
	}

}
