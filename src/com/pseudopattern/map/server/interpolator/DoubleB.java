package com.pseudopattern.map.server.interpolator;

public class DoubleB extends Interpolator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4627794097296466637L;
	Bezier one,two;
    double mid,fact1,fact2;
    
    protected DoubleB(){
    	
    }

    public DoubleB(double[] xs,double[] ys){
        mid = xs[1];
        double[] first = new double[2];
        first[0] = ys[0];
        first[1] = ys[1];
        one = new Bezier(first);
        first[0] = ys[1];
        first[1] = ys[2];
        two = new Bezier(first);
        fact1 = 1/mid;
        fact2 = 1/(1-mid);
    }



    @Override
    public double eval(double x) {
        if(x<=mid){
            return one.eval(x*fact1);
        } else {
            return two.eval((x-mid)*fact2);
        }
    }
	
}
