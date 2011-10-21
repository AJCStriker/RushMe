package com.tips48.rushMe.custom.blocks;

import org.bukkit.Location;
import org.getspout.spoutapi.SpoutManager;

public class BlockManager {

	public static void SpawnMCOM(Location base) {
		SpoutManager.getMaterialManager().overrideBlock(base.getWorld(), base.getBlockX(), base.getBlockY(), base.getBlockZ(), new MCOM());
	}

}
