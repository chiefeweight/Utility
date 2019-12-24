package com.hy.java.utility.math;

import java.math.BigDecimal;

import org.junit.Test;

public class MatrixTest {
	@Test
	public void matrix() {
		Matrix matrix_A = new Matrix(2, 3);
		matrix_A.setElement(1, 1, 0);
		matrix_A.setElement(1, 2, -1);
		matrix_A.setElement(1, 3, -2);
		matrix_A.setElement(2, 1, 2);
		matrix_A.setElement(2, 2, 3);
		matrix_A.setElement(2, 3, 1);
		Matrix matrix_B = Matrix.numMultiplication(BigDecimal.valueOf(-2), matrix_A);
		Matrix.print(matrix_A);
		System.out.println();
		Matrix.print(matrix_B);
		System.out.println();
		Matrix.print(Matrix.matrixAddition(matrix_A, matrix_B));
		System.out.println();
		Matrix.print(Matrix.matrixSubtraction(matrix_A, matrix_B));
		System.out.println("==========================");
		Matrix matrix_C = new Matrix(4, 4);
		matrix_C.setElement(1, 1, 22);
		matrix_C.setElement(1, 2, 4);
		matrix_C.setElement(1, 3, 4);
		matrix_C.setElement(1, 4, 23);
		matrix_C.setElement(2, 1, 3);
		matrix_C.setElement(2, 2, 3);
		matrix_C.setElement(2, 3, 2);
		matrix_C.setElement(2, 4, 6);
		matrix_C.setElement(3, 1, 1);
		matrix_C.setElement(3, 2, 23);
		matrix_C.setElement(3, 3, 2);
		matrix_C.setElement(3, 4, 2);
		matrix_C.setElement(4, 1, 3);
		matrix_C.setElement(4, 2, 4);
		matrix_C.setElement(4, 3, 54);
		matrix_C.setElement(4, 4, 4);
		Matrix.print(Matrix.matrixMultiplication(matrix_C, Matrix.matrixInverse(matrix_C)));
		System.out.println("==========================");
		Matrix.print(Matrix.identityMatrix(4));
	}
}
