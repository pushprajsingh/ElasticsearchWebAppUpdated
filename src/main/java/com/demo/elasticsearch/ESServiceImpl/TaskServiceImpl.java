package com.demo.elasticsearch.ESServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.demo.elasticsearch.ESEntities.Task;
import com.demo.elasticsearch.ESRepo.TaskRepository;
import com.demo.elasticsearch.ESService.TaskService;


@Service
public class TaskServiceImpl implements TaskService {

	private static final Log LOG = LogFactory.getLog(TaskServiceImpl.class);
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> findAllTasks() {
		LOG.info("In Get All Tasks");
		List<Task> retTasks = new ArrayList<Task>();
		Iterable<Task> tasks = taskRepository.findAll();
		for (Task t : tasks) {
			retTasks.add(t);
		}
		return retTasks;
	}

	@Override
	public Task saveTask(Task task) {
		LOG.info("In save task");
		Task retTask = taskRepository.save(task);
		return retTask;
	}

	@Override
	public void deleteTask(String taskId) {
		LOG.info("In delete task");
		Task deleteTask = taskRepository.findByTaskId(taskId);
		
		System.out.println(deleteTask.getTaskId() + " will be deleted.");
		taskRepository.delete(deleteTask);

	}

	@Override
	public void updateTask(Task task) {
		LOG.info("In update task");
		taskRepository.delete(task);
		Task updateTask = null;
		taskRepository.save(updateTask);
	}

	@Override
	public Page<Task> findByTagsName(String taskName, PageRequest pageRequest) {
		LOG.info("In findByTagsName");		
		return taskRepository.findByTaskStatus(taskName, pageRequest);
		
	}

	@Override
	public void deleteAllTask() {
		LOG.info("In delete all tasks");
		taskRepository.deleteAll();
	}

}
