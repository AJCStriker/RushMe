package com.tips48.rushMe;

public enum GameMode {
	RUSH, CONQUEST;

	public static String toString(GameMode mode) {
		switch (mode) {
			case RUSH:
				return "Rush";
			case CONQUEST:
				return "Conquest";
		}
		return "Unknown";
	}
	}
