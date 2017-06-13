package com.sarah.web_crawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;

/**
 * The class KafkaUtils
 * 
 * @author chandan
 */
public class KafkaUtils implements Serializable {

	/**
	 * hdfsUri
	 */
	String hdfsUri;
	/**
	 * topicName
	 */
	String topicName;
	/**
	 * gson
	 */
	Gson gson = new Gson();

	/**
	 * producer
	 */
	KafkaProducer<String, String> producer;

	/**
	 * KafkaUtils
	 * 
	 * @param topicName
	 * @param brokerAddress
	 * @param lingerMs
	 * @param producerConfigFile
	 * @throws Exception
	 */
	public KafkaUtils(String topicName, String brokerAddress, String lingerMs, File producerConfigFile)
			throws Exception {
		this.topicName = topicName;
		try (InputStream props = new FileInputStream(producerConfigFile)) {
			Properties properties = new Properties();
			properties.load(props);
			producer = new KafkaProducer<>(properties);
		}
	}

	/**
	 * sendMessage
	 * 
	 * @param url
	 * @param university
	 * @param hdfsUri
	 * @param hdfsFilePath
	 * @throws Exception
	 */
	public void sendMessage(int pageCount,String url, String university, String hdfsUri, String hdfsFilePath, String urlScrapTaskId)
			throws Exception {
		Map<String, Object> message = new HashMap<>();
		message.put("hdfsUri", hdfsUri);
		message.put("url", url);
		message.put("urlScrapTaskId", urlScrapTaskId);
		message.put("university", university);
		message.put("hdfsFilePath", hdfsFilePath);
		message.put("pageCount", pageCount);
		message.put("timestamp", new Date().getTime());
		System.out.println("Kafka Message "+gson.toJson(message));
		producer.send(new ProducerRecord<String, String>(topicName, gson.toJson(message)));
	}
}
