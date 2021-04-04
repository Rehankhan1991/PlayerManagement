package com.example.game.GameManagement.model;

import org.springframework.data.domain.Sort;

public class PlayerPage {
	
	private Integer pageNumber=0;
	private Integer pageSize=10;
	private Sort.Direction sort= Sort.Direction.ASC;
	private String sortBy = "player";
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Sort.Direction getSort() {
		return sort;
	}
	public void setSort(Sort.Direction sort) {
		this.sort = sort;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	

}
