package com.smallchill.pay.core.utils;

import com.smallchill.pay.betcatpay.utils.BetcatPayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCliUtils {
	private static final Logger LOGGER = LogManager.getLogger(HttpCliUtils.class);
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");
		X509TrustManager trustManager = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	public static String doPost(String url, Map<String, String> headers, Map<String, Object> params, String proxyIp,
			Integer proxyPort) {
		return doPost(url, headers, params, proxyIp, proxyPort, 5000);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String doPost(String url, Map<String, String> headers, Map<String, Object> params, String proxyIp,
			Integer proxyPort, int timeout) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = null;
			if (StringUtils.isNotEmpty(proxyIp) && proxyPort != null && proxyPort > 0) {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).setProxy(new HttpHost(proxyIp, proxyPort)).build();
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build();
			}
			httpPost.setConfig(requestConfig);

			if (headers != null) {
				headers.forEach(httpPost::setHeader);
			}

			if (params != null) {
				List<NameValuePair> pairList = (List) params.entrySet().stream().map((e) -> {
					return new BasicNameValuePair((String) e.getKey(), e.getValue().toString());
				}).collect(Collectors.toList());
				httpPost.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
			}

			SSLContext sslContext = createIgnoreVerifySSL();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);

			httpClient = HttpClients.custom().setConnectionManager(connManager).build();
			response = httpClient.execute(httpPost);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					return null;
				}
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("发送Http请求错误:doPost异常,Url:{},参数:{},代理IP:{},代理端口:{}", url, JSON.toJSONString(params), proxyIp,
//					proxyPort);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
		return null;
	}

	public static String doPostJson(String url, String jsonStr, String proxyIp, Integer proxyPort) {
		return doPostJson(url, jsonStr, proxyIp, proxyPort, 5000);
	}

	public static String doPostJson(String url, String jsonStr, String proxyIp, Integer proxyPort, int timeout) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);

			RequestConfig requestConfig = null;
			if (StringUtils.isNotEmpty(proxyIp) && proxyPort != null && proxyPort > 0) {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).setProxy(new HttpHost(proxyIp, proxyPort)).build();
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build();
			}
			httpPost.setConfig(requestConfig);

			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
			stringEntity.setContentType("application/json");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);

			SSLContext sslContext = createIgnoreVerifySSL();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			HttpClients.custom().setConnectionManager(connManager);

			httpClient = HttpClients.custom().setConnectionManager(connManager).build();
			response = httpClient.execute(httpPost);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					return "";
				}
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("发送Http请求错误:doPost异常,Url:{},参数:{},代理IP:{},代理端口:{}", url, jsonStr, proxyIp, proxyPort);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
		return "";
	}

	public static String doGet(String url, String proxyIp, Integer proxyPort) {
		return doGet(url, proxyIp, proxyPort, 5000);
	}

	public static String doGet(String url, String proxyIp, Integer proxyPort, int timeout) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = null;
			if (StringUtils.isNotEmpty(proxyIp) && proxyPort != null && proxyPort > 0) {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).setProxy(new HttpHost(proxyIp, proxyPort)).build();
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build();
			}

			httpGet.setConfig(requestConfig);
			SSLContext sslContext = createIgnoreVerifySSL();
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			HttpClients.custom().setConnectionManager(connManager);

			httpClient = HttpClients.custom().setConnectionManager(connManager).build();
			response = httpClient.execute(httpGet);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					return null;
				}
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("发送Http请求错误:doGet异常,Url:{},代理IP:{},代理端口:{}", url, proxyIp, proxyPort);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception f) {

			}
		}
		return null;
	}
}
