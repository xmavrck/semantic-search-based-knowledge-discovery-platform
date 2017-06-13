package com.sarah.realtime.spark.data_processor;

import com.sarah.realtime.spark.data_processor.spark.SparkJobLauncher;

/*
 * The class App
 * 
 * @author Chandan
 * */
public class App {
	/*
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// checking if environment variable is set or not
			// as this will contain the config.properties
			if (System.getenv("SARAH_SPARK_STREAMING_CONFIG") != null) {
//				Logger.getLogger("org").setLevel(Level.OFF);
//				Logger.getLogger("akka").setLevel(Level.OFF);
				new SparkJobLauncher().launchSparkJob();
			} else {
				System.out.println(
						"Please add SARAH_SPARK_STREAMING_CONFIG environment variable to launch your application.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
