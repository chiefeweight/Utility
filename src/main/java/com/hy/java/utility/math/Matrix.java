package com.hy.java.utility.math;

public class Matrix {
	private int row_num;
	private int column_num;

	private double[][] elements;

	private enum Operations {
		Get_Set, Matrix_Addition, Matrix_Multiplication
	}

	public Matrix(int row_num, int column_num) {
		this.row_num = row_num;
		this.column_num = column_num;
		this.elements = new double[this.row_num][this.column_num];
	}

	public int getRow_num() {
		return this.row_num;
	}

	public int getColumn_num() {
		return this.column_num;
	}

	public void setElement(int row_index, int column_index, double element) {
		if (checkDimension(row_index, column_index, this.getRow_num(), this.getColumn_num(), Operations.Get_Set)) {
			this.elements[row_index - 1][column_index - 1] = element;
		}
	}

	public double getElement(int row_index, int column_index) {
		double result = 0;
		if (checkDimension(row_index, column_index, this.getRow_num(), this.getColumn_num(), Operations.Get_Set)) {
			result = this.elements[row_index - 1][column_index - 1];
		}
		return result;
	}

	/**
	 * 获得第row_index行的行向量
	 * 
	 * @param row_index
	 * @return
	 */
	public Vector getRowVector(int row_index) {
		Vector result = new Vector(this.getColumn_num());
		for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
			result.setCoordinate(coordinate_index, this.elements[row_index - 1][coordinate_index - 1]);
		}
		return result;
	}

	/**
	 * 获得第column_index列的列向量
	 * 
	 * @param column_index
	 * @return
	 */
	public Vector getColumnVector(int column_index) {
		Vector result = new Vector(this.getRow_num());
		for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
			result.setCoordinate(coordinate_index, this.elements[coordinate_index - 1][column_index - 1]);
		}
		return result;
	}

	public double[][] getElements() {
		return this.elements;
	}

	/**
	 * 矩阵加法
	 * 
	 * @param matrix_A
	 * @param matrix_B
	 * @return
	 */
	public static Matrix matrixAddition(Matrix matrix_A, Matrix matrix_B) {
		Matrix result = null;
		// 同型则可以做加法
		if (checkDimension(matrix_A.getRow_num(), matrix_A.getColumn_num(), matrix_B.getRow_num(), matrix_B.getColumn_num(), Operations.Matrix_Addition)) {
			result = new Matrix(matrix_A.getRow_num(), matrix_A.getColumn_num());
			for (int row_index = 1; row_index <= result.getRow_num(); row_index++) {
				for (int column_index = 1; column_index <= result.getColumn_num(); column_index++) {
					result.setElement(row_index, column_index, matrix_A.getElement(row_index, column_index) + matrix_B.getElement(row_index, column_index));
				}
			}
		}
		return result;
	}

	/**
	 * 矩阵减法
	 * 
	 * @param matrix_A
	 * @param matrix_B
	 * @return
	 */
	public static Matrix matrixSubtraction(Matrix matrix_A, Matrix matrix_B) {
		return Matrix.matrixAddition(matrix_A, Matrix.numMultiplication(-1, matrix_B));
	}

	/**
	 * 矩阵数乘
	 * 
	 * @param num
	 * @param matrix
	 * @return
	 */
	public static Matrix numMultiplication(double num, Matrix matrix) {
		Matrix result = new Matrix(matrix.getRow_num(), matrix.getColumn_num());
		for (int row_index = 1; row_index <= matrix.getRow_num(); row_index++) {
			for (int column_index = 1; column_index <= matrix.getColumn_num(); column_index++) {
				result.setElement(row_index, column_index, num * matrix.getElement(row_index, column_index));
			}
		}
		return result;
	}

	/**
	 * 矩阵转置
	 * 
	 * @param matrix
	 * @return
	 */
	public static Matrix matrixTranspose(Matrix matrix) {
		Matrix result = new Matrix(matrix.getColumn_num(), matrix.getRow_num());
		for (int row_index = 1; row_index <= matrix.getRow_num(); row_index++) {
			for (int column_index = 1; column_index <= matrix.getColumn_num(); column_index++) {
				result.setElement(column_index, row_index, matrix.getElement(row_index, column_index));
			}
		}
		return result;
	}

	/**
	 * 矩阵乘法
	 * 
	 * @param matrix_A
	 * @param matrix_B
	 * @return
	 */
	public static Matrix matrixMultiplication(Matrix matrix_A, Matrix matrix_B) {
		Matrix result = null;
		// 如果矩阵A列数等于矩阵B行数，则可做乘法
		if (checkDimension(matrix_A.getRow_num(), matrix_A.getColumn_num(), matrix_B.getRow_num(), matrix_B.getColumn_num(), Operations.Matrix_Multiplication)) {
			// 结果矩阵行数等于矩阵A行数，列数等于矩阵B列数
			result = new Matrix(matrix_A.getRow_num(), matrix_B.getColumn_num());
			// 矩阵A按行遍历
			for (int row_index_A = 1; row_index_A <= matrix_A.getRow_num(); row_index_A++) {
				// 矩阵B按列遍历
				for (int column_index_B = 1; column_index_B <= matrix_B.getColumn_num(); column_index_B++) {
					// 矩阵A第row_index_A行向量与矩阵B第column_index_B列向量点乘，作为结果矩阵相应位置元素
					result.setElement(row_index_A, column_index_B, Vector.dotProduct(matrix_A.getRowVector(row_index_A), matrix_B.getColumnVector(column_index_B)));
				}
			}
		}
		return result;
	}

	/**
	 * 根据操作类型，检查操作涉及的行数、列数是否满足操作要求
	 * 
	 * @param row_num_A
	 * @param column_num_A
	 * @param row_num_B
	 * @param column_num_B
	 * @param mode
	 * @return
	 */
	private static boolean checkDimension(int row_num_A, int column_num_A, int row_num_B, int column_num_B, Operations mode) {
		boolean dimension_ok = false;
		boolean row_ok = false;
		boolean column_ok = false;
		switch (mode) {
		// 矩阵元素操作：检查操作坐标是否在矩阵内
		case Get_Set: {
			if (row_num_A > 0 && row_num_A <= row_num_B) {
				row_ok = true;
			} else {
				System.out.println("row index out of bounds");
			}
			if (column_num_A > 0 && column_num_A <= column_num_B) {
				column_ok = true;
			} else {
				System.out.println("column index out of bounds");
			}
			if (row_ok && column_ok) {
				dimension_ok = true;
			}
			break;
		}
		// 矩阵加法：检查两个矩阵是否同型
		case Matrix_Addition: {
			if (row_num_A == row_num_B) {
				row_ok = true;
			} else {
				System.out.println("row dimension not the same");
			}
			if (column_num_A == column_num_B) {
				column_ok = true;
			} else {
				System.out.println("column dimension not the same");
			}
			if (row_ok && column_ok) {
				dimension_ok = true;
			}
			break;
		}
		// 矩阵乘法：检查矩阵A的列数是否等于矩阵B的行数
		case Matrix_Multiplication: {
			if (column_num_A == row_num_B) {
				dimension_ok = true;
			} else {
				System.out.println("column_A not equals with row_B");
			}
			break;
		}
		default: {
			System.err.println("矩阵操作模式出错");
			break;
		}
		}
		return dimension_ok;
	}

	/**
	 * 打印一个矩阵，同时会将打印内容以String形式返回
	 * 
	 * @param matrix
	 * @return
	 */
	public static String print(Matrix matrix) {
		String result = null;
		StringBuilder sb = new StringBuilder();
		// 逐行打印
		for (int row_index = 1; row_index <= matrix.getRow_num(); row_index++) {
			// 先向sb中添加当前行第一个元素
			int column_index = 1;
			sb.append(matrix.getElement(row_index, column_index));
			column_index++;
			// 当前行后面的元素前都加制表符
			while (column_index <= matrix.getColumn_num()) {
				sb.append("\t" + matrix.getElement(row_index, column_index));
				column_index++;
			}
			// 如果当前行不是矩阵最后一行，则在末尾添加换行符
			if (row_index < matrix.getRow_num()) {
				sb.append("\n");
			}
		}
		result = sb.toString();
		System.out.println(result);
		return result;
	}
}
