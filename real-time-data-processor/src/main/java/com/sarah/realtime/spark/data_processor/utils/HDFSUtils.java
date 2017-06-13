package com.sarah.realtime.spark.data_processor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

/*
 * The class HDFSUtils
 * 
 * @author Chandan
 * 
 * */
public class HDFSUtils implements Serializable {

	/**
	 * hadoopUsername
	 */
	String hadoopUsername;
	/**
	 * BUFFER_SIZE
	 */
	private static int BUFFER_SIZE = 1024;
	/**
	 * BUFFER
	 */
	byte[] BUFFER = new byte[BUFFER_SIZE];

	/**
	 * HDFSUtils
	 * 
	 * @param hadoopUsername
	 * @throws Exception
	 */

	public HDFSUtils(String hadoopUsername) throws Exception {
		System.setProperty("HADOOP_USER_NAME", hadoopUsername);
	}

	/**
	 * readBinaryFile
	 * 
	 * @param hdfsUri
	 * @param hdfsFilePath
	 * @return String
	 * @throws IOException,
	 *             URISyntaxException
	 */
	public String readBinaryFile(final String hdfsUri, String hdfsFilePath) throws IOException, URISyntaxException {
		final Path path = new Path(hdfsFilePath);
		StringBuilder hdfsFileData = new StringBuilder();
		try (final DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		};
				final FSDataInputStream streamReader = dFS.open(path);
				final BufferedReader is = new BufferedReader(new InputStreamReader(streamReader.getWrappedStream()))) {
			String temp = "";

			while ((temp = is.readLine()) != null) {
				hdfsFileData.append(temp).append("\n");
			}

		}
		return hdfsFileData.toString();
	}

	/**
	 * readFile
	 * 
	 * @param hdfsUri
	 * @param hdfsFilePath
	 * @return String
	 * @throws IOException,
	 *             URISyntaxException
	 */
	public String readFile(final String hdfsUri, String hdfsFilePath) throws IOException, URISyntaxException {
		final Path path = new Path(hdfsFilePath);
		StringBuilder result = new StringBuilder();
		try (final DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		}; final FSDataInputStream streamReader = dFS.open(path); final Scanner scanner = new Scanner(streamReader);) {
			while (scanner.hasNextLine()) {
				result.append(scanner.nextLine()).append("\n");
			}
		}
		return result.toString();
	}
}
