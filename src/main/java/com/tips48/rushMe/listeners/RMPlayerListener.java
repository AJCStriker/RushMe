package com.tips48.rushMe.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.BlockIterator;

import com.tips48.rushMe.GameManager;
import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.SpoutGUI;
import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.teams.Team;
import com.tips48.rushMe.util.RMUtils;

public class RMPlayerListener extends PlayerListener {
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer()
				.getInventory()
				.addItem(
						RushMe.getInstance().getGunManager().getGun("AK - 47")
								.toItemStack(1));
		Random r = new Random();
		Team t = GameManager.getTeams().get(r.nextInt(GameManager.getTeams().size()));
		GameManager.addPlayerToTeam(event.getPlayer(), t);
		GameManager.updateNames();
		SpoutGUI.getHudOf(event.getPlayer()).updateHUD();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action action = event.getAction();
		if ((action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) && RMUtils.holdingGun(p)) {
			event.setCancelled(true);
			return;
		}
		if (action.equals(Action.LEFT_CLICK_AIR)
				|| action.equals(Action.LEFT_CLICK_BLOCK)) {
			if (RMUtils.holdingGun(p)) {
				Gun g = RMUtils.getGun(p);
				if (!g.canFire(p)) {
					event.setCancelled(true);
					return;
				}
				if (g.getBulletsExplode()) {
					List<Block> lookedAt = p.getLineOfSight(null, 100);
					Block last = lookedAt.get(lookedAt.size() - 1);
					p.getWorld().createExplosion(last.getLocation(),
							g.getExplosionSize());
				} else {
					if (getLookingAtHead(p) != null) {
						LivingEntity e = getLookingAtHead(p);
						e.damage(g.getHeadshotDamage(), p);
						if (e.getHealth() <= 0) {
							GameManager.getPlayerData(p).addKill();
							SpoutGUI.showKill(p, e, g.getName());
						}
					} else if (getLookingAt(p) != null) {
						LivingEntity e = getLookingAt(p);
						e.damage(g.getBodyDamage(), p);
						if (e.getHealth() <= 0) {
							GameManager.getPlayerData(p).addKill();
							SpoutGUI.showKill(p, e, g.getName());
						}
					}
				}
				g.fire(p);
				event.setCancelled(true);
			}
		}
	}

	private LivingEntity getLookingAtHead(Player player) {
		List<Entity> ne = player.getNearbyEntities(100, 100, 100);
		ArrayList<LivingEntity> entities = new ArrayList<LivingEntity>();

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
						if (GameManager.getPlayersTeam((Player) target).equals(
								GameManager.getPlayersTeam(player))) {
							continue;
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
		List<Entity> ne = player.getNearbyEntities(100, 100, 100);
		ArrayList<LivingEntity> entities = new ArrayList<LivingEntity>();

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
						if (GameManager.getPlayersTeam((Player) target).equals(
								GameManager.getPlayersTeam(player))) {
							continue;
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
