package com.hy.java.utility.http;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;

import com.hy.java.utility.common.FileEditor;

/**
 * 向目标url发送get请求，获取网页信息
 * <p>
 * 发送请求的方法有<code>getHtml({@code String} url,{@code String} save_type)</code>、
 * <code>saveImage({@code String} url, {@code String} img_path)</code>
 * 
 * @author chiefeweight
 */
public class HttpRequest {
	/**
	 * 返回已向之发送请求的url的html信息
	 * 
	 * @param url
	 *            目标url
	 * @param save_type
	 *            将html保存到的类型，只支持{@code Document}、{@code JSONObject}、 {@code String}
	 * @return html 已向之发送请求的url的html信息，保存类型为save_type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getHtml(String url, String save_type) {
		String html = "";
		try {
			URL real_URL = new URL(url);
			URLConnection urlConnection = real_URL.openConnection();
			// 设置请求属性，然后开始connect
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Accept-Encoding", "*");
			urlConnection.connect();
			// 获得url_connection上所有头信息，解析网页的编码
			Map<String, List<String>> header_fields_map = urlConnection.getHeaderFields();
			String charset = null;
			if (!header_fields_map.isEmpty()) {
				if (!header_fields_map.get("Content-Type").isEmpty()) {
					if (header_fields_map.get("Content-Type").get(0).contains(";")) {
						if (header_fields_map.get("Content-Type").get(0).split(";").length > 1) {
							if (header_fields_map.get("Content-Type").get(0).split(";")[1].contains("=")) {
								charset = header_fields_map.get("Content-Type").get(0).split(";")[1].split("=")[1];
							}
						}
					}
				}
			}
			// 将url_connection的信息保存到html里
			InputStream url_InputStream = urlConnection.getInputStream();
			InputStreamReader url_InputStreamReader = null;
			if (charset != null) {
				url_InputStreamReader = new InputStreamReader(url_InputStream, charset);// 注意此处的编码
			} else {
				url_InputStreamReader = new InputStreamReader(url_InputStream);
			}
			BufferedReader url_connection_reader = new BufferedReader(url_InputStreamReader);
			String temp_string = null;
			while ((temp_string = url_connection_reader.readLine()) != null) {
				html += temp_string;
			}
			url_connection_reader.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (save_type.equals("Document")) {
			return (T) Jsoup.parse(html);
		} else if (save_type.equals("JSONObject")) {
			return (T) JSONObject.fromObject(html);
		} else {
			return (T) html;
		}
	}

	/**
	 * 向目标url发送请求，并保存获取到的网页图片
	 * 
	 * @param url
	 *            目标url
	 * @param img_path
	 *            保存图片文件的路径
	 */
	public static void saveImage(String url, String img_path) {
		try {
			URL real_URL = new URL(url);
			URLConnection urlConnection = real_URL.openConnection();
			// 设置请求属性，然后开始connect
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Accept-Encoding", "*");
			urlConnection.connect();
			InputStream inputStream = urlConnection.getInputStream();
			FileEditor fileEditor = new FileEditor(img_path);
			fileEditor.write(inputStream, false);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
