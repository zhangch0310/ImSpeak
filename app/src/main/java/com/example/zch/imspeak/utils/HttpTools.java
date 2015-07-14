package com.example.zch.imspeak.utils;

import com.lidroid.xutils.HttpUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.List;

public class HttpTools extends HttpUtils {

	private static HttpTools httpTools;
	/**
	 * 全局cookies
	 */
	private CookieStore cookieStore = null;
	/**
	 * 当前活动的HttpContext上下文
	 */
	private HttpContext httpContext = null;

	public static HttpTools getInstance() {
		if (httpTools == null) {
			httpTools = new HttpTools();
		}
		return httpTools;
	}

	private HttpTools() {
		super();
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		configCookieStore(cookieStore);// 配置好全局cookies
		configCurrentHttpCacheExpiry(1000*15);
	}

	/**
	 * 清空所有的cookies
	 */
	public void clearCookies() {
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpTools.configCookieStore(cookieStore);// 清空cookies
	}

	/**
	 * 获取当前的cookieStore
	 * 
	 * @return
	 */
	public CookieStore getCookieStore() {
		return cookieStore;
	}

	/**
	 * 测试用
	 */
	public void printCookieStore() {
		if (cookieStore != null) {
			List<Cookie> cookies = cookieStore.getCookies();
			if (cookies.size() == 0) {
				LogUtils.d("服务器暂没有cookies");
			} else {
				for (Cookie cookie : cookies) {
					String name = cookie.getName();
					String value = cookie.getValue();
					LogUtils.d(name + " = " + value);
				}
			}
		}
	}
}
