package com.pseudopattern.map.server;

import java.io.Serializable;
import java.nio.ByteBuffer;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pseudopattern.map.server.interpolator.Interpolator;

@PersistenceCapable
public class AColorSpectrum implements Serializable  {
	
	@PrimaryKey
	private String name;
	
	@Persistent(serialized = "true")
	private Interpolator red;
	
	@Persistent(serialized = "true")
	private Interpolator green;
	
	@Persistent(serialized = "true")
	private Interpolator blue;
	
	public AColorSpectrum(String name,Interpolator red,Interpolator green,Interpolator blue) {
		this.name = name;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public ColorMap getMap(int n){
		byte[][] bytes = new byte[n][3];
		for(int i=0;i<n;i++){
			double current = (1.0*i)/(n-1);
			ByteBuffer bb = ByteBuffer.wrap(bytes[i]);
			bb.put(ShortHelper.d2b(getRed().eval(current)));
			bb.put(ShortHelper.d2b(getGreen().eval(current)));
			bb.put(ShortHelper.d2b(getBlue().eval(current)));
		}
		SimpleMap sm = new SimpleMap(bytes);
		return sm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Interpolator getRed() {
		return red;
	}

	public void setRed(Interpolator red) {
		this.red = red;
	}

	public Interpolator getGreen() {
		return green;
	}

	public void setGreen(Interpolator green) {
		this.green = green;
	}

	public Interpolator getBlue() {
		return blue;
	}

	public void setBlue(Interpolator blue) {
		this.blue = blue;
	}

	public static void main(String[] args){
		double[] xs = {0.0,0.33,0.67,1.0};
        double[] ys = {0.0,0.0,1.0,1.0};
        Interpolator red = Interpolator.get(xs, ys);
        double[] gxs = {0.0,0.67,1.0};
        double[] gys = {0.0,0.0,1.0};
        Interpolator green = Interpolator.get(gxs, gys);
        double[] bxs = {0.0,0.33,1.0};
        double[] bys = {0.0,1.0,1.0};
        Interpolator blue = Interpolator.get(bxs, bys);
        AColorSpectrum test = new AColorSpectrum("hi",red,green,blue);
        ColorMap map = test.getMap(20);
        for(int i=0;i<20;i++){
        	for(byte b : map.color(i))
        		System.out.print(b+"\t");
        	System.out.println();
        }
	}
	

}
