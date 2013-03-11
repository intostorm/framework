package com.ccesun.framework.core.web;

import com.ccesun.framework.core.dao.support.Page;

public class PagerAttributes {

	private int pageNo;
	private long totalCount;
	private int totalPages;
	private int indexSize;
	private int minIndexNo;
	private int maxIndexNo;
	private String url;
	private String firstUrl;
	private String previousUrl;
	private String nextUrl;
	private String lastUrl;
	private boolean isFirstAvailable;
	private boolean isPreviousAvailable;
	private boolean isNextAvailable;
	private boolean isLastAvailable;
	private String sp;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getIndexSize() {
		return indexSize;
	}
	public void setIndexSize(int indexSize) {
		this.indexSize = indexSize;
	}
	public int getMinIndexNo() {
		return minIndexNo;
	}
	public void setMinIndexNo(int minIndexNo) {
		this.minIndexNo = minIndexNo;
	}
	public int getMaxIndexNo() {
		return maxIndexNo;
	}
	public void setMaxIndexNo(int maxIndexNo) {
		this.maxIndexNo = maxIndexNo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFirstUrl() {
		return firstUrl;
	}
	public void setFirstUrl(String firstUrl) {
		this.firstUrl = firstUrl;
	}
	public String getPreviousUrl() {
		return previousUrl;
	}
	public void setPreviousUrl(String previousUrl) {
		this.previousUrl = previousUrl;
	}
	public String getNextUrl() {
		return nextUrl;
	}
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	public String getLastUrl() {
		return lastUrl;
	}
	public void setLastUrl(String lastUrl) {
		this.lastUrl = lastUrl;
	}
	public boolean isFirstAvailable() {
		return isFirstAvailable;
	}
	public void setFirstAvailable(boolean isFirstAvailable) {
		this.isFirstAvailable = isFirstAvailable;
	}
	public boolean isPreviousAvailable() {
		return isPreviousAvailable;
	}
	public void setPreviousAvailable(boolean isPreviousAvailable) {
		this.isPreviousAvailable = isPreviousAvailable;
	}
	public boolean isNextAvailable() {
		return isNextAvailable;
	}
	public void setNextAvailable(boolean isNextAvailable) {
		this.isNextAvailable = isNextAvailable;
	}
	public boolean isLastAvailable() {
		return isLastAvailable;
	}
	public void setLastAvailable(boolean isLastAvailable) {
		this.isLastAvailable = isLastAvailable;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	
	public static PagerAttributes parse(Page<?> page, String url, int indexSize) {
		int pageNo = page.getPageNo();
        int totalPages = page.getTotalPages();
        
        int minIndexNo = pageNo - (indexSize / 2) >= 1 ? pageNo - (indexSize / 2) : 1;
        int maxIndexNo = (indexSize + minIndexNo - 1) >= totalPages ? totalPages : (indexSize + minIndexNo - 1);
        maxIndexNo = Math.min(totalPages, maxIndexNo);
        
        String separator = url.indexOf("?") == -1 ? "?" : "&";
        String firstUrl = url.concat(separator).concat("pageNo=1");
        String previousUrl = url.concat(separator).concat("pageNo=" + (pageNo - 1));
        String nextUrl = url.concat(separator).concat("pageNo=" + (pageNo + 1));
        String lastUrl = url.concat(separator).concat("pageNo=" + totalPages);
        
        boolean isFirstAvailable = pageNo > 1 && pageNo <= totalPages;
        boolean isPreviousAvailable = pageNo > 1 && pageNo <= totalPages;
        boolean isNextAvailable = pageNo >= 1 && pageNo < totalPages;
        boolean isLastAvailable = pageNo >= 1 && pageNo < totalPages;
        
        PagerAttributes pagerAttributes = new PagerAttributes();
        pagerAttributes.setPageNo(pageNo);
        pagerAttributes.setTotalCount(page.getTotalCount());
        pagerAttributes.setIndexSize(indexSize);
        pagerAttributes.setMinIndexNo(minIndexNo);
        pagerAttributes.setMaxIndexNo(maxIndexNo);
        pagerAttributes.setUrl(url);
        pagerAttributes.setFirstUrl(firstUrl);
        pagerAttributes.setPreviousUrl(previousUrl);
        pagerAttributes.setNextUrl(nextUrl);
        pagerAttributes.setLastUrl(lastUrl);
        pagerAttributes.setFirstAvailable(isFirstAvailable);
        pagerAttributes.setPreviousAvailable(isPreviousAvailable);
        pagerAttributes.setNextAvailable(isNextAvailable);
        pagerAttributes.setLastAvailable(isLastAvailable);
        pagerAttributes.setSp(separator);
        
        return pagerAttributes;
	}
	
}
