package com.demo.elasticsearch.ESEntities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "healthcare", type = "task", shards = 1, replicas = 0, refreshInterval = "-1")
public class Task {

	@Id
	Integer indexId;
	Integer taskId;
	String taskTitle;
	String taskDueDate;
	String taskStatus;
	String taskSubStatus;
	String taskHealthGoal;
	String taskAssignedToRole;

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskSubStatus() {
		return taskSubStatus;
	}

	public void setTaskSubStatus(String taskSubStatus) {
		this.taskSubStatus = taskSubStatus;
	}

	public String getTaskHealthGoal() {
		return taskHealthGoal;
	}

	public void setTaskHealthGoal(String taskHealthGoal) {
		this.taskHealthGoal = taskHealthGoal;
	}

	public String getTaskAssignedToRole() {
		return taskAssignedToRole;
	}

	public void setTaskAssignedToRole(String taskAssignedToRole) {
		this.taskAssignedToRole = taskAssignedToRole;
	}

}
