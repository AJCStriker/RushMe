package com.tips48.rushMe;

import com.randomappdev.bukkitstats.CallHome;
import com.tips48.rushMe.commands.RushMeCommand;
import com.tips48.rushMe.configuration.GameModeConfiguration;
import com.tips48.rushMe.configuration.GunConfiguration;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.custom.items.GunManager;
import com.tips48.rushMe.listeners.RMEntityListener;
import com.tips48.rushMe.listeners.RMInputListener;
import com.tips48.rushMe.listeners.RMPlayerListener;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;
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
        private Team t;

	public void onLoad() {
		instance = this;
		inputListener = new RMInputListener();
		playerListener = new RMPlayerListener();
		entityListener = new RMEntityListener(t);
	}

	public void onEnable() {

		GunConfiguration.loadGuns();
		GameModeConfiguration.loadGameModes();

		registerEvents();

		getCommand("RushMe").setExecutor(new RushMeCommand());

		CallHome.load(this);

		log(Level.INFO, true, "RushMe Version " + version + "_" + subVersion
				+ " enabled");
		log(Level.INFO, true,
				"Guns loaded: " + RMUtils.readableSet(GunManager.getGunNames()));
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
