package com.hy.java.utility.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hy.java.utility.common.FileEditor;

/**
 * 向目标url发送get请求，获取网页信息
 * <p>
 * 发送请求的方法有<code>getString({@code String} url)</code>、<code>getXML({@code String} url)</code>、<code>getJSON({@code String} url)</code>、<code>saveFile({@code String} url, {@code String} file_path)</code>
 * 
 * @author chiefeweight
 */
public class HttpUtil {
	/**
	 * 向目标url发送请求，并将返回信息（XML或JSON）保存在{@code String}中
	 * 
	 * @param url 目标url
	 * @return {@code String}类型的返回信息
	 */
	public static String getString(String url) {
		String response_body = null;
		CloseableHttpClient http_client = HttpClients.createDefault();
		try {
			// 设置http方法
			HttpGet http_method_get = new HttpGet(url);
			// 自定义response_handler，用于处理http请求的返回值
			ResponseHandler<String> response_handler = new ResponseHandler<String>() {
				// 实现处理方法
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			try {
				// 执行http方法，存储到response_body中
				response_body = http_client.execute(http_method_get, response_handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				http_client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response_body;
	}

	/**
	 * 向目标url发送请求，并将返回信息（XML）保存在{@code Document}中
	 * 
	 * @param url 目标url
	 * @return {@code Document}对象，保存了XML信息
	 */
	public static Document getXML(String url) {
		Document document = null;
		String response_body = null;
		CloseableHttpClient http_client = HttpClients.createDefault();
		try {
			// 设置http方法
			HttpGet http_method_get = new HttpGet(url);
			// 自定义response_handler，用于处理http请求的返回值
			ResponseHandler<String> response_handler = new ResponseHandler<String>() {
				// 实现处理方法
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			try {
				// 执行http方法，存储到response_body中
				response_body = http_client.execute(http_method_get, response_handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				http_client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			document = DocumentHelper.parseText(response_body);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 向目标url发送请求，并将返回的JSON格式信息保存在{@code JSONArray}中。如果返回的格式是{@code JSONObject}，则将其保存在只有它一个元素的{@code JSONArray}中。
	 * 
	 * @param url 目标url
	 * @return {@code JSONArray}对象，保存了返回的JSON格式信息
	 */
	public static JSONArray getJSON(String url) {
		JSONArray json = null;
		String response_body = null;
		CloseableHttpClient http_client = HttpClients.createDefault();
		try {
			// 设置http方法
			HttpGet http_method_get = new HttpGet(url);
			// 自定义response_handler，用于处理http请求的返回值
			ResponseHandler<String> response_handler = new ResponseHandler<String>() {
				// 实现处理方法
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			try {
				// 执行http方法，存储到response_body中
				response_body = http_client.execute(http_method_get, response_handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				http_client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			json = new JSONArray(response_body);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			json = new JSONArray();
			json.put(new JSONObject(response_body));
		}
		return json;
	}

	/**
	 * 向目标url发送请求，并保存返回的文件到本地
	 * 
	 * @param url       目标url
	 * @param file_path 保存文件的路径
	 */
	public static void saveFile(String url, String file_path) {
		CloseableHttpClient http_client = HttpClients.createDefault();
		try {
			// 设置http方法
			HttpGet http_method_get = new HttpGet(url);
			// 自定义response_handler，用于处理http请求的返回值
			ResponseHandler<byte[]> response_handler = new ResponseHandler<byte[]>() {
				// 实现处理方法
				@Override
				public byte[] handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toByteArray(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			try {
				// 执行http方法，存储到response_body中
				byte[] response_body = http_client.execute(http_method_get, response_handler);
				// 将response_body写入文件
				FileEditor fileEditor = new FileEditor(file_path);
				fileEditor.write(response_body, false);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				http_client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
