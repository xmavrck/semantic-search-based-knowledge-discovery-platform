package com.sarah.web_crawler.controllers.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sarah.web_crawler.dto.AggregateDto;
import com.sarah.web_crawler.dto.Bindings;
import com.sarah.web_crawler.dto.BuildSparQLRequest;
import com.sarah.web_crawler.dto.RankedDocsDto;
import com.sarah.web_crawler.dto.RankingDto;
import com.sarah.web_crawler.dto.SparQLResult;
import com.sarah.web_crawler.dto.UserSearchRequest;
import com.sarah.web_crawler.utils.ConfigUtils;
import com.sarah.web_crawler.utils.GetResearchAreas;
import com.sarah.web_crawler.utils.RetrofitBuilder;
import com.sarah.web_crawler.utils.SparQLQueryUtils;

/**
 * The class SparQLController
 * 
 * @author chandan
 */

@Controller
@RequestMapping("/sparql")
public class SparQLController {

	@Autowired
	RetrofitBuilder retrofitBuilder;

	@Autowired
	SparQLQueryUtils sparQLQueryUtils;

	@Autowired
	ConfigUtils configUtils;

	@Autowired
	GetResearchAreas getResearchAreas;

	private final Integer MAX_TOP_RESULTS = 3;

	/**
	 * search
	 * 
	 * @param userSearchRequest
	 * @return ResponseEntity<Object>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public @ResponseBody ResponseEntity<Object> search(@RequestBody UserSearchRequest userSearchRequest)
			throws Exception {
		try {
			Map<String, Object> results = new HashMap<>();
			List<RankedDocsDto> docs = null;
			List<Map<String, String>> rdfData = null;
			List<String> urlToUUIDs = null;
			Object[] sparQLResult = null;
			if (userSearchRequest.getProjectDescription() == null
					|| userSearchRequest.getProjectDescription().trim().equals("")) {
				userSearchRequest.setWords(retrofitBuilder.getBuildSparQLApi()
						.getWords(new BuildSparQLRequest(userSearchRequest.getText())).getWords());
				if (userSearchRequest.getKeywordBased().equalsIgnoreCase("true")) {
					sparQLResult = sparQLQueryUtils.buildSparQLKeywordBasedQuery(userSearchRequest);
				} else {
					sparQLResult = sparQLQueryUtils.buildSparQLQuery(userSearchRequest);
				}
			} else {
				// project description query
				userSearchRequest.setText(userSearchRequest.getProjectDescription());
				
				BuildSparQLRequest buildSparQLRequest = new BuildSparQLRequest(
						userSearchRequest.getProjectDescription());

				Map<String, Object> researchAreaScores = retrofitBuilder.getBuildSparQLApi()
						.getResearchAreafromProjectDescription(buildSparQLRequest);

				Iterator<String> itr = researchAreaScores.keySet().iterator();
				Map<String, Double> researchAreaScoresWithValidScores = new HashMap<>();
				while (itr.hasNext()) {
					String key = itr.next();
					Object score = researchAreaScores.get(key);
					if (score == null || score == "NaN" || score == "nan") {
						continue;
					}
					try {
						researchAreaScoresWithValidScores.put(key, Double.parseDouble(score + ""));
					} catch (Exception e) {
					}
				}
				System.out.println(new Gson().toJson(researchAreaScoresWithValidScores));
				List<Entry<String, Double>> greatest = findGreatest(researchAreaScoresWithValidScores, MAX_TOP_RESULTS);
				if (greatest != null && greatest.size() > 0) {
					userSearchRequest.setResearchAreas(new ArrayList<>());
					System.out.println("Top 3 entries:");
					for (Entry<String, Double> entry : greatest) {
						userSearchRequest.getResearchAreas().add(entry.getKey());
						System.out.println(entry);
					}
					sparQLResult = sparQLQueryUtils.buildSparQLQuery(userSearchRequest);
					userSearchRequest.setWords(retrofitBuilder.getBuildSparQLApi()
							.getWords(new BuildSparQLRequest(userSearchRequest.getText())).getWords());
				} else {
					return new ResponseEntity<Object>(results, HttpStatus.OK);
				}
			}

			urlToUUIDs = urlUUIDs(String.valueOf(sparQLResult[0]));

			String query = sparQLQueryUtils.buildSparQLToGetAllRDFData(urlToUUIDs);
			if (query == null || query.trim().equals("")) {
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			}

			Object[] getRDFResult = getRdfData(query);

			rdfData = (List<Map<String, String>>) getRDFResult[0];
			docs = (List<RankedDocsDto>) getRDFResult[1];

			RankingDto rankingDto = rankingWords(userSearchRequest, (List<String>) sparQLResult[1], rdfData);
			Set<String> fetchedResearchAreas = new HashSet<>();

			if (rankingDto != null) {
				rankingDto = retrofitBuilder.getBuildSparQLApi().getRanking(rankingDto);
				List<RankedDocsDto> rankingDocs = rankingDocsDto(rankingDto, docs);

				Collections.sort(rankingDocs, new RankingComparator());
				results.put("results", rankingDocs);
				for (RankedDocsDto dto : rankingDocs) {
					fetchedResearchAreas.addAll(Arrays.asList(dto.getData().get("researchArea").split("<br/>")));
				}

				List aggregatesAll = new ArrayList<>();
				addAggregates(fetchedResearchAreas, rankingDocs, aggregatesAll);
				results.put("aggregates", aggregatesAll);
			}

			return new ResponseEntity<Object>(results, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	private static <K, Double extends Comparable<? super Double>> List<Entry<K, Double>> findGreatest(
			Map<K, Double> map, int n) {
		Comparator<? super Entry<K, Double>> comparator = new Comparator<Entry<K, Double>>() {
			@Override
			public int compare(Entry<K, Double> e0, Entry<K, Double> e1) {
				Double v0 = e0.getValue();
				Double v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, Double>> highest = new PriorityQueue<Entry<K, Double>>(n, comparator);
		for (Entry<K, Double> entry : map.entrySet()) {
			highest.offer(entry);
			while (highest.size() > n) {
				highest.poll();
			}
		}

		List<Entry<K, Double>> result = new ArrayList<Map.Entry<K, Double>>();
		while (highest.size() > 0) {
			result.add(highest.poll());
		}
		return result;
	}

	public void addAggregates(Set<String> fetchedResearchAreas, List<RankedDocsDto> rankingDocs, List aggregatesAll) {
		Map<Map<String, String>, Map<Integer, Integer>> aggregatesRA = new HashMap<>();
		for (String ra : fetchedResearchAreas) {
			Map<String, String> key = new HashMap<>();
			int publisherCounts = 0;
			int researchCounts = 0;
			StringBuilder universities = new StringBuilder();
			for (RankedDocsDto dto : rankingDocs) {
				if (dto.getData().get("researchArea").contains(ra)) {
					if (!universities.toString().contains(dto.getData().get("UniversityName"))) {
						universities.append(dto.getData().get("UniversityName")).append("<br/>");
					}
					researchCounts++;
					if (dto.getData().get("ResearchPublications") != null) {
						publisherCounts += dto.getData().get("ResearchPublications").split("<br/>").length;
					}
				}
			}
			Map<Integer, Integer> counts = null;
			counts = new HashMap<>();
			counts.put(researchCounts, publisherCounts);

			key.put(ra, universities.toString());
			aggregatesRA.put(key, counts);
			aggregatesAll.add(new AggregateDto(universities.toString(), ra, publisherCounts, researchCounts));

		}
	}

	public List<RankedDocsDto> rankingDocsDto(RankingDto rankingDto, List<RankedDocsDto> docs) throws Exception {
		List<RankedDocsDto> rankingDocs = new ArrayList<>();
		Iterator<String> itr = rankingDto.getURLscores().keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			String value = rankingDto.getURLscores().get(key);
			RankedDocsDto rankedDoc = null;
			for (RankedDocsDto dto : docs) {
				if (key.equals(dto.getUrl())) {
					rankedDoc = dto;
					dto.setScore(Double.parseDouble(value));
					dto.setUrl(dto.getUrl().split("___")[0]);
				}
			}
			if (rankedDoc != null) {

				String[] researchAreas = rankedDoc.getData().get("researchArea").split("<br/>");
				Set<String> otherRelatedTerms = new HashSet<>();
				for (String ra : researchAreas) {
					otherRelatedTerms.addAll(getResearchAreas.getRelatedAreas(ra));
				}
				rankedDoc.setOtherRelatedAreas(otherRelatedTerms);
				rankingDocs.add(rankedDoc);
				rankedDoc = null;
			}
		}
		return rankingDocs;
	}

	public List<String> urlUUIDs(String query) throws Exception {
		List<String> urlToUUIDs = new ArrayList<>();
		SparQLResult sparQLResult = retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet(), query);
		if (sparQLResult != null && sparQLResult.getResults() != null && sparQLResult.getResults().getBindings() != null
				&& sparQLResult.getResults().getBindings().length > 0) {
			for (Bindings binding : sparQLResult.getResults().getBindings()) {
				if (binding.getS() != null) {
					try {
						String url = binding.getS().getValue().split("#")[1];
						String[] data = url.split("___");
						urlToUUIDs.add(data[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return urlToUUIDs;
	}

	class RankingComparator implements Comparator<RankedDocsDto> {
		@Override
		public int compare(RankedDocsDto a, RankedDocsDto b) {
			if (a.getScore() > b.getScore()) {
				return -1;
			} else if (a.getScore() < b.getScore()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	private Object[] getRdfData(String query) {
		List<Map<String, String>> rdfData = new ArrayList<>();
		List<RankedDocsDto> docs = new ArrayList<>();
		SparQLResult sparQLResult = retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet(), query);
		if (sparQLResult != null && sparQLResult.getResults() != null && sparQLResult.getResults().getBindings() != null
				&& sparQLResult.getResults().getBindings().length > 0) {
			boolean hasEmailRead = true;
			Map<String, String> rdf = null;
			RankedDocsDto rankedDocsDto = null;
			for (Bindings binding : sparQLResult.getResults().getBindings()) {
				if (binding.getO() != null) {
					try {
						if (binding.getP() == null) {
							if (rdf.get("researchArea") != null) {
								rdf.put("researchArea",
										rdf.get("researchArea") + "<br/>" + binding.getO().getValue().split("#")[1]);
							} else {
								rdf.put("researchArea", binding.getO().getValue().split("#")[1]);
							}
						}
						if (binding.getP() != null) {
							String fieldName = binding.getP().getValue().split("#")[1];
							if (fieldName.equals("EmailAddress")) {
								if (rdf != null && rdf.size() > 0) {
									rankedDocsDto.setData(rdf);
									docs.add(rankedDocsDto);
									rdfData.add(rdf);
								}
								hasEmailRead = true;
							} else {
								hasEmailRead = false;
							}
							if (fieldName.equalsIgnoreCase("URL")) {
								rankedDocsDto.setUrl(binding.getO().getValue());
							}
							if (hasEmailRead) {
								rankedDocsDto = new RankedDocsDto();
								rdf = new HashMap<>();
							}
							if (rdf.get(fieldName) != null) {
								rdf.put(fieldName, rdf.get(fieldName) + "<br/>" + binding.getO().getValue());
							} else {
								rdf.put(fieldName, binding.getO().getValue());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (rdf != null && rdf.size() > 0) {
				rankedDocsDto.setData(rdf);
				docs.add(rankedDocsDto);
				rdfData.add(rdf);
			}

		}
		return new Object[] { rdfData, docs };
	}

	private RankingDto rankingWords(UserSearchRequest userSearchRequest, List<String> extendedWords,
			List<Map<String, String>> rdfData) {
		Set<String> queryWords = new HashSet<>();

		if (userSearchRequest.getWords() != null) {
			for (String word : userSearchRequest.getWords()) {
				queryWords.add(word);
			}
		}
		if (userSearchRequest.getUniversities() != null) {
			for (String univ : userSearchRequest.getUniversities()) {
				queryWords.add(univ);
			}
		}
		Map<String, String> documentData = new HashMap<>();
		if (rdfData != null && rdfData.size() > 0) {
			for (Map<String, String> rdf : rdfData) {
				String url = rdf.get("URL");
				Iterator<String> itr = rdf.keySet().iterator();
				StringBuilder data = new StringBuilder();
				while (itr.hasNext()) {
					data.append(rdf.get(itr.next())).append(" ");
				}
				documentData.put(rdf.get("URL"), data.toString());
			}
			return new RankingDto(new ArrayList(queryWords), extendedWords != null ? extendedWords : new ArrayList<>(),
					documentData);
		}
		return null;
	}

	/**
	 * universities
	 * 
	 * @return ResponseEntity<Object>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/universities")
	public @ResponseBody ResponseEntity<Object> universities() throws Exception {
		try {
			Set<String> universities = new HashSet<>();
			SparQLResult sparQLResult = retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet(),
					sparQLQueryUtils.getUniversityNames());
			if (sparQLResult != null && sparQLResult.getResults() != null
					&& sparQLResult.getResults().getBindings() != null
					&& sparQLResult.getResults().getBindings().length > 0) {
				for (Bindings binding : sparQLResult.getResults().getBindings()) {
					if (binding.getO() != null) {
						universities.add(binding.getO().getValue());
					}
				}
			}
			return new ResponseEntity<Object>(universities, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * researchAreas
	 * 
	 * @return ResponseEntity<Object>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/researchAreas")
	public @ResponseBody ResponseEntity<Object> researchAreas() throws Exception {
		try {
			Set<String> researchAreas = new HashSet<>();
			researchAreas.add("Algorithms_and_Complexity#Algorithms and Complexity");
			researchAreas
					.add("Artificial_intelligence_and_machine_learning#Artificial intelligence and machine learning");
			researchAreas.add("Computational_Biology#Computational Biology");
			researchAreas.add("Computer_Architecture_and_Design#Computer Architecture and Design");
			researchAreas.add("Computer_Graphics_and_Visualization#Computer Graphics and Visualization");
			researchAreas.add("Computer_vision#Computer vision");
			researchAreas.add("Data_mining_and_modeling#Data mining and modeling");
			researchAreas.add("Embedded_systems#Embedded systems");
			researchAreas.add("Multimedia_Computing#Multimedia Computing");
			researchAreas.add("Distributed_Systems_and_Parallel_computing#Distributed Systems and Parallel computing");
			researchAreas.add("Operating_Systems#Operating Systems");
			researchAreas.add("Programming_languages#Programming languages");
			researchAreas.add("Security-_Privacy_and_Cryptography#Security,Privacy and Cryptography");
			researchAreas.add("Software_Engineering#Software Engineering");
			researchAreas.add("Data_Structures#Data Structures");
			researchAreas.add("High_performance_Computing#High performance Computing");
			researchAreas.add("Human_computer_interaction#Human computer interaction");
			researchAreas.add("Natural_Language_Processing#Natural Language Processing");
			researchAreas.add("Networking#Networking");
			researchAreas.add("Mobile_and_sensor_systems#Mobile and sensor system");
			/*
			 * TODO: Right now, we have prepared ontology for 20 research
			 * areas.We will use below code to support all research areas in
			 * future.
			 */
			/*
			 * SparQLResult sparQLResult =
			 * retrofitBuilder.getSparQLApi().query(configUtils.getFusekiDataSet
			 * (), sparQLQueryUtils.getResearchAreas()); if (sparQLResult !=
			 * null && sparQLResult.getResults() != null &&
			 * sparQLResult.getResults().getBindings() != null &&
			 * sparQLResult.getResults().getBindings().length > 0) { for
			 * (Bindings binding : sparQLResult.getResults().getBindings()) { if
			 * (binding.getSubclass() != null) { String
			 * researchArea=binding.getSubclass().getValue().split("#")[1];
			 * researchAreas.add(researchArea+"#"+researchArea.replaceAll("_",
			 * " ")); } } }
			 */
			return new ResponseEntity<Object>(researchAreas, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

}
