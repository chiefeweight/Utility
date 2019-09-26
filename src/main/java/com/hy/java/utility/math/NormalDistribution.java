package com.hy.java.utility.math;

import java.util.Random;

import com.hy.java.utility.common.Pair;

public final class NormalDistribution {
	private final double mean;
	private final double variance;
	private final double standard_deviation;
	private final Random rnd_generator1;
	private final Random rnd_generator2;

	/**
	 * 返回一个服从正态分布N(mean,variance)的随机数；或返回一对服从联合正态分布的随机数
	 * 
	 * @param mean                    均值
	 * @param variance                方差
	 * @param random_number_generator 随机数发生器。如果为null，则默认使用{@code Random}产生种子
	 */
	public NormalDistribution(double mean, double variance, Random random_number_generator) {
		if (variance < 0.0) {
			throw new IllegalArgumentException("Variance must be non-negative value.");
		}
		this.mean = mean;
		this.variance = variance;
		this.standard_deviation = Math.sqrt(this.variance);
		if (random_number_generator == null) {
			random_number_generator = new Random();
		}
		this.rnd_generator1 = random_number_generator;
		this.rnd_generator2 = random_number_generator;
	}

	/**
	 * 返回一个服从正态分布N(mean,variance)的随机数
	 * 
	 * @return x
	 */
	public double nextRandom() {
		double rnd1 = 0.0;
		while (rnd1 == 0.0) {
			rnd1 = this.rnd_generator1.nextDouble();
		}
		double r = Math.sqrt(-2.0 * Math.log(rnd1));
		double rnd2 = this.rnd_generator2.nextDouble();
		return r * Math.cos(2.0 * Math.PI * rnd2) * this.standard_deviation + this.mean;
	}

	/**
	 * 返回一对服从联合正态分布的随机数
	 * 
	 * @return (x,y)
	 */
	public Pair<Double, Double> nextXYRandom() {
		double rnd1 = 0.0;
		while (rnd1 == 0.0) {
			rnd1 = rnd_generator1.nextDouble();
		}
		double r = Math.sqrt(-2.0 * Math.log(rnd1));
		double rnd2 = this.rnd_generator2.nextDouble();
		double x = r * Math.cos(2.0 * Math.PI * rnd2) * this.standard_deviation + this.mean;
		double y = r * Math.sin(2.0 * Math.PI * rnd2) * this.standard_deviation + this.mean;
		return Pair.createPair(Double.valueOf(x), Double.valueOf(y));
	}
}
