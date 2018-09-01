package com.hy.java.utility.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 用于编辑File，可以实现读、写任意数据的操作
 * <p>
 * 读的方法有<code>readFileToByteArray()</code>、<code>readFileToString()</code>、<code>readLines()</code>
 * <p>
 * 写的方法名字均为write，可以写入{@code byte[]}、{@code InputStream}、{@code String}
 * 
 * @author chiefeweight
 */
public class FileEditor {
	private File file;

	/**
	 * 构造新的{@code FileEditor}，可实现对file_path所指文件的读、写操作
	 * 
	 * @param file_path
	 *            目标文件路径。构造法会自动将路径中的非法字符（“*”、“?”、“<”、“>”）替换为“_”。如果目标文件的目录在文件系统中不存在，构造法会自动创建目录
	 */
	public FileEditor(String file_path) {
		file_path = file_path.replaceAll("\\*", "_").replaceAll("\\?", "_").replaceAll("<", "_").replaceAll(">", "_");
		this.file = new File(file_path);
		fileIsReady();
	}

	/**
	 * 构造新的{@code FileEditor}，可实现对file所指文件的读、写操作
	 * 
	 * @param file
	 *            目标文件。如果目标文件的目录在文件系统中不存在，构造法会自动创建目录
	 */
	public FileEditor(File file) {
		this.file = file;
		fileIsReady();
	}

	private boolean fileIsReady() {
		boolean file_available = true;
		if (this.file.exists()) {
			if (this.file.isDirectory()) {
				System.out.println("File '" + this.file + "' exists but is a directory");
				file_available = false;
			}
			if (this.file.canWrite() == false) {
				System.out.println("File '" + this.file + "' cannot be written to");
				file_available = false;
			}
		} else {
			/*
			 * 如果目标文件指明了父路径，而父路径在文件系统中不存在，则创建父路径
			 */
			File parent = this.file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					System.out.println("File '" + this.file + "' could not be created");
					file_available = false;
				} else {
					System.out.println("Directories were created: " + parent.getAbsolutePath());
				}
			}
		}
		return file_available;
	}

	/**
	 * 读取文件的所有内容
	 * 
	 * @return file_content_byte_array 文件所有内容，以{@code byte[]}的形式返回
	 */
	public byte[] readFileToByteArray() {
		byte[] file_content_byte_array = null;
		if (this.file.exists()) {
			try {
				file_content_byte_array = FileUtils.readFileToByteArray(this.file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesn't exist.");
		}
		return file_content_byte_array;
	}

	/**
	 * 读取文件的所有内容
	 * 
	 * @return file_content_string 文件所有内容，以{@code String}的形式返回
	 */
	public String readFileToString() {
		return readFileToString(null);
	}

	/**
	 * 读取文件的所有内容，指定编码（一般是utf-8或gbk）
	 * 
	 * @return file_content_string 文件所有内容，以{@code String}的形式返回
	 */
	public String readFileToString(String encoding) {
		String file_content_string = null;
		if (this.file.exists()) {
			try {
				file_content_string = FileUtils.readFileToString(this.file, encoding);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesn't exist.");
		}
		return file_content_string;
	}

	/**
	 * 按行读取文件内容，将所有行都保存在list中。所谓一行，即使用('\n')或('\r')为行末标识的内容。
	 * 
	 * @return file_line_list 文件的所有行
	 */
	public List<String> readLines() {
		return readLines(null);
	}

	/**
	 * 按行读取文件内容，指定编码（一般是utf-8或gbk），将所有行都保存在list中。所谓一行，即使用('\n')或('\r')为行末标识的内容。
	 * 
	 * @return file_line_list 文件的所有行
	 */
	public List<String> readLines(String encoding) {
		List<String> file_line_list = null;
		if (this.file.exists()) {
			try {
				file_line_list = FileUtils.readLines(this.file, encoding);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesn't exist.");
		}
		return file_line_list;
	}

	/**
	 * 向文件中写入二进制数据。
	 * 
	 * @param bytes
	 *            要写入的二进制数组数据
	 * @param append
	 *            是否续写。如果是<code>false</code>，则会清空原文件所有内容，重新写入；如果是<code>true</code>，则会在原文件的末尾开始写入
	 */
	public void write(byte[] bytes, boolean append) {
		OutputStream output_stream = null;
		try {
			if (fileIsReady()) {
				output_stream = new FileOutputStream(this.file, append);
				output_stream.write(bytes);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output_stream);
		}
	}

	/**
	 * 向文件中写入{@code String}
	 *
	 * @param str
	 *            要写入的{@code String}
	 * @param append
	 *            是否续写。如果是<code>false</code>，则会清空原文件所有内容，重新写入；如果是<code>true</code>，则会在原文件的末尾开始写入
	 * @throws IOException
	 */
	public void write(String str, boolean append) {
		write(str.getBytes(), append);
	}

	/**
	 * 向文件中写入数据。数据以{@code InputStream}的形式封存
	 *
	 * @param input_stream
	 *            封存着要写入的数据
	 * @param append
	 *            是否续写。如果是<code>false</code>，则会清空原文件所有内容，重新写入；如果是<code>true</code>，则会在原文件的末尾开始写入
	 */
	public void write(InputStream input_stream, boolean append) {
		try {
			ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
			byte[] temp_bytes = new byte[1024];
			int length = -1;
			// 读取inputStream，存到bytes里。如果返回的读取长度为-1，代表全部读取完毕
			while ((length = input_stream.read(temp_bytes)) != -1) {
				// 把bytes写到byte_array_output_stream中，中间参数代表要写的bytes起始位置，length代表要写的长度
				byte_array_output_stream.write(temp_bytes, 0, length);
			}
			write(byte_array_output_stream.toByteArray(), append);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
