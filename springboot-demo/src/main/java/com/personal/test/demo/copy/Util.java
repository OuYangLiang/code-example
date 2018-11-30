package com.personal.test.demo.copy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Util {
    
    public void post(String url, HttpServletRequest req, HttpServletResponse resp) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);
        
        Enumeration<String> enums = req.getHeaderNames();
        while (enums.hasMoreElements()) {
            String k = enums.nextElement();
            String v = req.getHeader(k);
            if (!k.equals("content-length")) {
                post.addHeader(k, v);
            }
        }
        
        post.setEntity(new InputStreamEntity(req.getInputStream()));
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(post);
        HttpEntity entity = response.getEntity();
        
        resp.setStatus(response.getStatusLine().getStatusCode());
        resp.setContentType(entity.getContentType().getValue());
        resp.setContentLength((int)entity.getContentLength());
        for (Header h : response.getAllHeaders()) {
            if ("Transfer-Encoding".equalsIgnoreCase(h.getName())) {
                continue;
            }
            resp.addHeader(h.getName(), h.getValue());
        }
        
        resp.getOutputStream().write(read(entity.getContent()));
        EntityUtils.consume(entity);
        response.close();
    }
    
    public void get(String url, HttpServletRequest req, HttpServletResponse resp) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(url);
        
        Enumeration<String> enums = req.getHeaderNames();
        while (enums.hasMoreElements()) {
            String k = enums.nextElement();
            String v = req.getHeader(k);
            httpGet.addHeader(k, v);
        }
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        
        
        resp.setStatus(response.getStatusLine().getStatusCode());
        resp.setContentType(entity.getContentType().getValue());
        resp.setContentLength((int)entity.getContentLength());
        for (Header h : response.getAllHeaders()) {
            if ("Transfer-Encoding".equalsIgnoreCase(h.getName())) {
                continue;
            }
            resp.addHeader(h.getName(), h.getValue());
        }
        
        resp.getOutputStream().write(read(entity.getContent()));
        EntityUtils.consume(entity);
        response.close();
    }
    
    
    public static byte[] read(InputStream in) throws IOException {
        ByteArrayOutputStream result = null;
        try {
            result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
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
