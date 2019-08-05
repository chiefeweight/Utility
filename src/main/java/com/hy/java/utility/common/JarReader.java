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
 * @author chiefeweight
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
	 * @param jar_file_path jar文件的路径
	 * @param resource_path jar文件中要读取的特定文件的路径
	 * @return 特定文件的内容，存在{@code byte[]}中
	 */
	public static byte[] readJARFile(String jar_file_path, String resource_path) {
		// 读取JAR文件中单个文件的信息
		ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
		try {
			// 根据传入的JAR文件创建JAR文件对象
			JarFile jar_file = new JarFile(jar_file_path);
			// 获得JAR文件中的单个文件的JAR实体
			JarEntry entry = jar_file.getJarEntry(resource_path);
			// 根据实体创建输入流
			InputStream input_stream = jar_file.getInputStream(entry);
			byte[] temp_bytes = new byte[1024];
			int length = 0;
			while ((length = input_stream.read(temp_bytes)) != -1) {
				byte_array_output_stream.write(temp_bytes, 0, length);
			}
			input_stream.close();
			jar_file.close();// 关闭JAR文件对象流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byte_array_output_stream.toByteArray();
	}

	/**
	 * 获取jar文件中所有文件的列表，以{@code ArrayList<String>}的形式返回
	 *
	 * @param jar_file_path jar文件的路径
	 * @return jarEntryList jar文件中所有文件的路径列表
	 */
	public static ArrayList<String> readJARList(String jar_file_path) {
		// 显示JAR文件内容列表
		ArrayList<String> jar_entry_list = new ArrayList<String>();
		try {
			JarFile jar_file = new JarFile(jar_file_path); // 创建JAR文件对象
			Enumeration<JarEntry> entries = jar_file.entries(); // 枚举获得JAR文件内的实体,即相对路径
			while (entries.hasMoreElements()) { // 遍历显示JAR文件中的内容信息
				JarEntry entry = entries.nextElement();
				jar_entry_list.add(entry.getName());// 保存文件名称
			}
			jar_file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jar_entry_list;
	}
}
