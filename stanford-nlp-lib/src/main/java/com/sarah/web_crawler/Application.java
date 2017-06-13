package com.sarah.web_crawler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sarah.web_crawler.filter.CorsFilter;

/**
 * The class Application
 * 
 * @author chandan
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@ServletComponentScan(basePackageClasses = CorsFilter.class)
public class Application {

	private final int THREAD_POOL_SIZE = 4;

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

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
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
}