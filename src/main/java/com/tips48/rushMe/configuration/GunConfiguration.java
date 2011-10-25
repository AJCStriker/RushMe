package com.tips48.rushMe.configuration;

import com.tips48.rushMe.RushMe;
import org.bukkit.configuration.file.YamlConfiguration;
import org.getspout.spoutapi.SpoutManager;

import java.io.File;
import java.io.IOException;

public class GunConfiguration {
	private static File gunsFile;
	private static YamlConfiguration guns;

	public static void loadGuns() {
		gunsFile = new File(RushMe.getInstance().getDataFolder()
				+ File.separator + "guns.yml");
		guns = YamlConfiguration.loadConfiguration(gunsFile);
		if (!gunsFile.exists()) {
			try {
				if (gunsFile.getParentFile() != null) {
					gunsFile.getParentFile().mkdirs();
				}
				gunsFile.createNewFile();
				addGunDefaults();
			} catch (IOException e) {
				RushMe.log(true, "Error creating file " + gunsFile.getName());
			}
			guns.options().copyDefaults(true);
		}
		for (String name : guns.getConfigurationSection("Guns").getKeys(false)) {
			String texture = guns.getString("Guns." + name + ".image");
			Integer reloadTime = guns.getInt("Guns." + name + ".reloadTime");
			Boolean autoReload = guns
					.getBoolean("Guns." + name + ".autoReload");
			Integer maxClipSize = guns.getInt("Guns." + name + ".maxClipSize");
			Integer maxAmmo = guns.getInt("Guns." + name + ".maxAmmo");
			Double timeBetweenFire = guns.getDouble("Guns." + name
					+ ".timeBetweenFire");
			Boolean bulletsExplode = guns.getBoolean("Guns." + name
					+ ".bulletsExplode");
			Float explosionSize = Double.valueOf(
					guns.getDouble("Guns." + name + ".explosionSize"))
					.floatValue();
			Double entityExplosionRadius = guns.getDouble("Guns." + name
					+ ".entityExplosionRadius");
			Integer headshotDamage = guns.getInt("Guns." + name
					+ ".headshotDamage");
			Integer bodyDamage = guns.getInt("Guns." + name + ".bodyDamage");
			Double recoilBack = guns.getDouble("Guns." + name + ".recoilBack");
			Float recoilVertical = Double.valueOf(
					guns.getDouble("Guns." + name + ".recoilHorizontal"))
					.floatValue();
			Float recoilHorizontal = Double.valueOf(
					guns.getDouble("Guns." + name + ".recoilHorizontal"))
					.floatValue();
			SpoutManager.getFileManager().addToPreLoginCache(
					RushMe.getInstance(), texture);
			RushMe.getInstance()
					.getGunManager()
					.createGun(name, texture, reloadTime, autoReload,
							maxClipSize, maxAmmo, timeBetweenFire,
							bulletsExplode, explosionSize,
							entityExplosionRadius, headshotDamage, bodyDamage,
							recoilBack, recoilVertical, recoilHorizontal);
		}
	}

	private static void addGunDefaults() {
		// M9
		guns.set("Guns.M9.image", "http://i.imgur.com/R4TMM.png");
		guns.set("Guns.M9.reloadTime", 60);
		guns.set("Guns.M9.autoReload", false);
		guns.set("Guns.M9.maxClipSize", 18);
		guns.set("Guns.M9.maxAmmo", 72);
		guns.set("Guns.M9.timeBetweenFire", 1D);
		guns.set("Guns.M9.bulletsExplode", false);
		guns.set("Guns.M9.explosionSize", "null");
		guns.set("Guns.M9.entityExplosionRadius", "null");
		guns.set("Guns.M9.headshotDamage", 10);
		guns.set("Guns.M9.bodyDamage", 5);
		guns.set("Guns.M9.recoilBack", 0.8D);
		guns.set("Guns.M9.recoilVertical", 3F);
		guns.set("Guns.M9.recoilHorizontal", 0.001F);
		// AK - 47
		guns.set("Guns.AK - 47.image", "http://i.imgur.com/Ok52I.png");
		guns.set("Guns.AK - 47.reloadTime", 20);
		guns.set("Guns.AK - 47.autoReload", false);
		guns.set("Guns.AK - 47.maxClipSize", 30);
		guns.set("Guns.AK - 47.maxAmmo", 120);
		guns.set("Guns.AK - 47.timeBetweenFire", 1D);
		guns.set("Guns.AK - 47.bulletsExplode", false);
		guns.set("Guns.AK - 47.explosionSize", "null");
		guns.set("Guns.AK - 47.entityExplosionRadius", "null");
		guns.set("Guns.AK - 47.headshotDamage", 15);
		guns.set("Guns.AK - 47.bodyDamage", 10);
		guns.set("Guns.AK - 47.recoilBack", 0.15D);
		guns.set("Guns.AK - 47.recoilVertical", 5F);
		guns.set("Guns.AK - 47.recoilHorizontal", 0.2F);
		// Bazooka
		guns.set("Guns.Bazooka.image", "http://i.imgur.com/8qmk0.png");
		guns.set("Guns.Bazooka.reloadTime", 100);
		guns.set("Guns.Bazooka.autoReload", true);
		guns.set("Guns.Bazooka.maxClipSize", 1);
		guns.set("Guns.Bazooka.maxAmmo", 3);
		guns.set("Guns.Bazooka.timeBetweenFire", 1D);
		guns.set("Guns.Bazooka.bulletsExplode", true);
		guns.set("Guns.Bazooka.explosionSize", 2F);
		guns.set("Guns.Bazooka.entityExplosionRadius", 15D);
		guns.set("Guns.Bazooka.headshotDamage", "null");
		guns.set("Guns.Bazooka.bodyDamage", "null");
		guns.set("Guns.Bazooka.recoilBack", 2D);
		guns.set("Guns.Bazooka.recoilVertical", 15F);
		guns.set("Guns.Bazooka.recoilHorizontal", 0F);

		try {
			guns.save(gunsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
