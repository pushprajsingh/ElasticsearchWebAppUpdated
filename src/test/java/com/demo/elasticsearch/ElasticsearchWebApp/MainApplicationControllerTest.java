package com.demo.elasticsearch.ElasticsearchWebApp;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.demo.elasticsearch.ESEntities.Task;
import com.demo.elasticsearch.ESModels.Filter;
import com.demo.elasticsearch.ESModels.FilterCriteriaModel;
import com.demo.elasticsearch.ESQryBuilder.DemoQueryBuilder;
import com.demo.elasticsearch.ESService.TaskService;
import com.demo.elasticsearch.ESUtils.JSONUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplicationController.class)
public class MainApplicationControllerTest {

	@Autowired
	private TaskService taskService;
/*
	@Autowired
	private TaskService2 taskService2;*/

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Before
	public void before() {

		elasticsearchTemplate.deleteIndex(Task.class);
		elasticsearchTemplate.createIndex(Task.class);
		elasticsearchTemplate.putMapping(Task.class);
		elasticsearchTemplate.refresh(Task.class);

		Task task0 = new Task();
		//task0.setTaskId(String.format("%d", 299));
		task0.setTaskId(299);
		task0.setTaskAssignedToRole("Test Role");
		task0.setTaskDueDate("2016/11/21");
		task0.setTaskHealthGoal("Admitted");
		task0.setTaskStatus("Diagnosis");
		task0.setTaskSubStatus("cedAathalogy");
		task0.setTaskTitle("Diabatic");
		taskService.saveTask(task0);

		Task task = new Task();
		//task.setTaskId(String.format("%d", 200));
		task.setTaskId(200);
		task.setTaskAssignedToRole("Test Role");
		task.setTaskDueDate("2016/11/21");
		task.setTaskHealthGoal("Admitted");
		task.setTaskStatus("Diagnosis");
		task.setTaskSubStatus("Pathalogy");
		task.setTaskTitle("Diabatic");
		taskService.saveTask(task);

		Task task2 = new Task();
		//task2.setTaskId(String.format("%d", 222));
		task2.setTaskId(222);
		task2.setTaskAssignedToRole("Test Role");
		task2.setTaskDueDate("2016/11/21");
		task2.setTaskHealthGoal("Admitted");
		task2.setTaskStatus("Diagnosis111111");
		task2.setTaskSubStatus("Zathalogy");
		task2.setTaskTitle("Diabatic");
		taskService.saveTask(task2);

		Task task3 = new Task();
		//task3.setTaskId(String.format("%d", 244));
		task3.setTaskId( 244);
		task3.setTaskAssignedToRole("Test Role");
		task3.setTaskDueDate("2016/11/21");
		task3.setTaskHealthGoal("Admitted");
		task3.setTaskStatus("Diagnosis");
		task3.setTaskSubStatus("Aathalogy");
		task3.setTaskTitle("Diabatic");
		taskService.saveTask(task3);

		Task task4 = new Task();
		//task4.setTaskId(String.format("%d", 266));
		task4.setTaskId(266);
		task4.setTaskAssignedToRole("Test Role");
		task4.setTaskDueDate("2016/11/21");
		task4.setTaskHealthGoal("Admitted");
		task4.setTaskStatus("Diagnosis-222");
		task4.setTaskSubStatus("123Aathalogy");
		task4.setTaskTitle("Diabatic");
		taskService.saveTask(task4);

		for (int l = 0; l < 20; l++) {
			Task taskt = new Task();
			String text = String.format("%d", (l * 10) + 300);
			taskt.setTaskId((l * 10) + 300);
			taskt.setTaskAssignedToRole("Test Role");
			taskt.setTaskDueDate("2016/11/21");
			taskt.setTaskHealthGoal("Admitted");
			taskt.setTaskStatus("Diagnosis");
			taskt.setTaskSubStatus(text + "ccdAathalogy");
			taskt.setTaskTitle("Diabatic");
			taskService.saveTask(taskt);
		}

	}

	@Test
	public void testSave() throws Exception {
		for (int i = 100; i < 120; i++) {
			Task task = new Task();
			//task.setTaskId(String.format("%d", i));
			task.setTaskId(i);
			task.setTaskAssignedToRole("Test Role");
			task.setTaskDueDate("2016/11/23");
			task.setTaskHealthGoal("Admitted");
			task.setTaskStatus("Diagnosis");
			task.setTaskSubStatus("Pathalogy");
			task.setTaskTitle("Diabatic");
			taskService.saveTask(task);
		}
	}

	@Test
	public void testSave2() throws Exception {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(regexpQuery("taskId", ".*2.*")).build();

		for (Task aa : elasticsearchTemplate.queryForList(searchQuery, Task.class)) {
			System.out.println("Id selected is :" + aa.getTaskId());
		}

	}

	@Test
	public void testSav2_sort() throws Exception {

		System.out.println("Original Order!!!");
		for (Task orgTask : taskService.findAllTasks()) {
			System.out.println("Id  :" + orgTask.getTaskId() + "  TaskSubStatus : " + orgTask.getTaskSubStatus());
		}

		System.out.println("Sorted Order!!!");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(regexpQuery("taskId", ".*2.*"))
				.withSort(new FieldSortBuilder("taskSubStatus").order(SortOrder.ASC)).build();
		int size = elasticsearchTemplate.queryForList(searchQuery, Task.class).size();
		for (Task aa : elasticsearchTemplate.queryForList(searchQuery, Task.class).subList(0, size)) {
			System.out.println("Id  :" + aa.getTaskId() + "  TaskSubStatus : " + aa.getTaskSubStatus());

		}
	}

	@Test
	public void testSav2_sort_and_Pagable() throws Exception {

		long StartTime = System.currentTimeMillis();
		System.out.println("Original Order!!!");
		for (Task orgTask : taskService.findAllTasks()) {
			System.out.println("Id  :" + orgTask.getTaskId() + "  TaskSubStatus : " + orgTask.getTaskSubStatus());
		}

		StartTime = System.currentTimeMillis();
		System.out.println("Sorted Order!!!");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(regexpQuery("taskId", ".*2.*"))
				.withSort(new FieldSortBuilder("taskSubStatus").order(SortOrder.ASC))
				.withPageable(new PageRequest(0, 15)).build();
		// SearchQuery searchQuery = new
		// NativeSearchQueryBuilder().withFilter(regexpQuery("taskSubStatus",
		// "Z.*")).withSort(new
		// FieldSortBuilder("taskSubStatus").order(SortOrder.ASC)).withPageable(new
		// PageRequest(0,15)).build();
		int size = elasticsearchTemplate.queryForList(searchQuery, Task.class).size();
		for (Task aa : elasticsearchTemplate.queryForList(searchQuery, Task.class).subList(0, size)) {
			System.out.println("Id  :" + aa.getTaskId() + "  TaskSubStatus : " + aa.getTaskSubStatus());

		}
		System.out.println(System.currentTimeMillis() - StartTime + " mSec");
	}

	@Test
	public void testSave3() {

		final String taskStatus = "Diagnosis111111";
		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("taskStatus", taskStatus))
				.build();
		for (Task aa : elasticsearchTemplate.queryForList(searchQuery, Task.class)) {
			System.out.println("Id selected is :" + aa.getTaskId());

		}

	}

	@Test
	public void sampleTest3() {
		DemoQueryBuilder testclass = new DemoQueryBuilder();
		/*String jsonData="{\"filters\":["
		+ "{\"type\":\"text\",\"field\":\"name\",\"operation\":\"StartWith\",\"value\":[\"TEST\"]},"
		+ "{\"type\":\"numeric\",\"field\":\"taskId\",\"operation\":\"Between\",\"value\":[\"240\",\"270\"]},"
		+ "{\"type\":\"numeric\",\"field\":\"taskId\",\"operation\":\"Between\",\"value\":[\"200\",\"225\"]},"
		+ "{\"type\":\"date\",\"field\":\"startDate\",\"operation\":\"LessThan\",\"value\":[\"2016-11-22\"]}],"
		+ "\"sorting\":{\"sortOrder\":\"ASC\"},"
		+ "\"pagination\":{\"pageId\":0,\"recordsPerPage\":10}}";*/

		String jsonData="{\"filters\":["
		+ "{\"type\":\"numeric\",\"field\":\"taskId\",\"operation\":\"Equals\",\"value\":[\"200\",\"225\"]},"
		+ "{\"type\":\"numeric\",\"field\":\"taskId\",\"operation\":\"Equals\",\"value\":[\"267\",\"270\"]}],"
		+ "\"sorting\":{\"sortOrder\":\"ASC\"},"
		+ "\"pagination\":{\"pageId\":0,\"recordsPerPage\":10}}";
		
		/*String jsonData="{\"filters\":["
		+ "{\"type\":\"date\",\"field\":\"taskDueDate\",\"operation\":\"Equals\",\"value\":[\"2016/11/23\"]}],"
		+ "\"sorting\":{\"sortOrder\":\"ASC\"},"
		+ "\"pagination\":{\"pageId\":0,\"recordsPerPage\":10}}";*/
		
/*		String jsonData="{\"filters\":["
				+ "{\"type\":\"text\",\"field\":\"taskStatus\",\"operation\":\"StartWith\",\"value\":[\"Diagnosis-222\"]}],"
				+ "\"sorting\":{\"sortOrder\":\"ASC\"},"
				+ "\"pagination\":{\"pageId\":0,\"recordsPerPage\":10}}";*/
		
		FilterCriteriaModel criteriaModel = null;
		try {
			criteriaModel = JSONUtils.getParseFilterCriteriaData(jsonData);


			//List<QueryBuilder> qblist = new ArrayList<QueryBuilder>();

			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			for (Filter f : criteriaModel.getFilters()) {
				
				switch(f.getType())
				{
					case "text":
					{
						System.out.println("In Text Type");
						boolQuery.should(testclass.generateTextCriteria(f));
						break;
					}
					case "numeric":
					{
						System.out.println("In numeric Type");
						boolQuery.should(testclass.generateNumericCriteria(f));
						break;
					}
					case "date":
					{
						System.out.println("In Date Type");
						boolQuery.should(testclass.generateDateCriteria(f));
						break;
					}
					default:
					{
						break;
					}
				}
				
				
				
				
			}
			
			int sortOrd=("ASC".equalsIgnoreCase(criteriaModel.getSorting().getSortOrder()))?1:0;

			Settings settings = Settings.settingsBuilder().put("cluster.name", "my-application").build();
			Client client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
			
			SearchResponse searchResponse = client.prepareSearch("healthcare").setTypes("task").setQuery(boolQuery)
					.execute().actionGet();

			System.out.println("Number of Records selected are :"+searchResponse.getHits().hits().length);
			for (SearchHit hit : searchResponse.getHits().getHits()) {
				Map<String, Object> values = hit.getSource();
				//System.out.println(values.toString());
				ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
				String json = mapper.writeValueAsString(values);
				System.out.println(json);
				Task obj = JSONUtils.getParseTaskData(json);
				System.out.println(obj.getTaskId());

			}
		} catch (JsonGenerationException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	
	@Test
	public void othertest_1() throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().put("cluster.name", "my-application").build();
		Client client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("127.0.0.1", 9300)));
		TransportClient client1 = TransportClient.builder().build();
		try {
			client1.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Client client =
		// TransportClient.builder().build().addTransportAddress(new
		// InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),
		// 9300));
		SearchResponse searchResponse = client.prepareSearch("healthcare").setTypes("task2")
				.setQuery(QueryBuilders.matchQuery("taskId", "340")).execute().actionGet();

		System.out.println(searchResponse.getHits().hits().length);
		for (SearchHit hit : searchResponse.getHits().getHits()) {

			Map<String, Object> values = hit.getSource();
			// Long id = hit.field("taskId").<Long>getValue();
			System.out.println(values.toString());

		}

	}

	@Test
	public void testSave4() {

		final String taskStatus = "Diagnosis";
		final SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("taskStatus", taskStatus).minimumShouldMatch("75%")).build();
		for (Task aa : elasticsearchTemplate.queryForList(searchQuery, Task.class)) {
			System.out.println("Id selected is :" + aa.getTaskId());
		}

	}

}
