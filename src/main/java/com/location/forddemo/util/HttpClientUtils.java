package com.location.forddemo.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Description: httpClient工具类
 *
 * @author JourWon
 * @date Created on 2018年4月19日
 */
public class HttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * POST 请求
     *
     * @param uri URI
     * @return response
     * @throws IOException
     */
    public static String doPost(String uri) throws IOException {
        CloseableHttpClient httpClient = createHttpClient("", true, 10);
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    log.error("Unexpected response status: " + status);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        String response = httpClient.execute(httpPost, responseHandler);
        return response;
    }

    /**
     * Get 请求
     *
     * @param uri URI
     * @return response
     * @throws IOException
     */
    public static String doGet(String uri) throws IOException {
        CloseableHttpClient httpClient = createHttpClient("", true, 10);
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    log.error("Unexpected response status: " + status);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        String response = httpClient.execute(httpGet, responseHandler);
        return response;
    }

    /**
     * @param sessionId         会话ID
     * @param automaticRedirect 自动重定向
     * @param timeOut           超时时间
     * @return CloseableHttpClient
     */
    private static CloseableHttpClient createHttpClient(String sessionId, boolean automaticRedirect, int timeOut) {
        //忽略校验HTTPS服务器证书, 将hostname校验和CA证书校验同时关闭
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.error("HttpClientUtils.createHttpClient: " + e);
        }
        //设置http和https协议对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //构建请求配置信息
        timeOut = timeOut * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeOut) //获取数据的超时时间
                .setConnectTimeout(timeOut) //建立连接的超时时间
                .setConnectionRequestTimeout(timeOut) //连接池获取到连接的超时时间
                .build();
        //创建自定义httpclient对象
        HttpClientBuilder builder = HttpClients.custom();
        builder.setDefaultRequestConfig(requestConfig); //设置默认请求配置
        builder.setConnectionManager(connectionManager); //设置连接管理器
        if (!automaticRedirect) { //设置自动重定向配置
            builder.disableRedirectHandling();
        }
        //配置CookieStore
        CookieStore cookieStore = null;
        if (StringUtils.isNotBlank(sessionId)) {
            //TODO...redis查找cookieStore
            if (cookieStore == null) {
                cookieStore = new BasicCookieStore();
            }
            builder.setDefaultCookieStore(cookieStore);
        }
        CloseableHttpClient httpClient = builder.build();
        return httpClient;
    }
}