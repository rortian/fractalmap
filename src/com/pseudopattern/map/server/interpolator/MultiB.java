package com.pseudopattern.map.server.interpolator;

public class MultiB extends Interpolator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3264395461320296131L;
	Interpolator[] bs;
    double[] xs,scale;
    
    protected MultiB(){
    	
    }

    public MultiB(double[] x,double[] y){
        xs = x;
        bs = new Interpolator[xs.length-1];
        scale = new double[bs.length];
        for(int i=0;i<bs.length;i++){
            if(y[i]==y[i+1]){
                bs[i] = new Constant(y[i]);
            } else {
                bs[i] = new Bezier(y[i],y[i+1]);
            }
            
            scale[i] = 1/(x[i+1]-x[i]);
        }
    }

    @Override
    public double eval(double x) {
        for(int i=0;i<bs.length;i++){
            if(x<=xs[i+1]){
                return bs[i].eval((x-xs[i])*scale[i]);
            }
        }
        return -1;
    }
	
}
