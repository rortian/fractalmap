package com.pseudopattern.map.server.interpolator;

public class Bezier extends Interpolator {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2731059988422705562L;
	double x_zero,x_one,y_zero,y_one;
	
	protected Bezier(){
		
	}

    public Bezier(double[] x,double[] y){
        x_zero = x[0];
        x_one = x[1];
        y_zero = y[0];
        y_one = y[1];
    }

    public Bezier(double[] y){
        x_zero = 0;
        x_one = 1;
        y_zero = y[0];
        y_one = y[1];
    }

    public Bezier(double y1,double y2){
        x_zero = 0;
        x_one = 1;
        y_zero = y1;
        y_one = y2;
    }

    @Override
    public double eval(double x) {
        if(x == x_one)
            return y_one;
        if(x == x_zero)
            return y_zero;
        double t = findT(x);
        double tp = 1-t;
        double t2 = t*t;
        double t3= t2*t;
        double tp2 = tp*tp;
        double tp3 = tp2*tp;
        return y_zero*(tp3+3*tp2*t)+y_one*(3*tp*t2+t3);
    }


    public double findT(double x){
        double t = x;
        while(Math.abs(bx(t)-x)>1e-6){
            t = t - (bx(t)-x)/dbx(t);
        }
        return t;
    }

    private double bx(double t){
        double tp = 1-t;
        double t2 = t*t;
        double t3 = t2*t;
        double tp2 = tp*tp;
        double tp3 = tp2*tp;
        return tp3*x_zero+3*tp2*t*0.25+3*tp*t2*0.75+t3*x_one;
    }

    private double dbx(double t){
        double tp = 1-t;
        double t2 = t*t;
        double tp2 = tp*tp;
        return -3*tp2*x_zero+(3*tp2-6*tp*t)*0.25+(6*tp*t-3*t2)*0.75+3*t2*x_one;
    }
}
