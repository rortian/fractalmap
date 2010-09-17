package com.pseudopattern.map.server;


import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class PNG {
	
	private static final ByteUtility help = ByteUtility.UTILITY;
	
	public static final byte[] BEGIN = firstEight();
	
	final CRC32 crc;
	
	final Deflater deflater;
	
	OutputStream out;
	
	ColorMap map;
	
	byte[] dots;
	
	public PNG(OutputStream outie){
		crc = new CRC32();
		out = outie;
		map = Rainbow.map;
		deflater = new Deflater(8);
	}
	
	public PNG(OutputStream outie,ColorMap map){
		crc = new CRC32();
		out = outie;
		this.map = map;
		deflater = new Deflater(8);
	}
	
	public void writeFile(){
		writeStart();
		writeIDHR();
		writePLTE();
		writeIDAT();
		writeEnd();
	}
	
	private void writeIDAT(){
		try {
			out.write(createChunk("IDAT",compress(dots)));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void setColors(ColorMap m){
		map = m;
	}
	
	public void setData(short[][] data){
		dots = new byte[(data.length+1)*data[0].length];
		ByteBuffer what = ByteBuffer.wrap(dots);
		for(int i=0;i<data.length;i++){
			what.put(help.get(0));
			for(int j=0;j<data[0].length;j++){
				what.put(help.get(data[i][j]-1));
				
			}
			
		}
	}
	
	public byte[] compress(byte[] data){
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream hel = new ByteArrayOutputStream();
		DeflaterOutputStream yo = new DeflaterOutputStream(hel,deflater);
		try {
			yo.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return hel.toByteArray();
	}
	
	private void writePLTE(){
		try {
			out.write(createChunk("PLTE",generatePalette()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private void writeIDHR(){
		try {
			out.write(chuckIHDR());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] chuckIHDR(){
		byte[] data = new byte[13];
		ByteBuffer yo = ByteBuffer.wrap(data);
		//Width
		yo.putInt(256);
		//Height
		yo.putInt(256);
		//Bit depth
		yo.put(help.get(8));
		//Colour Type
		yo.put(help.get(3));
		//Compression method
		yo.put(help.get(0));
		//Filter method
		yo.put(help.get(0));
		//Interlace method
		yo.put(help.get(0));
		return createChunk("IHDR",data);
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
	
	private byte[] createEmpty(CharSequence name){
		byte[] ret = new byte[4+4+4];
		ByteBuffer yo = ByteBuffer.wrap(ret);
		yo.putInt(0);
		yo.put(type(name));
		crc.update(ret,4,4);
		yo.putInt((int)crc.getValue());
		crc.reset();
		return ret;
	}
	
	private void writeStart(){
		try {
			out.write(BEGIN);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeEnd(){
		try {
			out.write(createEmpty("IEND"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] type(CharSequence seq){
		byte[] ret = new byte[4];
		for(int i=0;i<4;i++)
			ret[i] = (byte)seq.charAt(i);
		return ret;
	}
	
	private static byte[] firstEight(){
		byte[] ret = new byte[8];
		ret[0] = help.get(137);
		ret[1] = help.get(80);
		ret[2] = help.get(78);
		ret[3] = help.get(71);
		ret[4] = help.get(13);
		ret[5] = help.get(10);
		ret[6] = help.get(26);
		ret[7] = help.get(10);
		return ret;
	}
	
	
	
	
	
/*	public static void main(String[] args){
		FileOutputStream f = null;
		try {
			f = new FileOutputStream("C:\\pngexp\\what"+Math.round(Math.floor(Math.random()*100))+".png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PNG png = new PNG(f);
		Mapper m = new Mapper(new Complex(0,0.125),3);
		short[][] grid = new short[256][256];
		int max=0,min=1000;
		
		
		double delta = 1.0/256.0;
		Complex center = new Complex(0.5,-0.5);
		Complex temp = new Complex(-delta,delta);
		Complex init = center.plus(temp.times(128.5));
		Complex delta_x = Complex.ONE.times(delta);
		Complex delta_y = Complex.I.times(-delta);
		for(int j=0;j<256;j++){
			for(int i=0;i<256;i++){
				temp = init.plus(delta_x.times(i)).plus(delta_y.times(j));
				grid[j][i] = (short) m.escape(temp, 256);
			}
		}
		for(int i=0;i<256;i++){
			for(int j=0;j<256;j++){
				if(grid[i][j]>max){
					max = grid[i][j];
				}
				if(grid[i][j]<min){
					min = grid[i][j];
				}
			}
		}
		System.out.println("max:\t"+max);
		System.out.println("min:\t"+min);
		png.setData(grid);
		System.out.println(png.dots.length);
		png.writeFile();
		try {
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} */

}
