package com.ccesun.framework.core.dao.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ccesun.framework.util.StringUtils;

public class SearchForm implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public static final String OP_LIKE_SUFFIX = "_lk";
	public static final String OP_BOTH_LIKE_SUFFIX = "_blk";
	public static final String OP_EQ_SUFFIX = "_eq";
	public static final String OP_NOT_EQ_SUFFIX = "_neq";
	public static final String OP_GT_SUFFIX = "_gt";
	public static final String OP_LT_SUFFIX = "_lt";
	public static final String OP_EQ_AND_GT_SUFFIX = "_egt";
	public static final String OP_EQ_AND_LT_SUFFIX = "_elt";

	/**
	 * 用来描述in操作的后缀,需要和value进行配合,来截取出具体的 value值, 默认的字符串的split是';'
	 * 
	 * @author mawm at 2013-3-19 下午12:21:22
	 * @see TYPE_SPLITTXT
	 */
	public static final String OP_IN_SUFFIX = "_in";

	public static final String TYPE_STRING = "_string";
	public static final String TYPE_INT = "_int";
	public static final String TYPE_DATE = "_date";

	/**
	 * 对 数据进行通过';'进行split截取操作,得到一个字符串数组!
	 * 
	 * @author mawm at 2013-3-19 下午12:22:31
	 */
	public static final String TYPE_SPLITTXT1 = "_split1";
	/**
	 * 对 数据进行通过','进行split截取操作,得到一个字符串数组!
	 * 
	 * @author mawm at 2013-3-19 下午12:22:31
	 */
	public static final String TYPE_SPLITTXT2 = "_split2";
	/**
	 * 对 数据进行通过';'进行split截取操作,得到一个Integer数组!
	 * 
	 * @author mawm at 2013-3-19 下午12:22:31
	 */
	public static final String TYPE_SPLIT_INT1 = "_splitint1";
	/**
	 * 对 数据进行通过','进行split截取操作,得到一个Integer数组!
	 * 
	 * @author mawm at 2013-3-19 下午12:22:31
	 */
	public static final String TYPE_SPLIT_INT2 = "_splitint2";

	public static final String TYPE_DEFAULT = TYPE_STRING;

	private Map<String, String> form = new HashMap<String, String>();

	private PageRequest pageRequest = new PageRequest();

	private String orderBy;
	private String order;

	public Map<String, String> getForm() {
		return form;
	}

	public void setForm(Map<String, String> form) {
		this.form = form;
	}

	public PageRequest getPageRequest() {

		if (!StringUtils.isBlank(orderBy)) {
			if (!StringUtils.isBlank(order)) {
				pageRequest.setSort(new Sort().order(orderBy, order));
			} else {
				pageRequest.setSort(new Sort().asc(orderBy));
			}
		}

		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public void setPageNo(int pageNo) {
		this.pageRequest.setPageNo(pageNo);
	}

	public void setPageSize(int pageSize) {
		this.pageRequest.setPageSize(pageSize);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public SearchForm addFormEntry(String key, String value) {
		form.put(key, value);
		return this;
	}

	public SearchForm clone() {
		SearchForm result = new SearchForm();
		result.setOrderBy(this.getOrderBy() == null ? null : new String(this
				.getOrderBy()));
		result.setOrder(this.getOrder() == null ? null : new String(this
				.getOrder()));

		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNo(getPageRequest().getPageNo());
		pageRequest.setPageSize(getPageRequest().getPageSize());

		result.setPageRequest(pageRequest);
		result.setForm(new HashMap<String, String>(this.getForm()));

		return result;
	}

}
