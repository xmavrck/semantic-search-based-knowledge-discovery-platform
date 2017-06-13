package com.sarah.semantic_analysis.web_oauth_server.predicates.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.types.expr.BooleanExpression;
import com.sarah.semantic_analysis.web_oauth_server.constants.Constants.DBOperations;
import com.sarah.semantic_analysis.web_oauth_server.dto.SearchUserDto;
import com.sarah.semantic_analysis.web_oauth_server.predicates.UserPredicate;
import com.sarah.semantic_analysis.web_oauth_server.predicates.metamodels.UserMetaModel;
import com.sarah.semantic_analysis.web_oauth_server.predicates.util.PredicateUtils;
import com.sarah.semantic_analysis.web_oauth_server.types.SearchCriteria;

/**
 * The class UserPredicateBuilder.
 * 
 * @author chandan
 */
public class UserPredicateBuilder implements UserMetaModel {

	/**
	 * predicateUtils
	 */
	@Autowired
	PredicateUtils predicateUtils;

	/**
	 * build
	 * 
	 * @param dto
	 * @return BooleanExpression
	 * @throws Exception
	 */
	public BooleanExpression build(SearchUserDto dto) throws Exception {
		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		BooleanExpression result = null;
		if (dto != null) {
			UserPredicate predicate = new UserPredicate();
			SearchCriteria criteria = new SearchCriteria();
			if (dto.getId() != null) {
				criteria.setValues(ID, DBOperations.EQUALS, dto.getId());
				predicate.setCriteria(criteria);
				BooleanExpression exp = predicate.getPredicate();
				if (exp != null) {
					predicates.add(exp);
				}
			}
			if (dto.getRole() != null) {
				criteria.setValues(ROLES, DBOperations.EQUALS, dto.getRole());
				predicate.setCriteria(criteria);
				BooleanExpression exp = predicate.getPredicate();
				if (exp != null) {
					predicates.add(exp);
				}
			}
			if (predicates.size() > 0) {
				result = predicates.get(0);
				if (predicates.size() > 1) {
					for (int i = 1; i < predicates.size(); i++) {
						result = result.and(predicates.get(i));
					}
				}
			}
		}
		return result;
	}
}
