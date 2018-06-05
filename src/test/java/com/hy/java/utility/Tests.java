package com.hy.java.utility;

import java.awt.Dialog;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import com.hy.java.utility.common.FileEditor;
import com.hy.java.utility.common.SystemTime;
import com.hy.java.utility.common.Traverser;
import com.hy.java.utility.frame.CardFrame;
import com.hy.java.utility.frame.GridBagPanel;
import com.hy.java.utility.math.NormalDistribution;

public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tests t = new Tests();
		t.frame_test(-10.0, 5555.0, 5000000);
	}

	private void frame_test(final double mean, final double variance, final int size) {
		final CardFrame card_frame = new CardFrame("Test", 800, 600);
		// panel
		GridBagPanel panel = new GridBagPanel("panel") {
			/**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 884955750558840773L;

			@Override
			public void paint(Graphics graphics) {
				// 必须先调用父类的paint方法
				super.paint(graphics);
				// 用画笔Graphics，在panel上画图
				// X轴
				graphics.drawLine(0, card_frame.getHeight() / 2, card_frame.getWidth(), card_frame.getHeight() / 2);
				// Y轴
				graphics.drawLine(card_frame.getWidth() / 2, 0, card_frame.getWidth() / 2, card_frame.getHeight());
				// 生成正态分布随机数
				double[] nums = Tests.this.ran(mean, variance, size);
				// 找出样本最小值、最大值
				double min = 0;
				double max = 0;
				for (int i = 0; i < nums.length; i++) {
					if (nums[i] < min) {
						min = nums[i];
					}
					if (nums[i] > max) {
						max = nums[i];
					}
				}
				// 根据最小值、最大值，将随机数所在区间对称化，并记录区间宽度
				double width = 1.00;
				if (mean - min > max - mean) {
					width = 2 * (mean - min);
				} else {
					width = 2 * (max - mean);
					min = max - width;
				}
				// 记录每个点的频率
				int[] fs = new int[card_frame.getWidth() + 1];
				for (int i = 0; i < nums.length; i++) {
					// 将生成的每个随机数对应到要画的x轴上的某个坐标
					int x = (int) ((nums[i] - min) / width * card_frame.getWidth());
					fs[x] += 1;
				}
				// 记录最大频率，用于画y轴
				int f_max = 0;
				for (int i = 0; i < fs.length; i++) {
					if (fs[i] > f_max) {
						f_max = fs[i];
					}
				}
				// 画
				for (int i = 0; i < fs.length; i++) {
					// 将频率对应到要画的y轴上的某个坐标
					int y = (int) (card_frame.getHeight() / 2 - ((double) fs[i] / (double) f_max) * (card_frame.getHeight() / 2));
					graphics.drawLine(i, card_frame.getHeight() / 2, i, y);
				}
			}
		};
		card_frame.addGridBagPanel(panel, panel.getName());
		// help
		JFrame help_frame = new JFrame("Help");
		card_frame.setHelpContents(help_frame);
		help_frame.setSize(200, 200);
		// about
		Dialog about_dialog = new Dialog(card_frame);
		card_frame.setAbout(about_dialog);
		about_dialog.setTitle("About");
		about_dialog.setSize(200, 200);
	}

	private double[] ran(double mean, double variance, int size) {
		double[] result = new double[size];
		// 生成正态分布随机数
		NormalDistribution normal_dis = new NormalDistribution(mean, variance, null);
		for (int i = 0; i < result.length; i++) {
			result[i] = normal_dis.nextRandom();
		}
		// 打印平均值
		double sum = 0.0;
		for (int i = 0; i < result.length; i++) {
			sum += result[i];
		}
		System.out.println("期望：\t" + mean + "\n方差：\t" + variance + "\n样本量：\t" + size + "\n平均值：\t" + sum / result.length);
		return result;
	}

	@Test
	public void time_test() {
		// TODO Auto-generated method stub
		System.out.println(SystemTime.getCurrentTime());
	}

	@Test
	public void file_test() {
		List<String> t = new ArrayList<>();
		Traverser.traverseFolder("G:\\1\\", t);
		for (String s : t) {
			FileEditor f = new FileEditor(s);
			System.out.println(f.readFileToString());
		}
	}
}
