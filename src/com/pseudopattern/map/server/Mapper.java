package com.pseudopattern.map.server;

public class Mapper {
	
	final int m,n;
	final Complex lambda;
	final boolean same;
	
	public Mapper(Complex l,int m,int n){
		this.m = m;
		this.n = n;
		lambda = l;
		same = (m == n);
	}
	
	public Mapper(Complex l,int n){
		same = true;
		this.m = n;
		this.n = n;
		lambda = l;
	}
	
	public Complex step(Complex in){
		if(same){
			Complex raised = in.power(n);
			return raised.plus(lambda.times(raised.inverse()));
		} else {
			Complex mth,nth;
			mth = in.power(m);
			nth = in.power(n);
			return mth.plus(lambda.times(nth.inverse()));
		}
	}
	
	public int escape(Complex in,int max){
		int times = 0;
		Complex temp = in;
		while(temp.quickMag()<9){
			temp = step(temp);
			times++;
			if(times==max)
				return max; 
		}
		return times;
	}

}
