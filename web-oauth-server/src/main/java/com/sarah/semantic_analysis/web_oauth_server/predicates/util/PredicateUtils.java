package com.sarah.semantic_analysis.web_oauth_server.predicates.util;

import java.util.List;

import com.mysema.query.types.expr.BooleanExpression;
import com.sarah.semantic_analysis.web_oauth_server.constants.Constants.DBOperations;
import com.sarah.semantic_analysis.web_oauth_server.predicates.GenericPredicate;
import com.sarah.semantic_analysis.web_oauth_server.types.SearchCriteria;

import org.springframework.stereotype.Component;
/**
 * The class PredicateUtils.
 * 
 * @author chandan
 */
@Component
public class PredicateUtils {
	/**
	 * string
	 * 
	 * @param criteria
	 * @param predicate
	 * @param predicates
	 * @param field
	 * @param value
	 * @param value__i
	 * @param value__contains
	 * @param value__icontains
	 * @param value__startswith
	 * @param value__endswith
	 * @param value__in
	 * @return List<BooleanExpression>
	 * @throws Exception
	 */
	public List<BooleanExpression> string(SearchCriteria criteria, GenericPredicate predicate,
			List<BooleanExpression> predicates, String field, String value, String value__i, String value__contains,
			String value__icontains, String value__startswith, String value__endswith, String[] value__in)
			throws Exception {
		if (value != null) {
			criteria.setValues(field, DBOperations.EQUALS, value);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__i != null) {
			criteria.setValues(field, DBOperations.EQUALS_IGNORECASE, value__i);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__contains != null) {
			criteria.setValues(field, DBOperations.CONTAINS, value__contains);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__icontains != null) {
			criteria.setValues(field, DBOperations.ICONTAINS, value__icontains);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__startswith != null) {
			criteria.setValues(field, DBOperations.STARTSWITH, value__startswith);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__endswith != null) {
			criteria.setValues(field, DBOperations.ENDSWITH, value__endswith);
			predicate.setCriteria(criteria);

			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__in != null) {
			criteria.setValues(field, DBOperations.IN, value__in);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		return predicates;
	}
	/**
	 * date
	 * 
	 * @param criteria
	 * @param predicate
	 * @param predicates
	 * @param field
	 * @param value
	 * @param value__lte
	 * @param value__gt
	 * @param value__lt
	 * @param value__gte
	 * @param value__between
	 * @return List<BooleanExpression>
	 * @throws Exception
	 */
	public List<BooleanExpression> date(SearchCriteria criteria, GenericPredicate predicate,
			List<BooleanExpression> predicates, String field, String value, String value__lte, String value__gt,
			String value__lt, String value__gte, String[] value__between) throws Exception {
		if (value != null) {
			criteria.setValues(field, DBOperations.EQUALS, value);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__lte != null) {
			criteria.setValues(field, DBOperations.LTE, value__lte);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__gt != null) {
			criteria.setValues(field, DBOperations.GT, value__gt);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__lt != null) {
			criteria.setValues(field, DBOperations.LT, value__lt);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__gte != null) {
			criteria.setValues(field, DBOperations.GTE, value__gte);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__between != null) {
			criteria.setValues(field, DBOperations.BETWEEN, value__between);
			predicate.setCriteria(criteria);

			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		return predicates;
	}
	/**
	 * integer
	 * 
	 * @param criteria
	 * @param predicate
	 * @param predicates
	 * @param field
	 * @param value
	 * @param value__lte
	 * @param value__gt
	 * @param value__lt
	 * @param value__gte
	 * @param value__between
	 * @param value__in
	 * @return List<BooleanExpression>
	 * @throws Exception
	 */
	public List<BooleanExpression> integer(SearchCriteria criteria, GenericPredicate predicate,
			List<BooleanExpression> predicates, String field, Integer value, Integer value__lte, Integer value__gt,
			Integer value__lt, Integer value__gte, Integer[] value__between, Integer[] value__in) throws Exception {
		if (value != null) {
			criteria.setValues(field, DBOperations.EQUALS, value);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__lte != null) {
			criteria.setValues(field, DBOperations.LTE, value__lte);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__gt != null) {
			criteria.setValues(field, DBOperations.GT, value__gt);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__lt != null) {
			criteria.setValues(field, DBOperations.LT, value__lt);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__gte != null) {
			criteria.setValues(field, DBOperations.GTE, value__gte);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__between != null) {
			criteria.setValues(field, DBOperations.BETWEEN, value__between);
			predicate.setCriteria(criteria);

			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		if (value__in != null) {
			criteria.setValues(field, DBOperations.NUMBER_IN, value__in);
			predicate.setCriteria(criteria);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}
		return predicates;
	}
}
