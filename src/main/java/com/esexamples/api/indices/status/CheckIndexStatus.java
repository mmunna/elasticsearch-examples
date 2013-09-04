package com.esexamples.api.indices.status;

import com.esexamples.api.core.index.IndexCreateUpdate;
import com.esexamples.api.core.index.IndexDelete;
import com.esexamples.core.ElasticsearchClient;
import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.indices.status.IndexStatus;
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Maps;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class CheckIndexStatus {

    public static void main(String args[]) {
        final String indexName = generateUUIDIndexName();
        final Client esClient = ElasticsearchClient.createElasticSearchClient(); // create es client
        // create index
        IndexCreateUpdate.createIndex(esClient, indexName);
        // create a document
        final Map<String, Object> document = Maps.newHashMap();
        document.put("user", "miso");
        document.put("software", "elasticsearch");
        document.put("postdate", new Date());
        document.put("comment", "This is a cool document");
        // add document to index
        System.out.println(indexName);
        IndexCreateUpdate.addDocumentToIndex(esClient, indexName, "indexStatus", document);
        // check status of index
        System.out.println(indexName);
        checkIndexStatus(esClient, indexName); // check index status

        System.out.println(indexName);
        IndexDelete.deleteIndex(esClient, indexName);

        System.out.println(indexName);
        checkIndexStatus(esClient, indexName); // check index status

    }

    public static void checkIndexStatus(Client client, String indexName) {
        try {
            if (client.admin().indices().prepareExists(indexName).execute().actionGet().isExists()) {
                IndicesStatusResponse status = client.admin().indices().prepareStatus(indexName).execute().actionGet();
                System.out.println("# of failed shards : " + status.getFailedShards());
                System.out.println("# of successful shards : " + status.getSuccessfulShards());

                for (IndexStatus indexStatus : status.getIndices().values()) {
                    System.out.println("# of docs in index : " + indexStatus.getDocs().getNumDocs());
                }

                for (ShardOperationFailedException sofe : status.getShardFailures()) {
                    System.out.println("indexName : " + sofe.index());
                    System.out.println("shardId : " + sofe.shardId());
                    System.out.println("failed reason : " + sofe.reason());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateUUIDIndexName() {
        return UUID.randomUUID().toString();
    }

}
