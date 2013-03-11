package com.ccesun.framework.core.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Page<T> implements Iterable<T>, Serializable {

	private static final long serialVersionUID = 867755909294344406L;

	private final List<T> content = new ArrayList<T>();
	private final PageRequest pageRequest;
	private final long totalCount;


	public Page(List<T> content, PageRequest pageRequest, long totalCount) {

		this.setContent(content);
		this.totalCount = totalCount;
		this.pageRequest = pageRequest;
	}

	
	public Page(List<T> content) {

		this(content, null, (null == content) ? 0 : content.size());
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}


	public int getPageNo() {

		return pageRequest == null ? 1 : pageRequest.getPageNo();
	}


	public int getPageSize() {

		return pageRequest == null ? 0 : pageRequest.getPageSize();
	}


	public int getTotalPages() {

		return getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) getPageSize());
	}


	public int getContentSize() {

		return content.size();
	}


	public long getTotalCount() {

		return totalCount;
	}


	public boolean hasPreviousPage() {

		return getPageNo() > 1;
	}


	public boolean isFirstPage() {

		return !hasPreviousPage();
	}


	public boolean hasNextPage() {

		return (getPageNo() * getPageSize()) < totalCount;
	}


	public boolean isLastPage() {

		return !hasNextPage();
	}


	public Iterator<T> iterator() {

		return content.iterator();
	}

	public void setContent(List<T> content) {
		
		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}
		
		this.content.clear();
		this.content.addAll(content);
		
	}

	public List<T> getContent() {

		return Collections.unmodifiableList(content);
	}


	public boolean hasContent() {

		return !content.isEmpty();
	}

	@Override
	public String toString() {

		String contentType = "UNKNOWN";

		if (content.size() > 0) {
			contentType = content.get(0).getClass().getName();
		}

		return String.format("Page %s of %d containing %s instances",
				getPageNo(), getTotalPages(), contentType);
	}


	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Page<?>)) {
			return false;
		}

		Page<?> that = (Page<?>) obj;

		boolean totalEqual = this.totalCount == that.totalCount;
		boolean contentEqual = this.content.equals(that.content);
		boolean pageableEqual = this.pageRequest == null ? that.pageRequest == null : this.pageRequest.equals(that.pageRequest);

		return totalEqual && contentEqual && pageableEqual;
	}


	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + (int) (totalCount ^ totalCount >>> 32);
		result = 31 * result + (pageRequest == null ? 0 : pageRequest.hashCode());
		result = 31 * result + content.hashCode();

		return result;
	}
}
