package com.hy.java.utility.math;

public class Point {
	/**
	 * 点(x,y)
	 */
	public double x = 0.0;
	/**
	 * 点(x,y)
	 */
	public double y = 0.0;

	/**
	 * 点(x,y)
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 计算两点之间的距离
	 */
	public static double pointToPoint(Point point1, Point point2) {
		return Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
	}

	/**
	 * 判断该点是否在直线line上
	 */
	public boolean isOnLine(Line line) {
		return line.A * x + line.B * y + line.C == 0;
	}

	/**
	 * 计算该点到直线line的距离
	 */
	public double distanceToLine(Line line) {
		return Math.abs(line.A * x + line.B * y + line.C) / Math.sqrt(Math.pow(line.A, 2) + Math.pow(line.B, 2));
	}
}
