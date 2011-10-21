package com.tips48.rushMe.custom.items;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.item.GenericCustomItem;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.SpoutGUI;

public class Gun extends GenericCustomItem {
	// LAST FIRED
	private long lastFired;
	// RELOAD
	private boolean reloading;
	private int reloadTime;
	// AMMO
	private int maxClipSize;
	private int loadedInClip;
	private int ammo;
	private int maxAmmo;
	// OTHER
	private Double timeBetweenFire;
	private boolean autoReload;
	private Double recoilBack;
	private Float recoilVertical;
	private Float recoilHorizontal;
	// EXPLOSIONS
	private boolean bulletsExplode;
	private Float explosionSize;
	private Double entityDamageRadius;
	// DAMAGE
	private Integer headshotDamage;
	private Integer bodyDamage;

	protected Gun(String name, String texture, Integer reloadTime,
			Boolean autoReload, Integer maxClipSize, Integer maxAmmo,
			Double timeBetweenFire, Boolean bulletsExplode,
			Float explosionSize, Double entityDamageRadius,
			Integer headshotDamage, Integer bodyDamage, Double recoilBack,
			Float recoilVertical, Float recoilHorizontal) {
		super(RushMe.getInstance(), name, texture);
		this.reloadTime = reloadTime;
		this.maxClipSize = maxClipSize;
		this.loadedInClip = maxClipSize;
		this.autoReload = autoReload;
		this.maxAmmo = maxAmmo;
		this.ammo = maxAmmo;
		this.timeBetweenFire = timeBetweenFire;
		this.bulletsExplode = bulletsExplode;
		this.explosionSize = explosionSize;
		this.entityDamageRadius = entityDamageRadius;
		this.headshotDamage = headshotDamage;
		this.bodyDamage = bodyDamage;
		this.recoilBack = recoilBack;
		this.recoilVertical = recoilVertical;
		this.recoilHorizontal = recoilHorizontal;
	}

	public boolean getBulletsExplode() {
		return bulletsExplode;
	}

	public Float getExplosionSize() {
		return explosionSize;
	}

	public int getHeadshotDamage() {
		return headshotDamage;
	}

	public int getBodyDamage() {
		return bodyDamage;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		if (maxAmmo - ammo >= 0) {
			this.ammo = ammo;
			return;
		}
		this.ammo = maxAmmo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public int getLoadedInClip() {
		return loadedInClip;
	}

	public void setLoadedInClip(int loadedInClip) {
		if (maxClipSize - loadedInClip >= 0) {
			this.loadedInClip = loadedInClip;
			return;
		}
		this.loadedInClip = maxClipSize;
	}

	public int getMaxClipSize() {
		return maxClipSize;
	}

	public double getTimeBetweenFire() {
		return timeBetweenFire;
	}

	public void setTimeBetweenFire(double timeBetweenFire) {
		this.timeBetweenFire = timeBetweenFire;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public ItemStack toItemStack(int amount) {
		return SpoutManager.getMaterialManager().getCustomItemStack(this,
				amount);
	}

	public boolean canFire(Player player) {
		if (reloading) {
			return false;
		}
		if (loadedInClip == 0) {
			return false;
		}
		if (!(System.currentTimeMillis() - lastFired >= timeBetweenFire * 100)) {
			return false;
		}
		return true;
	}

	public void fire(final Player player) {
		loadedInClip--;
		SpoutGUI.getHudOf(player).updateHUD();
		lastFired = System.currentTimeMillis();
		if (!(player.isSneaking())) {
			player.setVelocity(player.getLocation().getDirection()
					.multiply(-recoilBack));
			Location loc = player.getLocation();
			loc.setPitch(loc.getPitch() + -recoilVertical);
			loc.setYaw(loc.getYaw() + recoilHorizontal);
			player.teleport(loc);
		} else {
			player.setVelocity(player.getLocation().getDirection()
					.multiply(-recoilBack / 2));
			Location loc = player.getLocation();
			loc.setPitch(loc.getPitch() + -recoilVertical / 2);
			loc.setYaw(loc.getYaw() + recoilHorizontal / 2);
			player.teleport(loc);
		}
		if (loadedInClip == 0) {
			if (autoReload) {
				reload(player);
			}
		}
	}

	public void reload(final Player player) {
		if (ammo <= 0) {
			return;
		}
		reloading = true;
		// SpoutGUI.showReloading(player, this);
		RushMe.getInstance().getServer().getScheduler()
				.scheduleSyncDelayedTask(RushMe.getInstance(), new Runnable() {
					public void run() {
						if (!(reloading)) {
							return;
						}
						if (ammo - maxClipSize >= 0) {
							int lastLoadedInClip = loadedInClip;
							loadedInClip = maxClipSize;
							ammo = ammo - (maxClipSize - lastLoadedInClip);
						} else {
							loadedInClip = ammo;
							ammo = 0;
						}
						reloading = false;
						SpoutGUI.getHudOf(player).updateHUD();
					}
				}, reloadTime);
	}

	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}

	public double getEntityDamageRadius() {
		return entityDamageRadius;
	}

}
