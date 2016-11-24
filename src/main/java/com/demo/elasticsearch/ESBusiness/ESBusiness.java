package com.demo.elasticsearch.ESBusiness;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.AndQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import com.demo.elasticsearch.ESEntities.Task;
import com.demo.elasticsearch.ESModels.Filter;
import com.demo.elasticsearch.ESModels.FilterCriteriaModel;
import com.demo.elasticsearch.ESQryBuilder.DemoQueryBuilder;
import com.demo.elasticsearch.ESUtils.IConstants;
import com.demo.elasticsearch.ESUtils.JSONUtils;

@Component
public class ESBusiness {

	public String getDataFromES(FilterCriteriaModel filterCriteriaModel, String indexName, String typeName) {
		DemoQueryBuilder testclass = new DemoQueryBuilder();
		String retString = "";

		FilterCriteriaModel criteriaModel = filterCriteriaModel;
		try {
			//BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			
			AndQueryBuilder andQueryBuilder = new AndQueryBuilder();
			
			for (Filter f : criteriaModel.getFilters()) {

				switch (f.getType()) {
				case IConstants.DATATYPE_TEXT: {
					System.out.println("In Text Type");
					//boolQuery.should(testclass.generateTextCriteria(f));
					andQueryBuilder.add(testclass.generateTextCriteria(f));
					break;
				}
				case IConstants.DATATYPE_NUMERIC: {
					System.out.println("In numeric Type");
					//boolQuery.should(testclass.generateNumericCriteria(f));
					andQueryBuilder.add(testclass.generateNumericCriteria(f));
					break;
				}
				case IConstants.DATATYPE_DATE: {
					System.out.println("In Date Type");
					//boolQuery.should(testclass.generateDateCriteria(f));
					andQueryBuilder.add(testclass.generateDateCriteria(f));
					break;
				}
				default: {
					break;
				}
				}

			}

			//int sortOrd = ("ASC".equalsIgnoreCase(criteriaModel.getSorting().getSortOrder())) ? 1 : 0;

			Settings settings = Settings.settingsBuilder().put("cluster.name", "my-application").build();
			Client client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("127.0.0.1", 9300)));

			String sortField="Id";
			String sortType="ASC";
			if(filterCriteriaModel.getSorting()!=null)
			{
			sortField=filterCriteriaModel.getSorting().getField();
			sortType=filterCriteriaModel.getSorting().getSortOrder();
			System.out.println("Sorting Filed >>"+sortField);
			System.out.println("Sorting Type  >>"+sortType);
			}
			
			Integer fromIndex=filterCriteriaModel.getPagination().getPageId();
			Integer recordCount=filterCriteriaModel.getPagination().getRecordsPerPage();
			
			
			if(fromIndex==null || fromIndex<0)
			{
				fromIndex=0;
			}
			
			if(recordCount==null || recordCount<0)
			{
				recordCount=10;
			}								
			
			
			SearchResponse searchResponse = client.prepareSearch(indexName).setTypes(typeName).setQuery(andQueryBuilder)
					.addSort(sortField,("DESC".equalsIgnoreCase(sortType)?SortOrder.DESC:SortOrder.ASC))
					.setFrom(fromIndex*recordCount)
					.setSize(recordCount)
					.execute().actionGet();
			
			/*SearchResponse searchResponse = client.prepareSearch(indexName).setTypes(typeName).setQuery(boolQuery)
					.addSort(sortField,("DESC".equalsIgnoreCase(sortType)?SortOrder.DESC:SortOrder.ASC))
					.setFrom(fromIndex*recordCount)
					.setSize(recordCount)
					.execute().actionGet();*/

			System.out.println("Number of Records selected are :" + searchResponse.getHits().hits().length);
			
			for (SearchHit hit : searchResponse.getHits().getHits()) {
				Map<String, Object> values = hit.getSource();
				ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(values);
				System.out.println(json);
				Task obj = JSONUtils.getParseTaskData(json);
				System.out.println(obj.getTaskId());
				retString += " " + json;

			}
		} catch (JsonGenerationException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return retString;
	}
}
