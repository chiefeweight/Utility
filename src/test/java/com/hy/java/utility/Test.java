package com.hy.java.utility;

import java.awt.Dialog;

import javax.swing.JFrame;

import com.hy.java.utility.frame.CardFrame;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardFrame cf = new CardFrame("Test", 500, 500);

		JFrame temp_cf = new JFrame("Test1");
		cf.setHelpContents(temp_cf);
		temp_cf.setSize(200, 200);

		Dialog d = new Dialog(cf);
		cf.setAbout(d);
		d.setSize(300, 300);

	}
}
