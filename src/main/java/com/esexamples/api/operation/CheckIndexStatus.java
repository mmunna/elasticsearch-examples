package com.esexamples.api.operation;

import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.indices.status.IndexStatus;
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.client.Client;

public class CheckIndexStatus {

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
}
