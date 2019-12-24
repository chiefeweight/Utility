package com.hy.java.utility.math;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * 矩阵存储、计算
 * 
 * @author cfw
 */
public class Matrix {
	private int row_num;
	private int column_num;

	private BigDecimal[][] elements;

	private enum Operations {
		POSITIVE, GET_SET, MATRIX_ADDITION, MATRIX_MULTIPLICATION, SQUARE_MATRIX, MINOR
	}

	public Matrix(int row_num, int column_num) {
		if (checkDimension(row_num, column_num, 1, 1, Operations.POSITIVE)) {
			this.row_num = row_num;
			this.column_num = column_num;
			elements = new BigDecimal[this.row_num][this.column_num];
		}
	}

	public int getRowNum() {
		return row_num;
	}

	public int getColumnNum() {
		return column_num;
	}

	public void setElement(int row_index, int column_index, double element) {
		if (checkDimension(row_index, column_index, getRowNum(), getColumnNum(), Operations.GET_SET)) {
			elements[row_index - 1][column_index - 1] = BigDecimal.valueOf(element);
		}
	}

	public void setElement(int row_index, int column_index, BigDecimal element) {
		if (checkDimension(row_index, column_index, getRowNum(), getColumnNum(), Operations.GET_SET)) {
			elements[row_index - 1][column_index - 1] = element;
		}
	}

	public BigDecimal getElement(int row_index, int column_index) {
		BigDecimal result = null;
		if (checkDimension(row_index, column_index, getRowNum(), getColumnNum(), Operations.GET_SET)) {
			result = elements[row_index - 1][column_index - 1];
		}
		return result;
	}

	/**
	 * 获得第row_index行的行向量
	 * 
	 * @param row_index
	 * @return 行向量
	 */
	public Vector getRowVector(int row_index) {
		Vector result = null;
		if (checkDimension(row_index, 1, getRowNum(), 1, Operations.GET_SET)) {
			result = new Vector(getColumnNum());
			for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
				result.setCoordinate(coordinate_index, elements[row_index - 1][coordinate_index - 1]);
			}
		}
		return result;
	}

	/**
	 * 获得第column_index列的列向量
	 * 
	 * @param column_index
	 * @return 列向量
	 */
	public Vector getColumnVector(int column_index) {
		Vector result = null;
		if (checkDimension(1, column_index, 1, getColumnNum(), Operations.GET_SET)) {
			result = new Vector(getRowNum());
			for (int coordinate_index = 1; coordinate_index <= result.getDimension(); coordinate_index++) {
				result.setCoordinate(coordinate_index, elements[coordinate_index - 1][column_index - 1]);
			}
		}
		return result;
	}

	public BigDecimal[][] getElements() {
		return elements;
	}

	/**
	 * 返回单位矩阵
	 * 
	 * @param dimension
	 * @return 单位矩阵
	 */
	public static Matrix identityMatrix(int dimension) {
		Matrix result = new Matrix(dimension, dimension);
		for (int row_index = 1; row_index <= result.getRowNum(); row_index++) {
			for (int column_index = 1; column_index <= result.getColumnNum(); column_index++) {
				if (row_index == column_index) {
					result.setElement(row_index, column_index, 1.0);
				} else {
					result.setElement(row_index, column_index, 0.0);
				}
			}
		}
		return result;
	}

	/**
	 * 矩阵加法
	 * 
	 * @param matrix_A
	 * @param matrix_B
	 * @return matrix_A+matrix_B
	 */
	public static Matrix matrixAddition(Matrix matrix_A, Matrix matrix_B) {
		Matrix result = null;
		// 同型则可以做加法
		if (checkDimension(matrix_A.getRowNum(), matrix_A.getColumnNum(), matrix_B.getRowNum(), matrix_B.getColumnNum(),
				Operations.MATRIX_ADDITION)) {
			result = new Matrix(matrix_A.getRowNum(), matrix_A.getColumnNum());
			for (int row_index = 1; row_index <= result.getRowNum(); row_index++) {
				for (int column_index = 1; column_index <= result.getColumnNum(); column_index++) {
					result.setElement(row_index, column_index,
							matrix_A.getElement(row_index, column_index).add(matrix_B.getElement(row_index, column_index)));
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
	 * @return matrix_A-matrix_B
	 */
	public static Matrix matrixSubtraction(Matrix matrix_A, Matrix matrix_B) {
		return Matrix.matrixAddition(matrix_A, Matrix.numMultiplication(BigDecimal.valueOf(-1.0), matrix_B));
	}

	/**
	 * 矩阵数乘
	 * 
	 * @param num
	 * @param matrix
	 * @return num*matrix
	 */
	public static Matrix numMultiplication(BigDecimal num, Matrix matrix) {
		Matrix result = null;
		if (checkDimension(matrix.getRowNum(), matrix.getColumnNum(), 1, 1, Operations.POSITIVE)) {
			result = new Matrix(matrix.getRowNum(), matrix.getColumnNum());
			for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
				for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
					result.setElement(row_index, column_index, matrix.getElement(row_index, column_index).multiply(num));
				}
			}
		}
		return result;
	}

	/**
	 * 矩阵转置
	 * 
	 * @param matrix
	 * @return matrix<sup>T</sup>
	 */
	public static Matrix matrixTranspose(Matrix matrix) {
		Matrix result = new Matrix(matrix.getColumnNum(), matrix.getRowNum());
		for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
			for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
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
	 * @return matrix_A×matrix_B
	 */
	public static Matrix matrixMultiplication(Matrix matrix_A, Matrix matrix_B) {
		Matrix result = null;
		// 如果矩阵A列数等于矩阵B行数，则可做乘法
		if (checkDimension(matrix_A.getRowNum(), matrix_A.getColumnNum(), matrix_B.getRowNum(), matrix_B.getColumnNum(),
				Operations.MATRIX_MULTIPLICATION)) {
			// 结果矩阵行数等于矩阵A行数，列数等于矩阵B列数
			result = new Matrix(matrix_A.getRowNum(), matrix_B.getColumnNum());
			// 矩阵A按行遍历
			for (int row_index_A = 1; row_index_A <= matrix_A.getRowNum(); row_index_A++) {
				// 矩阵A当前行向量
				Vector current_row_vector_A = matrix_A.getRowVector(row_index_A);
				// 矩阵B按列遍历
				for (int column_index_B = 1; column_index_B <= matrix_B.getColumnNum(); column_index_B++) {
					// 矩阵A当前行向量与矩阵B当前列向量点乘，作为结果矩阵相应位置元素
					result.setElement(row_index_A, column_index_B, Vector.dotProduct(current_row_vector_A, matrix_B.getColumnVector(column_index_B)));
				}
			}
		}
		return result;
	}

	/**
	 * 行列式
	 * 
	 * @param matrix
	 * @return det(matrix)
	 */
	public static BigDecimal det(Matrix matrix) {
		BigDecimal result = null;
		if (checkDimension(matrix.getRowNum(), matrix.getColumnNum(), 1, 1, Operations.SQUARE_MATRIX)) {
			result = new BigDecimal("0");
			if (matrix.getRowNum() == 1) {
				result = matrix.getElement(1, 1);
			} else {
				int row_index = 1;
				for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
					result = result.add(matrix.getElement(row_index, column_index).multiply(Matrix.cofactor(matrix, row_index, column_index)));
				}
			}
		}
		return result;
	}

	/**
	 * 逆矩阵
	 * 
	 * @param matrix
	 * @return 逆矩阵
	 */
	public static Matrix matrixInverse(Matrix matrix) {
		Matrix result = null;
		BigDecimal det = Matrix.det(matrix);
		if (det.compareTo(BigDecimal.ZERO) != 0) {
			result = Matrix.numMultiplication(BigDecimal.valueOf(1.0).divide(det, MathContext.DECIMAL128), Matrix.adjugateMatrix(matrix));
		}
		return result;
	}

	/**
	 * 代数余子式
	 * 
	 * @param matrix
	 * @param row_index
	 * @param column_index
	 * @return cofactor
	 */
	private static BigDecimal cofactor(Matrix matrix, int row_index, int column_index) {
		// TODO Auto-generated method stub
		return Matrix.det(Matrix.minor(matrix, row_index, column_index)).multiply(BigDecimal.valueOf(Math.pow(-1, row_index + column_index)));
	}

	/**
	 * 余子阵
	 * 
	 * @param matrix
	 * @param row_index
	 * @param column_index
	 * @return 余子阵M
	 */
	private static Matrix minor(Matrix matrix, int row_index, int column_index) {
		// TODO Auto-generated method stub
		Matrix result = null;
		if (checkDimension(row_index, column_index, matrix.getRowNum(), matrix.getColumnNum(), Operations.MINOR)) {
			result = matrix;
			// 从当前result中删除第row_index行
			result = Matrix.deleteRow(result, row_index);
			// 从当前result中删除第column_index列
			result = Matrix.deleteColumn(result, column_index);
		}
		return result;
	}

	/**
	 * 删除矩阵的一行
	 * 
	 * @param matrix
	 * @param row_index
	 * @return 删掉第row_index行的matrix
	 */
	public static Matrix deleteRow(Matrix matrix, int row_index) {
		// TODO Auto-generated method stub
		Matrix result = null;
		if (checkDimension(row_index, 1, matrix.getRowNum(), 2, Operations.MINOR)) {
			result = new Matrix(matrix.getRowNum() - 1, matrix.getColumnNum());
			int row_index_before_end = row_index - 1;
			int row_index_after_start = row_index + 1;
			for (int row_index_before = 1; row_index_before <= row_index_before_end; row_index_before++) {
				for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
					result.setElement(row_index_before, column_index, matrix.getElement(row_index_before, column_index));
				}
			}
			for (int row_index_after = row_index_after_start; row_index_after <= matrix.getRowNum(); row_index_after++) {
				for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
					result.setElement(row_index_after - 1, column_index, matrix.getElement(row_index_after, column_index));
				}
			}
		}
		return result;
	}

	/**
	 * 删除矩阵的一列
	 * 
	 * @param matrix
	 * @param column_index
	 * @return 删掉第column_index列的matrix
	 */
	public static Matrix deleteColumn(Matrix matrix, int column_index) {
		// TODO Auto-generated method stub
		Matrix result = null;
		if (checkDimension(1, column_index, 2, matrix.getColumnNum(), Operations.MINOR)) {
			result = new Matrix(matrix.getRowNum(), matrix.getColumnNum() - 1);
			int column_index_before_end = column_index - 1;
			int column_index_after_start = column_index + 1;
			for (int column_index_before = 1; column_index_before <= column_index_before_end; column_index_before++) {
				for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
					result.setElement(row_index, column_index_before, matrix.getElement(row_index, column_index_before));
				}
			}
			for (int column_index_after = column_index_after_start; column_index_after <= matrix.getColumnNum(); column_index_after++) {
				for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
					result.setElement(row_index, column_index_after - 1, matrix.getElement(row_index, column_index_after));
				}
			}
		}
		return result;
	}

	/**
	 * 伴随矩阵
	 * 
	 * @param matrix
	 * @return 伴随矩阵
	 */
	private static Matrix adjugateMatrix(Matrix matrix) {
		// TODO Auto-generated method stub
		Matrix result = null;
		if (checkDimension(matrix.getRowNum(), matrix.getColumnNum(), 1, 1, Operations.SQUARE_MATRIX)) {
			result = new Matrix(matrix.getRowNum(), matrix.getColumnNum());
			for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
				for (int column_index = 1; column_index <= matrix.getColumnNum(); column_index++) {
					result.setElement(row_index, column_index, Matrix.cofactor(matrix, row_index, column_index));
				}
			}
			result = Matrix.matrixTranspose(result);
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
	 * @return dimension_ok
	 */
	private static boolean checkDimension(int row_num_A, int column_num_A, int row_num_B, int column_num_B, Operations mode) {
		boolean row_num_A_ok = false;
		boolean column_num_A_ok = false;
		boolean row_num_B_ok = false;
		boolean column_num_B_ok = false;
		boolean row_compare_ok = false;
		boolean column_compare_ok = false;
		boolean dimension_ok = false;
		if (row_num_A > 0) {
			row_num_A_ok = true;
		} else {
			System.out.println("row_A must be greater than 0");
		}
		if (column_num_A > 0) {
			column_num_A_ok = true;
		} else {
			System.out.println("column_A must be greater than 0");
		}
		if (row_num_B > 0) {
			row_num_B_ok = true;
		} else {
			System.out.println("row_B must be greater than 0");
		}
		if (column_num_B > 0) {
			column_num_B_ok = true;
		} else {
			System.out.println("column_A must be greater than 0");
		}
		if (row_num_A_ok && column_num_A_ok && row_num_B_ok && column_num_B_ok) {
			switch (mode) {
			case POSITIVE: {
				dimension_ok = true;
				break;
			}
			// 余子阵
			case MINOR: {
				boolean row_greater_than_one = false;
				boolean column_greater_than_one = false;
				if (row_num_B > 1) {
					row_greater_than_one = true;
				} else {
					System.out.println("matrix row must be greater than 1 in order to be minored");
				}
				if (column_num_B > 1) {
					column_greater_than_one = true;
				} else {
					System.out.println("matrix column must be greater than 1 in order to be minored");
				}
				if (row_greater_than_one && column_greater_than_one) {
					if (row_num_A <= row_num_B) {
						row_compare_ok = true;
					} else {
						System.out.println("row index out of bounds");
					}
					if (column_num_A <= column_num_B) {
						column_compare_ok = true;
					} else {
						System.out.println("column index out of bounds");
					}
					if (row_compare_ok && column_compare_ok) {
						dimension_ok = true;
					}
				}
				break;
			}
			// 矩阵元素操作：检查操作坐标是否在矩阵内
			case GET_SET: {
				if (row_num_A <= row_num_B) {
					row_compare_ok = true;
				} else {
					System.out.println("row index out of bounds");
				}
				if (column_num_A <= column_num_B) {
					column_compare_ok = true;
				} else {
					System.out.println("column index out of bounds");
				}
				if (row_compare_ok && column_compare_ok) {
					dimension_ok = true;
				}
				break;
			}
			// 矩阵加法：检查两个矩阵是否同型
			case MATRIX_ADDITION: {
				if (row_num_A == row_num_B) {
					row_compare_ok = true;
				} else {
					System.out.println("row dimension not the same");
				}
				if (column_num_A == column_num_B) {
					column_compare_ok = true;
				} else {
					System.out.println("column dimension not the same");
				}
				if (row_compare_ok && column_compare_ok) {
					dimension_ok = true;
				}
				break;
			}
			// 矩阵乘法：检查矩阵A的列数是否等于矩阵B的行数
			case MATRIX_MULTIPLICATION: {
				if (column_num_A == row_num_B) {
					dimension_ok = true;
				} else {
					System.out.println("column_A not equals with row_B");
				}
				break;
			}
			// 行列式：检查矩阵是否为方块矩阵
			case SQUARE_MATRIX: {
				if (row_num_A == column_num_A) {
					dimension_ok = true;
				} else {
					System.out.println("matrix is not a Square Matrix");
				}
				break;
			}
			default: {
				System.err.println("矩阵操作模式出错");
				break;
			}
			}
		}
		return dimension_ok;
	}

	/**
	 * 打印一个矩阵，同时会将打印内容以String形式返回
	 * 
	 * @param matrix
	 * @return {@code String}格式的matrix内容
	 */
	public static String print(Matrix matrix) {
		String result = null;
		StringBuilder sb = new StringBuilder();
		if (matrix != null) {
			// 逐行打印
			for (int row_index = 1; row_index <= matrix.getRowNum(); row_index++) {
				// 先向sb中添加当前行第一个元素
				int column_index = 1;
				sb.append(matrix.getElement(row_index, column_index).doubleValue());
				column_index++;
				// 当前行后面的元素前都加制表符
				while (column_index <= matrix.getColumnNum()) {
					sb.append("\t" + matrix.getElement(row_index, column_index).doubleValue());
					column_index++;
				}
				// 如果当前行不是矩阵最后一行，则在末尾添加换行符
				if (row_index < matrix.getRowNum()) {
					sb.append("\n");
				}
			}
		}
		result = sb.toString();
		System.out.println(result);
		return result;
	}
}
