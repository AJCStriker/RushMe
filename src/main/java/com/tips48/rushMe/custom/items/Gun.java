package com.tips48.rushMe.custom.items;

import org.bukkit.Bukkit;
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
	// FIRETYPE
	private FireType type;
	private double timeBetweenFire;
	// EXPLOSIONS
	private boolean bulletsExplode;
	private Float explosionSize;
	// DAMAGE
	private int headshotDamage;
	private int bodyDamage;

	protected Gun(String name, String texture, int reloadTime, int maxClipSize,
			int maxAmmo, FireType type, double timeBetweenFire,
			boolean bulletsExplode, Float explosionSize, int headshotDamage,
			int bodyDamage) {
		super(RushMe.getInstance(), name, texture);
		this.reloadTime = reloadTime;
		this.maxClipSize = maxClipSize;
		this.loadedInClip = maxClipSize;
		this.maxAmmo = maxAmmo;
		this.ammo = maxAmmo;
		this.type = type;
		this.timeBetweenFire = timeBetweenFire;
		this.bulletsExplode = bulletsExplode;
		this.explosionSize = explosionSize;
		this.headshotDamage = headshotDamage;
		this.bodyDamage = bodyDamage;
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

	public FireType getFireType() {
		return type;
	}

	public void setFireType(FireType type) {
		this.type = type;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public ItemStack toItemStack(int amount) {
		return SpoutManager.getMaterialManager().getCustomItemStack(this, amount);
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
		// TODO time between firing Don't know if this is right
	}

	public void fire(Player player) {
		loadedInClip--;
		SpoutGUI.getHudOf(player).updateHUD();
		lastFired = System.currentTimeMillis();
		// TODO recoil
	}

	public void reload(final Player player) {
		if (ammo <= 0) {
			return;
		}
		reloading = true;
		// SpoutGUI.showReloading(player, this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(RushMe.getInstance(),
				new Runnable() {
					@Override
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

}
