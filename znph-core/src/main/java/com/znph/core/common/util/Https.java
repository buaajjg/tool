
package com.znph.core.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.UUID;

public class Https {

	public static final String USER_AGENT_IE_64 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)";
	public static final String USER_AGENT_IE_86 = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
	public static final String USER_AGENT_CHROME = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
	public static final String USER_AGENT_FIREFOX = "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10";

	static {
		HttpURLConnection.setFollowRedirects(true);
	}

	public static final String DEFAULT_ENCODE = "UTF-8";
	public static final int DEFAULT_TIMEOUT = 10000;

	public static long timeout(String host, int port) {
		try {
			return timeout(host, port, DEFAULT_TIMEOUT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static long timeout(String host, int port, int timeout) {
		try {
			long start = System.currentTimeMillis();
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), timeout);
			socket.close();
			return System.currentTimeMillis() - start;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpURLConnection connection(String url) {
		try {
			if (!url.startsWith("http")) {
				url = "http://" + url;
			}
			return httpConnection(new URL(url).openConnection());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpURLConnection connection(String url, String proxyHost, int proxyPort) {
		try {
			if (!url.startsWith("http")) {
				url = "http://" + url;
			}
			return httpConnection(
					new URL(url).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort))));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpURLConnection httpConnection(URLConnection urlConnection) {
		HttpURLConnection connection = (HttpURLConnection) urlConnection;
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.addRequestProperty("Pragma", "no-cache");
		connection.addRequestProperty("Cache-Control", "no-cache");
		connection.setRequestProperty("Accept-Language", "zh-CN");
		connection.setRequestProperty("Accept-Encoding", "gzip");
		connection.addRequestProperty("User-Agent", USER_AGENT_CHROME);
		connection.setConnectTimeout(DEFAULT_TIMEOUT);
		connection.setReadTimeout(DEFAULT_TIMEOUT);
		return connection;
	}

	public static HttpURLConnection ajaxConnection(String url) {
		HttpURLConnection connection = connection(url);
		connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		return connection;
	}

	public static HttpURLConnection getConnection(String url) {
		return connection(url);
	}

	public static HttpURLConnection getConnection(String url, Map<String, ? extends Object> params) {
		String urlParams = Strings.params(params);
		if (url.indexOf("?") > -1) {
			url += "&" + urlParams;
		} else {
			url += "?" + urlParams;
		}
		return connection(url);
	}

	public static HttpURLConnection postConnection(String url) {
		try {
			HttpURLConnection connection = connection(url);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			return connection;
		} catch (ProtocolException e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String url) {
		return get(url, null, null);
	}

	public static String get(String url, String charset) {
		return get(url, null, charset);
	}

	public static String get(String url, Map<String, ? extends Object> params) {
		try {
			return response(getConnection(url, params), null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String url, Map<String, ? extends Object> params, String charset) {
		try {
			return response(getConnection(url, params), charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String post(String url, Map<String, ? extends Object> params) {
		return post(url, params, null);
	}

	public static String post(String url, Map<String, ? extends Object> params, String charset) {
		return post(url, Strings.params(params).getBytes(), charset);
	}

	public static String post(String url, byte[] content) {
		return post(url, content, null);
	}

	public static String post(String url, byte[] content, String charset) {
		try {
			HttpURLConnection connection = postConnection(url);
			connection.getOutputStream().write(content);
			return response(connection, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String post(String url, File file) throws Exception {
		return post(url, file, "media");
	}
	
	public static String post(String url, File file, String fileFormId) throws Exception {
		return post(url, file, fileFormId, file.getName());
	}
	
	public static String post(String url, File file, String fileFormId, String fileName) throws Exception {
		HttpURLConnection connection = postConnection(url);
		String BOUNDARY = "----------" + UUID.randomUUID();
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		StringBuffer sb = new StringBuffer();
		sb.append("--" + BOUNDARY + "\r\n");
		sb.append("Content-Disposition: form-data;name=\""+fileFormId+"\";filename=\"" + fileName + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		OutputStream out = connection.getOutputStream();
		out.write(sb.toString().getBytes());
		Streams.copy(new FileInputStream(file), out);
		out.write(("\r\n--" + BOUNDARY + "--\r\n").getBytes());
		out.flush();
		out.close();
		return response(connection);
	}

	public static String response(HttpURLConnection connection) {
		return response(connection, null);
	}

	public static String response(HttpURLConnection connection, String charset) {
		try {
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				if (charset == null) {
					String contentType = connection.getContentType();
					if (contentType != null && contentType.indexOf("=") > -1) {
						charset = contentType.substring(contentType.indexOf("=") + 1);
					}
				}
				byte[] bytes = Streams.bytes(connection.getInputStream());
				if (connection.getContentEncoding() != null && connection.getContentEncoding().equals("gzip")) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					Streams.ungzip(new ByteArrayInputStream(bytes), bos);
					bytes = bos.toByteArray();
				}
				if (charset == null) {
					String content = new String(bytes);
					charset = Strings.find(content, "<meta.*?charset=\"?(.*?)\".*?>", 1);
					if (charset == null) {
						return new String(bytes, DEFAULT_ENCODE);
					} else {
						return new String(bytes, charset);
					}
				} else {
					return new String(bytes, charset);
				}
			} else if (responseCode == 302) {
				return responseCode + " " + connection.getHeaderField("location");
			} else {
				return responseCode + " " + connection.getResponseMessage();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		// System.out.println(get("http://jira.sihuatech.com:8088/browse/SCSPII-302"));
		// System.out.println(get("http://www.kuaidaili.com/"));
		System.out.println(get("https://www.baidu.com"));
	}
}
