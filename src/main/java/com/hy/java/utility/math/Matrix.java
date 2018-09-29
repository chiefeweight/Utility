package com.hy.java.utility.math;

public class Matrix {
	private double[][] elements;
	private int row_num;
	private int column_num;

	public Matrix(int row_num, int column_num) {
		this.row_num = row_num;
		this.column_num = column_num;
		this.elements = new double[this.row_num][this.column_num];
	}

	public void setElement(int row_index, int column_index, double element) {
		if (checkIndices(row_index, column_index)) {
			this.elements[row_index - 1][column_index - 1] = element;
		}
	}

	public double getElement(int row_index, int column_index) {
		double result = 0;
		if (checkIndices(row_index, column_index)) {
			result = this.elements[row_index - 1][column_index - 1];
		}
		return result;
	}

	private boolean checkIndices(int row_index, int column_index) {
		boolean indices_ok = false;
		boolean row_index_ok = false;
		boolean column_index_ok = false;
		if (row_index > 0 && row_index <= this.row_num) {
			row_index_ok = true;
		} else {
			System.out.println("row index out of bounds");
		}
		if (column_index > 0 && column_index <= this.column_num) {
			column_index_ok = true;
		} else {
			System.out.println("column index out of bounds");
		}
		if (row_index_ok && column_index_ok) {
			indices_ok = true;
		}
		return indices_ok;
	}
}
