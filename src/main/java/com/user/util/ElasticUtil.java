package com.user.util;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class ElasticUtil {

	private static JestClient client;

	private ElasticUtil() {
	}

	public static JestClient getClient() {
		System.out.println("Elastic API created");
		if (client == null) {
			// Creating Elastic Client
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder("http://52.66.114.79:9200").multiThreaded(true).build());
			client = factory.getObject();
		}
		return client;
	}
}