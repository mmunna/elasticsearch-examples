package com.esexamples.api.operation;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

public class IndexGet {

    public static GetResponse getIndexSource(Client client, String index, String indexType, String id) {
        return client.get(new GetRequest(index).id(id).type(indexType)).actionGet();
    }

    public static GetResponse getIndexFields(Client client, String index, String indexType, String id, String ...fields) {
        return client.get(new GetRequest(index).id(id).type(indexType).fields(fields)).actionGet();
    }
}
