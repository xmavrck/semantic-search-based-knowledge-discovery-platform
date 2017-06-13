package com.sarah.web_crawler.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sarah.web_crawler.entities.UrlScrapJob;
/**
 * The interface UrlScrapJobRepository
 * @author chandan
 */
@RepositoryRestResource(collectionResourceRel = "url-scrap-job", path = "url-scrap-job", exported = true)
public interface UrlScrapJobRepository extends CrudRepository<UrlScrapJob, String> {
	/**
	 * findAll
	 * @param pageable
	 * @return Page<UrlScrapJob>
	 */
	Page<UrlScrapJob> findAll(Pageable pageable);
	
	
}