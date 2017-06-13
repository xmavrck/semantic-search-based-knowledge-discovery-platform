package com.sarah.realtime.spark.data_processor.ruby;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.sarah.realtime.spark.data_processor.utils.PropertyUtils;

/**
 * The class RubyScriptExceutor
 * 
 * @author Chandan
 */
public class RubyScriptExceutor implements Serializable {
	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils = null;

	/**
	 * RubyScriptExceutor
	 * 
	 * @throws Exception
	 */
	public RubyScriptExceutor() throws Exception {
		propertyUtils = new PropertyUtils();
	}

	/**
	 * executeScript
	 * 
	 * @param text
	 * @return Map<String, List<String>>
	 * @throws Exception
	 */
	public Object[] executeScript(String text) throws Exception {

		text = text.replaceAll("\"", " ");

		String filePath = propertyUtils.getRubyTemppath() + File.separator + new Date().getTime() + ".ruby";
		
		File pf = new File(filePath).getParentFile();

		if (!pf.isDirectory()) {
			pf.mkdirs();
		}

		try (FileWriter fw = new FileWriter(filePath)) {
			fw.write(new StringBuilder("# encoding: utf-8\nrequire 'anystyle/parser'\nputs Anystyle.parse '")
					.append(text).append("'").toString());
			fw.flush();
		}
		System.out.println("Ruby Script Created "+new StringBuilder("# encoding: utf-8\nrequire 'anystyle/parser'\nputs Anystyle.parse '")
					.append(text).append("'").toString());
		Map<String, List<String>> results = new HashMap<>();
		StringBuilder builder = new StringBuilder();
		try {
			Process process = Runtime.getRuntime().exec("ruby " + filePath);
			process.waitFor();
			
			BufferedReader processIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			
			while ((line = processIn.readLine()) != null) {
				builder.append(line).append("\n");
				
				String[] array = line.split("\",");
				boolean hasAuthor = false;
				String title = "", author = "";
				for (String data : array) {
					String[] values = data.split("=>\"");
					if (values != null && values.length > 1 && values[0].contains("booktitle")
							&& !values[1].trim().equals("")) {
						List<String> list = null;
						if (results.get("booktitle") != null) {
							list = results.get("booktitle");
						} else {
							list = new ArrayList<>();
						}
						list.add(values[1]);
						results.put("booktitle", list);
					} else if (values != null && values.length > 1 && values[0].contains("title")) {
						title = values[1];
					} else if (values != null && values.length > 1 && values[0].contains("author")) {
						if (!values[1].trim().equals("")) {
							hasAuthor = true;
							author = values[1];
						}
					}
				}
				if (hasAuthor && !author.trim().equals("") && !title.equals("")) {
					List<String> list = null;
					if (results.get("title") != null) {
						list = results.get("title");
					} else {
						list = new ArrayList<>();
					}
					list.add(title);
					results.put("title", list);
				}
			}
			
			new File(filePath).delete();
			return new Object[] { results, builder.toString() };
		} catch (Exception e) {
			e.printStackTrace();
			new File(filePath).delete();
		}
		return new Object[] { results, builder.toString() };
	}

	public String getNames(String nameData) throws Exception {
		String[] data = nameData.split("=>");
		boolean isAuthor = false;
		boolean unMatchedAuthor = false;

		List<String> mixNames = new ArrayList<>();

		for (String a : data) {
			if (isAuthor) {
				isAuthor = false;
				a = a.substring(a.indexOf("\"") + 1, a.lastIndexOf("\""));
				mixNames.addAll(Arrays.asList(a.split(",")));
			}
			if (unMatchedAuthor) {
				a = a.substring(a.indexOf("\"") + 1, a.lastIndexOf("\""));
				unMatchedAuthor = false;
				mixNames.addAll(Arrays.asList(a.split(",")));
			}
			if (a.contains(":author") && !a.contains(":authority")) {
				isAuthor = true;
				continue;
			}
			if (a.contains("unmatched-author")) {
				unMatchedAuthor = true;
			}
		}
		System.out.println(mixNames);

		Set<String> names = new HashSet<String>();

		for (String mixName : mixNames) {
			if (!mixName.matches("[A-Za-z0-9 .]+")) {
				names.add(mixName);
			}
		}

		List<String> splittedNames = new ArrayList<String>();
		for (String name : names) {
			splittedNames.addAll(Arrays.asList(name.split(" ")));
		}
		List<String> alphaBetic = new ArrayList<>();
		for (String name : names) {
			if (!name.matches("[A-Za-z0-9 .]+")) {
				alphaBetic.add(name);
			}
		}
			
		SortedMap<String, Integer> score = new TreeMap<String, Integer>();

		for (String splittedName : alphaBetic) {
			if (splittedName.length() > 1) {
				for (String name : names) {
					if (name.contains(splittedName)) {

						if (score.get(name) != null) {
							score.put(name, score.get(name) + 1);
						} else {
							score.put(name, 1);
						}
					}
				}
			}
		}
		Map<String, Integer> sortedByValue = sortByValue(score);
		Map.Entry<String, Integer> maxEntry = null;

		for (Map.Entry<String, Integer> entry : sortedByValue.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		StringBuilder namesMax = new StringBuilder();
		for (Map.Entry<String, Integer> entry : sortedByValue.entrySet()) {
			if (entry.getValue() == maxEntry.getValue()) {
				namesMax.append(entry.getKey()).append(" ");
			}
		}
		System.out.println(namesMax.toString());
		return namesMax.toString();
	}

	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
