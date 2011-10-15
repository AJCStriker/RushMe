package com.tips48.rushMe.data;

public class PlayerData {
	private final String name;
	private int score = 0;
	private int kills = 0;
	private int deaths = 0;

	public PlayerData(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void addKill() {
		kills++;
	}

	public void addDeath() {
		deaths++;
	}

}
