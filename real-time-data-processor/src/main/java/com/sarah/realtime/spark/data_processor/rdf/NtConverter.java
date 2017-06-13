package com.sarah.realtime.spark.data_processor.rdf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sarah.realtime.spark.data_processor.dto.RDFData;
import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;

/**
 * The class NtConverter
 * 
 * @author Chandan
 */
public class NtConverter implements Serializable {

	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils = null;

	/**
	 * RDFConverter
	 * 
	 * @throws Exception
	 */

	public NtConverter() throws Exception {
		propertyUtils = new PropertyUtils();

	}

	/**
	 * convertDataToRDF
	 * 
	 * @param rdfData
	 * @param attributes
	 * @return String
	 * @throws Exception
	 */
	public String convertDataToNT(RDFData rdfData, Map<String, List<String>> attributes) throws Exception {
		StringBuilder rdf = new StringBuilder("<");
		// email
		rdf.append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">").append(" ")
				.append("<").append(propertyUtils.getOntologyUri()).append("#")
				.append(propertyUtils.getEmailFieldName()).append(">").append(" ").append("\"")
				.append(rdfData.getEmail()).append("\"").append(" .").append("\n");

		// university
		rdf.append("<").append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">").append(" ")
				.append("<").append(propertyUtils.getOntologyUri()).append("#")
				.append(propertyUtils.getUniversityFieldName()).append(">").append(" ").append("\"")
				.append(rdfData.getUniversity()).append("\"").append(" .").append("\n");

		// url
		rdf.append("<").append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">").append(" ")
				.append("<").append(propertyUtils.getOntologyUri()).append("#").append(propertyUtils.getUrlFieldName())
				.append(">").append(" ").append("\"").append(rdfData.getUrl()).append("\"").append(" .").append("\n");

		// research areas
		if (rdfData.getResearchAreas() != null) {
			for (String researchArea : rdfData.getResearchAreas()) {
				rdf.append("<").append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">").append(" ")
						.append("<").append(propertyUtils.getOntologyUri()).append("#")
						.append(propertyUtils.getResearchAreasFieldName()).append(">").append(" ").append("\"")
						.append(researchArea).append("\"").append(" .").append("\n");
			}
		}

		// recent publication
		if (attributes != null) {
			if (attributes.get("title") != null) {
				for (String publicationTitle : attributes.get("title")) {
					rdf.append("<").append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">")
							.append(" ").append("<").append(propertyUtils.getOntologyUri()).append("#")
							.append(propertyUtils.getPublicationsFieldName()).append(">").append(" ").append("\"")
							.append(publicationTitle).append("\"").append(" .").append("\n");

				}
			}
			// keywords
			if (attributes.get("booktitle") != null) {
				for (String keyword : attributes.get("booktitle")) {
					rdf.append("<").append(propertyUtils.getOntologyUri()).append("#").append(rdfData.getUrl()).append(">")
							.append(" ").append("<").append(propertyUtils.getOntologyUri()).append("#")
							.append(propertyUtils.getKeywordsFieldName()).append(">").append(" ").append("\"")
							.append(keyword).append("\"").append(" .").append("\n");

				}
			}
		}
		return rdf.toString();
	}

}
