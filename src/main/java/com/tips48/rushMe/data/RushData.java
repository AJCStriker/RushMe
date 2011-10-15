package com.tips48.rushMe.data;

import java.util.HashSet;
import java.util.Set;

import com.tips48.rushMe.custom.blocks.MCOM;

public class RushData {

	private Set<MCOM> mcoms = new HashSet<MCOM>();

	public Set<MCOM> getMCOMS() {
		return mcoms;
	}

	public Set<MCOM> getArmedMCOMS() {
		Set<MCOM> armed = new HashSet<MCOM>();
		for (MCOM m : getMCOMS()) {
			if (m.isArmed()) {
				armed.add(m);
			}
		}
		return armed.isEmpty() ? null : armed;
	}

}
