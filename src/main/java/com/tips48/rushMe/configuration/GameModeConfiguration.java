package com.tips48.rushMe.configuration;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.GameModeType;
import com.tips48.rushMe.RushMe;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class GameModeConfiguration {

	private static File gamemodeFile;
	private static YamlConfiguration gamemode;

	public static void loadGameModes() {
		gamemodeFile = new File(RushMe.getInstance().getDataFolder()
				+ File.separator + "gamemodes.yml");
		gamemode = YamlConfiguration.loadConfiguration(gamemodeFile);
		if (!gamemodeFile.exists()) {
			try {
				if (gamemodeFile.getParentFile() != null) {
					if (!gamemodeFile.getParentFile().mkdirs()) {
						RushMe.log(Level.SEVERE, true,
								"Unable to create folder "
										+ gamemodeFile.getParentFile()
												.getName());
					}
				}
				if (!gamemodeFile.createNewFile()) {
					RushMe.log(Level.SEVERE, true, "Unable to create file "
							+ gamemodeFile.getName());
				}
				addGameModeDefaults();
			} catch (IOException e) {
				RushMe.log(Level.SEVERE, true, "Error creating file "
						+ gamemodeFile.getName());
			}
		}
		for (String name : gamemode.getConfigurationSection("GameModes")
				.getKeys(false)) {
			String gmtString = gamemode.getString("GameModes." + name
					+ ".gameModeType");
			GameModeType t = GameModeType.valueOf(gmtString);
			if (t == null) {
				RushMe.log(Level.SEVERE, true, "Error loading GameMode " + name
						+ ".  The GameModeType " + gmtString
						+ " is not a valid GameModeType.");
				continue;
			}
			Boolean respawn = gamemode.getBoolean("GameModes." + name
					+ ".respawn");
			Integer respawnTime = gamemode.getInt("GameModes." + name
					+ ".respawnTime");
			Integer time = gamemode.getInt("GameModes." + name + ".time");
			if (gamemode.getBoolean("GameModes." + name + ".default")) {
				GameManager.setDefaultGameMode(GameManager.createGameMode(name,
						t, time, respawn, respawnTime));
			} else {
				GameManager.createGameMode(name, t, time, respawn, respawnTime);
			}
		}
	}

	private static void addGameModeDefaults() {
		gamemode.set("GameModes.Rush.gameModeType", "OBJECTIVE");
		gamemode.set("GameModes.Rush.respawn", true);
		gamemode.set("GameModes.Rush.respawnTime", 5);
		gamemode.set("GameModes.Rush.time", 600);
		gamemode.set("GameModes.Rush.default", true);
		try {
			gamemode.save(gamemodeFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
