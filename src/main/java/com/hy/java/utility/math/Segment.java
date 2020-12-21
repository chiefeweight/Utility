package com.hy.java.utility.math;

public class Segment extends Line {
	public Point pt1;
	public Point pt2;
	public double length;

	public Segment(Point pt1, Point pt2) {
		super(pt2.y - pt1.y, pt1.x - pt2.x, pt2.x * pt1.y - pt1.x * pt2.y);
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.length = Math.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2));
	}
}
