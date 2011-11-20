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

package com.tips48.rushMe.packets;

import com.tips48.rushMe.data.PlayerData;

import org.getspout.spoutapi.io.*;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PacketPlayerDataUpdate extends AddonPacket {

	private int score;
	private int kills;
	private int deaths;
	private int health;
	private boolean spotted;

	@Override
	public void read(SpoutInputStream stream) {
		score = stream.readInt();
		kills = stream.readInt();
		deaths = stream.readInt();
		health = stream.readInt();
		spotted = stream.readInt() == 0;
	}

	@Override
	public void run(SpoutPlayer sp) {
		PlayerData.setScore(sp, score);
		PlayerData.setKills(sp, kills);
		PlayerData.setDeaths(sp, deaths);
		PlayerData.setHealth(sp, health);
		PlayerData.setSpotted(sp, spotted);
	}

	@Override
	public void write(SpoutOutputStream stream) {
		stream.writeInt(score);
		stream.writeInt(kills);
		stream.writeInt(deaths);
		stream.writeInt(health);
		stream.writeInt(spotted ? 0 : 1);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isSpotted() {
		return spotted;
	}

	public void setSpotted(boolean spotted) {
		this.spotted = spotted;
	}

}
