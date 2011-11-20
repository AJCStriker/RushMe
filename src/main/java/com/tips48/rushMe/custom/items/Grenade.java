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

package com.tips48.rushMe.custom.items;

import com.tips48.rushMe.RushMe;

import org.getspout.spoutapi.material.item.GenericCustomItem;

import javax.xml.stream.Location;

public class Grenade extends GenericCustomItem {

	private GrenadeType type;
	private Integer startAmount;
	private Integer amount;

	private final String shortName;

	private Integer explosionSize;
	private Integer timeBeforeExplosion;

	private Integer damage;

	private Integer stunTime;

	protected Grenade(String name, String shortName, String texture,
			GrenadeType type, Integer startAmount, Integer explosionSize,
			Integer timeBeforeExplosion, Integer damage, Integer stunTime) {
		super(RushMe.getInstance(), name, texture);

		this.shortName = shortName;

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

	public String getShortName() {
		return shortName;
	}

}
