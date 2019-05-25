/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lcyframework.kernel.common.util;

import lombok.extern.slf4j.Slf4j;
import net.lcyframework.kernel.core.config.BaseProperties;
import net.lcyframework.kernel.core.consts.SysErrorConsts;
import net.lcyframework.kernel.core.consts.SysRestConsts;
import net.lcyframework.kernel.core.exception.SysException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 名称: EncryptUtils
 * 描述: HtpClient4.0封装
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public final class HttpClientUtil {

    private static final String REQUEST_ID = "requestId";
    private static final String STR_ENCODE = "UTF-8";
    private static final String USER_AGENT = "user_agent";
    private static final String HJ_IBJ = "hj_ibj";

    private static RequestConfig REQUEST_CONFIG;

    private HttpClientUtil() { }
    static {
        if (REQUEST_CONFIG == null) {
            REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(BaseProperties.getProperty("httpclient.socketTimeout", Integer.class, SysRestConsts.HTTP_TIMEOUT))
            .setConnectTimeout(BaseProperties.getProperty("httpclient.connectTimeout", Integer.class, SysRestConsts.HTTP_TIMEOUT))
            .setConnectionRequestTimeout(BaseProperties.getProperty("httpclient.connectionRequestTimeout", Integer.class, SysRestConsts.HTTP_TIMEOUT))
            .build();
        }
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param header 请求头
     * @return string 字符串
     */
    public static String sendHttpPost(final String httpUrl, final Header... header) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        if (header != null) {
            httpPost.setHeaders(header);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     * @param header 请求头
     * @return string 字符串
     */
    public static String sendHttpPost(final String httpUrl, final String params, final Header... header) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, STR_ENCODE);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            if (header != null) {
                httpPost.setHeaders(header);
            }
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * post请求
     * @param httpUrl 地址
     * @param maps 参数
     * @param headers 请求头
     * @return string 响应字符串
     */
    public static String sendHttpPost(final String httpUrl, final Map<String, String> maps,
            final Map<String, String> headers) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), maps.get(entry.getKey())));
        }
        try {
            if (EmptyUtils.isNotEmpty(headers)) {
                headers.forEach((n, k) -> {
                    httpPost.addHeader(new BasicHeader(n, k));
                });
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, STR_ENCODE));
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     * @param header 请求头
     * @return string 响应字符串
     */
    public static String sendHttpPost(final String httpUrl, final Map<String, String> maps, final Header... header) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), maps.get(entry.getKey())));
        }
        try {
            if (header != null) {
                httpPost.setHeaders(header);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, STR_ENCODE));
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     * @return string 响应字符串
     */
    public static String sendHttpPost(final String httpUrl, final Map<String, String> maps) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), maps.get(entry.getKey())));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, STR_ENCODE));
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     * @param headers 请求头
     * @return string 响应字符串
     */
    public static String sendHttpPut(final String httpUrl, final Map<String, String> maps, final Header... headers) {
        // 创建httpPost
        HttpPut httpPut = new HttpPut(httpUrl);
        if (headers != null) {
            httpPut.setHeaders(headers);
        }
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), maps.get(entry.getKey())));
        }
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs, STR_ENCODE));
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        }
        return sendHttpRequest(httpPut);
    }

    /**
     * 发送 put请求
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     * @param header 请求头
     * @return string 响应字符串
     */
    public static String sendHttpPut(final String httpUrl, final String params, final Header... header) {
        HttpPut httpPut = new HttpPut(httpUrl);
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);
            if (header != null) {
                httpPut.setHeaders(header);
            }
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e.getCause());
        }
        return sendHttpRequest(httpPut);
    }

    /**
     * 发送delete请求，带body
     *
     * @param httpUrl 地址
     * @param params 请求参数
     * @param headers 请求头
     * @return string 响应字符串
     */
    public static String sendHttpDelete(final String httpUrl, final String params, final Header... headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (EmptyUtils.isNotEmpty(headers)) {
            httpHeaders.add(SysRestConsts.REQUEST_ID, MDC.get(SysRestConsts.REQUEST_ID));
            httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            for (Header header : headers) {
                httpHeaders.add(header.getName(), header.getValue());
            }
        }
        org.springframework.http.HttpEntity<String> httpEntity = new org.springframework.http.HttpEntity<String>(params, httpHeaders);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(httpUrl, HttpMethod.DELETE, httpEntity, String.class);
        return responseEntity.getBody();
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param headers 参数
     * @return string 响应字符串
     */
    public static String sendHttpDelete(final String httpUrl, final Header... headers) {
        // 创建httpDete
        HttpDelete httpDelete = new HttpDelete(httpUrl);
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        return sendHttpRequest(httpDelete);
    }

    /**
     * 发送Post请求
     * @param httpPost the httpPost
     * @return string 响应字符串
     */
    private static String sendHttpPost(final HttpPost httpPost) {
        return HttpClientUtil.sendHttpRequest(httpPost);
    }

    /**
     * 发送 get请求
     * @param httpUrl 地址
     * @return string 响应字符串
     */
    public static String sendHttpGet(final String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求
     * @param httpUrl 地址
     * @param header 请求头
     * @return string 响应字符串
     */
    public static String sendHttpGet(final String httpUrl, final Header... header) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        if (header != null) {
            httpGet.setHeaders(header);
        }
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求Https
     * @param httpUrl 地址
     * @param header 请求头
     * @return string 响应字符串
     * @throws Exception 异常
     */
    public static String sendHttpsGet(final String httpUrl, final Header... header) throws Exception {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        if (header != null) {
            httpGet.setHeaders(header);
        }
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送Get请求
     * @param httpGet
     * @return string 响应字符串
     */
    private static String sendHttpGet(final HttpGet httpGet) {
        return sendHttpRequest(httpGet);
    }

    /**
     * 发送Get请求Https
     * @param httpGet the httpGet
     * @return string 响应字符串
     */
    private static String sendHttpsGet(final HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(REQUEST_CONFIG);
            //在http header中存入requestId
            setRequestId(httpGet);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, STR_ENCODE);
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                HttpClientUtil.closeResources(response, httpClient);
            } catch (final IOException e) {
                throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     * @param httpRequestBase the httpRequestBase
     * @return string 响应字符串
     */
    public static String sendHttpRequest(final HttpRequestBase httpRequestBase) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        long start = System.currentTimeMillis();
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpRequestBase.setConfig(REQUEST_CONFIG);
            //在http header中存入requestId
            setRequestId(httpRequestBase);
            // 执行请求
            response = httpClient.execute(httpRequestBase);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, STR_ENCODE);
        } catch (final Exception e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                HttpClientUtil.closeResources(response, httpClient);
            } catch (final IOException e) {
                throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
            }
        }
        log.info("httpclient [{}], cost time [{}] ms )", httpRequestBase.getURI().toString(), System.currentTimeMillis() - start);
        return responseContent;
    }

    private static void closeResources(final CloseableHttpResponse closeableHttpResponse,
            final CloseableHttpClient closeableHttpClient) throws IOException {
        if (closeableHttpResponse != null) {
            closeableHttpResponse.close();
        }
        if (closeableHttpClient != null) {
            closeableHttpClient.close();
        }
    }

    private static void setRequestId(final HttpMessage httpMessage) {
         httpMessage.addHeader(REQUEST_ID, MDC.get(REQUEST_ID));
         httpMessage.addHeader(USER_AGENT, HJ_IBJ);
    }
}
