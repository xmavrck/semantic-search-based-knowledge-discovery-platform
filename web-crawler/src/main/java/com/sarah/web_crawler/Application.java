package com.sarah.web_crawler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sarah.web_crawler.filter.CorsFilter;
import com.sarah.web_crawler.utils.HDFSUtils;
import com.sarah.web_crawler.utils.KafkaUtils;

/**
 * The class Application
 * @author chandan
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@ServletComponentScan(basePackageClasses=CorsFilter.class)
public class Application {
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (System.getenv("SARAH_WEB_CRAWLER_CONFIG") == null) {
			System.err.println("Please add SARAH_WEB_CRAWLER_CONFIG environment variable");
		} else {
			SpringApplication.run(Application.class, args);
		}
	}

	/**
	 * threadPoolTaskExecutor
	 * 
	 * @return Executor
	 */
	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	/**
	 * executorService
	 * 
	 * @param noOfConcurrentThreads
	 * @return ExecutorService
	 */
	@Bean
	public ExecutorService executorService(@Value("${concurrent.threads}") Integer noOfConcurrentThreads) {
		return Executors.newFixedThreadPool(noOfConcurrentThreads);
	}

	/**
	 * hdfsUtils
	 * 
	 * @param hadoopUsername
	 * @param hdfsUri
	 * @param kafkaTopic
	 * @param kafkaBrokerAddress
	 * @param lingerInms
	 * @param producerConfigPath
	 * @return HDFSUtils
	 * @throws Exception
	 */
	@Bean
	public HDFSUtils hdfsUtils(@Value("${hadoop.username}") String hadoopUsername, @Value("${hdfs.uri}") String hdfsUri,
			@Value("${kafka.topic}") String kafkaTopic, @Value("${kafka.broker.address}") String kafkaBrokerAddress,
			@Value("${linger.ms}") String lingerInms, @Value("${kafka.producer.config}") String producerConfigPath)
			throws Exception {
		return new HDFSUtils(hdfsUri, hadoopUsername,
				kafkaUtils(kafkaTopic, kafkaBrokerAddress, lingerInms, producerConfigPath));
	}

	/**
	 * kafkaUtils
	 * 
	 * @param kafkaTopic
	 * @param kafkaBrokerAddress
	 * @param lingerInms
	 * @param producerConfigPath
	 * @return KafkaUtils
	 * @throws Exception
	 */
	@Bean
	public KafkaUtils kafkaUtils(String kafkaTopic, String kafkaBrokerAddress, String lingerInms,
			String producerConfigPath) throws Exception {
		return new KafkaUtils(kafkaTopic, kafkaBrokerAddress, lingerInms, new File(producerConfigPath));
	}

	/**
	 * propertyPlaceholderConfigurer
	 * 
	 * @param webScrappyDataDto
	 * @return PropertyPlaceholderConfigurer
	 * @throws IOException
	 */
	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(new FileSystemResource(System.getenv("SARAH_WEB_CRAWLER_CONFIG")));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}

}