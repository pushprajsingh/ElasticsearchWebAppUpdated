package com.demo.elasticsearch.ESModels;

import java.util.List;

public class FilterCriteriaModel {

	List<Filter> filters;
	Sorting sorting;
	Pagination pagination;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Sorting getSorting() {
		return sorting;
	}

	public void setSorting(Sorting sorting) {
		this.sorting = sorting;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	@Override
	public String toString() {
		return "FilterCriteriaModel [filters=" + filters + ", sorting=" + sorting + ", pagination=" + pagination + "]";
	}
}
