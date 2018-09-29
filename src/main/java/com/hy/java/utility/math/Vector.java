package com.hy.java.utility.math;

public class Vector {
	private int dimension;
	private double[] coordinates;

	private enum Operations {
		Get_Set, Vector_Addition, Dot_Product
	}

	public Vector(int dimension) {
		this.dimension = dimension;
		this.coordinates = new double[this.dimension];
	}

	public int getDimension() {
		return this.dimension;
	}

	public void setCoordinate(int coordinate_index, double coordinate) {
		if (checkDimension(coordinate_index, this.getDimension(), Operations.Get_Set)) {
			this.coordinates[coordinate_index - 1] = coordinate;
		}
	}

	public double getCoordinate(int coordinate_index) {
		double result = 0;
		if (checkDimension(coordinate_index, this.getDimension(), Operations.Get_Set)) {
			result = this.coordinates[coordinate_index - 1];
		}
		return result;
	}

	public double[] getCoordinates() {
		return this.coordinates;
	}

	/**
	 * 向量加法
	 * 
	 * @param vector_A
	 * @param vector_B
	 * @return
	 */
	public static Vector vectorAddition(Vector vector_A, Vector vector_B) {
		Vector result = null;
		// 同型则可以做加法
		if (checkDimension(vector_A.getDimension(), vector_B.getDimension(), Operations.Vector_Addition)) {
			result = new Vector(vector_A.getDimension());
			for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
				result.setCoordinate(coordinate_index, vector_A.getCoordinate(coordinate_index) + vector_B.getCoordinate(coordinate_index));
			}
		}
		return result;
	}

	/**
	 * 向量减法
	 * 
	 * @param vector_A
	 * @param vector_B
	 * @return
	 */
	public static Vector vectorSubtraction(Vector vector_A, Vector vector_B) {
		return Vector.vectorAddition(vector_A, Vector.scalarMultiplication(-1, vector_B));
	}

	/**
	 * 向量数乘
	 * 
	 * @param num
	 * @param vector
	 * @return
	 */
	public static Vector scalarMultiplication(double num, Vector vector) {
		Vector result = new Vector(vector.getDimension());
		for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
			result.setCoordinate(coordinate_index, num * vector.getCoordinate(coordinate_index));
		}
		return result;
	}

	/**
	 * 向量点乘
	 * 
	 * @param vector_A
	 * @param vector_B
	 * @return
	 */
	public static double dotProduct(Vector vector_A, Vector vector_B) {
		double result = 0;
		// 同型则可以做点乘
		if (checkDimension(vector_A.getDimension(), vector_B.getDimension(), Operations.Dot_Product)) {
			for (int coordinate_index = 1; coordinate_index <= vector_A.getDimension(); coordinate_index++) {
				result += vector_A.getCoordinate(coordinate_index) * vector_B.getCoordinate(coordinate_index);
			}
		}
		return result;
	}

	/**
	 * 根据操作类型，检查操作涉及的长度是否满足操作要求
	 * 
	 * @param dimension_A
	 * @param dimension_B
	 * @param mode
	 * @return
	 */
	private static boolean checkDimension(int dimension_A, int dimension_B, Operations mode) {
		boolean dimension_ok = false;
		switch (mode) {
		// 向量坐标操作：检查操作坐标是否在向量内
		case Get_Set: {
			if (dimension_A > 0 && dimension_A <= dimension_B) {
				dimension_ok = true;
			} else {
				System.out.println("dimension out of bounds");
			}
			break;
		}
		// 向量加法、点乘：检查两个向量是否同型
		case Vector_Addition:
		case Dot_Product: {
			if (dimension_A == dimension_B) {
				dimension_ok = true;
			} else {
				System.out.println("dimension_A not equals with dimension_B");
			}
			break;
		}
		default: {
			System.err.println("向量操作模式出错");
			break;
		}
		}
		return dimension_ok;
	}

	/**
	 * 打印一个向量，同时会将打印内容以String形式返回
	 * 
	 * @param vector
	 * @return
	 */
	public static String print(Vector vector) {
		String result = null;
		StringBuilder sb = new StringBuilder();
		// 先向sb中添加第一个元素
		int coordinate_index = 1;
		sb.append(vector.getCoordinate(coordinate_index));
		coordinate_index++;
		// 后面的元素前都加制表符
		while (coordinate_index <= vector.getDimension()) {
			sb.append("\t" + vector.getCoordinate(coordinate_index));
			coordinate_index++;
		}
		result = sb.toString();
		System.out.println(result);
		return result;
	}
}
