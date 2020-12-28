package com.hy.java.utility.math.geometry;

import java.math.BigDecimal;

public class Line {
	/**
	 * 直线方程：Ax+By+C=0
	 */
	public double A = 0.0;
	/**
	 * 直线方程：Ax+By+C=0
	 */
	public double B = 0.0;
	/**
	 * 直线方程：Ax+By+C=0
	 */
	public double C = 0.0;
	/**
	 * 斜率
	 * <p>
	 * （直线方程：y=kx+b）
	 */
	public BigDecimal k = null;
	/**
	 * 纵截距
	 * <p>
	 * （直线方程：y=kx+b）
	 */
	public BigDecimal b = null;

	/**
	 * 直线方程：Ax+By+C=0
	 * <p>
	 * 如果B==0，则k和b都是<code>null</code>。如果B!=0，则k=-A/B，b=-C/B
	 */
	public Line(double A, double B, double C) {
		if (A == 0.0 && B == 0.0) {
			System.err.println("A and B are 0");
		} else {
			this.A = A;
			this.B = B;
			this.C = C;
			if (B == 0.0) {
				this.k = null;
				this.b = null;
			} else {
				this.k = new BigDecimal(-A / B);
				this.b = new BigDecimal(-C / B);
			}
		}
	}

	public Line() {

	}

	/**
	 * 判断点point是否在直线line上
	 */
	public static boolean ifPointOnLine(Point point, Line line) {
		return line.A * point.x + line.B * point.y + line.C == 0;
	}

	/**
	 * 计算点point到直线line的距离
	 */
	public static double pointToLine(Point point, Line line) {
		return Math.abs(line.A * point.x + line.B * point.y + line.C) / Math.sqrt(Math.pow(line.A, 2) + Math.pow(line.B, 2));
	}
}
