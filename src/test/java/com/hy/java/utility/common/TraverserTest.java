package com.hy.java.utility.common;

import org.junit.jupiter.api.Test;

import com.hy.java.utility.common.Traverser.FileNode;

public class TraverserTest {
	@Test
	public void traverseDir() {
		FileNode t = Traverser.traverseDir(System.getProperty("user.dir") + "\\src\\test\\resources");
		System.out.println("=============");
		if (t != null) {
			System.out.println(t.parent_path);
			if (t.children != null) {
				for (FileNode s : t.children) {
					System.out.println(s.path + " " + s.parent_path);
				}
			}
		}
	}
}
