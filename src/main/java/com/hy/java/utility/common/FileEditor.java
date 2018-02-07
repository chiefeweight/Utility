package com.hy.java.utility.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于编辑File，可以实现读、写任意数据的操作
 * <p>
 * 读的方法有<code>readAll()</code>、<code>readLine()</code>
 * <p>
 * 写的方法名字均为write，可以写入{@code byte[]}、{@code InputStream}、{@code String}
 * 
 * @author chiefeweight
 */
public class FileEditor {
	private File file;
	/**
	 * 构造新的{@code FileEditor}时，默认为<code>true</code>。每调用一次write方法都会将此标识改为<code>true</code>。
	 */
	private boolean is_changed = true;
	private BufferedReader buffered_reader;

	/**
	 * 构造新的{@code FileEditor}，可实现对file_path所指的文件读、写操作
	 * 
	 * @param file_path
	 *            目标文件路径。构造法会自动将路径中的非法字符（“*”、“?”、“<”、“>”）替换为“_”。如果没有目标文件路径中的目录，构造法会自动创建该目录
	 */
	public FileEditor(String file_path) {
		file_path = file_path.replaceAll("\\*", "_").replaceAll("\\?", "_").replaceAll("<", "_").replaceAll(">", "_");
		this.file = new File(file_path);
		File parent = this.file.getParentFile();
		// 如果目标文件指明了父路径，而父路径在文件系统中不存在
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
			System.out.println("Directories made: " + parent.getAbsolutePath());
		}
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
		try {
			FileOutputStream output_stream = new FileOutputStream(this.file, append);
			output_stream.write(bytes);
			output_stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		is_changed = true;
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
		ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
		byte[] temp_bytes = new byte[1024];
		// 每次读取的字符串长度。如果为-1，代表全部读取完毕
		int length = 0;
		try {
			// 读取inputStream，存到bytes里
			while ((length = input_stream.read(temp_bytes)) != -1) {
				// 把bytes写到byte_array_output_stream中，中间参数代表从哪个位置开始读，length代表读取的长度
				byte_array_output_stream.write(temp_bytes, 0, length);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.write(byte_array_output_stream.toByteArray(), append);
	}

	/**
	 * 向文件中写入{@code String}
	 *
	 * @param str
	 *            要写入的{@code String}
	 * @param append
	 *            是否续写。如果是<code>false</code>，则会清空原文件所有内容，重新写入；如果是<code>true</code>，则会在原文件的末尾开始写入
	 */
	public void write(String str, boolean append) {
		this.write(str.getBytes(), append);
	}

	/**
	 * 读取文件的所有内容
	 * 
	 * @return fileContentBytes 文件所有内容，以{@code byte[]}的形式返回
	 */
	public byte[] readAll() {
		byte[] file_content_bytes = null;
		if (this.file.exists()) {
			ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
			try {
				byte[] temp_bytes = new byte[1024];
				int length = 0;
				FileInputStream file_input_stream = new FileInputStream(this.file);
				while ((length = file_input_stream.read(temp_bytes)) != -1) {
					byte_array_output_stream.write(temp_bytes, 0, length);
				}
				file_input_stream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file_content_bytes = byte_array_output_stream.toByteArray();
		} else {
			System.out.println("File is null");
		}
		return file_content_bytes;
	}

	/**
	 * 读取文件的一行内容。所谓一行，即使用('\n')或('\r')为行末标识的内容。连续使用该方法可以将文件内容“一行一行”地读完
	 * <p>
	 * 每当对文件进行write操作后，此方法都会重新从头读
	 * <p>
	 * 任何时间（包括读完所有内容后）如果想重新从头读，都需调用resetReader()
	 * 
	 * @return line 文件的一行内容，以{@code String}的形式返回
	 */
	public String readLine() {
		String line = null;
		if (this.file.exists()) {
			if (this.is_changed) {
				resetReader();
				this.is_changed = false;
			}
			try {
				if ((line = this.buffered_reader.readLine()) == null) {
					this.buffered_reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("File is null");
		}
		return line;
	}

	/**
	 * 调用该方法将使readLine()重新从头读
	 */
	public void resetReader() {
		try {
			this.buffered_reader = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
