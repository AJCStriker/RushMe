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

import com.tips48.rushMe.custom.GUI.MainHUD;
import com.tips48.rushMe.custom.GUI.SpoutGUI;

import org.getspout.spoutapi.io.*;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PacketToggleHUD extends AddonPacket {

	private boolean toggle;

	@Override
	public void read(SpoutInputStream stream) {
		toggle = stream.readInt() == 0;
	}

	@Override
	public void run(SpoutPlayer sp) {
		if (toggle) {
			MainHUD hud = SpoutGUI.getHudOf(sp);
			if (hud != null) {
				hud.init();
			}
		} else {
			MainHUD hud = SpoutGUI.getHudOf(sp);
			if (hud != null) {
				hud.shutdown();
			}
		}
	}

	@Override
	public void write(SpoutOutputStream stream) {
		stream.writeInt(toggle ? 0 : 1);
	}

	public boolean isToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

}
