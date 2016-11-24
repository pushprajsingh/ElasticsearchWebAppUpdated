package com.demo.elasticsearch.ElasticsearchWebApp;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.demo.elasticsearch.ESModels.Filter;
import com.demo.elasticsearch.ESModels.FilterCriteriaModel;
import com.demo.elasticsearch.ESUtils.JSONUtils;

public class TestQB {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

		System.out.println("TEST !!!");
		
		String jsonData="{\"filters\":["
				+ "{\"type\":\"text\",\"field\":\"name\",\"operation\":\"StartWith\",\"value\":[\"TEST\"]},"
				+ "{\"type\":\"numeric\",\"field\":\"id\",\"operation\":\"LessThanEqual\",\"value\":[\"10\",\"20\"]},"
				+ "{\"type\":\"date\",\"field\":\"startDate\",\"operation\":\"LessThan\",\"value\":[\"2016-11-22\"]}],"
				+ "\"sorting\":{\"sortOrder\":\"ASC\"},"
				+ "\"pagination\":{\"pageId\":0,\"recordsPerPage\":10}}";
		FilterCriteriaModel criteriaModel=JSONUtils.getParseFilterCriteriaData(jsonData);
		
		MatchQueryBuilder finalMatchQueryBuilder;
		
		for(Filter f:criteriaModel.getFilters())
		{
			System.out.println(f.toString());
			
			// Check the type 
			switch(f.getType())
			{
				case "text":
				{
					System.out.println("In Text Type");
					break;
				}
				case "numeric":
				{
					System.out.println("In numeric Type");
					//generateNumericCriteria(f);
					final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(generateNumericCriteria(f)).build();
			        System.out.println("TEST!!"+searchQuery.toString());
					break;
				}
				case "date":
				{
					System.out.println("In Date Type");
					break;
				}
				default:
				{
					break;
				}
			}
			
		}
		
		System.out.println(criteriaModel.getSorting().getSortOrder());
		System.out.println(criteriaModel.getPagination().toString());
		
		testSave4();
		

	

	}
	
	 public static void testSave4() {

	        final String taskStatus = "Diagnosis";
	       final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("taskStatus", taskStatus).minimumShouldMatch("75%")).build();
	        //final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(generateNumericCriteria(f)).build();
	        System.out.println("TEST!!");
	    }
	
	
	
	public static QueryBuilder generateNumericCriteria(Filter f)
	{
		QueryBuilder retQueryBuilder=null;
		/*
		Type : "Numeric",
		Field : "Id",
		Operation: ["Equals","LessThan","GreaterThan","LessThanEqual","GreaterThanEqual","Between"],
		Value : [val1,val2]
		 */
		
		String keyVal=f.getField();
		List<String> values=f.getValue();
		
		switch(f.getOperation())
		{
		case "Equals":
		{
			System.out.println("In Numeric Equals");
			retQueryBuilder=QueryBuilders.matchQuery(keyVal, values.get(0)); 
			break;
		}
		case "LessThan":
		{
			System.out.println("In Numeric LessThan");
			retQueryBuilder=QueryBuilders.rangeQuery(keyVal).lt(values.get(0));
			break;
		}
		case "GreaterThan":
		{
			System.out.println("In Numeric GreaterThan");
			retQueryBuilder=QueryBuilders.rangeQuery(keyVal).gt(values.get(0));
			break;
		}
		case "LessThanEqual":
		{
			System.out.println("In Numeric LessThanEqual");
			retQueryBuilder=QueryBuilders.rangeQuery(keyVal).lte(values.get(0));
			break;
		}
		case "GreaterThanEqual":
		{
			System.out.println("In Numeric GreaterThanEqual");
			retQueryBuilder=QueryBuilders.rangeQuery(keyVal).gte(values.get(0));
			break;
		}
		case "Between":
		{
			System.out.println("In Numeric Between");
			retQueryBuilder=QueryBuilders.rangeQuery(keyVal).gte(values.get(0)).lte(values.get(1));
			break;
		}
		default:
		{
			break;
		}
		
		}
	
		return retQueryBuilder;
		//return retMatchBuilder;
	}
	
	
	
	public static QueryBuilder generateDateCriteria(Filter f)
	{
		QueryBuilder retQueryBuilder=null;
		//MatchQueryBuilder retMatchBuilder=null;
		//QueryBuilders.rangeQuery(f.getField()).from(f.getType())
		
		/*
		 	Type  : "Date",
			Field : "StartDate",
			Operation: ["Equals","LessThan","GreaterThan","LessThanEqual","GreaterThanEqual"]
			Value : 
		 */
		switch(f.getOperation())
		{
		case "Equals":
		{
			System.out.println("In Date Equals");
			break;
		}
		case "LessThan":
		{
			System.out.println("In Date LessThan");
			break;
		}
		case "GreaterThan":
		{
			System.out.println("In Date GreaterThan");
			break;
		}
		case "LessThanEqual":
		{
			System.out.println("In Date LessThanEqual");
			break;
		}
		case "GreaterThanEqual":
		{
			System.out.println("In Date GreaterThanEqual");
			break;
		}
		default:
		{
			break;
		}
		
		}
		return retQueryBuilder;
		//return retMatchBuilder;
	}
	
	
	
	
	
	public static QueryBuilder generateTextCriteria(Filter f)
	{
		QueryBuilder retQueryBuilder=null;
		//MatchQueryBuilder retMatchBuilder=null;
		//QueryBuilders.rangeQuery(f.getField()).from(f.getType())
		
		/*
		Text:
		Type : "Text",
		Field : "Name",
		Operation: ["Equals","StartWith","Contains"]
		Value : [text1,text2]
		 */
		switch(f.getOperation())
		{
		case "Equals":
		{
			System.out.println("In Text Equals");
			break;
		}
		case "StartWith":
		{
			System.out.println("In Text StartWith");
			break;
		}
		case "Contains":
		{
			System.out.println("In Text Contains");
			break;
		}		
		default:
		{
			break;
		}
		
		}
		return retQueryBuilder;
		//return retMatchBuilder;
	}
	
	

}
