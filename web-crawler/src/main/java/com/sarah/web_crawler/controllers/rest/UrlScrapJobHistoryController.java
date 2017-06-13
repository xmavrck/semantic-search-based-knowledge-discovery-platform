package com.sarah.web_crawler.controllers.rest;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.web_crawler.entities.UrlHistory;
import com.sarah.web_crawler.repositories.UrlScrapJobHistoryRepository;

/**
 * The class UrlScrapJobHistoryController
 * 
 * @author chandan
 */
@RepositoryRestController
public class UrlScrapJobHistoryController {

	/**
	 * urlScrapJobHistoryRepository
	 */
	@Autowired
	private UrlScrapJobHistoryRepository urlScrapJobHistoryRepository;

	/**
	 * findAll
	 * 
	 * @param pageable
	 * @return ResponseEntity<Page<UrlHistory>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-history")
	public @ResponseBody ResponseEntity<Page<UrlHistory>> findAll(Pageable pageable) throws Exception {

		Page<UrlHistory> list = null;

		list = urlScrapJobHistoryRepository.findAll(pageable);

		return new ResponseEntity<Page<UrlHistory>>(list, HttpStatus.OK);
	}

	/**
	 * findByTaskId
	 * 
	 * @param taskId
	 * @return ResponseEntity<List<UrlHistory>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-history/findByTaskId")
	public @ResponseBody ResponseEntity<List<UrlHistory>> findByTaskId(@RequestParam("taskId") String taskId)
			throws Exception {
		Future<List<UrlHistory>> listFuture = urlScrapJobHistoryRepository.findByTaskIdAndIsRetrieved(taskId,true);
		while (!listFuture.isDone()) {
		}
		return new ResponseEntity<List<UrlHistory>>(listFuture.get(), HttpStatus.OK);
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return ResponseEntity<Page<UrlHistory>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-history/{id}")
	public @ResponseBody ResponseEntity<UrlHistory> findById(@PathVariable String id) throws Exception {
		try {
			UrlHistory UrlScrapJob = urlScrapJobHistoryRepository.findOne(id);
			if (UrlScrapJob != null) {
				return new ResponseEntity<UrlHistory>(UrlScrapJob, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UrlHistory>(HttpStatus.NOT_FOUND);
	}

	/**
	 * updateById
	 * 
	 * @param id
	 * @param urlHistory
	 * @return ResponseEntity<Page<UrlHistory>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "/url-history/{id}")
	public @ResponseBody ResponseEntity<UrlHistory> updateById(@PathVariable String id,@RequestBody UrlHistory url) throws Exception {
		try {
			UrlHistory UrlScrapJob = urlScrapJobHistoryRepository.findOne(id);
			UrlScrapJob.setRetrieved(true);
			urlScrapJobHistoryRepository.save(UrlScrapJob);
			if (UrlScrapJob != null) {
				return new ResponseEntity<UrlHistory>(UrlScrapJob, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UrlHistory>(HttpStatus.NOT_FOUND);
	}

}