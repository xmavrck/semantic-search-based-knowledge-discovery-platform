package com.sarah.realtime.spark.data_processor.fuseki;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Date;

import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;
import com.sarah.realtime.spark.data_processor.utils.RetrofitBuilder;
import com.sarah.realtime.spark.data_processor.utils.SparQLUtils;

import retrofit.mime.TypedFile;

/*
 * The class FusekiRDFWriter
 * 
 * @author Chandan
 * */
public class FusekiRDFWriter implements Serializable {

	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils;
	/**
	 * sparQLUtils
	 */
	SparQLUtils sparQLUtils;
	/**
	 * retrofitBuilder
	 */
	transient RetrofitBuilder retrofitBuilder;

	/**
	 * FusekiRDFWriter
	 * 
	 * @throws Exception
	 */
	public FusekiRDFWriter() throws Exception {
		propertyUtils = new PropertyUtils();
	}
	
	/**
	 * writeToSolr
	 * 
	 * @param rdf
	 * @throws Exception
	 */
	public void writeToFuseki(String rdf) throws Exception {
		String completePath = new StringBuilder(propertyUtils.getRubyTemppath()).append(File.separator)
				.append(new Date().getTime() + ".owl").toString();
		try (FileWriter fw = new FileWriter(completePath);) {
			fw.write(rdf);
			fw.flush();
		}
		TypedFile typedFile = new TypedFile("multipart/form-data", new File(completePath));
		sparQLUtils = new RetrofitBuilder().getSparQLUtil();
		try {
			sparQLUtils.saveRDF(propertyUtils.getFusekiDataset(), typedFile);
		} catch (Exception e) {
		}
		new File(completePath).delete();
	}

}
