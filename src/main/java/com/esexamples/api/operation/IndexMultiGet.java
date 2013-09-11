package com.esexamples.api.operation;

import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;

import java.util.List;

public class IndexMultiGet {

    public static MultiGetResponse multiGetIndex(Client client, String indexName, String indexType, List<String> ids) {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        for(String id : ids) {
            multiGetRequest = multiGetRequest.add(new MultiGetRequest.Item(indexName, indexType, id));
        }
        return client.multiGet(multiGetRequest).actionGet();
    }
}
