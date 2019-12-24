package com.hy.java.utility.math;

import java.math.BigDecimal;

import org.junit.Test;

public class VectorTest {
	@Test
	public void vector() {
		Vector vector_A = new Vector(3);
		vector_A.setCoordinate(1, 1);
		vector_A.setCoordinate(2, 2);
		vector_A.setCoordinate(3, -3);
		Vector vector_B = Vector.scalarMultiplication(BigDecimal.valueOf(-2), vector_A);
		Vector.print(vector_A);
		System.out.println();
		Vector.print(vector_B);
		System.out.println();
		Vector.print(Vector.vectorAddition(vector_A, vector_B));
		System.out.println();
		Vector.print(Vector.vectorSubtraction(vector_A, vector_B));
		System.out.println(Vector.dotProduct(vector_A, vector_B));
		Vector vector_C = new Vector(2);
		vector_C.setCoordinate(1, 1);
		vector_C.setCoordinate(2, 2);
		System.out.println((Vector.norm(vector_C).subtract(BigDecimal.valueOf(1.0))).doubleValue() / 2);
	}
}
