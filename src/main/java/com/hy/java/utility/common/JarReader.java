package com.hy.java.utility.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于读取jar文件中的内容
 * <p>
 * 可以获取系统属性、jar文件中特定文件的内容、jar文件中所有文件的列表
 * 
 * @author 陈方伟
 */
public class JarReader {
	/**
	 * 显示系统属性
	 * 
	 * @return 此工程的java.class.path，存在{@code String[]}中
	 */
	public static String[] currentProperties() {
		return System.getProperty("java.class.path").split(";");
	}

	/**
	 * 读取jar文件中特定文件的内容，以{@code byte[]}的形式返回
	 *
	 * @param jarFilePath
	 *            jar文件的路径
	 * @param resourcePath
	 *            jar文件中要读取的特定文件的路径
	 * @return 特定文件的内容，存在{@code byte[]}中
	 */
	public static byte[] readJARFile(String jarFilePath, String resourcePath) {// 读取JAR文件中单个文件的信息
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JarFile jarFile = new JarFile(jarFilePath);// 根据传入的JAR文件创建JAR文件对象
			JarEntry entry = jarFile.getJarEntry(resourcePath);// 获得JAR文件中的单个文件的JAR实体
			InputStream inputStream = jarFile.getInputStream(entry);// 根据实体创建输入流
			byte[] tempBytes = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(tempBytes)) != -1) {
				byteArrayOutputStream.write(tempBytes, 0, length);
			}
			inputStream.close();
			jarFile.close();// 关闭JAR文件对象流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * 获取jar文件中所有文件的列表，以{@code ArrayList<String>}的形式返回
	 *
	 * @param jarFilePath
	 *            jar文件的路径
	 * @return jarEntryList jar文件中所有文件的路径列表
	 */
	public static ArrayList<String> readJARList(String jarFilePath) {// 显示JAR文件内容列表
		ArrayList<String> jarEntryList = new ArrayList<String>();
		try {
			JarFile jarFile = new JarFile(jarFilePath); // 创建JAR文件对象
			Enumeration<JarEntry> en = jarFile.entries(); // 枚举获得JAR文件内的实体,即相对路径
			while (en.hasMoreElements()) { // 遍历显示JAR文件中的内容信息
				JarEntry entry = en.nextElement();
				jarEntryList.add(entry.getName());// 保存文件名称
			}
			jarFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jarEntryList;
	}
}
