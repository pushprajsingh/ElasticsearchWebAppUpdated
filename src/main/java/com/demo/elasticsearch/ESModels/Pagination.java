package com.demo.elasticsearch.ESModels;

public class Pagination {

	Integer pageId;
	Integer recordsPerPage;
	
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}


	@Override
	public String toString() {
		return "Pagination [pageId=" + pageId + ", recordsPerPage=" + recordsPerPage + "]";
	}
}
