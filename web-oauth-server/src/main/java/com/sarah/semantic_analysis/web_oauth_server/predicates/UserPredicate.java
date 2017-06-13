package com.sarah.semantic_analysis.web_oauth_server.predicates;

import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.types.SearchCriteria;
/**
 * The class UserPredicate.
 * 
 * @author chandan
 */
public class UserPredicate extends GenericPredicate<User> {
	
	/**
	 * UserPredicate
	 */
	public UserPredicate() {
		super(null);
	}
	/**
	 * UserPredicate
	 * 
	 * @param criteria
	 */
	public UserPredicate(SearchCriteria criteria) {
		super(criteria);
	}
}
