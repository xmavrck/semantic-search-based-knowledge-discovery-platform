package com.sarah.web_crawler.repositories;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.scheduling.annotation.Async;

import com.sarah.web_crawler.entities.UrlHistory;

/**
 * The interface UrlScrapJobHistoryRepository
 * 
 * @author chandan
 */
@RepositoryRestResource(collectionResourceRel = "url-history", path = "url-history", exported = true)
public interface UrlScrapJobHistoryRepository extends CrudRepository<UrlHistory, String> {

	/**
	 * findByUrl
	 * 
	 * @param url
	 * @return Future<List<UrlHistory>>
	 */
	@Async
	@RestResource(exported = true)
	Future<List<UrlHistory>> findByUrl(String url);

	/**
	 * findByTaskId
	 * 
	 * @param taskId
	 * @return Future<List<UrlHistory>>
	 */
	@Async
	Future<List<UrlHistory>> findByTaskId(String taskId);
	
	/**
	 * findByTaskIdAndIsRetrieved
	 * 
	 * @param taskId
	 * @param isRetrieved
	 * @return Future<List<UrlHistory>>
	 */
	@Async
	Future<List<UrlHistory>> findByTaskIdAndIsRetrieved(String taskId,boolean isRetrieved);

	/**
	 * countByTaskIdAndIsRetrieved
	 * 
	 * @param taskId
	 * @param isRetrieved
	 * @return Future<Long>
	 */
	@Async
	Future<Long> countByTaskIdAndIsRetrieved(String taskId, boolean isRetrieved);

	/**
	 * countByTaskId
	 * 
	 * @param taskId
	 * @return Future<Long>
	 */
	@Async
	Future<Long> countByTaskId(String taskId);

	/**
	 * findByTaskIdAndUrlTrackStatus
	 * 
	 * @param taskId
	 * @param urlTrackStatus
	 * @return Future<List<UrlHistory>>
	 */
	@Async
	Future<Long> countByTaskIdAndUrlTrackStatus(String taskId, String urlTrackStatus);

	/**
	 * findAll
	 * 
	 * @param pageable
	 * @return Page<UrlHistory>
	 */
	Page<UrlHistory> findAll(Pageable pageable);

	/**
	 * deleteByTaskId
	 * 
	 * @param taskId
	 * @return Future<Long>
	 */
	@Async
	Future<Long> deleteByTaskId(String taskId);
}
