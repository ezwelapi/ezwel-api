package com.ezwel.htl.interfaces.server.commons.constants;

public class PatternConstants {

	
	public final static String PATTERN_NUMBER;
	public final static String PATTERN_NOT_NUMBER;
	
	static {
		PATTERN_NUMBER = "\\d"; //[0-9]
		PATTERN_NOT_NUMBER = "[^\\d]"; //[^0-9]
	}
}
