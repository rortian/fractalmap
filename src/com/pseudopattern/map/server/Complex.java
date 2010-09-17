package com.pseudopattern.map.server;

public class Complex {
	
	final double x,y;
	
	public static final Complex ONE = new Complex(1,0);
	public static final Complex I = new Complex(0,1);
	public static final Complex INFINITY = new Complex(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	
	public Complex(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "" + x + " + " + y + "i";
	}
	
	public Complex plus(Complex that){
		return new Complex(x+that.x,y+that.y);
	}
	
	public Complex times(double scalar){
		return new Complex(scalar*x,scalar*y);
	}
	
	public Complex times(Complex that){
		return new Complex(x*that.x-y*that.y,x*that.y+y*that.x);
	}
	
	public double quickMag(){
		return x*x + y*y;
	}
	
	public double abs(){
		return Math.sqrt(x*x+y*y);
	}
	
	public Complex conjugate(){
		return new Complex(x,-y);
	}
	
	public Complex inverse(){
		if(quickMag()==0.0){
			return INFINITY;
		} else {
			return conjugate().times(1/quickMag());
		}
	}
	
	public Complex squared(){
		return times(this);
	}
	
	public Complex power(int n){
		switch (n){
		case 0:
			return ONE;
		case 1:
			return this;
		case 2:
			return squared();
		case 3:
			return times(squared());
		}
		if(n<0)
			return inverse().power(-n);
		Complex fourth,sq;
		sq = squared();
		fourth = sq.squared();
		if(n==4)
			return fourth;
		return fourth.times(power(n-4));
	}

}
