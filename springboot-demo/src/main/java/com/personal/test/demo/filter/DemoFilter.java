package com.personal.test.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.test.demo.filter.util.HttpClientUtil;
import com.personal.test.demo.filter.util.RemoteResponseResult;
import com.personal.test.demo.filter.util.RemoteResponseResult.Pair;
import com.personal.test.demo.filter.wrapper.RequestWrapper;
import com.personal.test.demo.filter.wrapper.ResponseWrapper;

public final class DemoFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req   = null;
        HttpServletResponse resp = null;

        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        }

        if (response instanceof HttpServletResponse) {
            resp = (HttpServletResponse) response;
        }

        if (null == req || null == resp) {
            chain.doFilter(request, response);
            return;
        }

        if (!this.isUrlMatch(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        RequestWrapper wrapperRequest = new RequestWrapper(req);
        ResponseWrapper wrapperResponse = new ResponseWrapper(resp);
        chain.doFilter(wrapperRequest, wrapperResponse);
        RemoteResponseResult remoteRlt = remoteCall(wrapperRequest, resp);

        resp.setStatus(remoteRlt.getStatusCode());
        resp.setContentType(remoteRlt.getContentType());
        resp.setContentLength(remoteRlt.getContentLength());
        if (null != remoteRlt.getRespHeaders()) {
            for (Pair p : remoteRlt.getRespHeaders()) {
                resp.addHeader(p.getName(), p.getVal());
            }
        }
        resp.getOutputStream().write(remoteRlt.getBody());

        log.info("Local Response: " + wrapperResponse.toString());
        log.info("Remote response: " + new String(remoteRlt.getBody(), "utf-8"));
    }

    private RemoteResponseResult remoteCall(final HttpServletRequest req, final HttpServletResponse resp)
            throws ClientProtocolException, IOException {
        String method = req.getMethod();
        String url = "http://192.168.6.149:9000" + req.getRequestURI();
        if ("GET".equalsIgnoreCase(method)) {
            return HttpClientUtil.getInstance().get(url, req, resp);
        } else if ("POST".equalsIgnoreCase(method)) {
            return HttpClientUtil.getInstance().post(url, req, resp);
        } else {
            throw new RuntimeException("Unsupport http method: " + method);
        }
    }

    private boolean isUrlMatch(final String uri) {
        return uri.startsWith("/hello") || uri.startsWith("/people");
    }

}
