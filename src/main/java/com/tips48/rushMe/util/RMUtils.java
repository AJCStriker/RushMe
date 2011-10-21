package com.tips48.rushMe.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.items.Gun;

public class RMUtils {

	public static boolean holdingGun(Player player) {
		SpoutPlayer p = SpoutManager.getPlayer(player);
		ItemStack inHand = p.getItemInHand();
		if (SpoutManager.getMaterialManager().getCustomItem(inHand) != null) {
			CustomItem i = SpoutManager.getMaterialManager().getCustomItem(
					inHand);
			if (RushMe.getInstance().getGunManager().getGun(i) != null) {
				return true;
			}
		}
		return false;
	}

	public static boolean isGun(ItemStack item) {
		CustomItem i = SpoutManager.getMaterialManager().getCustomItem(item);
		if (i != null) {
			Gun g = RushMe.getInstance().getGunManager().getGun(i);
			if (g != null) {
				return true;
			}
		}
		return false;
	}

	public static void clearInventoryOfGuns(Player player) {
		List<ItemStack> stack = new ArrayList<ItemStack>();
		for (ItemStack item : player.getInventory().getContents()) {
			if (!isGun(item)) {
				stack.add(item);
			}
		}
		player.getInventory().setContents(
				stack.toArray(new ItemStack[stack.size()]));
	}

	public static Gun getGun(Player player) {
		SpoutPlayer p = SpoutManager.getPlayer(player);
		ItemStack inHand = p.getItemInHand();
		CustomItem i = SpoutManager.getMaterialManager().getCustomItem(inHand);
		return RushMe.getInstance().getGunManager().getGun(i);
	}

	public static List<Entity> getNearbyEntities(Location loc, double radiusX,
			double radiusY, double radiusZ) {
		Entity e = loc.getWorld().spawn(loc, Minecart.class);
		List<Entity> entities = e.getNearbyEntities(radiusX, radiusY, radiusZ);
		e.remove();
		return entities;
	}

	public static void createAstheticExplosion(Gun g, Location loc) {

		Set<Block> blocks = new HashSet<Block>();

		float f = g.getExplosionSize();
		byte b0 = 16;

		int i;
		int j;
		int k;

		double d0;
		double d1;
		double d2;

		for (i = 0; i < b0; ++i) {
			for (j = 0; j < b0; ++j) {
				for (k = 0; k < b0; ++k) {
					if (i == 0 || i == b0 - 1 || j == 0 || j == b0 - 1
							|| k == 0 || k == b0 - 1) {
						double d3 = (double) ((float) i / ((float) b0 - 1.0F)
								* 2.0F - 1.0F);
						double d4 = (double) ((float) j / ((float) b0 - 1.0F)
								* 2.0F - 1.0F);
						double d5 = (double) ((float) k / ((float) b0 - 1.0F)
								* 2.0F - 1.0F);
						double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);

						d3 /= d6;
						d4 /= d6;
						d5 /= d6;
						// RushMe - this.size -> F
						// Rushme - this.world.random - new Random()
						float f1 = f * (0.7F + new Random().nextFloat() * 0.6F);

						// RushMe - Modify d0, d1, d2 to use Location
						d0 = loc.getX();
						d1 = loc.getY();
						d2 = loc.getZ();
						// RushMe

						for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
							int l = floor(d0);
							int i1 = floor(d1);
							int j1 = floor(d2);
							int k1 = loc.getWorld().getBlockAt(l, i1, j1)
									.getTypeId();

							if (k1 > 0) {
								f1 -= (net.minecraft.server.Block.byId[k1]
										.a((net.minecraft.server.Entity) null) + 0.3F)
										* f2;
							}

							if (f1 > 0.0F) {
								// RushMe - Don't use ChunkPosition
								// this.blocks.add(new ChunkPosition(l, i1,
								// j1));
								Block b = loc.getWorld().getBlockAt(l, i1, j1);
								if (b.getType() != Material.AIR) {
									blocks.add(b);
								}
							}

							d0 += d3 * (double) f2;
							d1 += d4 * (double) f2;
							d2 += d5 * (double) f2;
						}
					}
				}
			}
		}
		for (Block b : blocks) {
			b.setTypeId(0);
		}
	}

	private static int floor(double d0) {
		int i = (int) d0;

		return d0 < (double) i ? i - 1 : i;
	}

}
