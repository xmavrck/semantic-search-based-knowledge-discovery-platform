package com.sarah.realtime.spark.data_processor.spark;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.sarah.realtime.spark.data_processor.services.MessageProcessor;
import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;

import kafka.serializer.StringDecoder;
import scala.Tuple2;

/*
 * The class SparkJobLauncher
 * 
 * @author Chandan
 * */
public class SparkJobLauncher implements Serializable {

	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils;

	/**
	 * jssc
	 */
	transient JavaStreamingContext jssc;

	/**
	 * messageProcessor
	 */
	MessageProcessor messageProcessor;

	/**
	 * SparkJobLauncher
	 * 
	 * @throws Exception
	 */
	public SparkJobLauncher() throws Exception {
		propertyUtils = new PropertyUtils();
		messageProcessor = new MessageProcessor();
	}

	/**
	 * launchSparkJob
	 * 
	 * @throws Exception
	 */
	public void launchSparkJob() throws Exception {
		SparkConf sparkConf = new SparkConf().setAppName(propertyUtils.getSparkJobName());
		jssc = new JavaStreamingContext(sparkConf,
				Durations.seconds(Integer.parseInt(propertyUtils.getSparkStreamingInterval())));
		JavaPairInputDStream<String, String> kafkaDataStream = KafkaUtils.createDirectStream(jssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, new HashMap<String, String>() {
					{
						put("metadata.broker.list", propertyUtils.getMetadataBrokerList());
					}
				}, new HashSet<String>() {
					{
						add(propertyUtils.getKafkaTopic());
					}
				});

		JavaDStream<String> lines = kafkaDataStream.map(new Function<Tuple2<String, String>, String>() {

			public String call(Tuple2<String, String> tuple2) {
				try {
					messageProcessor.processMessage(tuple2._2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return tuple2._2();
			}
		});
		lines.print();
		jssc.start();
		jssc.awaitTermination();
		jssc.stop();
	}
}
