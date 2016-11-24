package com.demo.elasticsearch.ESModels;

import java.util.List;

public class Filter {
	String type;
	String field;
	String operation;
	List<String> value;
	
	

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}



	@Override
	public String toString() {
		return "Filter [type=" + type + ", field=" + field + ", operation=" + operation + ", value=" + value +"]";
	}

}
