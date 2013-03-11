package com.ccesun.framework.core.web.taglib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.web.PagerAttributes;
import com.ccesun.framework.util.StringUtils;

public class PagerTag extends TagSupport {

    private static final long serialVersionUID = -7143075554439591255L;

    private Integer indexSize = 7;

    private Page<?> page;

    private String url;
    
    private String var;

    public void setIndexSize(Integer indexSize) {
        this.indexSize = indexSize;
    }

    public void setPage(Page<?> page) {
		this.page = page;
	}
    
    public void setVar(String var) {
		this.var = var;
	}

    @SuppressWarnings("unchecked")
	public void setUrl(String url) {
    	StringBuffer paramSb = new StringBuffer();
        Enumeration<String> eu = pageContext.getRequest().getParameterNames();

        boolean firstTime = true;
        while (eu.hasMoreElements()) {
            String paramName = (String) eu.nextElement();
            String paramValue = pageContext.getRequest().getParameter(paramName);
            if (!"pageNo".equals(paramName)) {

                if (!firstTime) {
                    paramSb.append("&");
                }

                try {
                    paramSb.append(paramName + "=" + URLEncoder.encode(paramValue, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }

            firstTime = false;
        }
        
        
        this.url = (paramSb.length() > 0) ? url + "?" + paramSb.toString() : url;
    }

    //------------------------------------------- override methods

    public int doStartTag() throws JspException {

    	int pageNo = page.getPageNo();
        int totalPages = page.getTotalPages();
        
        int minIndexNo = pageNo - (indexSize / 2) >= 1 ? pageNo - (indexSize / 2) : 1;
        int maxIndexNo = (indexSize + minIndexNo - 1) >= totalPages ? totalPages : (indexSize + minIndexNo - 1);
        maxIndexNo = Math.min(totalPages, maxIndexNo);
        
        String separator = this.url.indexOf("?") == -1 ? "?" : "&";
        String firstUrl = this.url.concat(separator).concat("pageNo=1");
        String previousUrl = this.url.concat(separator).concat("pageNo=" + (pageNo - 1));
        String nextUrl = this.url.concat(separator).concat("pageNo=" + (pageNo + 1));
        String lastUrl = this.url.concat(separator).concat("pageNo=" + totalPages);
        
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
        
		if (StringUtils.isBlank(var)) {
			pageContext.setAttribute("pageNo", pageNo);
	        pageContext.setAttribute("totalCount", page.getTotalCount());
	        pageContext.setAttribute("totalPages", totalPages);
	        pageContext.setAttribute("indexSize", this.indexSize);
	        pageContext.setAttribute("minIndexNo", minIndexNo);
	        pageContext.setAttribute("maxIndexNo", maxIndexNo);
	        pageContext.setAttribute("url", this.url);
	        pageContext.setAttribute("firstUrl", firstUrl);
	        pageContext.setAttribute("previousUrl", previousUrl);
	        pageContext.setAttribute("nextUrl", nextUrl);
	        pageContext.setAttribute("lastUrl", lastUrl);
	        pageContext.setAttribute("isFirstAvailable", isFirstAvailable);
	        pageContext.setAttribute("isPreviousAvailable", isPreviousAvailable);
	        pageContext.setAttribute("isNextAvailable", isNextAvailable);
	        pageContext.setAttribute("isLastAvailable", isLastAvailable);
	        pageContext.setAttribute("sp", separator);
		} else {
			pageContext.setAttribute(var, pagerAttributes);
		}
        
        return SKIP_BODY;
    }

}

