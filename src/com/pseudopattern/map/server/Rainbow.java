package com.pseudopattern.map.server;

import java.util.LinkedList;
import java.util.List;


public class Rainbow implements ColorMap {
	
	public static final ColorMap map = new Rainbow();
	
	byte[][] arrays;
	
	private Rainbow(){
		List<byte[]> colors = new LinkedList<byte[]>();
		colors.add(HSVEight.RED);
		colors.add(HSVEight.ORANGE);
		colors.add(HSVEight.YELLOW);
		colors.add(HSVEight.YELLOWGREEN);
		colors.add(HSVEight.GREEN);
		colors.add(HSVEight.AQUAGREEN);
		colors.add(HSVEight.BABYBLUE);
		colors.add(HSVEight.LIGHTBLUE);
		colors.add(HSVEight.BLUE);
		colors.add(HSVEight.PURPLE);
		colors.add(HSVEight.MAGENTA);
		colors.add(HSVEight.PINK);
		arrays = new byte[colors.size()][];
		for(int i=0;i<arrays.length;i++){
			arrays[i] = colors.get(i);
		}
	}

	@Override
	public byte[] color(int i) {
		return arrays[i % arrays.length];
	}

}
