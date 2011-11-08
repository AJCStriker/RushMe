package com.tips48.rushMe;

public class GameMode {

	private final GameModeType type;
	private final String name;
	private final Integer time;
	private final Boolean respawn;
	private final Integer respawnTime;

	protected GameMode(String name, GameModeType type, Integer time,
			Boolean respawn, Integer respawnTime) {
		this.name = name;
		this.type = type;
		this.time = time;
		this.respawn = respawn;
		this.respawnTime = respawnTime;
	}

	public GameModeType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	public boolean shouldRespawn() {
		return respawn;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	@Override
	public String toString() {
		return name;
	}

}
