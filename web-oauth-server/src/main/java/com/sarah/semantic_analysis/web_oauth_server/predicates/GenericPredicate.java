package com.sarah.semantic_analysis.web_oauth_server.predicates;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;
import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.types.SearchCriteria;

/**
 * The class GenericPredicate.
 * 
 * @author chandan
 */
public class GenericPredicate<T> implements Constants {
	/**
	 * criteria
	 */
	private SearchCriteria criteria;
	/**
	 * sdf
	 */
	SimpleDateFormat sdf = null;
	/**
	 * entityClass
	 */
	protected Class<T> entityClass;

	/**
	 * GenericPredicate
	 * 
	 * @param criteria
	 */
	public GenericPredicate(SearchCriteria criteria) {
		super();
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.criteria = criteria;
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}

	/**
	 * setCriteria
	 * 
	 * @param criteria
	 */
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * GenericPredicate
	 * 
	 * @param criteria
	 * @param format
	 */
	public GenericPredicate(SearchCriteria criteria, String format) {
		super();
		this.criteria = criteria;
		sdf = new SimpleDateFormat(format);
	}

	/**
	 * getPredicate
	 * 
	 * @return BooleanExpression
	 */
	public BooleanExpression getPredicate() throws Exception {
		PathBuilder entityPath = new PathBuilder(entityClass, entityClass.getName());
		StringPath path = entityPath.getString(criteria.getKey());
		BooleanExpression exp = null;
		Object value = criteria.getValue();
		DateTimePath<Date> dateTimePath = null;
		NumberPath<Integer> numPath = null;
		String[] dateRange = null;
		Integer[] range = null;
		switch (criteria.getOperation()) {
		case EQUALS:
			exp = path.eq(criteria.getValue().toString());
			break;
		case EQUALS_IGNORECASE:
			exp = path.equalsIgnoreCase(criteria.getValue().toString());
			break;
		case DATE_IN:
			dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
			dateRange = (String[]) value;
			Date[] dateArr = new Date[dateRange.length];
			for (int i = 0; i < dateRange.length; i++) {
				dateArr[i] = sdf.parse(dateRange[i].toString());
			}
			exp = dateTimePath.in(dateArr);
			break;
		case NUMBER_IN:
			numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
			range = (Integer[]) value;
			exp = numPath.in(range);
			break;
		case IN:
			exp = path.in((String[]) criteria.getValue());
			break;
		case STARTSWITH:
			exp = path.startsWithIgnoreCase(criteria.getValue().toString());
			break;
		case ENDSWITH:
			exp = path.endsWithIgnoreCase(criteria.getValue().toString());
			break;
		case CONTAINS:
			exp = path.contains(criteria.getValue().toString());
			break;
		case ICONTAINS:
			exp = path.containsIgnoreCase(criteria.getValue().toString());
			break;
		case GT:
			value = criteria.getValue();
			try {
				dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
				exp = dateTimePath.gt(sdf.parse(value.toString()));
			} catch (Exception e) {
				numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
				exp = numPath.gt(Integer.parseInt(value.toString()));
			}
			break;
		case GTE:
			value = criteria.getValue();
			try {
				dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
				exp = dateTimePath.goe(sdf.parse(value.toString()));
			} catch (Exception e) {
				numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
				exp = numPath.goe(Integer.parseInt(value.toString()));
			}
			break;
		case LT:
			value = criteria.getValue();
			if (value instanceof Date) {
				dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
				exp = dateTimePath.lt(sdf.parse(value.toString()));

			} else if (value instanceof Integer) {
				numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
				exp = numPath.lt(Integer.parseInt(value.toString()));
			}
			break;
		case LTE:
			value = criteria.getValue();
			try {
				dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
				exp = dateTimePath.loe(sdf.parse(value.toString()));
			} catch (Exception e) {
				numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
				exp = numPath.loe(Integer.parseInt(value.toString()));
			}
			break;
		case BETWEEN:
			value = criteria.getValue();
			try {
				dateTimePath = entityPath.getDateTime(criteria.getKey(), Date.class);
				dateRange = (String[]) value;
				exp = dateTimePath.between(sdf.parse(dateRange[0].toString()), sdf.parse(dateRange[1].toString()));
			} catch (Exception e) {
				e.printStackTrace();
				numPath = entityPath.getNumber(criteria.getKey(), Integer.class);
				range = (Integer[]) value;
				exp = numPath.between(range[0], range[1]);
			}
			break;
		}
		return exp;
	}

}
