package com.tips48.rushMe.listeners;

import com.tips48.rushMe.Arena;
import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.data.PlayerData;
import com.tips48.rushMe.util.RMUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.util.BlockIterator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RMPlayerListener extends PlayerListener {
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Player player = event.getPlayer();

	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (GameManager.inGame(p)) {
			GameManager.getPlayerArena(p).removePlayer(p);
		}
		RMUtils.clearInventoryOfGuns(p);
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action action = event.getAction();
		if ((action.equals(Action.RIGHT_CLICK_BLOCK) || action
				.equals(Action.RIGHT_CLICK_AIR))) {
			if (!(event.hasBlock())) {
				return;
				// Fix NPE's
			}
			if (Arena.hasArena(p)) {
				Arena a = Arena.getArena(p);
				if (a.getVector1() == null) {
					a.setVector1(event.getClickedBlock().getLocation()
							.toVector());
					if (a.getVector2() != null) {
						Arena.removeArena(p);
						p.sendMessage(ChatColor.AQUA + "Points selected");
					}
				} else {
					a.setVector2(event.getClickedBlock().getLocation()
							.toVector());
					Arena.removeArena(p);
					p.sendMessage(ChatColor.AQUA + "Points selected");
				}
				event.setCancelled(true);
				return;
			}
			if (RMUtils.holdingGun(p)) {
				event.setCancelled(true);
				return;
			}
		}
		if (!GameManager.inGame(p)) {
			return;
		}

		if (action.equals(Action.LEFT_CLICK_AIR)
				|| action.equals(Action.LEFT_CLICK_BLOCK)) {
			if (RMUtils.holdingGun(p)) {
				Gun g = RMUtils.getGun(p);
				if (g.canFire()) {
					event.setCancelled(true);
					return;
				}
				if (g.getBulletsExplode()) {
					List<Block> lookedAt = p.getLineOfSight(null, 100);
					Block last = lookedAt.get(lookedAt.size() - 1);
					RMUtils.createAstheticExplosion(g, last.getLocation());
					for (Entity e : RMUtils.getNearbyEntities(
							last.getLocation(), g.getEntityDamageDistance(),
							g.getEntityDamageDistance(),
							g.getEntityDamageDistance())) {
						if (!(e instanceof LivingEntity)) {
							return;
						}
						LivingEntity le = (LivingEntity) e;
						if (le instanceof Player) {
							PlayerData.registerDamage((Player) le, p, 10, g);
						} else {
							le.damage(10, p);
						}
					}
				} else {
					if (getLookingAtHead(p) != null) {
						LivingEntity e = getLookingAtHead(p);
						if (e instanceof Player) {
							PlayerData.registerDamage((Player) e, p,
									g.getHeadshotDamage(), g);
						} else {
							e.damage(g.getHeadshotDamage(), p);
						}
					} else if (getLookingAt(p) != null) {
						LivingEntity e = getLookingAt(p);
						if (e instanceof Player) {
							PlayerData.registerDamage((Player) e, p,
									g.getBodyDamage(), g);
						} else {
							e.damage(g.getBodyDamage(), p);
						}
					}
				}
				g.fire(p);
				event.setCancelled(true);
			}
		}
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom();
		if (GameManager.inGame(player)) {
			Arena a = GameManager.getPlayerArena(player);
				if (!(a.inArena(event.getTo().toVector()))) {
					from.setX(from.getBlockX() + 0.5);
					from.setY(from.getBlockY());
					from.setZ(from.getBlockZ() + 0.5);
					event.setTo(from);
			}
		}
	}

	@Override
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		final Player player = event.getPlayer();
		if (!GameManager.inGame(player)) {
			return;
		}
		RushMe.getInstance().getServer().getScheduler()
				.scheduleSyncDelayedTask(RushMe.getInstance(), new Runnable() {
					public void run() {
						if (RMUtils.holdingGun(player)) {
							SpoutGUI.getHudOf(player).updateHUD();
						}
					}
				}, 1);
	}

	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (!GameManager.inGame(player)) {
			return;
		}
		PlayerData.setHealth(player, 100);
		PlayerData.addDeath(player);
		SpoutGUI.getHudOf(player).updateHUD();
	}

	private LivingEntity getLookingAtHead(Player player) {
		List<Entity> ne = player.getNearbyEntities(500, 500, 500);
		Set<LivingEntity> entities = new HashSet<LivingEntity>();

		for (Entity e : ne) {
			if (e instanceof LivingEntity) {
				entities.add((LivingEntity) e);
			}
		}

		LivingEntity target = null;
		BlockIterator bitr = new BlockIterator(player, 100);
		Block b;
		Location l;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bitr.hasNext()) {
			b = bitr.next();
			bx = b.getX();
			by = b.getY();
			bz = b.getZ();
			// check for entities near this block in the line of sight
			for (LivingEntity e : entities) {
				l = e.getEyeLocation();
				ex = l.getX();
				ey = l.getY();
				ez = l.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75)
						&& (bz - .75 <= ez && ez <= bz + 1.75)
						&& (by - 1 <= ey && ey <= by)) {
					if (target instanceof Player) {
						// Check teams
						if (GameManager.inGame((Player) target)) {
							Arena a = GameManager
									.getPlayerArena((Player) target);
							if (a.getPlayerTeam((Player) target).equals(
									a.getPlayerTeam(player))) {
								continue;
							}
						}
					}
					// entity is close enough, set target and stop
					target = e;
					break;
				}
			}
		}
		return target;
	}

	private LivingEntity getLookingAt(Player player) {
		List<Entity> ne = player.getNearbyEntities(500, 500, 500);
		Set<LivingEntity> entities = new HashSet<LivingEntity>();

		for (Entity e : ne) {
			if (e instanceof LivingEntity) {
				entities.add((LivingEntity) e);
			}
		}

		LivingEntity target = null;
		BlockIterator bitr = new BlockIterator(player, 100);
		Block b;
		Location l;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bitr.hasNext()) {
			b = bitr.next();
			bx = b.getX();
			by = b.getY();
			bz = b.getZ();
			// check for entities near this block in the line of sight
			for (LivingEntity e : entities) {
				l = e.getLocation();
				ex = l.getX();
				ey = l.getY();
				ez = l.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75)
						&& (bz - .75 <= ez && ez <= bz + 1.75)
						&& (by - 1 <= ey && ey <= by + 2.5)) {
					if (target instanceof Player) {
						if (GameManager.inGame((Player) target)) {
							Arena a = GameManager
									.getPlayerArena((Player) target);
							if (a.getPlayerTeam((Player) target).equals(
									a.getPlayerTeam(player))) {
								continue;
							}
						}
					}
					// entity is close enough, set target and stop
					target = e;
					break;
				}
			}
		}
		return target;
	}
}
