/*
 * Kyle Wong
 * 14.4.30
 * Machine Learning
 * kwong23
 * Assignment 6
 */
package cs475.RBM;
import java.util.Random;

import cs475.Util;
import cs475.RBM.*;

public class RBMEnergy {
	private RBMParameters _parameters;
	private int numSamples;
	private Random rand;
	private int numVisible; //num Visible nodes x m
	private int numHidden; //num Hidden nodes h n
	private double[] xt;
	private double[] ht;
	private double[] xm;
	// TODO: Add the required data structures and methods.

	public RBMEnergy(RBMParameters parameters, int numSamples) {
		this._parameters = parameters;
		this.numSamples = numSamples;
		this.rand = new Random(0);
		this.numVisible = parameters.numVisibleNodes();
		this.numHidden = parameters.numHiddenNodes();
		this.xt = new double[this.numVisible];
		this.ht = new double[this.numHidden];
		this.xm = new double[this.numVisible];
		// initialize xm and xt to start as xm
		for (int i = 0; i < this.numVisible; i++) {
			this.xm[i] = parameters.visibleNode(i);
			this.xt[i] = this.xm[i];
		}
		
		
	}
	
	public double computeMarginal() {
		int xcount = 0;
		for (int i = 0; i < this.numSamples; i++) {
			this.ht = sampleH();
			this.xt = sampleX();
			if (xMatches()) {
				xcount++;
			}
		}
		return 1.0 * xcount / this.numSamples;
	}
	
	private double[] sampleH() {
		double[] h = new double[this.numHidden]; //size of hidden
		for (int j = 0; j < this.numHidden; j++) {
			double[] wj = new double[this.numVisible];
			for (int i = 0; i < this.numVisible; i++) {
				wj[i] = this._parameters.weight(i, j);
			}
			double z = Util.dot(this.xt, wj) + this._parameters.hiddenBias(j);
			double p = sigma(z);
			double u = this.rand.nextDouble();
			if (u < (1-p)) {
				h[j] = 0;
			} else {
				h[j] = 1;
			}
		}
		return h;
	}
	
	private double[] sampleX() {
		double[] x = new double[this.numVisible]; //size of hidden
		for (int i = 0; i < this.numVisible; i++) {
			double[] wi = new double[this.numHidden];
			for (int j = 0; j < this.numHidden; j++) {
				wi[j] = this._parameters.weight(i, j);
			}
			double z = Util.dot(this.ht, wi) + this._parameters.visibleBias(i);
			double p = sigma(z);
			double u = this.rand.nextDouble();
			if (u < (1-p)) {
				x[i] = 0;
			} else {
				x[i] = 1;
			}
		}
		return x;
	}
	
	private double sigma(double z) {
		return 1.0 / (1 + Math.exp(-z));
	}
	
	private boolean xMatches() {
		for (int i = 0; i < this.numVisible; i++) {
			if (this.xt[i] != this.xm[i]) {
				return false;
			}
		}
		return true;
	}
	
//	private double[] sampleUsing(double[] ra) {
//		int given, result = 0;
//		boolean givenx = false;
//		if (ra.length == this.m) {// given visible x
//			given = this.m;
//			result = this.n; //hidden
//			givenx = true;
//		}
//		else { //given hidden
//			given = this.n; //hidden
//			result = this.m;
//		}
//		
//		double[] samples = new double[result];
//		double[] w = new double[given];
//		double u = 0.0;
//		double p = 0.0;
//		for (int i = 0; i < result; i++) {
//			u = this.rand.nextDouble();
//			for (int j = 0; j < given; j++) {
//				if (givenx) {
//					w[j] = this._parameters.weight(j, i);
//				} else { //given h
//					w[j] = this._parameters.weight(i, j);
//				}
//			}
//			
//			
//		}
//		return samples;
//	}
	

}
