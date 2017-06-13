package com.sarah.web_crawler.controllers.rest;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sarah.web_crawler.constants.Constants;
import com.sarah.web_crawler.constants.Constants.TaskType;
import com.sarah.web_crawler.entities.UrlScrapJob;
import com.sarah.web_crawler.repositories.UrlScrapJobRepository;
import com.sarah.web_crawler.services.WebCrawlerService;
/**
 * The class FileController
 * @author chandan
 */
@Controller
public class FilesController implements Constants.JobStatus, Constants.TaskType {

	/**
	 * webCrawlingService
	 */
	@Autowired
	private WebCrawlerService webCrawlingService;

	/**
	 * urlHistoryTaskRepository
	 */
	@Autowired
	private UrlScrapJobRepository urlHistoryTaskRepository;

	
	/**
	 * uploadFiles
	 * @param file
	 * @param university
	 * @return ResponseEntity<UrlScrapJob>
	 * @throws Exception
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/upload-files")
	public @ResponseBody ResponseEntity<UrlScrapJob> uploadFiles(@RequestParam("file") MultipartFile file) throws Exception {
		try {

			UrlScrapJob urlScrapJob = new UrlScrapJob();
			urlScrapJob.setRootUrl(file.getOriginalFilename());
			if (urlScrapJob.getDateAdded() == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, 3);
				urlScrapJob.setDateAdded(calendar.getTime());
			}
			urlScrapJob.setUniversity("kau");
			urlScrapJob.setTaskType(TaskType.FILE);
			urlScrapJob.setTaskStatus(ACCEPTED);
			urlScrapJob = urlHistoryTaskRepository.save(urlScrapJob);
			webCrawlingService.uploadFilesToHDFS(file, urlScrapJob);
			return new ResponseEntity<UrlScrapJob>(urlScrapJob, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UrlScrapJob>(HttpStatus.BAD_REQUEST);
	}

}
