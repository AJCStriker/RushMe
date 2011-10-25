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

public class MCOM extends GenericCustomBlock {

	private final boolean armed = false;

	public MCOM() {
		super(RushMe.getInstance(), "MCOM", true, new MCOMBlockDesign(), 0);
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

	public boolean isIndirectlyProvidingPowerTo
			(World world, int x, int y, int z,
			 BlockFace bf) {
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

}
