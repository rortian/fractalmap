package com.pseudopattern.map.server;

import java.io.Serializable;

public class SimpleMap implements Serializable,ColorMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1606194851968979321L;
	/**
	 * 
	 */

	
	private byte[][] bytes;
	
	protected SimpleMap(){
		
	}
	
	public SimpleMap(byte[][] in){
		bytes = in;
	}

	@Override
	public byte[] color(int i) {
		return bytes[i % bytes.length];
	}
	
	
}
