package com.tips48.rushMe.custom.blocks;

public class BlockManager {
	
	public static Flag flag;
	public static FlagBase flagBase;
	public static MCOM mcom;
	
	public static void init() {
		flag = new Flag();
		flagBase = new FlagBase();
		mcom = new MCOM();
	}

}
