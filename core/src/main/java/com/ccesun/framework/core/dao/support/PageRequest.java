package com.ccesun.framework.core.dao.support;

import java.io.Serializable;

public class PageRequest implements Serializable {

	private static final long serialVersionUID = 8280485938848398236L;

	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_SIZE = 10;
	
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	
	private int pageNo;
	private int pageSize;
	
	private Sort sort;

	public PageRequest() {

		this(DEFAULT_PAGE, DEFAULT_SIZE);
	}
	
	public PageRequest(int pageNo, int pageSize) {

		this(pageNo, pageSize, null);
	}

	public PageRequest(int pageNo, int pageSize, Sort sort) {

		if (1 > pageNo) {
			throw new IllegalArgumentException(
					"Page index must not be less than one!");
		}

		if (0 >= pageSize) {
			throw new IllegalArgumentException(
					"Page size must not be less than or equal to zero!");
		}

		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sort = sort;
	}

	public int getPageSize() {

		return pageSize;
	}

	public int getPageNo() {

		return pageNo;
	}

	public int getOffset() {

		return (pageNo - 1) * pageSize;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

		
}
