package com.personal.oyl.code.example.nettydemo.httpxml;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author OuYang Liang
 * @since 2019-06-23
 */
public class HttpXmlResponse {
    private FullHttpResponse httpResponse;
    private Order result;

    public HttpXmlResponse(FullHttpResponse httpResponse, Order result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }

    public FullHttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public Order getResult() {
        return result;
    }

    public void setResult(Order result) {
        this.result = result;
    }
}
