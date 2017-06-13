package com.sarah.realtime.spark.data_processor.services;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.sarah.realtime.spark.data_processor.dto.ClassificationMlibRequest;
import com.sarah.realtime.spark.data_processor.dto.ClassificationMlibResponse;
import com.sarah.realtime.spark.data_processor.dto.KafkaMessage;
import com.sarah.realtime.spark.data_processor.dto.NamedEntityExtractionRequest;
import com.sarah.realtime.spark.data_processor.dto.RDFData;
import com.sarah.realtime.spark.data_processor.dto.UrlHistory;
import com.sarah.realtime.spark.data_processor.fuseki.FusekiRDFWriter;
import com.sarah.realtime.spark.data_processor.rdf.RDFConverter;
import com.sarah.realtime.spark.data_processor.ruby.RubyScriptExceutor;
import com.sarah.realtime.spark.data_processor.utils.HDFSUtils;
import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;
import com.sarah.realtime.spark.data_processor.utils.RetrofitBuilder;

/*
 * The class MessageProcessor
 * 
 * @author Chandan
 * */
public class MessageProcessor implements Serializable {

	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils;
	/**
	 * hdfsUtils
	 */
	HDFSUtils hdfsUtils;

	/**
	 * rubyScriptExceutor
	 */
	RubyScriptExceutor rubyScriptExceutor;

	/**
	 * rdfConverter
	 */
	RDFConverter rdfConverter;
	/**
	 * fusekiRDFWriter
	 */
	FusekiRDFWriter fusekiRDFWriter;
	/**
	 * NO_OF_WORDS_NLP
	 */
	private final int NO_OF_WORDS_NLP = 400;

	/**
	 * MessageProcessor
	 * 
	 * @throws Exception
	 */
	public MessageProcessor() throws Exception {
		propertyUtils = new PropertyUtils();
		fusekiRDFWriter = new FusekiRDFWriter();
		hdfsUtils = new HDFSUtils(propertyUtils.getHdfsUsername());
		rubyScriptExceutor = new RubyScriptExceutor();
		rdfConverter = new RDFConverter();
	}

	/**
	 * processMessage
	 * 
	 * @param kafkaMessage
	 * @throws Exception
	 */
	public void processMessage(String kafkaMessage) throws Exception {
		System.out.println("KafkaMessage " + kafkaMessage);
		if (kafkaMessage == null || kafkaMessage.equals(""))
			return;
		try {
			KafkaMessage message = new Gson().fromJson(kafkaMessage, KafkaMessage.class);
			new ConvertTextToHDFS(message).call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class ConvertTextToHDFS implements Callable<Void> {
		KafkaMessage message;

		public ConvertTextToHDFS(KafkaMessage kafkaMessage) {
			this.message = kafkaMessage;
		}

		@Override
		public Void call() throws Exception {
			String hdfsFileData = hdfsUtils.readBinaryFile(message.getHdfsUri(), message.getHdfsFilePath());
			RetrofitBuilder builder = new RetrofitBuilder();
			System.out.println("HDFS Data  "+hdfsFileData);
			if (!hdfsFileData.trim().equals("")) {
				ClassificationMlibResponse classificationMlibResponse = builder.getClassificationMLibUtil()
						.getClassificationResults(new ClassificationMlibRequest(hdfsFileData));
				System.out.println("Classification Response "+new Gson().toJson(classificationMlibResponse));
				System.out.println("## message processed  " + message.getPageCount());
				if (classificationMlibResponse != null && classificationMlibResponse.isProperDocument()
						&& classificationMlibResponse.getTopResearchAreas() != null) {

					if (classificationMlibResponse.getEmail() == null) {
						classificationMlibResponse.setEmail("NA");
					}
					RDFData rdfData = new RDFData(message.getUniversity(), message.getUrl(),
							classificationMlibResponse.getEmail(), classificationMlibResponse.getTopResearchAreas());

					hdfsFileData = hdfsFileData.trim().replaceAll(" +", " ");
					hdfsFileData = hdfsFileData.replaceAll("\\.", ". ");
					hdfsFileData = hdfsFileData.replaceAll(",", ", ");
					String nlpData = "";
					if (hdfsFileData.length() > NO_OF_WORDS_NLP) {
						nlpData = hdfsFileData.substring(0, NO_OF_WORDS_NLP);
					}
					
					rdfData.setName(builder.getNlpUtil()
							.getNames(new NamedEntityExtractionRequest(classificationMlibResponse.getEmail(), nlpData))
							.getName());
					System.out.println("NLP Results   "+rdfData.getName());

					Object[] res = rubyScriptExceutor.executeScript(hdfsFileData);
					System.out.println("Ruby FInal Result  "+new Gson().toJson(res));
					Map<String, List<String>> attributes = null;
					try {
						attributes = (Map<String, List<String>>) res[0];
						if (rdfData.getName() == null || rdfData.getName().equals("")) {
							rdfData.setName(rubyScriptExceutor.getNames((String) res[1]));
						}
					} catch (Exception e) {
						if (attributes == null)
							attributes = new HashMap<>();
						e.printStackTrace();
					}
					String rdf = rdfConverter.convertDataToRDF(message.getUrlScrapTaskId(), rdfData, attributes);
					System.out.println("RDF Data  "+rdf);
					if (rdf != null && !rdf.trim().equals("")) {
						try {
							builder.getMongoUtil().update(message.getUrlScrapTaskId(), new UrlHistory());
						} catch (Exception e) {
							e.printStackTrace();
						}
						fusekiRDFWriter.writeToFuseki(rdf);
					}
				}
			}
			return null;
		}
	}
}
