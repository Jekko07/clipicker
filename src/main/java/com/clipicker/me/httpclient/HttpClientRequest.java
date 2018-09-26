package com.clipicker.me.httpclient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLContext;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientRequest {
	private static CloseableHttpClient client;
	private static final int ONE_SECOND = 1000;
	private static final int FIFTEEN_SECONDES = 15 * ONE_SECOND;
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientRequest.class);
	private static final String ACCEPT_TYPE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private static final Header ACCEPT_HEADER = new BasicHeader("accept", ACCEPT_TYPE);
	private static final int MAX_PER_ROUTE = 100;
	private static final int MAX_TOTAL = 300;
	private static RequestConfig TIMEOUT_1MINUTE = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(ONE_SECOND * 60).build();
	static {
		client = HttpClients.custom().setDefaultRequestConfig(globalRequestConfig()).setDefaultHeaders(globalHeader()).setConnectionManager(newConnectionManager()).build();
	}

	private static PoolingHttpClientConnectionManager newConnectionManager() {
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build();
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

			Registry<ConnectionSocketFactory> registries = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslSocketFactory).build();

			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registries);
			cm.setDefaultConnectionConfig(globalConnectionConfig());
			cm.setDefaultSocketConfig(socketConfig);
			cm.setMaxTotal(MAX_TOTAL);
			cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
			return cm;
		} catch (Exception e) {
			LOGGER.error("Init ssl for httpclient failed!", e);
		}
		return null;
	}

	private static RequestConfig globalRequestConfig() {
		return RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(FIFTEEN_SECONDES).setConnectTimeout(FIFTEEN_SECONDES).build();

	}

	private static List<Header> globalHeader() {
		return Arrays.asList(ACCEPT_HEADER);
	}

	private static ConnectionConfig globalConnectionConfig() {
		return ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
	}

	public static String post(String url) throws IOException {
		return post(url, null);
	}

	public static String get(String url) throws IOException {
		HttpGet get = new HttpGet(url);
		return execute(get);
	}

	public static String getWithTimeout1minute(String url) throws IOException {
		return getWithConfig(url, TIMEOUT_1MINUTE);
	}

	public static String getWithConfig(String url, RequestConfig config) throws IOException {
		HttpGet get = new HttpGet(url);
		get.setConfig(config);
		return execute(get);
	}

	private static String execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		LOGGER.info("Get url:{}", request.getURI().toString());
		CloseableHttpResponse response = client.execute(request);
		try {
			return EntityUtils.toString(response.getEntity());
		} finally {
			response.close();
		}
	}

	public static String post(String url, PostParams params) throws IOException {
		HttpPost post = new HttpPost(url);
		if (params != null) {
			post.setEntity(params.getEntity());
		}
		return execute(post);
	}

    public static String postJson(String url, String json, Map<String, String> headers) throws IOException {
        HttpPost post = new HttpPost(url);
        if (headers != null) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        post.setEntity(new StringEntity(json, ContentType.create("application/json", Consts.UTF_8)));
        return execute(post);
    }
}
