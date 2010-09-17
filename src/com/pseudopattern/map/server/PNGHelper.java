package com.pseudopattern.map.server;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class PNGHelper {
	
	final CRC32 crc;
	
	ColorMap map;
	
	public PNGHelper(){
		crc = new CRC32();
	}
	
	private byte[] generatePalette(){
		byte[] ret = new byte[256*3];
		ByteBuffer yo = ByteBuffer.wrap(ret);
		for(int i=0;i<256;i++)
			yo.put(map.color(i));
	/*	System.out.println(yo.position());
		for(int i=0;i<256;i++){
			System.out.println(ret[3*i]+"\t"+ret[3*i+1]+"\t"+ret[3*i+2]);
		} */
		return ret;
	}
	
	private byte[] createChunk(CharSequence name,byte[] data){
		byte[] ret = new byte[4+4+data.length+4];
		ByteBuffer yo = ByteBuffer.wrap(ret);
		yo.putInt(data.length);
		yo.put(type(name));
		yo.put(data);
		crc.update(type(name));
		crc.update(data);
		//yo.putInt((int)crc.getValue());
		yo.put(convertLong(crc.getValue()));
		crc.reset();
		return ret;
	}
	
	private static byte[] convertLong(long yo){
		byte[] s = new byte[4];
		byte[] l = new byte[8];
		ByteBuffer p = ByteBuffer.wrap(l);
		p.mark();
		p.putLong(yo);
		s[0] = l[4];
		s[1] = l[5];
		s[2] = l[6];
		s[3] = l[7];
		return s;
	}
	
	public static byte[] type(CharSequence seq){
		byte[] ret = new byte[4];
		for(int i=0;i<4;i++)
			ret[i] = (byte)seq.charAt(i);
		return ret;
	}
	
	public void replaceColors(byte[] old,ColorMap map){
		this.map = map;
		ByteBuffer buffer = ByteBuffer.wrap(old);
		buffer.position(8);
		buffer.mark();
		int chunkSize = buffer.getInt();
		//System.out.println(chunkSize);
		buffer.position(20+chunkSize);
		/*System.out.println(buffer.getInt());
		buffer.position(20+chunkSize);*/
		byte[] plte = createChunk("PLTE",generatePalette());
		//System.out.println(ByteBuffer.wrap(plte).getInt());
		buffer.put(plte);
	}

}
