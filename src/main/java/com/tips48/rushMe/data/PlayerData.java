package com.tips48.rushMe.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.SpoutGUI;
import com.tips48.rushMe.spout.GUI.MainHUD;
import com.tips48.rushMe.util.RMUtils;

public class PlayerData {
	private static Map<String, Integer> scores = new HashMap<String, Integer>();
	private static Map<String, Integer> kills = new HashMap<String, Integer>();
	private static Map<String, Integer> deaths = new HashMap<String, Integer>();
	private static Map<String, Integer> health = new HashMap<String, Integer>();
	private static Map<String, Boolean> active = new HashMap<String, Boolean>();

	public static int getScore(Player player) {
		return getScore(player.getName());
	}

	public static int getScore(String player) {
		return scores.get(player);
	}
	
	public static Map<String, Integer> getScores() {
		return scores;
	}

	public static void setScore(Player player, Integer score) {
		setScore(player.getName(), score);
	}

	public static void setScore(String player, Integer score) {
		scores.put(player, score);
	}

	public static int getKills(Player player) {
		return getKills(player.getName());
	}

	public static int getKills(String player) {
		return kills.get(player);
	}

	public static void setKills(Player player, Integer kill) {
		setKills(player.getName(), kill);
	}

	public static void setKills(String player, Integer kill) {
		kills.put(player, kill);
	}

	public static int getDeaths(Player player) {
		return getDeaths(player.getName());
	}

	public static int getDeaths(String player) {
		return deaths.get(player);
	}

	public static void setDeaths(Player player, Integer death) {
		setDeaths(player.getName(), death);
	}

	public static void setDeaths(String player, Integer death) {
		deaths.put(player, death);
	}

	public static void addKill(Player player) {
		addKill(player.getName());
	}

	public static void addKill(String player) {
		kills.put(player, kills.get(player) + 1);
	}

	public static void addDeath(Player player) {
		addDeath(player.getName());
	}

	public static void addDeath(String player) {
		deaths.put(player, deaths.get(player) + 1);
	}

	public static int getHealth(Player player) {
		return getHealth(player.getName());
	}

	public static int getHealth(String player) {
		return health.get(player);
	}

	public static void setHealth(Player player, Integer h) {
		setHealth(player.getName(), h);
	}

	public static void setHealth(String player, Integer h) {
		health.put(player, h);
		MainHUD hud = SpoutGUI.getHudOf(player);
		if (hud != null) {
			hud.updateHUD();
		}
	}

	public static void damage(Player player, Integer h) {
		damage(player.getName(), h);
	}

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

	public static void heal(Player player, Integer h) {
		heal(player.getName(), h);
	}

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

	public static boolean isActive(Player player) {
		return isActive(player.getName());
	}

	public static boolean isActive(String player) {
		return active.get(player);
	}

	public static void setActive(Player player, boolean a) {
		setActive(player.getName(), a);
	}

	public static void setDefaults(Player player) {
		setDefaults(player.getName());
	}

	public static void setDefaults(String player) {
		setDeaths(player, 0);
		setHealth(player, 100);
		setKills(player, 0);
		setScore(player, 0);
	}

	public static void setActive(String player, boolean a) {
		if (a == true) {
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
