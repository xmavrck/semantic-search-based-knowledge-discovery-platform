package com.sarah.web_crawler.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.web_crawler.dto.NamedEntityExtractionDto;
import com.sarah.web_crawler.dto.NamedEntityExtractionResponse;
import com.sarah.web_crawler.utils.StanfordNlpUtils;

/**
 * The class NLPController
 * 
 * @author chandan
 */
@Controller
@RequestMapping("/nlp")
public class NLPController {

	@Autowired
	StanfordNlpUtils stanfordUtils;

	/**
	 * threadPool
	 */
	@Autowired
	ExecutorService threadPool;

	/**
	 * names
	 * 
	 * @param namedEntityExtractionDto
	 * @return ResponseEntity<Object>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/names")
	public @ResponseBody ResponseEntity<Object> names(@RequestBody NamedEntityExtractionDto namedEntityExtractionDto)
			throws Exception {
		try {
			Future<String> ftListNames = threadPool.submit(new ExtractNamesTask(namedEntityExtractionDto.getText()));
			while (!ftListNames.isDone()) {
			}
			String namesList = ftListNames.get();
			return new ResponseEntity<Object>(new NamedEntityExtractionResponse(namesList), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	private class ExtractNamesTask implements Callable<String> {
		String text;

		public ExtractNamesTask(String text) {
			this.text = text;
		}

		@Override
		public String call() throws Exception {
			return stanfordUtils.getName(text);
		}
	}

}
