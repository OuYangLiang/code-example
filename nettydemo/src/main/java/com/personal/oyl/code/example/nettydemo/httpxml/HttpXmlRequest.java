package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Order body;

    public HttpXmlRequest(FullHttpRequest request, Order body) {
        this.request = request;
        this.body = body;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Order getBody() {
        return body;
    }

    public void setBody(Order body) {
        this.body = body;
    }
}
