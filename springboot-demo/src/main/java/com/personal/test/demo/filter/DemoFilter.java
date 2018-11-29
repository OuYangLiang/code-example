package com.personal.test.demo.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DemoFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        if (!"/hello".equals(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        
        HttpServletResponse resp = (HttpServletResponse) response;
        
        
        CharResponseWrapper wrapperResponse = new CharResponseWrapper(resp);
        
        chain.doFilter(request, wrapperResponse);
        
        LOG.error(new String(wrapperResponse.getByteArray()), "utf-8");
        
        
        resp.addHeader("test-header", "123");
        resp.getOutputStream().write("HHHHH".getBytes());
    }

    

    private static class ByteArrayServletStream extends ServletOutputStream {
        ByteArrayOutputStream baos;

        ByteArrayServletStream(ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        public void write(int param) throws IOException {
            baos.write(param);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }

    private static class ByteArrayPrintWriter {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        private PrintWriter pw = new PrintWriter(baos);

        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter() {
            return pw;
        }

        public ServletOutputStream getStream() {
            return sos;
        }

        byte[] toByteArray() {
            return baos.toByteArray();
        }
    }

    public class CharResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayPrintWriter output;
        private boolean usingWriter;

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
            usingWriter = false;
            output = new ByteArrayPrintWriter();
        }

        public byte[] getByteArray() {
            return output.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            // will error out, if in use
            if (usingWriter) {
                super.getOutputStream();
            }
            usingWriter = true;
            return output.getStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            // will error out, if in use
            if (usingWriter) {
                super.getWriter();
            }
            usingWriter = true;
            return output.getWriter();
        }

        public String toString() {
            return output.toString();
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
