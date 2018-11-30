package com.personal.test.demo.filter.util;

import java.util.LinkedList;
import java.util.List;

public class RemoteResponseResult {
    private int statusCode;
    private String contentType;
    private int contentLength;
    private List<Pair> respHeaders;
    private byte[] body;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(final int contentLength) {
        this.contentLength = contentLength;
    }

    public List<Pair> getRespHeaders() {
        return respHeaders;
    }

    public void setRespHeaders(final List<Pair> respHeaders) {
        this.respHeaders = respHeaders;
    }

    public void addRespHeader(final String name, final String val) {
        if (null == respHeaders) {
            respHeaders = new LinkedList<>();
        }
        respHeaders.add(new Pair(name, val));
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(final byte[] body) {
        this.body = body;
    }

    public static class Pair {
        private String name;
        private String val;

        Pair(final String name, final String val) {
            super();
            this.name = name;
            this.val = val;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getVal() {
            return val;
        }

        public void setVal(final String val) {
            this.val = val;
        }

    }
}
