package com.esexamples.core;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticsearchClient {

    public static Client createElasticSearchClient() {
        final Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "texan").build();

        // assuming you are running a three node cluster
        return new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300))
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9301))
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9302));
    }
}
