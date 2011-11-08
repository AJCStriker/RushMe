package com.tips48.rushMe.configuration;

import com.tips48.rushMe.RushMe;
import com.tips48.rushMe.custom.items.GunManager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.getspout.spoutapi.SpoutManager;

import java.io.File;
import java.util.logging.Level;

public class GunConfiguration {
	private static File gunsFile;
	private static YamlConfiguration guns;

	/**
	 * Loads all guns from the configuration file guns.yml
	 */
	public static void loadGuns() {
		gunsFile = new File(RushMe.getInstance().getDataFolder()
				+ File.separator + "guns.yml");
		guns = YamlConfiguration.loadConfiguration(RushMe.getInstance()
				.getResource("guns.yml"));
		if (!(gunsFile.exists())) {
			if (!(gunsFile.getParentFile().exists())) {
				if (!(gunsFile.getParentFile().mkdirs())) {
					RushMe.log(Level.SEVERE, true, "Error creating the folder "
							+ gunsFile.getParentFile().getName());
				}
			}
			try {
				if (!(gunsFile.createNewFile())) {
					RushMe.log(Level.SEVERE, true, "Error creating the file "
							+ gunsFile.getName());
				}
			} catch (Exception e) {
				RushMe.log(Level.SEVERE, true, "Error creating the file "
						+ gunsFile.getName());
			}
			guns.options().copyDefaults(true);
			try {
				guns.save(gunsFile);
			} catch (Exception e) {
				RushMe.log(Level.SEVERE, true,
						"Error saving to " + gunsFile.getName());
			}
			RushMe.log(Level.INFO, true, "Created " + gunsFile.getName());
		}
		guns = YamlConfiguration.loadConfiguration(gunsFile);
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
			GunManager.createGun(name, texture, reloadTime, autoReload,
					maxClipSize, maxAmmo, timeBetweenFire, bulletsExplode,
					explosionSize, entityExplosionRadius, headshotDamage,
					bodyDamage, recoilBack, recoilVertical, recoilHorizontal);
		}
	}
}
