package com.pseudopattern.map.server;

import java.nio.ByteBuffer;

public class HSVColors {
	
	byte[][] table;
	
	private static final short zero = (short) 0;
	private static final short ffff = (short) -1;
	private static final short eighty = (short)128;
	
	public static final byte[] RED = red();
	public static final byte[] ORANGE = orange();
	public static final byte[] YELLOW = yellow();
	public static final byte[] YELLOWGREEN = yellowGreen();
	public static final byte[] GREEN = green();
	public static final byte[] AQUAGREEN = aquaGreen();
	public static final byte[] BABYBLUE = babyBlue();
	public static final byte[] LIGHTBLUE = lightBlue();
	public static final byte[] BLUE = blue();
	public static final byte[] PURPLE = purple();
	public static final byte[] MAGENTA = magenta();
	public static final byte[] PINK = pink();
	public static final byte[] WHITE = white();
	public static final byte[] BLACK = black();
	
	
	private static byte[] red(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(zero);
		red.putShort(zero);
		return r; 
	}
	
	private static byte[] orange(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(eighty);
		red.putShort(zero);
		return r; 
	}
	
	private static byte[] yellow(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(ffff);
		red.putShort(zero);
		return r; 
	}
	
	private static byte[] yellowGreen(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(eighty);
		red.putShort(ffff);
		red.putShort(zero);
		return r; 
	}
	
	private static byte[] green(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(ffff);
		red.putShort(zero);
		return r; 
	}
	
	private static byte[] aquaGreen(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(ffff);
		red.putShort(eighty);
		return r; 
	}
	
	private static byte[] babyBlue(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(ffff);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] lightBlue(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(eighty);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] blue(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(zero);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] purple(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(eighty);
		red.putShort(zero);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] magenta(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(zero);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] pink(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(zero);
		red.putShort(eighty);
		return r; 
	}
	
	private static byte[] white(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(ffff);
		red.putShort(ffff);
		red.putShort(ffff);
		return r; 
	}
	
	private static byte[] black(){
		byte[] r = new byte[6];
		ByteBuffer red = ByteBuffer.wrap(r);
		red.putShort(zero);
		red.putShort(zero);
		red.putShort(zero);
		return r; 
	}
	

}
