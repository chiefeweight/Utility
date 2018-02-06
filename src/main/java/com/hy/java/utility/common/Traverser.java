package com.hy.java.utility.common;

import java.io.File;

public class Traverser {
	public void traverseFolder(String path) {
		File rootFile = new File(path);
		if (rootFile.exists()) {
			if (rootFile.isDirectory()) {
				File[] files = rootFile.listFiles();
				if (files.length == 0) {
					System.out.println("文件夹是空的!");
				} else {
					for (File file : files) {
						if (file.isDirectory()) {
							System.out.println("文件夹:" + file.getAbsolutePath());
							traverseFolder(file.getAbsolutePath());
						} else {
							System.out.println("文件:" + file.getAbsolutePath());
						}
					}
				}
			} else {
				System.out.println("文件:" + rootFile.getAbsolutePath());
			}
		} else {
			System.out.println("目录不存在!");
		}
	}
}
