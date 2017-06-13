package com.sarah.web_crawler.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sarah.web_crawler.dto.Bindings;
import com.sarah.web_crawler.dto.Results;
import com.sarah.web_crawler.dto.SparQLResult;
import com.sarah.web_crawler.dto.UserSearchRequest;

/*
 * The class SparQLQueryUtils
 * */
@Component
public class SparQLQueryUtils {

	/*
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;

	/*
	 * limit for sparql results
	 */
	private static final Integer LIMIT = 500;

	/*
	 * retrofitBuilder
	 */
	@Autowired
	RetrofitBuilder retrofitBuilder;

	/*
	 * header
	 */
	String header = null;

	/**
	 * init - Initialize our header
	 * 
	 */
	@PostConstruct
	public void init() {
		/*
		 * contains prefix for 3 things 1. rdfs 2. owl 3. compsci - this is
		 * custom ontology
		 */
		header = new StringBuilder("prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n")
				.append("prefix owl:<http://www.w3.org/2002/07/owl#>\n")
				.append("prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n")
				.append("prefix compsci:<" + configUtils.getOntologyUri() + "#>\n").toString();
	}

	/**
	 * getUniversityNames - return query to reterive all distinct university
	 * names in our rdf data
	 * 
	 * @return String
	 */
	public String getUniversityNames() {
		StringBuilder query = new StringBuilder(header).append("\n");
		query.append("select distinct ?o where { ?s compsci:UniversityName ?o } LIMIT " + LIMIT);
		return query.toString();
	}

	/**
	 * getUniversityNames - return query to reterive all distinct university
	 * names in our rdf data
	 * 
	 * @return String
	 */

	public String getResearchAreasDescriptionQuery(String researchArea) {

		String header = new StringBuilder("prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n")
				.append("prefix owl:<http://www.w3.org/2002/07/owl#>\n")
				.append("prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n")
				.append("prefix compsci:<http://www.semanticweb.org/sarah_mvc/ontologies/2017/3/ComSciOntology#>\n")
				.toString();

		StringBuilder query = new StringBuilder(header).append("\n");
		query.append("  select distinct ?s ?o  where { ?s (rdfs:isDefinedBy) ?o . }");
		return query.toString();
	}

	/**
	 * getSubClassesOfResearchArea - return query to reterive all distinct
	 * research areas & its subclassess in our onotology
	 * 
	 * @return String
	 */
	public String getSubClassesOfResearchArea() {
		StringBuilder query = new StringBuilder(header).append("\n");
		query.append(" select distinct ?subclass where { ?subclass (rdfs:subClassOf)* compsci:ResearchArea } LIMIT "
				+ LIMIT);
		return query.toString();
	}

	/**
	 * buildSparQLQuery - this method is used to build sparql query from user
	 * request
	 * 
	 * @param userSearchRequest
	 * @return String
	 * @throws Exception
	 */
	public Object[] buildSparQLKeywordBasedQuery(UserSearchRequest userSearchRequest) throws Exception {

		List<String> extendedKeywords = new ArrayList<>();
		/*
		 * if user's didn't selected any research areas , then initilaize our
		 * empty research area list
		 */

		if (userSearchRequest.getResearchAreas() == null)
			userSearchRequest.setResearchAreas(new ArrayList<>());

		// using stringbuilder to build query as it is memory efficient
		// appending our header into the query
		StringBuilder query = new StringBuilder(header).append("\n");

		// getting subclasses of research areas if any from ontology
		List<String> researchAreas = userSearchRequest.getResearchAreas();

		if (researchAreas != null) {
			for (String ra : researchAreas) {
				extendedKeywords.addAll(Arrays.asList(ra.split("_")));
			}
		}

		// then selecting subject,predicate and object and adding "#http" filter
		// to ignore other ontology file results
		// and extract only the cv's info that are extract from websites
		query.append(" select distinct ?s ?p ?o where { {  FILTER (regex(str(?s), \"http___\" ) )\n");

		// this flag is used to check that whether user has selected university
		// or research area from dropdown or not
		boolean hasOtherElements = false;
		// checking that if user has selected any research areas or not.
		if (researchAreas.size() > 0) {
			// if selected then set hasOtherElements=true
			hasOtherElements = true;

			query.append("{ ");
			// iterating over the research areas that are selected by user
			for (int i = 0; i < researchAreas.size(); i++) {
				if (researchAreas.get(i).contains("++") || researchAreas.get(i).contains("'")
						|| researchAreas.get(i).contains("/"))
					continue;
				// appending research area into query(this is actually search
				// query expansion happening)
				query.append("{?s ?subClassOf compsci:").append(researchAreas.get(i)).append(" } \n");
				// adding UNION to add "OR" condition within all research areas
				// that are added to query
				// so that if any RDF containing any of the research area , that
				// rdf will be extracted
				if (i < researchAreas.size() - 1) {
					query.append("UNION \n");
				}
			}
			// closing the bracket
			query.append(" }\n");
		}

		// adding universities into query
		if (userSearchRequest.getUniversities() != null && userSearchRequest.getUniversities().size() > 0) {
			// setting hasOtherElements=true if user has selected universities
			hasOtherElements = true;
			// adding university filter
			query.append("{ ?s compsci:UniversityName ?univ .\n");
			query.append("FILTER (");
			// iterating over the universities that are selected by user
			for (int i = 0; i < userSearchRequest.getUniversities().size(); i++) {
				// using regex filter on university field
				query.append("(regex(?univ,\"").append(userSearchRequest.getUniversities().get(i).toLowerCase())
						.append("\"))");
				// adding "OR " condition within all universities
				if (i < userSearchRequest.getUniversities().size() - 1) {
					query.append(" || ");
				}
			}
			// closing the brackets
			query.append(")  ");
			if (userSearchRequest.getWords() == null || userSearchRequest.getWords().size() == 0) {
				query.append(" } ");
			}
		}

		// adding the user search query words into query
		if (userSearchRequest.getWords() != null && userSearchRequest.getWords().size() > 0) {
			// if user has selected university or research area
			// then appending "UNION" to add OR condition within
			// university,researchareas and words
			if (hasOtherElements) {
				query.append("\n");
			}

			query.append("  ?s ?p ?o .\n    ");

			// initializing filter
			query.append("FILTER (");
			query.append("(");
			// iterating over words filled by user in search query
			for (int i = 0; i < userSearchRequest.getWords().size(); i++) {
				// adding regex filter(it like if user's words is computer, then
				// it will scan all RDF's having
				// computer word in it
				query.append("(regex(?o,\"").append(userSearchRequest.getWords().get(i).toLowerCase()).append("\"))");
				// adding OR operator
				if (i < userSearchRequest.getWords().size() - 1) {
					query.append(" || ");
				}
			}
			query.append(")");
			query.append(") ");
			if (userSearchRequest.getUniversities() != null && userSearchRequest.getUniversities().size() > 0) {
				query.append(" } ");
			}

		}
		query.append("\n");
		query.append("}\n");
		// setting limit for extracting max results using sparql
		query.append("} LIMIT " + LIMIT + "\n");
		System.out.println(query);
		return new Object[] { query.toString(), extendedKeywords };
	}

	/**
	 * buildSparQLQuery - this method is used to build sparql query from user
	 * request
	 * 
	 * @param userSearchRequest
	 * @return String
	 * @throws Exception
	 */
	public Object[] buildSparQLQuery(UserSearchRequest userSearchRequest) throws Exception {

		List<String> extendedKeywords = new ArrayList<>();
		/*
		 * if user's didn't selected any research areas , then initilaize our
		 * empty research area list
		 */

		if (userSearchRequest.getResearchAreas() == null)
			userSearchRequest.setResearchAreas(new ArrayList<>());

		// getting research areas from words
		List<String> reseachAreasFromWords = getResearchAreasFromWords(userSearchRequest.getWords());

		// this set will contains research areas from both a) research area drop
		// down and b) research area detected from
		// user's search query
		Set<String> mergeResearchAreas = new HashSet<>();
		// adding reseachAreasFromWords into mergeResearchAreas

		if (reseachAreasFromWords != null && reseachAreasFromWords.size() > 0) {
			mergeResearchAreas.addAll(reseachAreasFromWords);
		}

		// adding research area selected from dropdown into mergeResearchAreas
		if (userSearchRequest.getResearchAreas() != null && userSearchRequest.getResearchAreas().size() > 0)
			mergeResearchAreas.addAll(userSearchRequest.getResearchAreas());

		// updating user's research area from new research area list i.e.
		// mergeResearchAreas
		userSearchRequest.setResearchAreas(new ArrayList<>(mergeResearchAreas));

		// using stringbuilder to build query as it is memory efficient
		// appending our header into the query
		StringBuilder query = new StringBuilder(header).append("\n");

		// getting subclasses of research areas if any from ontology
		List<String> researchAreas = getResearchAreas(userSearchRequest);

		if (researchAreas != null) {
			for (String ra : researchAreas) {
				extendedKeywords.addAll(Arrays.asList(ra.split("_")));
			}
		}

		// then selecting subject,predicate and object and adding "#http" filter
		// to ignore other ontology file results
		// and extract only the cv's info that are extract from websites
		query.append(" select distinct ?s ?p ?o where { {  FILTER (regex(str(?s), \"http___\" ) )\n");

		// this flag is used to check that whether user has selected university
		// or research area from dropdown or not
		boolean hasOtherElements = false;
		// checking that if user has selected any research areas or not.
		if (researchAreas.size() > 0) {
			// if selected then set hasOtherElements=true
			hasOtherElements = true;

			query.append("{ ");
			// iterating over the research areas that are selected by user
			for (int i = 0; i < researchAreas.size(); i++) {
				if (researchAreas.get(i).contains("++") || researchAreas.get(i).contains("'")
						|| researchAreas.get(i).contains("/"))
					continue;
				// appending research area into query(this is actually search
				// query expansion happening)
				query.append("{?s ?subClassOf compsci:").append(researchAreas.get(i)).append(" } \n");
				// adding UNION to add "OR" condition within all research areas
				// that are added to query
				// so that if any RDF containing any of the research area , that
				// rdf will be extracted
				if (i < researchAreas.size() - 1) {
					query.append("UNION \n");
				}
			}
			// closing the bracket
			query.append(" }\n");
		}

		// adding universities into query
		if (userSearchRequest.getUniversities() != null && userSearchRequest.getUniversities().size() > 0) {
			// setting hasOtherElements=true if user has selected universities
			hasOtherElements = true;
			// adding university filter
			query.append("{ ?s compsci:UniversityName ?univ .\n");
			query.append("FILTER (");
			// iterating over the universities that are selected by user
			for (int i = 0; i < userSearchRequest.getUniversities().size(); i++) {
				// using regex filter on university field
				query.append("(regex(?univ,\"").append(userSearchRequest.getUniversities().get(i).toLowerCase())
						.append("\"))");
				// adding "OR " condition within all universities
				if (i < userSearchRequest.getUniversities().size() - 1) {
					query.append(" || ");
				}
			}
			// closing the brackets
			query.append(") ");
			if (userSearchRequest.getWords() == null || userSearchRequest.getWords().size() == 0) {
				query.append(" } ");
			}
		}

		// adding the user search query words into query
		if (userSearchRequest.getWords() != null && userSearchRequest.getWords().size() > 0) {
			// if user has selected university or research area
			// then appending "UNION" to add OR condition within
			// university,researchareas and words

			query.append(" \n ?s ?p ?o .\n    ");

			// initializing filter
			query.append("FILTER (");
			query.append("(");
			// iterating over words filled by user in search query
			for (int i = 0; i < userSearchRequest.getWords().size(); i++) {
				// adding regex filter(it like if user's words is computer, then
				// it will scan all RDF's having
				// computer word in it
				query.append("(regex(?o,\"").append(userSearchRequest.getWords().get(i).toLowerCase()).append("\"))");
				// adding OR operator
				if (i < userSearchRequest.getWords().size() - 1) {
					query.append(" || ");
				}
			}
			query.append(")");
			query.append(") ");
			if (userSearchRequest.getUniversities() != null && userSearchRequest.getUniversities().size() > 0) {
				query.append(" } ");
			}

		}
		query.append("\n");
		query.append("}\n");
		// setting limit for extracting max results using sparql
		query.append("} LIMIT " + LIMIT + "\n");
		System.out.println(query);
		return new Object[] { query.toString(), extendedKeywords };
	}

	/**
	 * buildSparQLQuery - this method is used to build sparql query from user
	 * request
	 * 
	 * @param userSearchRequest
	 * @return String
	 * @throws Exception
	 */
	public String buildSparQLToGetAllRDFData(List<String> uuids) throws Exception {
		if (uuids == null || uuids.size() == 0)
			return null;

		StringBuilder query = new StringBuilder(header).append("\n");
		// SELECT ?p ?o
		// WHERE {
		// {
		// compsci:http___5657656576576676 ?p ?o . filter isLiteral(?o)
		// }
		// UNION
		// {
		// compsci:chanucv ?p ?o . filter isLiteral(?o)
		// }
		// }

		// then selecting subject,predicate and object and adding "#http" filter
		// to ignore other ontology file results
		// and extract only the cv's info that are extract from websites
		query.append(" SELECT ?p ?o WHERE {");
		for (int index = 0; index < uuids.size(); index++) {
			query.append("{ {  ");
			query.append("compsci:http___").append(uuids.get(index)).append(" ?p ?o . filter isLiteral(?o)");
			query.append(" } UNION { compsci:http___" + uuids.get(index) + " rdf:type ?o . } } ");

			if (index < uuids.size() - 1) {
				query.append(" UNION ");
			}
		}
		query.append(" }");
		System.out.println(query.toString());
		return query.toString();
	}

	/**
	 * getResearchAreasFromWords - this method is used to get research areas
	 * from words
	 * 
	 * @param userWords
	 * @return List<String>
	 */
	public List<String> getResearchAreasFromWords(List<String> userWords) {
		// if user hasn;t write any words in search field, then it will
		// return null
		if (userWords == null || userWords.size() == 0)
			return null;

		// these list will be storing all combinations of all words that are
		// entered by user in searhc query
		List<String> words = new ArrayList<>();
		// firstly adding all userwords into words list
		words.addAll(userWords);

		// then iterating over user words
		for (int index = 0; index < userWords.size() - 1; index++) {
			// creating the combination of user words and adding it into our
			// main words list
			words.add(
					new StringBuilder(userWords.get(index) + "_" + userWords.get(index + 1)).toString().toLowerCase());
		}
		// getting all researchareas from our ontology deployed in JENA
		SparQLResult result = retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet(),
				getSubClassesOfResearchArea());
		// intializing empty research areas list in ontology
		List<String> ontologyResearchAreas = new ArrayList<>();
		// extracting research areas from sparql result and adding it to
		// ontologyResearchAreas
		if (result != null && result.getResults() != null && result.getResults().getBindings() != null
				&& result.getResults().getBindings().length > 0) {
			for (Bindings binding : result.getResults().getBindings()) {
				if (binding.getSubclass() != null) {
					// extract the name of research area from subclass field and
					// adding into ontologyResearchAreas
					ontologyResearchAreas.add(binding.getSubclass().getValue().split("#")[1]);
				}
			}
		}
		// using Set collections here to store researchAreasFromWords
		// so that no duplicate research areas should be there
		Set<String> researchAreasFromWords = new HashSet<>();

		// checking onotology research areas size
		if (ontologyResearchAreas.size() > 0) {
			// iterating over research areas stored in JENA
			for (String ontologyResearchArea : ontologyResearchAreas) {
				// iterating over words(cominbations we made from user search
				// query)
				for (String word : words) {
					// comparing JENA research area with user words(with each
					// word one by one)
					if (ontologyResearchArea.toLowerCase().contains(word)) {
						// if condition matched , updating
						// researchAreasFromWords set
						researchAreasFromWords.add(ontologyResearchArea);
					}
				}
			}
		}
		// convert "Set" to "ArrayList" and returning it
		return new ArrayList<>(researchAreasFromWords);
	}

	/**
	 * getResearchAreas - this method is used to get research areas from user
	 * search request and this method is calling JENA API
	 * 
	 * @param userSearchRequest
	 * @return List<String>
	 */

	public List<String> getResearchAreas(UserSearchRequest userSearchRequest) {
		// initializing our empty research area set
		Set<String> reaseachAreas = new HashSet<>();
		// checking if user has selected any research area or not
		if (userSearchRequest != null && userSearchRequest.getResearchAreas() != null
				&& userSearchRequest.getResearchAreas().size() > 0) {
			// iterating over research areas selected by user
			for (String researchArea : userSearchRequest.getResearchAreas()) {
				// adding research area into our set
				reaseachAreas.add(researchArea);
				// calling JENA API to extract subclasses of this research areas
				SparQLResult result = retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet(),
						new StringBuilder(header)
								.append("select distinct ?subclass where { ?subclass (rdfs:subClassOf|owl:equivalentClass)* compsci:"
										+ researchArea + " } LIMIT " + LIMIT)
								.toString());
				// checking if there are subclasses of current research area or
				// not
				if (result != null && result.getResults() != null && result.getResults().getBindings() != null
						&& result.getResults().getBindings().length > 0) {
					// iterating over sparql results
					for (Bindings binding : result.getResults().getBindings()) {
						if (binding.getSubclass() != null) {
							// adding sublcass into our research areas
							reaseachAreas.add(binding.getSubclass().getValue().split("#")[1]);
						}
					}
				}
			}
		}
		return new ArrayList<>(reaseachAreas);
	}
}
