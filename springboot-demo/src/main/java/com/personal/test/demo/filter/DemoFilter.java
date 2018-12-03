package com.personal.test.demo.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import com.personal.test.demo.filter.util.Switch;
import com.personal.test.demo.filter.wrapper.RequestWrapper;
import com.personal.test.demo.filter.wrapper.ResponseWrapper;

public final class DemoFilter implements Filter {

    private static final int DEFAULT_BUFFER_SIZE = 500;
    private static final int NUMBER_OF_THREADS = 10;
    private static final Logger log = LoggerFactory.getLogger(DemoFilter.class);

    private static final ExecutorService executors = new ThreadPoolExecutor(NUMBER_OF_THREADS, NUMBER_OF_THREADS, 0L,
            TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        boolean useRemote = true;

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

        if (Switch.getInstance().isUrlOpen(req.getRequestURI()) && Switch.getInstance().isGlobalSwitchOpen()) {
            useRemote = false;
        }

        RequestWrapper wrapperRequest = new RequestWrapper(req);
        ResponseWrapper wrapperResponse = new ResponseWrapper(resp);
        StringBuilder reqLog = this.formatRequestInfo(wrapperRequest);

        Future<RemoteResponseResult> future = executors.submit(new RemoteCaller(wrapperRequest));
        chain.doFilter(wrapperRequest, wrapperResponse);
        RemoteResponseResult remoteRlt = null;
        try {
            remoteRlt = future.get();
        } catch (InterruptedException e) {
            throw new IOException(e.getCause());
        } catch (ExecutionException e) {
            throw new IOException(e.getCause());
        }

        if (useRemote) {
            resp.setStatus(remoteRlt.getStatusCode());
            resp.setContentType(remoteRlt.getContentType());
            resp.setContentLength(remoteRlt.getContentLength());
            if (null != remoteRlt.getRespHeaders()) {
                for (Pair p : remoteRlt.getRespHeaders()) {
                    resp.addHeader(p.getName(), p.getVal());
                }
            }
            resp.getOutputStream().write(remoteRlt.getBody());
        } else {
            resp.getOutputStream().write(wrapperResponse.getContent());
        }

        String localResp  = new String(wrapperResponse.getContent(), "utf-8");
        String remoteResp = new String(remoteRlt.getBody(), "utf-8");

        reqLog.append("\n\n*****Local Resonpse*****\n\n").append(localResp);
        reqLog.append("\n\n*****Remote Response*****\n\n").append(remoteResp);
        reqLog.append("\n\nResponse Check:").append(localResp.equals(remoteResp)).append("\n\n");

        log.info(reqLog.toString());
    }

    private StringBuilder formatRequestInfo(RequestWrapper req) {
        StringBuilder sb = new StringBuilder(DEFAULT_BUFFER_SIZE);
        sb.append("\n\n*****Request Log*****\n\n");
        sb.append("Request Path: ").append(req.getRequestURL());

        String method = req.getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            sb.append("\n\n*****Request Headers*****\n\n");
            Enumeration<String> enums = req.getHeaderNames();
            while (enums.hasMoreElements()) {
                String k = enums.nextElement();
                String v = req.getHeader(k);
                sb.append(k).append(":").append(v).append("\n");
            }

            sb.append("\n\n*****Request Parameters*****\n\n");
            enums = req.getParameterNames();
            while (enums.hasMoreElements()) {
                String k = enums.nextElement();
                String v = req.getParameter(k);
                sb.append(k).append("=").append(v).append("\n");
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            sb.append("\n\n*****Request Headers*****\n\n");
            Enumeration<String> enums = req.getHeaderNames();
            while (enums.hasMoreElements()) {
                String k = enums.nextElement();
                String v = req.getHeader(k);
                sb.append(k).append(":").append(v).append("\n");
            }

            sb.append("\n\n*****Request Body*****\n\n").append(new String(req.getBody())).append("\n");
        } else {
            throw new RuntimeException("Unsupport http method: " + method);
        }

        return sb;
    }

    static class RemoteCaller implements Callable<RemoteResponseResult>  {

        private RequestWrapper requestWrapper;

        RemoteCaller(RequestWrapper requestWrapper) {
            this.requestWrapper = requestWrapper;
        }

        @Override
        public RemoteResponseResult call() throws ClientProtocolException, IOException {
            RemoteResponseResult remoteRlt = remoteCall(requestWrapper);
            return remoteRlt;
        }

        private RemoteResponseResult remoteCall(final HttpServletRequest req)
                throws ClientProtocolException, IOException {
            String method = req.getMethod();
            String url = Switch.getInstance().addressOfUrl(req.getRequestURI()) + req.getRequestURI();
            if ("GET".equalsIgnoreCase(method)) {
                return HttpClientUtil.getInstance().get(url, req);
            } else if ("POST".equalsIgnoreCase(method)) {
                return HttpClientUtil.getInstance().post(url, req);
            } else {
                throw new RuntimeException("Unsupport http method: " + method);
            }
        }

    }

}
