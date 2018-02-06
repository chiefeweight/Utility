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
 * 发送请求的方法有<code>getHtml({@code String} url,{@code String} saveClass)</code>、
 * <code>saveImage({@code String} url, {@code String} imgPath)</code>
 */
public class HttpRequest {
	/**
	 * 返回已向之发送请求的url的html信息
	 * 
	 * @param url
	 *            目标url
	 * @param saveClass
	 *            将html保存到的类型，只支持{@code Document}、{@code JSONObject}、 {@code String}
	 * @return html 已向之发送请求的url的html信息，保存类型为saveClass
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getHtml(String url, String saveClass) {
		String html = "";
		try {
			URL realURL = new URL(url);
			URLConnection urlConnection = realURL.openConnection();
			// 设置请求属性，然后开始connect
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Accept-Encoding", "*");
			urlConnection.connect();
			// 获得urlConnection上所有头信息，解析网页的编码
			Map<String, List<String>> headerFieldsMap = urlConnection.getHeaderFields();
			String charset = null;
			if (!headerFieldsMap.isEmpty()) {
				if (!headerFieldsMap.get("Content-Type").isEmpty()) {
					if (headerFieldsMap.get("Content-Type").get(0).contains(";")) {
						if (headerFieldsMap.get("Content-Type").get(0).split(";").length > 1) {
							if (headerFieldsMap.get("Content-Type").get(0).split(";")[1].contains("=")) {
								charset = headerFieldsMap.get("Content-Type").get(0).split(";")[1].split("=")[1];
							}
						}
					}
				}
			}
			// 将urlConnection的信息保存到html里
			InputStream urlInputStream = urlConnection.getInputStream();
			InputStreamReader urlInputStreamReader = null;
			if (charset != null) {
				urlInputStreamReader = new InputStreamReader(urlInputStream, charset);// 注意此处的编码
			} else {
				urlInputStreamReader = new InputStreamReader(urlInputStream);
			}
			BufferedReader urlConnectionReader = new BufferedReader(urlInputStreamReader);
			String tempString = null;
			while ((tempString = urlConnectionReader.readLine()) != null) {
				html += tempString;
			}
			urlConnectionReader.close();
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
		if (saveClass.equals("Document")) {
			return (T) Jsoup.parse(html);
		} else if (saveClass.equals("JSONObject")) {
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
	 * @param imgPath
	 *            保存图片文件的路径
	 */
	public static void saveImage(String url, String imgPath) {
		try {
			URL realURL = new URL(url);
			URLConnection urlConnection = realURL.openConnection();
			// 设置请求属性，然后开始connect
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Accept-Encoding", "*");
			urlConnection.connect();
			InputStream inputStream = urlConnection.getInputStream();
			FileEditor fileEditor = new FileEditor(imgPath);
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
