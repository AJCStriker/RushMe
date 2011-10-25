package com.tips48.rushMe.data;

import com.tips48.rushMe.custom.blocks.Flag;

import java.util.HashSet;
import java.util.Set;

//import com.tips48.rushMe.teams.Team;

public class ConquestData {
	private Set<Flag> flags = new HashSet<Flag>();

	public Set<Flag> getFlags() {
		return flags;
	}

	/*
		  * public Set<Flag> getFlags(Team team) { Set<Flag> f = new HashSet<Flag>();
		  * for (Flag flag : getFlags()) { if (flag.getOwner().equals(team)) {
		  * f.add(flag); } } return f.isEmpty() ? null : f; }
		  */
}
