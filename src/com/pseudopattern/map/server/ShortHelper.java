package com.pseudopattern.map.server;

public class ShortHelper {
	
	private static int whole = 65535;
	
	private static int small = 255;
	
	public static short f2s(float num){
		int trans = (int) Math.floor(whole*num);
		if(trans <= Short.MAX_VALUE){
			return (short) trans;
		}
		return (short) (trans-65536);
	}
	
	public static short d2s(double num){
		return f2s((float)num);
	}
	
	public static byte d2b(double num){
		int trans = (int) Math.floor(small*num);
		if(trans <= Byte.MAX_VALUE)
			return (byte) trans;
		return (byte)(trans-256);
	}
	
	
	
	public static void main(String[] args){
		for(int i=0;i<101;i++){
			float curr = i*0.01f;
			System.out.println(curr+":\t"+f2s(curr));
		}
	}

}
