package com.esexamples.api.core.index;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class IndexCreateUpdate {

    public static void createIndex(Client client, String indexName) {
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
        createIndexRequestBuilder.execute().actionGet();
    }

    // Add documents to index
    public static void addDocumentToIndex(Client client, String indexName, String indexType, Map<String, Object> document) {
        client.prepareIndex(indexName, indexType)
                .setSource(document)
                .execute()
                .actionGet();
    }
}
