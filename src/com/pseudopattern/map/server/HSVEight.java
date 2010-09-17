package com.pseudopattern.map.server;

import java.nio.ByteBuffer;

public class HSVEight {
	
	
	private static final short zero = (short) 0;
	private static final short ffff = (short) -1;
	private static final short eighty = (short)Short.MIN_VALUE;
	
	public static final byte[] RED = convert(red());
	public static final byte[] ORANGE = convert(orange());
	public static final byte[] YELLOW = convert(yellow());
	public static final byte[] YELLOWGREEN = convert(yellowGreen());
	public static final byte[] GREEN = convert(green());
	public static final byte[] AQUAGREEN = convert(aquaGreen());
	public static final byte[] BABYBLUE = convert(babyBlue());
	public static final byte[] LIGHTBLUE = convert(lightBlue());
	public static final byte[] BLUE = convert(blue());
	public static final byte[] PURPLE = convert(purple());
	public static final byte[] MAGENTA = convert(magenta());
	public static final byte[] PINK = convert(pink());
	public static final byte[] WHITE = convert(white());
	public static final byte[] BLACK = convert(black());
	
	public static byte[] convert(byte[] six){
		if(six.length!=6)
			return null;
		byte[] ret = new byte[3];
		ret[0] = six[0];
		ret[1] = six[2];
		ret[2] = six[4];
		return ret;
	}
	
	
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
