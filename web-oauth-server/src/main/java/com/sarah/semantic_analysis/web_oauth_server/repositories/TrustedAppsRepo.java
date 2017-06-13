package com.sarah.semantic_analysis.web_oauth_server.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sarah.semantic_analysis.web_oauth_server.entities.TrustedApps;
/**
 * The class TrustedAppsRepo.
 * 
 * @author chandan
 */
@RepositoryRestResource(collectionResourceRel = "trustedapps", path = "trustedapps")
public interface TrustedAppsRepo extends MongoRepository<TrustedApps, String> {
	
}
