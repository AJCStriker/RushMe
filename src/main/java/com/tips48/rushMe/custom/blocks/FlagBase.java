package com.tips48.rushMe.custom.blocks;

import com.tips48.rushMe.RushMe;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class FlagBase extends GenericCustomBlock {
	public FlagBase() {
		super(RushMe.getInstance(), "FlagBase", true);

		// TODO block model and id
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

	public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z,
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
