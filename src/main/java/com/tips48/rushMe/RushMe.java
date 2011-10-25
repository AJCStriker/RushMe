package com.tips48.rushMe;

import com.tips48.rushMe.commands.RushMeCommand;
import com.tips48.rushMe.configuration.GunConfiguration;
import com.tips48.rushMe.custom.items.GunManager;
import com.tips48.rushMe.listeners.RMEntityListener;
import com.tips48.rushMe.listeners.RMInputListener;
import com.tips48.rushMe.listeners.RMPlayerListener;
import org.blockface.bukkitstats.CallHome;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RushMe extends JavaPlugin {

	private final static String prefix = "[RushMe]";
	private final static double version = 0.1;
	private final static int subVersion = 0;
	private final static Logger log = Logger.getLogger("Minecraft");
	private final GunManager gManager = new GunManager();

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

		getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN,
				playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_LOGIN,
				GameManager.getPListener(), Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN,
				GameManager.getPListener(), Priority.Low, this);
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

		getCommand("RushMe").setExecutor(new RushMeCommand());

		CallHome.load(this);

		log(true, "RushMe Version " + version + "_" + subVersion + " enabled");
		log(true, "Guns loaded: " + gManager.getGunNames());
	}

	public void onDisable() {
		log(true, "Disabled");
	}

	public static RushMe getInstance() {
		return instance;
	}

	public static void log(boolean usePrefix, String message) {
		if (usePrefix) {
			log.info(prefix + " " + message);
		} else {
			log.info(message);
		}
	}

	public GunManager getGunManager() {
		return gManager;
	}

	public static double getVersion() {
		return version;
	}

	public static int getSubVersion() {
		return subVersion;
	}
}
