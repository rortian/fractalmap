package com.pseudopattern.map.server;

import java.nio.ByteBuffer;

public class ByteUtility {
	
	byte[] allBytes;
	
	public static final ByteUtility UTILITY = new ByteUtility();
	
	private ByteUtility(){
		allBytes = new byte[256];
		byte[] temp = new byte[2];
		ByteBuffer tempBuff = ByteBuffer.wrap(temp);
		for(int i=0;i<256;i++){
			tempBuff.putShort((short)i);
			allBytes[i] = temp[1];
			tempBuff.clear();
		}
	}
	
	public byte get(int i){
		return allBytes[i];
	}
	
	public static void main(String[] args){
		for(int i=0;i<256;i++){
			System.out.println(i+"\t"+UTILITY.get(i));
		}
	}

}
