package com.hy.java.utility.math;

import org.junit.jupiter.api.Test;

public class LineTest {
	@Test
	public void distanceTest() {
		Point point = new Point(6.0, -4.0);
		Segment segment = new Segment(new Point(1.0, -1.0 / 8.0), new Point(0.0, 5.0 / 8.0));
		System.out.println("点(" + point.x + "," + point.y + ")到直线"+"y=" + segment.k.doubleValue() + "x+" + segment.b.doubleValue()+"的距离为" + Line.pointToLine(point, segment));
	}
}
