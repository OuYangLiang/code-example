package com.personal.test.demo.filter.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayPrintWriter output;
    private boolean usingWriter;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        usingWriter = false;
        output = new ByteArrayPrintWriter();
    }

    /*public byte[] getByteArray() {
        return output.toByteArray();
    }*/

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (usingWriter) {
            super.getOutputStream();
        }
        usingWriter = true;
        return output.getStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (usingWriter) {
            super.getWriter();
        }
        usingWriter = true;
        return output.getWriter();
    }

    public String toString() {
        return output.toString();
    }

    static class ByteArrayServletStream extends ServletOutputStream {
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

    static class ByteArrayPrintWriter {
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintWriter pw = new PrintWriter(baos);
        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter() {
            return pw;
        }

        public ServletOutputStream getStream() {
            return sos;
        }

        /*byte[] toByteArray() {
            return baos.toByteArray();
        }*/
    }
}
