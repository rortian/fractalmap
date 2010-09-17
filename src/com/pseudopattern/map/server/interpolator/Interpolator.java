package com.pseudopattern.map.server.interpolator;

import java.io.Serializable;

public class Interpolator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2634451894311732928L;

	public Interpolator(){
		
	}
	
	public static Interpolator get(double[] x,double[] y){
        assert(x.length==y.length);
        if(y.length==1)
            return new Constant(y[0]);
        if(x.length==2)
            return new Bezier(x,y);
        if(x.length==3)
            return new DoubleB(x,y);
        return new MultiB(x,y);
    }
	
	 public double eval(double x){
		 return 0;
	 }

}
