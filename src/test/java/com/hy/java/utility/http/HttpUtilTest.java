package com.hy.java.utility.http;

import org.junit.Test;

public class HttpUtilTest {
	@Test
	public void getString() {
		// System.out.print(HttpUtil.getString("https://www.baidu.com"));
		System.out.println(HttpUtil.getJSON("https://api.codetabs.com/v1/loc?github=chiefeweight/utility"));
		System.out.println(HttpUtil.getJSON("https://api.github.com/"));
		/*
		 * HttpUtil.saveFile(
		 * "http://www.worlduc.com/UploadFiles/BlogFile/643/19345149/libsvm.pdf",
		 * "F:\\1"); Document d =
		 * HttpUtil.getXML("https://dom4j.github.io/#string-conversion");
		 * System.out.print(d.asXML());
		 */
	}
}
