package com.demo.elasticsearch.ESService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.demo.elasticsearch.ESEntities.Task;


public interface TaskService {

	public List<Task> findAllTasks();

	public Task saveTask(Task task);

	public void deleteTask(String taskId);
	
	public void deleteAllTask();

	public void updateTask(Task task);

	public Page<Task> findByTagsName(String taskName, PageRequest pageRequest);
}
