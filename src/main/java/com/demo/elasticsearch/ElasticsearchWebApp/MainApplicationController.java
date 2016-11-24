package com.demo.elasticsearch.ElasticsearchWebApp; 

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.elasticsearch.ESBusiness.ESBusiness;
import com.demo.elasticsearch.ESEntities.Task;
import com.demo.elasticsearch.ESModels.FilterCriteriaModel;
import com.demo.elasticsearch.ESService.TaskService;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.demo.elasticsearch")
@EnableElasticsearchRepositories(basePackages = "com.demo.elasticsearch.ESRepo")
@RequestMapping("/healthcare")
public class MainApplicationController implements CommandLineRunner{

	private static final Log LOG = LogFactory.getLog(MainApplicationController.class);

	@Autowired
	private TaskService taskService;
	
	@Autowired 
	private ESBusiness esBusiness;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION!!");
		SpringApplication.run(MainApplicationController.class, args);
	}
	
	@RequestMapping("/findAllTasks")
	@ResponseBody
    List<Task> findAllTasks() {	
		return taskService.findAllTasks();         
    }
	
	@RequestMapping(value="/deleteAllTasks",method=RequestMethod.DELETE)
	@ResponseBody
    String deleteAllTasks() {	
		taskService.deleteAllTask();
		return "Successfully Deleted all tasks";		
    }
	
	@RequestMapping(value="/saveTask",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    Task saveTask(@RequestBody Task task) {	
		return taskService.saveTask(task);         
    }
	
	@RequestMapping(value="/updateTask",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    String updateTask(@RequestBody Task task) {	
		taskService.updateTask(task);  
		return "Successfully Updated the task";	
    }
	
	@RequestMapping(value="/deleteTask",method=RequestMethod.DELETE)
	@ResponseBody
    String deleteTask(@RequestParam("id") String taskId) {	
		taskService.deleteTask(taskId);  
		return "Successfully Deleted the task";
    }
	
	@RequestMapping(value="/getPageRequest")
	@ResponseBody
	List<Task>  getPageRequest(@RequestParam("taskName") String taskName,@RequestParam("pageIdx") Integer pageIdx,@RequestParam("recordCount") Integer recordCount) {
		System.out.println("Task Name " + taskName);
		System.out.println("pageIdx " + pageIdx);
		System.out.println("recordCount " + recordCount);
		Page<Task> pageTasks=taskService.findByTagsName(taskName, new PageRequest(pageIdx,recordCount));  
		return pageTasks.getContent();
    }
	
	@RequestMapping(value="/getTaskWithDynamicFilter",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    String getTaskWithDynamicFilter(@RequestParam("indexName") String indexName,@RequestParam("typeName") String typeName,@RequestBody FilterCriteriaModel filterCriteriaModel) {	
		//taskService.updateTask(task);  
		//System.out.println("indexName>>>"+ indexName);
		//System.out.println("typeName>>>"+ typeName);
		//System.out.println(filterCriteriaModel.getFilters().get(0).getField());		
		//getDataFromES(filterCriteriaModel);
		return esBusiness.getDataFromES(filterCriteriaModel,indexName,typeName);	
    }
	
	
	@RequestMapping(value="/testSave")
	@ResponseBody
    Task saveTask() {
		Task task=new Task();
		//task.setTaskId("100");
		task.setTaskId(100);
		task.setTaskAssignedToRole("Test Role");
		task.setTaskDueDate("2015/12/12");
		task.setTaskHealthGoal("Admitted");
		task.setTaskStatus("Diagnosis");
		task.setTaskSubStatus("Pathalogy");
		task.setTaskTitle("Diabatic");
		return taskService.saveTask(task);         
    }

	@Override
	public void run(String... arg0) throws Exception {
		//taskService.deleteAllTask();		
	}
	
	
	
	
	

}
