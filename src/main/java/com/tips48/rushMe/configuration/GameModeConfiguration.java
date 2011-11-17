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

package com.tips48.rushMe.configuration;

import com.tips48.rushMe.*;
import com.tips48.rushMe.teams.Team;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GameModeConfiguration {

	private static File gamemodeFile;
	private static YamlConfiguration gamemode;

	private GameModeConfiguration() {

	}

	public static void loadGameModes() {
		gamemodeFile = new File(RushMe.getInstance().getDataFolder()
				+ File.separator + "gamemodes.yml");
		gamemode = YamlConfiguration.loadConfiguration(RushMe.getInstance()
				.getResource("gamemodes.yml"));
		if (!(gamemodeFile.exists())) {
			if (!(gamemodeFile.getParentFile().exists())) {
				if (!(gamemodeFile.getParentFile().mkdirs())) {
					RushMe.log(Level.SEVERE, true, "Error creating the folder "
							+ gamemodeFile.getParentFile().getName());
				}
			}
			try {
				if (!(gamemodeFile.createNewFile())) {
					RushMe.log(Level.SEVERE, true, "Error creating the file "
							+ gamemodeFile.getName());
				}
			} catch (Exception e) {
				RushMe.log(Level.SEVERE, true, "Error creating the file "
						+ gamemodeFile.getName());
			}
			gamemode.options().copyDefaults(true);
			try {
				gamemode.save(gamemodeFile);
			} catch (Exception e) {
				RushMe.log(Level.SEVERE, true, "Error saving to "
						+ gamemodeFile.getName());
			}
			RushMe.log(Level.INFO, true, "Created " + gamemodeFile.getName());
		}
		gamemode = YamlConfiguration.loadConfiguration(gamemodeFile);
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
			Integer maxPlayers = gamemode.getInt("GameModes." + name
					+ ".maxPlayers");
			List<Team> teams = new ArrayList<Team>();
			for (String teamName : gamemode.getConfigurationSection(
					"GameModes." + name + ".teams").getKeys(false)) {
				String skin = gamemode.getString("GameModes." + name
						+ ".teams." + teamName + ".skin");
				String prefix = gamemode.getString("GameModes." + name
						+ ".teams." + teamName + ".prefix");
				Boolean infiniteSpawns = gamemode.getBoolean("GameModes."
						+ name + ".teams." + teamName + ".infiniteSpawns");
				Integer spawns = gamemode.getInt("GameModes." + name
						+ ".teams." + teamName + ".spawns");
				Team team = new Team(teamName, prefix, maxPlayers, skin, spawns);
				team.setInfiniteLives(infiniteSpawns);
				teams.add(team);
			}
			if (gamemode.getBoolean("GameModes." + name + ".default")) {
				GameManager.setDefaultGameMode(GameManager.createGameMode(name,
						t, time, respawn, respawnTime, maxPlayers, teams));
			} else {
				GameManager.createGameMode(name, t, time, respawn, respawnTime,
						maxPlayers, teams);
			}
		}
	}

}
