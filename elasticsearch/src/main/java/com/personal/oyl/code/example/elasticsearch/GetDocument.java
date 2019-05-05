package com.personal.oyl.code.example.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;

/**
 * Hello world!
 *
 */
public class GetDocument {
    public static void main(String[] args) throws IOException {
        
        GetRequest getRequest = new GetRequest("employee", "doc", "1");
        
        GetResponse getResponse = ConnectionHolder.getClient().get(getRequest, RequestOptions.DEFAULT);
        
        
        
        String index = getResponse.getIndex();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();        
            System.out.println(sourceAsString);
            System.out.println(index);
            System.out.println(id);
            System.out.println(version);
        } else {
            
        }
        
        
        ConnectionHolder.close();
    }
}
