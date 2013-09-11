package com.esexamples.api.operation;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.util.Map;

public class IndexCreateUpdate {

    public static void createIndex(Client client, String indexName) {
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
        createIndexRequestBuilder.execute().actionGet();
    }

    // Add documents to index
    public static void addDocumentToIndex(Client client, String indexName, String indexType, String id, Map<String, Object> document) {
        client.prepareIndex(indexName, indexType, id)
                .setSource(document)
                .execute()
                .actionGet();
    }
}
