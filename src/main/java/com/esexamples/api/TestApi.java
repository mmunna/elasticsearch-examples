package com.esexamples.api;

import com.beust.jcommander.internal.Lists;
import com.esexamples.api.operation.IndexGet;
import com.esexamples.api.operation.IndexCreateUpdate;
import com.esexamples.api.operation.IndexDelete;
import com.esexamples.api.operation.CheckIndexStatus;
import com.esexamples.api.operation.IndexMultiGet;
import com.esexamples.client.ElasticsearchClient;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.get.GetField;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import static org.testng.Assert.assertEquals;

public class TestApi {

    public static void main(String args[]) {
        final String indexName = generateUUIDIndexName();
        final String indexType = "indexStatus";
        final String id = "1";

        final Client esClient = ElasticsearchClient.createElasticSearchClient(); // create es client
        // ------------------------------------------
        // create index
        IndexCreateUpdate.createIndex(esClient, indexName);
        // create a document
        final Map<String, Object> document = Maps.newHashMap();
        document.put("user", "miso");
        document.put("software", "elasticsearch");
        document.put("postdate", new Date());
        document.put("comment", "This is a cool document");

        // ---------------------------------------------
        // add document to index
        IndexCreateUpdate.addDocumentToIndex(esClient, indexName, indexType, id, document);

        // ----------------------------------------------
        // check status of index
        CheckIndexStatus.checkIndexStatus(esClient, indexName); // check index status

        // ----------------------------------------------
        // delete the index
        IndexDelete.deleteIndex(esClient, indexName);


        // ----------------------------------------------
        // re-create the index
        // and then add document
        IndexCreateUpdate.createIndex(esClient, indexName);
        IndexCreateUpdate.addDocumentToIndex(esClient, indexName, indexType, id, document);


        // get index source
        GetResponse getResponse = IndexGet.getIndexSource(esClient, indexName, indexType, id);
        // Print the fields
        Map<String, Object> indexSourceMap = getResponse.getSourceAsMap();
        for (Map.Entry<String, Object> indexSource : indexSourceMap.entrySet()) {
            System.out.println("field key is " + indexSource.getKey());
            System.out.println("field value name is " + indexSource.getValue().toString());
        }

        // get index fields
        // by default get opertion only returns source if no filed is specified. If field(s) is/are specified then only those fields are returned.
        // is it a heavy operation comparing to getting the source?
        getResponse = IndexGet.getIndexFields(esClient, indexName, indexType, id, "user", "software");
        Map<String, GetField> indexFieldMap = getResponse.getFields();
        assertEquals(indexFieldMap.size(), 2);
        for(Map.Entry<String, GetField> indexField : indexFieldMap.entrySet()) {
            System.out.println("Field key is " + indexField.getKey());
            System.out.println("Field name is " + indexField.getValue().getName()); // field key and name are same
            for (Object obj : indexField.getValue().getValues()) {
                System.out.println("Field value is " + obj.toString());
            }
        }

        // multi get

        // create a document
        final Map<String, Object> document1 = Maps.newHashMap();
        document1.put("user", "sushi");
        document1.put("software", "elasticsearch");
        document1.put("postdate", new Date());
        document1.put("comment", "This is a cool document");

        IndexCreateUpdate.addDocumentToIndex(esClient, indexName, indexType, "2", document1);

        List<String> ids = Lists.newArrayList();
        ids.add("1");
        ids.add("2");
        MultiGetResponse multiGetResponses = IndexMultiGet.multiGetIndex(esClient, indexName, indexType, ids);

        for (MultiGetItemResponse multiGetItemResponse : multiGetResponses.getResponses()) {
            System.out.println("Index id is " +multiGetItemResponse.getId());
            System.out.println("Index name is " + multiGetItemResponse.getIndex());
            System.out.println("Index type is " + multiGetItemResponse.getType());
            Map<String, Object> getResponseMap = multiGetItemResponse.getResponse().getSource();
            for (Map.Entry<String, Object> getResponseItem : getResponseMap.entrySet()) {
                System.out.println("Field name is " + getResponseItem.getKey());
                System.out.println("Field value is " + getResponseItem.getValue().toString());
            }
        }


    }

    private static String generateUUIDIndexName() {
        return UUID.randomUUID().toString();
    }

}
