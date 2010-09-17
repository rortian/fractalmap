package com.pseudopattern.map.server.interpolator;

public class Constant extends Interpolator {
	    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 6966889300733603553L;
		double same;
	    
	    protected Constant(){
	    	
	    }
	    
	    public Constant(double y){
	        same = y;
	    }

	    @Override
	    public double eval(double x) {
	        return same;
	    }
	            
	

}
