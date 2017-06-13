package com.sarah.web_crawler.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import retrofit.RetrofitError;

/**
 * The class HDFSUtils
 * 
 * @author chandan
 */
public class HDFSUtils implements Serializable {
	/**
	 * hdfsUri
	 */
	String hdfsUri;
	/**
	 * hadoopUsername
	 */
	String hadoopUsername;
	/**
	 * BUFFER_SIZE
	 */
	private static int BUFFER_SIZE = 1024;
	/**
	 * hdfsUri
	 */
	byte[] BUFFER = new byte[BUFFER_SIZE];
	/**
	 * kafkaUtils
	 */
	KafkaUtils kafkaUtils;

	/**
	 * HDFSUtils
	 * 
	 * @param uri
	 * @param hadoopUsername
	 * @param kafkaUtils
	 * @throws Exception
	 */
	public HDFSUtils(String uri, String hadoopUsername, KafkaUtils kafkaUtils) throws Exception {
		System.setProperty("HADOOP_USER_NAME", hadoopUsername);
		hdfsUri = uri;
		this.kafkaUtils = kafkaUtils;

	}

	/**
	 * readFile
	 * 
	 * @param hdfsUri
	 * @param hdfsFilePath
	 * @return String
	 * @throws IOException
	 * @throws URISyntaxException
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

	/**
	 * createRootDirectory
	 * 
	 * @param rootDirectoryPath
	 * @throws Exception
	 */
	public void createRootDirectory(String rootDirectoryPath) throws Exception {
		Path path = new Path(rootDirectoryPath);
		try (DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		}; final FSDataOutputStream streamWriter = dFS.create(path);) {

		} catch (Exception e) {

		}
	}

	/**
	 * writeToHDFS
	 * 
	 * @param url
	 * @param university
	 * @param hdfsoutputPath
	 * @param data
	 * @return boolean
	 * @throws Exception
	 */
	public boolean writeToHDFS(int pageCount, String url, String university, String hdfsoutputPath, String data,
			String urlHistoryId) throws Exception {
		writeEventToKafka(pageCount, url, university, hdfsoutputPath, urlHistoryId);
		Path path = new Path(hdfsoutputPath);
		try (DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		};
				final FSDataOutputStream streamWriter = dFS.create(path);
				PrintWriter writer = new PrintWriter(streamWriter);) {
			data = data.replaceAll("\"", " ");
			
			writer.println(data);
			writer.flush();
//			writeEventToKafka(pageCount, url, university, hdfsoutputPath, urlHistoryId);
			return true;
		}
	}

	public boolean deleteFromHDFS(String hdfsFilePath) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		FileSystem hdfs = FileSystem.get(URI.create(hdfsUri), conf);
		hdfs.delete(new Path(hdfsFilePath), true);
		return true;
	}

	/**
	 * writeToHDFSBinary
	 * 
	 * @param url
	 * @param university
	 * @param hdfsoutputPath
	 * @param inputStream
	 * @return boolean
	 * @throws Exception
	 */
	public boolean writeToHDFSBinary(int pageCount, String url, String university, String hdfsoutputPath,
			InputStream inputStream, String urlHistoryId) throws Exception {
		writeEventToKafka(pageCount, url, university, hdfsoutputPath, urlHistoryId);
		Path path = new Path(hdfsoutputPath);
		try (DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		};
				final FSDataOutputStream streamWriter = dFS.create(path);
				PrintWriter writer = new PrintWriter(streamWriter);) {
			String content = extractPDF(inputStream);
			content = content.replaceAll("\"", " ");
			
			writer.println(content);
			writer.flush();
//			writeEventToKafka(pageCount, url, university, hdfsoutputPath, urlHistoryId);
			return true;
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	// TODO: for pdf deploy it on spring boot tomcat server and give that url
	/**
	 * writeToHDFSBinary
	 * 
	 * @param url
	 * @param university
	 * @param configUtils
	 * @param hdfsoutputPath
	 * @param inputStream
	 * @return boolean
	 * @throws RetrofitError
	 */
	public boolean writeToHDFSBinary(String url, String university, ConfigUtils configUtils, String hdfsoutputPath,
			InputStream inputStream) throws Exception {
		try (DistributedFileSystem dFS = new DistributedFileSystem() {
			{
				initialize(new URI(hdfsUri), new Configuration());
			}
		};) {
			try {
				String filename = new Date().getTime() + "";
				String path = configUtils.getZipFilesTempath() + File.separator + filename;

				File pDir = new File(path).getParentFile();
				if (!pDir.isDirectory()) {
					pDir.mkdirs();
				}

				try (FileOutputStream fos = new FileOutputStream(path);) {
					int bytesCount = 0;
					byte[] buffer = new byte[4096];

					while ((bytesCount = inputStream.read(buffer)) != -1) {
						fos.write(buffer, 0, bytesCount);
					}
					fos.flush();
					ZipFile zipFile = new ZipFile(path);

					Enumeration<? extends ZipEntry> entries = zipFile.entries();
					hdfsUri = "hdfs://master:9000";
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						InputStream stream = zipFile.getInputStream(entry);
						String content = extractPDF(stream);
						String hdfsFilePath = "";
						try {
							hdfsFilePath = new StringBuilder(hdfsoutputPath).append("/").append(entry.getName())
									.toString();
							try (FSDataOutputStream streamWriter = dFS.create(new Path(hdfsFilePath));
									BufferedOutputStream outputStream = new BufferedOutputStream(
											streamWriter.getWrappedStream());) {
								outputStream.write(content.getBytes());
							}

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (stream != null)
								stream.close();
						}
						// writeEventToKafka(entry.getName(), university,
						// hdfsFilePath ,
						// entry.getName());
					}
					new File(path).delete();
				} catch (Exception e) {
					new File(path).delete();
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * extractPDF
	 * 
	 * @param inputStream
	 * @return String
	 * @throws Exception
	 */
	public String extractPDF(InputStream inputStream) throws Exception {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = "";
		try {
			java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);
			PDFParser parser = new PDFParser(inputStream);
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(pdDoc.getNumberOfPages());
			parsedText = pdfStripper.getText(pdDoc);
		} finally {
			if (pdDoc != null) {
				pdDoc.close();
			}
		}
		return parsedText;
	}

	/**
	 * writeEventToKafka
	 * 
	 * @param url
	 * @param university
	 * @param hdfsFilePath
	 * @throws Exception
	 */
	private void writeEventToKafka(int pageCount, String url, String university, String hdfsFilePath,
			String urlHistoryId) throws Exception {
		kafkaUtils.sendMessage(pageCount, url, university, hdfsUri, hdfsFilePath, urlHistoryId);
	}
}