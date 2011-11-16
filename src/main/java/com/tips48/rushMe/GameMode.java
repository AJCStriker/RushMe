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

import java.util.List;

import com.tips48.rushMe.teams.Team;

public class GameMode {

	private final GameModeType type;
	private final String name;
	private final Integer time;
	private final Boolean respawn;
	private final Integer respawnTime;
	private final Integer maxPlayers;
	private final List<Team> teams;

	protected GameMode(String name, GameModeType type, Integer time,
			Boolean respawn, Integer respawnTime, Integer maxPlayers,
			List<Team> teams) {
		this.name = name;
		this.type = type;
		this.time = time;
		this.respawn = respawn;
		this.respawnTime = respawnTime;
		this.maxPlayers = maxPlayers;
		this.teams = teams;
	}

	public GameModeType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	public boolean shouldRespawn() {
		return respawn;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public List<Team> getTeams() {
		return teams;
	}

	@Override
	public String toString() {
		return name;
	}

}
