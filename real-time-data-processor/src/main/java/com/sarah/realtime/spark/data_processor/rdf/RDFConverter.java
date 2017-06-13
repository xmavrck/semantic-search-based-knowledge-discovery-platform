package com.sarah.realtime.spark.data_processor.rdf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sarah.realtime.spark.data_processor.dto.RDFData;
import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;

/**
 * The class RDFConverter
 * 
 * @author Chandan
 */
public class RDFConverter implements Serializable {
	/**
	 * header
	 */
	private String header;
	/**
	 * namespace
	 */
	private String namespace = "compsci";
	/**
	 * header
	 */
	private String footer;
	/**
	 * fieldStart,fieldEnd
	 */
	private String fieldStart, fieldEnd;
	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils = null;

	/**
	 * RDFConverter
	 * 
	 * @throws Exception
	 */

	public RDFConverter() throws Exception {
		propertyUtils = new PropertyUtils();
		header = new StringBuilder("<?xml version='1.0'?>\n<rdf:RDF")
				.append(" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n")
				.append(" xmlns:" + namespace + "=\"" + propertyUtils.getOntologyUri() + "#\"\n")
				.append("xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n")
				.append("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n")
				.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\">\n").toString();

		fieldStart = "<rdf:Description rdf:about=\"" + propertyUtils.getOntologyUri() + "#";
		fieldEnd = "</rdf:Description>";

		footer = "  </rdf:Description></rdf:RDF>";
	}

	/**
	 * convertDataToRDF
	 * 
	 * @param rdfData
	 * @param attributes
	 * @return String
	 * @throws Exception
	 */
	public String convertDataToRDF(String taskHistoryId, RDFData rdfData, Map<String, List<String>> attributes)
			throws Exception {
		StringBuilder rdf = new StringBuilder(header);
		
		System.out.println(attributes);
		
		// description with url
		rdf.append("\n").append(fieldStart).append("http___").append(taskHistoryId).append("\">").append("\n");

		// email
		rdf.append("<").append(namespace).append(":").append(propertyUtils.getEmailFieldName()).append(">")
				.append(rdfData.getEmail()).append("</").append(namespace).append(":")
				.append(propertyUtils.getEmailFieldName()).append(">").append("\n");

		// university
		rdf.append("<").append(namespace).append(":").append(propertyUtils.getUniversityFieldName()).append(">")
				.append(rdfData.getUniversity().toLowerCase()).append("</").append(namespace).append(":")
				.append(propertyUtils.getUniversityFieldName()).append(">").append("\n");

		// url
		rdf.append("<").append(namespace).append(":").append(propertyUtils.getUrlFieldName()).append(">")
				.append(rdfData.getUrl()).append("___").append(taskHistoryId).append("</").append(namespace).append(":")
				.append(propertyUtils.getUrlFieldName()).append(">").append("\n");

		// names
		rdf.append("<").append(namespace).append(":").append(propertyUtils.getNameFieldName()).append(">")
				.append(rdfData.getName().toLowerCase()).append("</").append(namespace).append(":")
				.append(propertyUtils.getNameFieldName()).append(">").append("\n");

		// research areas
		if (rdfData.getResearchAreas() != null) {
			for (String researchArea : rdfData.getResearchAreas()) {
				rdf.append("<rdf:type rdf:resource=\"").append(propertyUtils.getOntologyUri()).append("#")
						.append(researchArea.replaceAll(" ", "_")).append("\"/>");
			}
		}

		// recent publication
		if (attributes != null) {
			if (attributes.get("title") != null) {
				for (String publicationTitle : attributes.get("title")) {
					rdf.append("<").append(namespace).append(":").append(propertyUtils.getPublicationsFieldName())
							.append(">")
							.append(publicationTitle.replaceAll("\"", "").replaceAll("&", " ").toLowerCase())
							.append("</").append(namespace).append(":").append(propertyUtils.getPublicationsFieldName())
							.append(">").append("\n");

				}
			}
			if (attributes.get("booktitle") != null) {
				for (String keyword : attributes.get("booktitle")) {
					rdf.append("<").append(namespace).append(":").append(propertyUtils.getKeywordsFieldName())
							.append(">").append(keyword.replaceAll("\"", "").replaceAll("&", " ").toLowerCase())
							.append("</").append(namespace).append(":").append(propertyUtils.getKeywordsFieldName())
							.append(">").append("\n");
				}
			}
		}
		rdf.append(footer);
		return rdf.toString();
	}

}
