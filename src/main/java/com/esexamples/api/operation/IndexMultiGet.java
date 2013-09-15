package com.esexamples.api.operation;

import com.google.common.base.Joiner;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.internal.Join;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexMultiGet {

    public static MultiGetResponse multiGetIndex(Client client, String indexName, String indexType, List<String> ids) {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        for(String id : ids) {
            multiGetRequest = multiGetRequest.add(new MultiGetRequest.Item(indexName, indexType, id));
        }
        return client.multiGet(multiGetRequest).actionGet();
    }

    public static MultiGetResponse multiGetResponseWithFields(Client client, String indexName, String indexType, Map<String, String[]> idAndField) {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        for(Map.Entry<String, String[]> idAndFieldEntry : idAndField.entrySet()) {
            multiGetRequest = multiGetRequest.add(new MultiGetRequest.Item(indexName, indexType, idAndFieldEntry.getKey()).fields(idAndFieldEntry.getValue()));
        }
        return client.multiGet(multiGetRequest).actionGet();
    }
}
