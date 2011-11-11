package com.tips48.rushMe.custom.items;

import com.tips48.rushMe.RushMe;
import org.getspout.spoutapi.material.item.GenericCustomItem;

import javax.xml.stream.Location;

/**
 * Created by IntelliJ IDEA. User: aidan Date: 11/6/11 Time: 6:27 PM To change
 * this template use File | Settings | File Templates.
 */
public class Grenade extends GenericCustomItem {

	private GrenadeType type;
	private Integer startAmount;
	private Integer amount;

	private Integer explosionSize;
	private Integer timeBeforeExplosion;

	private Integer damage;

	private Integer stunTime;

	protected Grenade(String name, String texture, GrenadeType type,
			Integer startAmount, Integer explosionSize,
			Integer timeBeforeExplosion, Integer damage, Integer stunTime) {
		super(RushMe.getInstance(), name, texture);

		this.type = type;
		this.startAmount = startAmount;
		this.amount = startAmount;
		this.explosionSize = explosionSize;
		this.timeBeforeExplosion = timeBeforeExplosion;
		this.damage = damage;
		this.stunTime = stunTime;
	}

	public GrenadeType getType() {
		return type;
	}

	public Integer getStartAmount() {
		return startAmount;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Integer getExplosionSize() {
		return explosionSize;
	}

	public Integer getTimeBeforeExplosion() {
		return timeBeforeExplosion;
	}

	public Integer getDamage() {
		return damage;
	}

	public Integer getStunTime() {
		return stunTime;
	}

	public void fire(Location loc) {
		if (amount <= 0) {
			return;
		}
		
	}

}
