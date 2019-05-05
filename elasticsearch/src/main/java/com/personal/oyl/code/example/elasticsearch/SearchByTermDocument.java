package com.personal.oyl.code.example.elasticsearch;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class SearchByTermDocument {
    public static void main(String[] args) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("employee");
        
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("employeeName", "Name 888"));
        sourceBuilder.fetchSource(new String[] {"employeeId"}, null);
        
        sourceBuilder.sort(new FieldSortBuilder("employeeId").order(SortOrder.DESC));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        
        searchRequest.source(sourceBuilder);
        
        SearchResponse searchResponse = ConnectionHolder.getClient().search(searchRequest, RequestOptions.DEFAULT);
        
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();
        
        System.out.println(status);
        System.out.println(took);
        System.out.println(terminatedEarly);
        System.out.println(timedOut);
        
        
        
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsString());
        }
        
        ConnectionHolder.close();
    }
}
