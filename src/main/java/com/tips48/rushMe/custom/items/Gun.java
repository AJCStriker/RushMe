package com.tips48.rushMe.custom.items;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.GUI.SpoutGUI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class Gun extends GenericCustomItem {
	// LAST FIRED
	private long lastFired;
	// RELOAD
	private boolean reloading;
	private final int reloadTime;
	// AMMO
	private final int maxClipSize;
	private int loadedInClip;
	private int ammo;
	private final int maxAmmo;
	// OTHER
	private Double timeBetweenFire;
	private boolean autoReload;
	private final Double recoilBack;
	private final Float recoilVertical;
	private final Float recoilHorizontal;
	// EXPLOSIONS
	private final boolean bulletsExplode;
	private final Float explosionSize;
	private final Double entityDamageDistance;
	// DAMAGE
	private final Integer headshotDamage;
	private final Integer bodyDamage;

	/**
	 * Creates a gun
	 * 
	 * @param name
	 *            Name of gun
	 * @param texture
	 *            Online texture URL
	 * @param reloadTime
	 *            Time between reloads
	 * @param autoReload
	 *            If gun auto reloads
	 * @param maxClipSize
	 *            Max clip size
	 * @param maxAmmo
	 *            Max ammo of gun
	 * @param timeBetweenFire
	 *            Time between firing
	 * @param bulletsExplode
	 *            If Bullets explode
	 * @param explosionSize
	 *            Size of explosion (Can be null if bullets don't explode)
	 * @param entityDamageDistance
	 *            Distance in which entities get damaged by explosions (Can be
	 *            null if bullets don't explode)
	 * @param headshotDamage
	 *            Damage of a head shot (Can be null if bullets explode)
	 * @param bodyDamage
	 *            Damage of a body shot (Can be null if bullets explode)
	 * @param recoilBack
	 *            Recoil moving the player back (Negative if the player should
	 *            move forward)
	 * @param recoilVertical
	 *            Recoil moving the player's gun up (Negative if the player's
	 *            gun should go down)
	 * @param recoilHorizontal
	 *            Recoil moving the player's gun to the right (Negative if the
	 *            player's gun should go to the left)
	 */
	protected Gun(String name, String texture, Integer reloadTime,
			Boolean autoReload, Integer maxClipSize, Integer maxAmmo,
			Double timeBetweenFire, Boolean bulletsExplode,
			Float explosionSize, Double entityDamageDistance,
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
		this.entityDamageDistance = entityDamageDistance;
		this.headshotDamage = headshotDamage;
		this.bodyDamage = bodyDamage;
		this.recoilBack = recoilBack;
		this.recoilVertical = recoilVertical;
		this.recoilHorizontal = recoilHorizontal;
	}

	/**
	 * Gets if bullets explode when hitting an object
	 * 
	 * @return if bullets explode
	 */
	public boolean getBulletsExplode() {
		return bulletsExplode;
	}

	/**
	 * Gets the explosion size for when bullets explode
	 * 
	 * @return explosion size when bullets explode
	 */
	public Float getExplosionSize() {
		return explosionSize;
	}

	/**
	 * Gets the guns head shot damage
	 * 
	 * @return head shot damage
	 */
	public int getHeadshotDamage() {
		return headshotDamage;
	}

	/**
	 * Gets the guns body damage
	 * 
	 * @return body damage
	 */
	public int getBodyDamage() {
		return bodyDamage;
	}

	/**
	 * Gets the current amount of ammo loaded not loaded in the gun
	 * 
	 * @return current amount of not loaded ammo
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * Sets the amount of ammo not loaded in the gun
	 * 
	 * @param ammo
	 *            Ammo to be not loaded in the gun
	 */
	public void setAmmo(int ammo) {
		if (maxAmmo - ammo >= 0) {
			this.ammo = ammo;
			return;
		}
		this.ammo = maxAmmo;
	}

	/**
	 * Gets the maximum ammo a gun can have not loaded at one time
	 * 
	 * @return maximum amount of ammo the gun can have not loaded at one time
	 */
	public int getMaxAmmo() {
		return maxAmmo;
	}

	/**
	 * Gets how much ammo is currently loaded in the gun
	 * 
	 * @return how much ammo is currently loaded
	 */
	public int getLoadedInClip() {
		return loadedInClip;
	}

	/**
	 * Sets how much ammo is currently loaded in the gun
	 * 
	 * @param loadedInClip
	 *            how much ammo is to be loaded
	 */
	public void setLoadedInClip(int loadedInClip) {
		if (maxClipSize - loadedInClip >= 0) {
			this.loadedInClip = loadedInClip;
			return;
		}
		this.loadedInClip = maxClipSize;
	}

	/**
	 * Gets the maximum amount of ammo that can be loaded in the gun at one time
	 * 
	 * @return maximum amount of ammo that can be loaded at one time
	 */
	public int getMaxClipSize() {
		return maxClipSize;
	}

	/**
	 * Gets the time between shots
	 * 
	 * @return how long between shots
	 */
	public double getTimeBetweenFire() {
		return timeBetweenFire;
	}

	/**
	 * Sets the time between shots
	 * 
	 * @param timeBetweenFire
	 *            Time between shots
	 */
	public void setTimeBetweenFire(double timeBetweenFire) {
		this.timeBetweenFire = timeBetweenFire;
	}

	/**
	 * Gets how long the gun takes to reload
	 * 
	 * @return time gun takes to reload
	 */
	public int getReloadTime() {
		return reloadTime;
	}

	/**
	 * Converts the gun to an ItemStack
	 * 
	 * @param amount
	 *            Amount of guns that should be in the ItemStack
	 * @return {@link ItemStack} with the gun in it
	 */
	public ItemStack toItemStack(int amount) {
		return SpoutManager.getMaterialManager().getCustomItemStack(this,
				amount);
	}

	/**
	 * Gets if the gun can fire
	 * 
	 * @return if the gun can fire
	 */
	public boolean canFire() {
		return !(reloading || loadedInClip == 0 || System.currentTimeMillis()
				- lastFired < timeBetweenFire * 100);
	}

	/**
	 * Simulates the gun being fired
	 * 
	 * @param player
	 *            Player that fired the gun
	 */
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

	/**
	 * Reloads the gun
	 * 
	 * @param player
	 *            Player to reload the gun
	 */
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

	/**
	 * Gets if the gun automatically reloads when out of ammo
	 * 
	 * @return if the gun should automatically reload when its out of ammo
	 */
	public boolean isAutoReload() {
		return autoReload;
	}

	/**
	 * Sets if the gun should automatically reload when out of ammo
	 * 
	 * @param autoReload
	 *            If the gun should automatically reload
	 */
	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}

	/**
	 * Gets the distance from a bullet when it explodes (@see
	 * #getBulletsExplode) that entities will get hurt
	 * 
	 * @return distance entities have to be to not get hurt by explosions
	 */
	public double getEntityDamageDistance() {
		return entityDamageDistance;
	}

	/**
	 * Overrides and returns the name of the Gun
	 * 
	 * @return name of gun
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
