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

package com.tips48.rushMe.custom.blocks;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.blocks.designs.MCOMBlockDesign;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MCOMLeft extends GenericCustomBlock {

	private final boolean armed = false;

	public MCOMLeft() {
		super(RushMe.getInstance(), "MCOM", true, new MCOMBlockDesign());
	}

	public boolean isArmed() {
		return armed;
	}

	private void setupArmScheduler() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RushMe.getInstance(),
				new Runnable() {
					public void run() {
						if (isArmed()) {
							// TODO
						}
					}
				}, 20 * 45);
	}

	public void arm() {
		setupArmScheduler();
		// TODO GUI
		// TODO set texture
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return true;
	}

	public boolean canPlaceBlockAt(World arg0, int x, int y, int z, BlockFace bf) {
		return true;
	}

	public boolean isIndirectlyProdivingPowerTo(World world, int x, int y,
			int z, BlockFace bf) {
		return true;
	}

	public boolean isProvidingPowerTo(World world, int x, int y, int z,
			BlockFace bf) {
		return true;
	}

	public boolean isIndirectlyProvidingPowerTo(World world, int x, int y,
			int z, BlockFace bf) {
		return true;
	}

	public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer sp) {
	}

	public void onBlockDestroyed(World world, int x, int y, int z) {
	}

	public boolean onBlockInteract(World world, int x, int y, int z,
			SpoutPlayer sp) {
		return true;
	}

	public void onBlockPlace(World world, int x, int y, int z) {
	}

	public void onBlockPlace(World world, int x, int y, int z,
			LivingEntity placer) {
	}

	public void onEntityMoveAt(World world, int x, int y, int z, Entity mover) {
	}

	public void onNeighborBlockChange(World world, int x, int y, int z,
			int unknown) {
		// TODO find out 4th arg
	}

	public void onBlockDestroyed(World arg0, int arg1, int arg2, int arg3,
			LivingEntity arg4) {
		// TODO Auto-generated method stub

	}

}
