package com.personal.test.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.personal.test.demo.filter.util.HttpClientUtil;
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

        if (req.getRequestURI().startsWith("/hello")) {
            String url = "http://192.168.6.149:9000" + req.getRequestURI();
            log.info(url);
            HttpClientUtil.getInstance().get(url, wrapperRequest, resp);
            return;
        }

        if (req.getRequestURI().startsWith("/people")) {
            String url = "http://192.168.6.149:9000" + req.getRequestURI();
            log.info(url);
            HttpClientUtil.getInstance().post(url, wrapperRequest, resp);
            return;
        }
    }

    private boolean isUrlMatch(final String uri) {
        return uri.startsWith("/hello") || uri.startsWith("/people");
    }

}
