package com.demo.elasticsearch.ESRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.demo.elasticsearch.ESEntities.Task;

public interface TaskRepository extends ElasticsearchRepository<Task, String> {

	public Task findByTaskTitle(String taskTitle);
	public Task findByTaskId(String taskId);
	public Page<Task> findByTaskStatus(String taskStatus,Pageable pageable);

	

   /* @Query("{\"bool\": {\"must\": [{\"match\": {\"taskId\": \"?0\"}}]}}")
    Page<Task> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);*/
	
}
