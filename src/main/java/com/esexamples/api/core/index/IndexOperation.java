package com.esexamples.api.core.index;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class IndexOperation {

    public static void createIndex(Client client, String indexName) {
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
        createIndexRequestBuilder.execute().actionGet();
    }

    public static void createIndexAndDocument(Client client, String indexName, String indexType) {
        try {
            client.prepareIndex(indexName, indexType)
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("user", "kimchy")
                            .field("postDate", new Date())
                            .field("message", "trying out ES")
                            .endObject())
                    .execute()
                    .actionGet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Add documents to index
    public static void addDocumentToIndex(Client client, String indexName, String indexType, Map<String, Object> document) {
        client.prepareIndex(indexName, indexType)
                .setSource(document)
                .execute()
                .actionGet();
    }
}
