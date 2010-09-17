package com.pseudopattern.map.server;

import java.io.Serializable;

public class ImageData implements Serializable {
	
	private String color;
	
	private int param;
	
	private byte[] data;
	
	public ImageData(){
		
	}
	
	public ImageData(String name,int param,byte[] data){
		this.color = name;
		this.param = param;
		this.data = data;
	}

	public String getColor() {
		return color;
	}

	public int getParam() {
		return param;
	}

	public byte[] getData() {
		return data;
	}

}
