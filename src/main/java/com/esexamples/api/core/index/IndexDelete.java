package com.esexamples.api.core.index;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.client.Client;

public class IndexDelete {

    public static void deleteIndex(Client client, String indexName) {
        client.admin().indices().prepareDelete(indexName).execute().actionGet();
    }
}
