package com.personal.oyl.code.example.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public final class ConnectionHolder {
    private static volatile RestHighLevelClient instance;
    
    private ConnectionHolder() {}
    
    public static RestHighLevelClient getClient() {
        if (null == instance) {
            synchronized(ConnectionHolder.class) {
                if (null == instance) {
                    instance = new RestHighLevelClient(
                            RestClient.builder(
                                    new HttpHost("localhost", 9200, "http")));
                }
            }
        }
        
        return instance;
    }
    
    public static void close() {
        if (null != instance) {
            synchronized(ConnectionHolder.class) {
                if (null != instance) {
                    try {
                        instance.close();
                        instance = null;
                    } catch (IOException e) {
                        
                    }
                }
            }
        }
    }
}
