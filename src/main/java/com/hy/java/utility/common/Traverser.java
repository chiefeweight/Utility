package com.hy.java.utility.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Traverser {
	/*
	 * 如果path指向文件，则返回的Node包含该文件的path
	 * 
	 * 如果path指向文件夹，则返回的Node包含该文件夹的path，且children包含该文件夹下的所有文件/文件夹
	 */
	public static FileNode traverseDir(String path) {
		FileNode result = null;
		File dir_or_file = new File(path);
		if (dir_or_file.exists()) {
			result = new FileNode(dir_or_file.getAbsolutePath());
			if (!dir_or_file.isDirectory()) {
				System.out.println("文件: " + result.path);
			} else {
				System.out.println("文件夹: " + result.path);
				File[] subDirs_or_subFiles = dir_or_file.listFiles();
				if (subDirs_or_subFiles == null) {
					System.out.println("无权访问文件夹: " + result.path);
				} else {
					result.children = new ArrayList<>();
					for (File subDir_or_subFile : subDirs_or_subFiles) {
						result.children.add(Traverser.traverseDir(subDir_or_subFile.getAbsolutePath()));
					}
				}
			}
		} else {
			System.out.println("目录不存在: " + dir_or_file);
		}
		return result;
	}

	public static class FileNode {
		public String path;
		public String parent_path = null;
		public List<FileNode> children = null;

		public FileNode(String path) {
			this.path = path;
			if (this.path.split("\\\\").length >= 2) {
				StringBuilder sb = new StringBuilder(this.path);
				int i = sb.lastIndexOf("\\");
				while (i < sb.length()) {
					sb.deleteCharAt(i);
				}
				this.parent_path = sb.toString();
			} else {
				System.out.println("没有上级目录: " + this.path);
			}
		}
	}
}
