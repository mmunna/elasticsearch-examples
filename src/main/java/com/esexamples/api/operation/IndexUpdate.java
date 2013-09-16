package com.esexamples.api.operation;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.util.Map;

public class IndexUpdate {

    public static void updateDocument(Client client,
                                      String indexName,
                                      String indexType,
                                      String id,
                                      String script,
                                      Map<String, Object> scriptParams) {
        client.prepareUpdate()
                .setIndex(indexName)
                .setType(indexType)
                .setId(id)
                .setScript(script)
                .setScriptParams(scriptParams)
                .execute().actionGet();
    }

    public static void updateDocumentAddField(Client client,
                                       String indexName,
                                       String indexType,
                                       String id,
                                       String script) {
        client.prepareUpdate(indexName, indexType, id)
                .setScript(script)
                .execute()
                .actionGet();
    }

    public static void updateDocumentRemoveField(Client client,
                                                 String indexName,
                                                 String indexType,
                                                 String id,
                                                 String script) {
        client.prepareUpdate(indexName, indexType, id)
                .setScript(script)
                .execute()
                .actionGet();
    }

    public static void updateDocumentPartialDocument(Client client,
                                                     String indexName,
                                                     String indexType,
                                                     String id,
                                                     Map<String, Object> doc) {
        client.prepareUpdate(indexName, indexType, id)
                .setDoc(doc)
                .execute()
                .actionGet();
    }

    public static void updateDocumentUpsert(Client client,
                                            String indexName,
                                            String indexType,
                                            String id,
                                            String script,
                                            Map<String, Object> scriptParams,
                                            Map<String, Object> upsert) {
        client.prepareUpdate(indexName, indexType, id)
                .setScript(script)
                .setScriptParams(scriptParams)
                .setUpsert(upsert)
                .execute()
                .actionGet();
    }

    public static void updateDocAsUpsert(Client client,
                                                 String indexName,
                                                 String indexType,
                                                 String id,
                                                 Map<String, Object> doc) {
        client.prepareUpdate(indexName, indexType, id)
                .setDoc(doc)  // ES will index the fields of the document but it wouldn't store the doc as source
                .setDocAsUpsert(true)
                .execute()
                .actionGet();
    }

}
