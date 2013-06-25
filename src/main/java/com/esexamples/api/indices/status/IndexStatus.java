package com.esexamples.api.indices.status;

import com.esexamples.api.core.index.IndexOperation;
import com.esexamples.core.ElasticsearchClient;
import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.action.admin.indices.status.ShardStatus;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Maps;

import java.util.Date;
import java.util.Map;

public class IndexStatus {
    private static final String INDEX_NAME = "esexample";

    public static void main(String args[]) {
        Client esClient = ElasticsearchClient.createElasticSearchClient(); // create es client
        IndexOperation.createIndex(esClient, INDEX_NAME); //create index
        //create a document to index
        Map<String, Object> document = Maps.newHashMap();
        document.put("user", "kimchy");
        document.put("software", "elasticsearch");
        document.put("postdate", new Date());
        document.put("comment", "This is a cool document");

        IndexOperation.addDocumentToIndex(esClient, "esexample", "indexStatus", document);
        checkIndexStatus(esClient, INDEX_NAME); // check index status
    }

    public static void checkIndexStatus(Client client, String indexName) {
        IndicesStatusResponse status = client.admin().indices().prepareStatus(indexName).execute().actionGet();
        System.out.println("# of failed shards : " + status.failedShards());
        System.out.println("# of successful shards : " + status.successfulShards());

        for (ShardStatus shardStatus : status.shards()) {
            System.out.println("shard status : " + shardStatus.state().name());
            System.out.println("# of docs in shard : " + shardStatus.docs().numDocs());
            System.out.println("# of deleted docs in shard : " + shardStatus.docs().deletedDocs());
        }

        for (ShardOperationFailedException sofe: status.getShardFailures()) {
            System.out.println("indexName : " +sofe.index());
            System.out.println("shardId : " + sofe.shardId());
            System.out.println("failed reason : " + sofe.reason());
        }
    }
}
