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

package com.tips48.rushMe;

import com.randomappdev.pluginstats.Ping;
import com.tips48.rushMe.commands.RushMeCommand;
import com.tips48.rushMe.configuration.GameModeConfiguration;
import com.tips48.rushMe.configuration.GunConfiguration;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.custom.items.GrenadeManager;
import com.tips48.rushMe.custom.items.GrenadeType;
import com.tips48.rushMe.custom.items.GunManager;
import com.tips48.rushMe.listeners.RMEntityListener;
import com.tips48.rushMe.listeners.RMInputListener;
import com.tips48.rushMe.listeners.RMPlayerListener;
import com.tips48.rushMe.util.RMUtils;

import me.kalmanolah.cubelist.classfile.cubelist;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RushMe extends JavaPlugin {

	private final static String prefix = "[RushMe]";
	private final static double version = 0.1;
	private final static int subVersion = 0;
	private final static Logger log = Logger.getLogger("Minecraft");

	private RMInputListener inputListener;
	private RMPlayerListener playerListener;
	private RMEntityListener entityListener;

	private static RushMe instance;

	public void onLoad() {
		instance = this;
		inputListener = new RMInputListener();
		playerListener = new RMPlayerListener();
		entityListener = new RMEntityListener();
	}

	public void onEnable() {

		GunConfiguration.loadGuns();
		GameModeConfiguration.loadGameModes();

		registerEvents();

		getCommand("RushMe").setExecutor(new RushMeCommand());

		Ping.init(this);
		new cubelist(this);

		GrenadeManager.createGrenade("TestGrenade1234", "Test", "Bleh",
				GrenadeType.CONCUSSION, 3, 1, 2, 5, 5);

		log(Level.INFO, true, "RushMe Version " + version + "_" + subVersion
				+ " enabled");
		log(Level.INFO, true,
				"Guns loaded: " + RMUtils.readableSet(GunManager.getGunNames()));
		log(Level.INFO,
				true,
				"Grenades loaded: "
						+ RMUtils.readableSet(GrenadeManager.getGrenadeNames()));
		log(Level.INFO,
				true,
				"GameModes loaded: "
						+ RMUtils.readableSet(GameManager.getGameModeNames()));
	}

	public void onDisable() {
		log(Level.INFO, true, "Disabled");
	}

	public static RushMe getInstance() {
		return instance;
	}

	public static void log(Level lvl, boolean usePrefix, String message) {
		if (usePrefix) {
			log.log(lvl, prefix + " " + message);
		} else {
			log.log(lvl, message);
		}
	}

	public static double getVersion() {
		return version;
	}

	public static int getSubVersion() {
		return subVersion;
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN,
				playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_MOVE,
				playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_QUIT,
				playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_LOGIN,
				GameManager.getPListener(), Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN,
				SpoutGUI.getPListener(), Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT,
				playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.CUSTOM_EVENT,
				inputListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_ITEM_HELD,
				playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE,
				entityListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.ENTITY_REGAIN_HEALTH,
				entityListener, Priority.Normal, this);
	}

}
