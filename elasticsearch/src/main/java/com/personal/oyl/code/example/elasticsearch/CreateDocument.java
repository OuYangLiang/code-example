package com.personal.oyl.code.example.elasticsearch;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Hello world!
 *
 */
public class CreateDocument {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        
        Map<String, Object> jsonMap = new HashMap<>();
        for (int i = 1; i <= 1000; i++) {
            
            jsonMap.put("employeeName", "Name " + i);
            jsonMap.put("age", 35 + i);
            jsonMap.put("employeeId", i);
            jsonMap.put("join", new Date().getTime());
            
            IndexRequest indexRequest = new IndexRequest("employee", "doc", Integer.toString(i)).source(jsonMap);
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            
            if (indexResponse.getResult() == Result.CREATED) {
                System.out.println("created");
            } else if (indexResponse.getResult() == Result.UPDATED) {
                System.out.println("updated");
            }
            
        }
        
        
        client.close();
    }
}
