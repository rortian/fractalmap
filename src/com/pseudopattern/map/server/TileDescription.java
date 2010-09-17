package com.pseudopattern.map.server;

import java.io.Serializable;

public class TileDescription implements Serializable {
	
	private int m,n,zoom,x_id,y_id;
	
	private double real,imag;
	
	public TileDescription(){
		
	}
	
	public TileDescription(int m,int n,int zoom,int x_id,int y_id,double real,double imag){
		this.m = m;
		this.n = n;
		this.zoom = zoom;
		this.x_id = x_id;
		this.y_id = y_id;
		this.real = real;
		this.imag = imag;
	}
	

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int getZoom() {
		return zoom;
	}

	public int getX_id() {
		return x_id;
	}

	public int getY_id() {
		return y_id;
	}

	public double getReal() {
		return real;
	}

	public double getImag() {
		return imag;
	}
	
	

}
