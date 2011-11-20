/*
 * This file is part of RushMe.
 *
 * RushMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RushMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.tips48.rushMe.packets;

import com.tips48.rushMe.custom.items.Gun;
import com.tips48.rushMe.custom.items.GunManager;

import org.getspout.spoutapi.io.*;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PacketGunUpdate extends AddonPacket {

	private String name;
	private int reloadTime;
	private int maxClipSize;
	private int loadedInClip;
	private int ammo;
	private int maxAmmo;
	private Double timeBetweenFire;
	private boolean autoReload;
	private boolean bulletsExplode;
	private Float explosionSize;
	private Double entityDamageDistance;
	private Integer headshotDamage;
	private Integer bodyDamage;

	@Override
	public void read(SpoutInputStream stream) {
		name = stream.readString("name");
		System.out.println("Reading gun: " + name);
		reloadTime = stream.readInt();
		maxClipSize = stream.readInt();
		loadedInClip = stream.readInt();
		ammo = stream.readInt();
		maxAmmo = stream.readInt();
		timeBetweenFire = stream.readDouble();
		autoReload = stream.readInt() == 0;
		bulletsExplode = stream.readInt() == 0;
		explosionSize = stream.readFloat();
		entityDamageDistance = stream.readDouble();
		headshotDamage = stream.readInt();
		bodyDamage = stream.readInt();
	}

	@Override
	public void run(SpoutPlayer sp) {
		Gun gun = GunManager.getGun(name);
		if (gun == null) {
			gun = GunManager.createGun(name, null, reloadTime, autoReload,
					maxClipSize, maxAmmo, timeBetweenFire, bulletsExplode,
					explosionSize, entityDamageDistance, headshotDamage,
					bodyDamage, null, null, null);
		}
		gun.setAmmo(ammo);
		gun.setAutoReload(autoReload);
		gun.setLoadedInClip(loadedInClip);
		gun.setTimeBetweenFire(timeBetweenFire);
	}

	@Override
	public void write(SpoutOutputStream stream) {
		System.out.println("Writing: " + name);
		stream.writeString(name);
		stream.writeInt(reloadTime);
		stream.writeInt(maxClipSize);
		stream.writeInt(loadedInClip);
		stream.writeInt(ammo);
		stream.writeInt(maxAmmo);
		stream.writeDouble(timeBetweenFire);
		stream.writeInt(autoReload ? 0 : 1);
		stream.writeInt(bulletsExplode ? 0 : 1);
		stream.writeFloat(explosionSize);
		stream.writeDouble(entityDamageDistance);
		stream.writeInt(headshotDamage);
		stream.writeInt(bodyDamage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public int getMaxClipSize() {
		return maxClipSize;
	}

	public void setMaxClipSize(int maxClipSize) {
		this.maxClipSize = maxClipSize;
	}

	public int getLoadedInClip() {
		return loadedInClip;
	}

	public void setLoadedInClip(int loadedInClip) {
		this.loadedInClip = loadedInClip;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

	public Double getTimeBetweenFire() {
		return timeBetweenFire;
	}

	public void setTimeBetweenFire(Double timeBetweenFire) {
		this.timeBetweenFire = timeBetweenFire;
	}

	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}

	public boolean isBulletsExplode() {
		return bulletsExplode;
	}

	public void setBulletsExplode(boolean bulletsExplode) {
		this.bulletsExplode = bulletsExplode;
	}

	public Float getExplosionSize() {
		return explosionSize;
	}

	public void setExplosionSize(Float explosionSize) {
		this.explosionSize = explosionSize;
	}

	public Double getEntityDamageDistance() {
		return entityDamageDistance;
	}

	public void setEntityDamageDistance(Double entityDamageDistance) {
		this.entityDamageDistance = entityDamageDistance;
	}

	public Integer getHeadshotDamage() {
		return headshotDamage;
	}

	public void setHeadshotDamage(Integer headshotDamage) {
		this.headshotDamage = headshotDamage;
	}

	public Integer getBodyDamage() {
		return bodyDamage;
	}

	public void setBodyDamage(Integer bodyDamage) {
		this.bodyDamage = bodyDamage;
	}

	public void processGun(Gun gun) {
		setName(gun.getName());
		setReloadTime(gun.getReloadTime());
		setMaxClipSize(gun.getMaxClipSize());
		setLoadedInClip(gun.getLoadedInClip());
		setAmmo(gun.getAmmo());
		setMaxAmmo(gun.getMaxAmmo());
		setTimeBetweenFire(gun.getTimeBetweenFire());
		setAutoReload(gun.isAutoReload());
		setBulletsExplode(gun.getBulletsExplode());
		setExplosionSize(gun.getExplosionSize());
		setEntityDamageDistance(gun.getEntityDamageDistance());
		setHeadshotDamage(gun.getHeadshotDamage());
		setBodyDamage(gun.getBodyDamage());
	}

}
