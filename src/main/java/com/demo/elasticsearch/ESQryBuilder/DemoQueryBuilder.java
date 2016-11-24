package com.demo.elasticsearch.ESQryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.demo.elasticsearch.ESModels.Filter;
import com.demo.elasticsearch.ESUtils.IConstants;

public class DemoQueryBuilder {

	public static QueryBuilder generateNumericCriteria(Filter f) {
		QueryBuilder retQueryBuilder = null;
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		/*
		 * Type : "Numeric", Field : "Id", Operation:
		 * ["Equals","LessThan","GreaterThan","LessThanEqual","GreaterThanEqual"
		 * ,"Between"], Value : [val1,val2]
		 */

		String keyVal = f.getField();
		List<String> values = f.getValue();

		switch (f.getOperation()) {
		case IConstants.OPR_EQUALS: {
			retQueryBuilder = QueryBuilders.matchQuery(keyVal, values.get(0));
			break;
		}
		case IConstants.OPR_LESS_THAN: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).lt(values.get(0));
			break;
		}
		case IConstants.OPR_GREATER_THAN: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gt(values.get(0));
			break;
		}
		case IConstants.OPR_LESS_THAN_EQ: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).lte(values.get(0));
			break;
		}
		case IConstants.OPR_GREATER_THAN_EQ: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gte(values.get(0));
			break;
		}
		case IConstants.OPR_BETWEEN: {
			System.out.println("In Numeric Between");
			String startVal, endVal;
			if (values == null || values.size() != 2) {
				startVal = endVal = "N/A";
			} else {
				startVal = values.get(0);
				endVal = values.get(1);
			}
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gte(startVal).lte(endVal);
			break;
		}
		default: {
			break;
		}

		}
		retQueryBuilder = qb.must(retQueryBuilder);
		return retQueryBuilder;
		// return retMatchBuilder;
	}

	public static QueryBuilder generateDateCriteria(Filter f) {
		QueryBuilder retQueryBuilder = null;
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		// MatchQueryBuilder retMatchBuilder=null;
		// QueryBuilders.rangeQuery(f.getField()).from(f.getType())

		/*
		 * Type : "Date", Field : "StartDate", Operation:
		 * ["Equals","LessThan","GreaterThan","LessThanEqual",
		 * "GreaterThanEqual"] Value :
		 */
		String keyVal = f.getField();
		List<String> values = f.getValue();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
		Date searchDate=null;
		try {
			searchDate=fmt.parse(values.get(0));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (f.getOperation()) {
		case IConstants.OPR_EQUALS: {
			retQueryBuilder = QueryBuilders.matchQuery(keyVal, searchDate);

			break;
		}
		case IConstants.OPR_LESS_THAN: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).lt(values.get(0));

			break;
		}
		case IConstants.OPR_GREATER_THAN: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gt(values.get(0));

			break;
		}
		case IConstants.OPR_LESS_THAN_EQ: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).lte(values.get(0));

			break;
		}
		case IConstants.OPR_GREATER_THAN_EQ: {
			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gte(values.get(0));

			break;
		}
		case IConstants.OPR_BETWEEN: {

			retQueryBuilder = QueryBuilders.rangeQuery(keyVal).gte(values.get(0)).lte(values.get(1));
			break;
		}
		default: {
			break;
		}

		}
		retQueryBuilder = qb.must(retQueryBuilder);
		return retQueryBuilder;
		// return retMatchBuilder;
	}

	public static QueryBuilder generateTextCriteria(Filter f) {
		QueryBuilder retQueryBuilder = null;
		// MatchQueryBuilder retMatchBuilder=null;
		// QueryBuilders.rangeQuery(f.getField()).from(f.getType())

		/*
		 * Text: Type : "Text", Field : "Name", Operation:
		 * ["Equals","StartWith","Contains"] Value : [text1,text2]
		 */

		String keyVal = f.getField();
		List<String> values = f.getValue();

		switch (f.getOperation()) {
		case IConstants.OPR_EQUALS: {
			retQueryBuilder = QueryBuilders.matchQuery(keyVal, values.get(0));

			break;
		}
		case IConstants.OPR_STARTS_WITH: {
			//retQueryBuilder = QueryBuilders.regexpQuery(keyVal, values.get(0) + "*");
			retQueryBuilder = QueryBuilders.matchPhrasePrefixQuery(keyVal, values.get(0));
			break;
		}
		case IConstants.OPR_CONTAINS: {
			//retQueryBuilder = QueryBuilders.queryFilter(regexpQuery(keyVal, "*" + values.get(0) + "*")); // (keyVal,
																											// values.get(0));
			//retQueryBuilder = QueryBuilders.regexpQuery(keyVal, ".*" + values.get(0) + ".*");
			//retQueryBuilder = QueryBuilders.regexpQuery(keyVal, ".*ccdAathalogy.*");
			//retQueryBuilder = QueryBuilders.termQuery(keyVal, "*" + values.get(0) + "*");

			break;
		}
		default: {
			break;
		}

		}
		return retQueryBuilder;
		// return retMatchBuilder;
	}

}
