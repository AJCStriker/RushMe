package com.tips48.rushMe.util;

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
			CustomItem i = SpoutManager.getMaterialManager().getCustomItem(inHand);
			if (RushMe.getInstance().getGunManager().getGun(i) != null) {
				return true;
			}
		}
		return false;
	}

	public static Gun getGun(Player player) {
		SpoutPlayer p = SpoutManager.getPlayer(player);
		ItemStack inHand = p.getItemInHand();
		CustomItem i = SpoutManager.getMaterialManager().getCustomItem(inHand);
		return RushMe.getInstance().getGunManager().getGun(i);
	}

}
