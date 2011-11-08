package com.tips48.rushMe.custom.blocks;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.teams.Team;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Flag extends GenericCustomBlock {

	private Team owner;

	public Flag() {
		super(RushMe.getInstance(), "Flag", true);
		// TODO BlockModel and data
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

	public Team getOwner() {
		return owner;
	}

	public void setOwner(Team owner) {
		this.owner = owner;
	}

}
