package com.hy.java.utility;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.hy.java.utility.common.FileEditor;
import com.hy.java.utility.common.Traverser;
import com.hy.java.utility.frame.CardFrame;
import com.hy.java.utility.math.NormalDistribution;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// frame_test();
		// num_test();
		file_test();
	}

	private static void file_test() {
		List<String> t = new ArrayList<>();
		Traverser.traverseFolder("G:\\1\\", t);
		for (String s : t) {
			FileEditor f = new FileEditor(s);
			System.out.println(f.readFileToString());
		}
	}

	private static void num_test() {
		// TODO Auto-generated method stub
		FileEditor f = new FileEditor("F:\\1.txt");
		NormalDistribution n = new NormalDistribution(10, 25000000, null);
		for (int i = 0; i < 1000; i++) {
			f.write(Double.toString(n.nextRandom()) + "\n", true);
		}
	}

	private static void frame_test() {
		CardFrame cf = new CardFrame("Test", 500, 500);

		JFrame temp_cf = new JFrame("Test1");
		cf.setHelpContents(temp_cf);
		temp_cf.setSize(200, 200);

		Dialog d = new Dialog(cf);
		cf.setAbout(d);
		d.setSize(300, 300);
	}
}
