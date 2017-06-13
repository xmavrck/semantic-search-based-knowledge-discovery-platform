package com.sarah.web_crawler.controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.json.simple.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sarah.web_crawler.constants.Constants;
import com.sarah.web_crawler.constants.Constants.TaskType;
import com.sarah.web_crawler.dto.UrlScrapJobDto;
import com.sarah.web_crawler.entities.UrlHistory;
import com.sarah.web_crawler.entities.UrlScrapJob;
import com.sarah.web_crawler.repositories.UrlScrapJobHistoryRepository;
import com.sarah.web_crawler.repositories.UrlScrapJobRepository;
import com.sarah.web_crawler.services.WebCrawlerService;
import com.sarah.web_crawler.utils.HDFSUtils;

/**
 * The class UrlScrapJobController
 * 
 * @author chandan
 */
@RepositoryRestController
public class UrlScrapJobController implements Constants.JobStatus, Constants.TaskType {

	/**
	 * webCrawlingService
	 */
	@Autowired
	private WebCrawlerService webCrawlingService;

	/**
	 * webCrawlingService
	 */
	@Autowired
	private HDFSUtils hdfsUtils;

	/**
	 * urlHistoryTaskRepository
	 */
	@Autowired
	private UrlScrapJobRepository urlScrapJobRepository;
	/**
	 * urlScrapJobHistoryRepository
	 */
	@Autowired
	private UrlScrapJobHistoryRepository urlScrapJobHistoryRepository;

	/**
	 * findAll
	 * 
	 * @param pageable
	 * @return ResponseEntity<Page<UrlScrapJob>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-scrap-job")
	public @ResponseBody ResponseEntity<List<UrlScrapJobDto>> findAll(Pageable pageable) throws Exception {
		Page<UrlScrapJob> list = urlScrapJobRepository.findAll(pageable);
		List<UrlScrapJobDto> listScrapJobDto = new ArrayList<>();
		for (UrlScrapJob urlScrapJob : list.getContent()) {
//			Future<Long> listFuture = urlScrapJobHistoryRepository.countByTaskIdAndUrlTrackStatus(urlScrapJob.getId(),
//					RUNNING);
//			while (!listFuture.isDone()) {
//			}
//			Long pendingTasks = listFuture.get();
//			if (pendingTasks != null && pendingTasks > 0) {
//				urlScrapJob.setTaskStatus(RUNNING);
//			} else {
//				urlScrapJob.setTaskStatus(COMPLETED);
//			}
			Future<Long> urlsProcessedFuture = urlScrapJobHistoryRepository.countByTaskId(urlScrapJob.getId());
			while (!urlsProcessedFuture.isDone()) {
			}
			Long urlsProcessed = urlsProcessedFuture.get();

			Future<Long> urlsProcessedRetrieved = urlScrapJobHistoryRepository
					.countByTaskIdAndIsRetrieved(urlScrapJob.getId(), true);
			while (!urlsProcessedRetrieved.isDone()) {
			}
			Long urlsRetrieved = urlsProcessedRetrieved.get();

			listScrapJobDto.add(new UrlScrapJobDto(urlScrapJob.getId(), urlScrapJob.getRootUrl(),
					urlScrapJob.getUniversity(), urlScrapJob.getTaskStatus(), urlScrapJob.getDateAdded(),
					urlScrapJob.getDateCompleted(), urlScrapJob.getTaskType(), urlsProcessed, urlsRetrieved));
		}
		return new ResponseEntity<List<UrlScrapJobDto>>(listScrapJobDto, HttpStatus.OK);
	}

	/**
	 * getAllUniversities
	 * 
	 * @param pageable
	 * @return ResponseEntity<List<UrlScrapJob>>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-scrap-job/universities")
	public @ResponseBody ResponseEntity<List<UrlScrapJob>> getAllUniversities(Pageable pageable) throws Exception {
		return new ResponseEntity<List<UrlScrapJob>>(urlScrapJobRepository.findAll(pageable).getContent(),
				HttpStatus.OK);
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return ResponseEntity<UrlScrapJob>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/url-scrap-job/{id}")
	public @ResponseBody ResponseEntity<UrlScrapJob> findById(@PathVariable String id) throws Exception {
		try {
			UrlScrapJob urlScrapJob = urlScrapJobRepository.findOne(id);
			if (urlScrapJob != null) {
				Future<Long> listFuture = urlScrapJobHistoryRepository.countByTaskIdAndUrlTrackStatus(id, RUNNING);
				while (!listFuture.isDone()) {
				}

				Long pendingTasks = listFuture.get();
				if (pendingTasks != null && pendingTasks > 0) {
					urlScrapJob.setTaskStatus(RUNNING);
				} else {
					urlScrapJob.setTaskStatus(COMPLETED);
				}
				return new ResponseEntity<UrlScrapJob>(urlScrapJob, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UrlScrapJob>(HttpStatus.NOT_FOUND);
	}

	/**
	 * save
	 * 
	 * @param urlScrapJob
	 * @return ResponseEntity<UrlScrapJob>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/url-scrap-job")
	public @ResponseBody ResponseEntity<UrlScrapJob> save(@RequestBody UrlScrapJob urlScrapJob) throws Exception {
		try {
			if (urlScrapJob.getDateAdded() == null) {
				urlScrapJob.setDateAdded(new Date());
				urlScrapJob.setTaskType(TaskType.WEBSITE);
			}
			urlScrapJob.setTaskStatus(ACCEPTED);
			urlScrapJob = urlScrapJobRepository.save(urlScrapJob);
			System.out.println("User Search Request in Web Crawler Service: "+new Gson().toJson(urlScrapJob));
			webCrawlingService.startCrawling(urlScrapJob.getRootUrl(), urlScrapJob);
			return new ResponseEntity<UrlScrapJob>(urlScrapJob, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UrlScrapJob>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * delete
	 * 
	 * @param pageable
	 * @return ResponseEntity<JSONObject>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/url-scrap-job/{id}")
	public @ResponseBody ResponseEntity<JSONObject> delete(@PathVariable("id") String id, Pageable pageable)
			throws Exception {
		JSONObject json = new JSONObject();
		UrlScrapJob urlScrapJob = urlScrapJobRepository.findOne(id);
		System.out.println("Delete "+urlScrapJob.getHdfsDirectoryPath());
		hdfsUtils.deleteFromHDFS(urlScrapJob.getHdfsDirectoryPath());

		urlScrapJobRepository.delete(id);
		urlScrapJobHistoryRepository.deleteByTaskId(id);

		return new ResponseEntity<JSONObject>(json, HttpStatus.OK);
	}
}
