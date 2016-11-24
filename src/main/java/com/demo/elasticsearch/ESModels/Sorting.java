package com.demo.elasticsearch.ESModels;

public class Sorting {
	String field;
	String sortOrder;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "Sorting [field=" + field + ", sortOrder=" + sortOrder + "]";
	}

	

}
