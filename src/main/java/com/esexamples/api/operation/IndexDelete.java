package com.esexamples.api.operation;

import org.elasticsearch.client.Client;

public class IndexDelete {

    public static void deleteIndex(Client client, String indexName) {
        client.admin().indices().prepareDelete(indexName).execute().actionGet();
    }
}
