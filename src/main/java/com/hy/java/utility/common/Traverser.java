package com.hy.java.utility.common;

import java.io.File;
import java.util.List;

public class Traverser {
	public static List<String> traverseDir(String path, List<String> result) {
		File file_or_dir = new File(path);
		if (file_or_dir.exists()) {
			if (file_or_dir.isDirectory()) {
				File[] subFiles_or_subDirs = file_or_dir.listFiles();
				if (subFiles_or_subDirs == null) {
					System.out.println("无权访问文件夹: " + file_or_dir);
				} else if (subFiles_or_subDirs.length == 0) {
					System.out.println("文件夹是空的: " + file_or_dir);
				} else {
					for (File subFile_or_subDir : subFiles_or_subDirs) {
						if (subFile_or_subDir.isDirectory()) {
							System.out.println("文件夹: " + subFile_or_subDir.getAbsolutePath());
							traverseDir(subFile_or_subDir.getAbsolutePath(), result);
						} else {
							System.out.println("文件: " + subFile_or_subDir.getAbsolutePath());
							result.add(subFile_or_subDir.getAbsolutePath());
						}
					}
				}
			} else {
				System.out.println("文件: " + file_or_dir.getAbsolutePath());
				result.add(file_or_dir.getAbsolutePath());
			}
		} else {
			System.out.println("目录不存在: " + file_or_dir);
		}
		return result;
	}
}
