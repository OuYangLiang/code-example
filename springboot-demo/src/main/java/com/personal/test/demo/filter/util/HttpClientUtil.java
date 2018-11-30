package com.personal.test.demo.filter.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public final class HttpClientUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static volatile HttpClientUtil instance;
    private HttpClientUtil() {

    }

    public static HttpClientUtil getInstance() {
        if (null == instance) {
            synchronized (HttpClientUtil.class) {
                if (null == instance) {
                    instance = new HttpClientUtil();
                }
            }
        }

        return instance;
    }

    private static final int MAC_CONCURRENT_PER_ROUTE = 20;
    private static final int MAX_CONCURRENT = 30;
    private static final int REQ_TIMEOUT = 2000;
    private static final int CONN_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 10000;

    private static final RequestConfig REQ_CONFIG = RequestConfig.custom()
            .setConnectTimeout(CONN_TIMEOUT)
            .setConnectionRequestTimeout(REQ_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT).build();


    public CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connMgr =
                new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(MAX_CONCURRENT);
        connMgr.setDefaultMaxPerRoute(MAC_CONCURRENT_PER_ROUTE);

        return HttpClientBuilder.create()
                .setConnectionManager(connMgr)
                .build();
    }

    public RemoteResponseResult post(final String url, final HttpServletRequest req,
            final HttpServletResponse resp) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);
        post.setConfig(REQ_CONFIG);

        Enumeration<String> enums = req.getHeaderNames();
        while (enums.hasMoreElements()) {
            String k = enums.nextElement();
            String v = req.getHeader(k);
            if (!k.equals("content-length")) {
                post.addHeader(k, v);
            }
        }

        post.setEntity(new InputStreamEntity(req.getInputStream()));

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = this.getClient();
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();

            RemoteResponseResult rlt = new RemoteResponseResult();
            rlt.setStatusCode(response.getStatusLine().getStatusCode());
            rlt.setContentType(entity.getContentType().getValue());
            rlt.setContentLength((int) entity.getContentLength());
            for (Header h : response.getAllHeaders()) {
                if ("Transfer-Encoding".equalsIgnoreCase(h.getName())) {
                    continue;
                }
                rlt.addRespHeader(h.getName(), h.getValue());
            }
            rlt.setBody(read(entity.getContent()));
            EntityUtils.consume(entity);

            return rlt;
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpclient) {
                httpclient.close();
            }
        }
    }

    public RemoteResponseResult get(final String url,
            final HttpServletRequest req, final HttpServletResponse resp)
                    throws ClientProtocolException, IOException {

        StringBuilder sb = new StringBuilder(url);

        Enumeration<String> enums = req.getParameterNames();
        if (null != enums) {
            sb.append("?");
            while (enums.hasMoreElements()) {
                String k = enums.nextElement();
                String v = req.getParameter(k);
                sb.append(k).append("=").append(v).append("&");
            }
        }

        HttpGet httpGet = new HttpGet(sb.toString());
        httpGet.setConfig(REQ_CONFIG);

        enums = req.getHeaderNames();
        while (enums.hasMoreElements()) {
            String k = enums.nextElement();
            String v = req.getHeader(k);
            httpGet.addHeader(k, v);
        }

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = this.getClient();
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            RemoteResponseResult rlt = new RemoteResponseResult();
            rlt.setStatusCode(response.getStatusLine().getStatusCode());
            rlt.setContentType(entity.getContentType().getValue());
            rlt.setContentLength((int) entity.getContentLength());
            for (Header h : response.getAllHeaders()) {
                if ("Transfer-Encoding".equalsIgnoreCase(h.getName())) {
                    continue;
                }
                rlt.addRespHeader(h.getName(), h.getValue());
            }
            rlt.setBody(read(entity.getContent()));
            EntityUtils.consume(entity);

            return rlt;
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpclient) {
                httpclient.close();
            }
        }
    }

    public byte[] read(final InputStream in) throws IOException {
        ByteArrayOutputStream result = null;
        try {
            result = new ByteArrayOutputStream();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toByteArray();
        } finally {
            if (null != result) {
                result.close();
            }
        }

    }

}
