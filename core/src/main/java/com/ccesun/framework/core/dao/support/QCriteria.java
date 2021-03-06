package com.ccesun.framework.core.dao.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ccesun.framework.util.DateUtils;
import com.ccesun.framework.util.NumberUtils;
import com.ccesun.framework.util.StringUtils;

/**
 * 用于封装查询条件与参数，作为泛型Dao部分方法的参数，
 * <p>
 * 示例：
 * 
 * <p>
 * Foo类:<br>
 * public class Foo {<br>
 * private String name;<br>
 * private String password;<br>
 * private Bar bar;<br>
 * //省略getter和setter<br>
 * }
 * 
 * <p>
 * public class Bar {<br>
 * private String name;<br>
 * //省略getter和setter<br>
 * }
 * 
 * <p>
 * QCriteria criteria = new QCriteria();<br>
 * criteria<br>
 * .addEntry("name", Op.EQ, "name1")<br>
 * .where("o.password like ? and o.password not like ?", "pass".concat("%"),
 * "password1".concat("%"))<br>
 * .innerFetch("bar"); //内联接Bar属性，可选的联接方式有innerFetch, leftFetch, rightFetch<br>
 * 
 * <p>
 * List<Foo> = simpleDao.find(QCriteria criteria);
 * 
 * @author Jaron
 * 
 */
public class QCriteria {

	private List<ParamEntry> entries = new ArrayList<ParamEntry>();

	private List<Fetch> fetchList = new ArrayList<Fetch>();

	private List<WhereEntry> whereEntries = new ArrayList<WhereEntry>();

	private int firstResult;
	private int maxResults;

	/**
	 * 得到所有通过addEntry()方法添加的查询条件
	 * 
	 * @return 查询条件的集合
	 */
	public List<ParamEntry> getEntries() {
		return entries;
	}

	/**
	 * 添加查询条件
	 * <p>
	 * 示例：
	 * <p>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria<br>
	 * .addEntry("name", Op.EQ, "name1");<br>
	 * 
	 * @param paramName
	 *            查询对象的属性名
	 * @param queryCase
	 *            操作符
	 * @param value
	 *            属性值
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria addEntry(String paramName, String queryCase, Object value) {
		ParamEntry entry = new ParamEntry(paramName, queryCase, value);
		return addEntry(entry);
	}

	/**
	 * 添加一个已经封装好的查询条件对象!
	 * 
	 * @param entry
	 * @return
	 * @author mawm at 2013-3-19 下午12:19:47
	 */
	public QCriteria addEntry(ParamEntry entry) {
		this.entries.add(entry);
		return this;
	}

	/**
	 * 得到由innerFetch(), leftFetch, rightFetch() 添加的联接信息
	 * 
	 * @return 联接信息的集合
	 */
	public List<Fetch> getFetchList() {
		return fetchList;
	}

	/**
	 * 添加一个内联接信息并抓取目标对象
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria innerFetch(String targetObjectName) {
		this.fetchList.add(Fetch.innerFetch(targetObjectName));
		return this;
	}

	/**
	 * 添加一个左外联接信息并抓取目标对象
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria leftFetch(String targetObjectName) {
		this.fetchList.add(Fetch.leftFetch(targetObjectName));
		return this;
	}

	/**
	 * 添加一个右外联接信息并抓取目标对象
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria rightFetch(String targetObjectName) {
		this.fetchList.add(Fetch.rightFetch(targetObjectName));
		return this;
	}

	/**
	 * 添加一个内联接信息
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria innerJoin(String targetObjectName) {
		this.fetchList.add(Fetch.innerJoin(targetObjectName));
		return this;
	}

	/**
	 * 添加一个左外联接信息
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria leftJoin(String targetObjectName) {
		this.fetchList.add(Fetch.leftJoin(targetObjectName));
		return this;
	}

	/**
	 * 添加一个右外联接信息
	 * 
	 * @param targetObjectName
	 *            属性
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria rightJoin(String targetObjectName) {
		this.fetchList.add(Fetch.rightJoin(targetObjectName));
		return this;
	}

	/**
	 * 添加一个或多个查询条件，与addEntry作用相似，不同处在于where()方法内指定属性时要由一个前缀"o."，
	 * 而且where()方法内可以一次指定多个条件，能适应更多的情况。比如 OR条件, 使用数据库函数等。
	 * 
	 * <p>
	 * 示例：
	 * <p>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria<br>
	 * .where("o.name = ? or o.name = ?", "name1", "name2");<br>
	 * 
	 * @param whereClause
	 *            查询条件
	 * @param values
	 *            查询参数
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria where(String whereClause, Object... values) {
		ArrayList<Object> params = new ArrayList<Object>(values.length);
		for (Object value : values) {
			params.add(value);
		}
		WhereEntry whereEntry = new WhereEntry(whereClause, params);
		this.whereEntries.add(whereEntry);
		return this;
	}

	/**
	 * 判断是否含有由where()方法添加的查询条件
	 * 
	 * @return 对象本身，为了形成链式操作
	 */
	public boolean hasWhereClause() {
		return whereEntries != null && !whereEntries.isEmpty();
	}

	/**
	 * 清除所有已添加的查询条件，联接信息
	 * 
	 * @return 对象本身，为了形成链式操作
	 */
	public QCriteria clear() {
		this.entries.clear();
		this.fetchList.clear();
		this.whereEntries.clear();
		return this;
	}

	/**
	 * 得到查询结果的起始偏移值，用于分页操作
	 * 
	 * @return 起始偏移值
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * 设置结果的起始偏移值，用于分页操作
	 * 
	 * @param firstResult
	 *            起始偏移值
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * 得到查询结果的数量，用于分页操作
	 * 
	 * @return 数量
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * 设置查询结果的数量，用于分页操作
	 * 
	 * @param maxResults
	 *            数量
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 得到由where()方法添加的查询条件集合
	 * 
	 * @return
	 */
	public List<WhereEntry> getWhereEntries() {
		return whereEntries;
	}

	public static class WhereEntry {
		private String whereClause;

		private List<Object> whereParams;

		public WhereEntry(String whereClause, List<Object> whereParams) {
			super();
			this.whereClause = whereClause;
			this.whereParams = whereParams;
		}

		public String getWhereClause() {
			return whereClause;
		}

		public void setWhereClause(String whereClause) {
			this.whereClause = whereClause;
		}

		public List<Object> getWhereParams() {
			return whereParams;
		}

		public void setWhereParams(List<Object> whereParams) {
			this.whereParams = whereParams;
		}

	}

	public static class ParamEntry {

		private String paramName;

		private String queryCase;

		private Object value;

		public ParamEntry() {
		}

		public ParamEntry(String paramName, String queryCase, Object value) {
			this.paramName = paramName;
			this.queryCase = queryCase;
			this.value = value;
		}

		public String getParamName() {
			return paramName;
		}

		public void setParamName(String paramName) {
			this.paramName = paramName;
		}

		public String getQueryCase() {
			return queryCase;
		}

		public void setQueryCase(String queryCase) {
			this.queryCase = queryCase;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

	public static class Op {
		public static final String EQ = "=";
		/** 仅对NULL值使用 */
		public static final String IS = "IS";
		public static final String LT = "<";
		public static final String GT = ">";
		public static final String LTEQ = "<=";
		public static final String GTEQ = ">=";
		public static final String NE = "<>";
		public static final String LIKE = "LIKE";
		public static final String IN = "IN";
	}
	
	/**
	 * 把Value转换成真实的类型
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static Object converValue(String fieldName, String value) {

		if (fieldName.endsWith(SearchForm.TYPE_DATE)) {
			try {
				return new SimpleDateFormat(DateUtils.PATTERN_DATE).parse(value);
			} catch (ParseException e) {
			}
		}
		else if (fieldName.endsWith(SearchForm.TYPE_INT)) {
			return new Integer(String.valueOf(value));
		}
		else if (fieldName.endsWith(SearchForm.TYPE_STRING)) {
			return String.valueOf(value);
		} 
		else if (fieldName.endsWith(SearchForm.TYPE_SPLITTXT)) {
			String[] split = StringUtils.split(value, ",");
			return Arrays.asList(split);
		} 
		else if (fieldName.endsWith(SearchForm.TYPE_SPLIT_INT)) {
			String[] split = StringUtils.split(value, ",");
			Integer[] integerArray = NumberUtils.toIntegerArray(split);
			return Arrays.asList(integerArray);
		}
		return String.valueOf(value);
	}

	/**
	 * 解析一个表达式
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @author mawm at 2013-3-19 下午1:16:12
	 */
	public static ParamEntry parseOne(String name, String value) {

		if (StringUtils.isBlank(value))
			return null;

		ParamEntry e = null;
		if (name.contains(SearchForm.OP_GT_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_GT_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, ">", realValue);
		} else if (name.contains(SearchForm.OP_LT_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_LT_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "<", realValue);
		} else if (name.contains(SearchForm.OP_LIKE_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_LIKE_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "like", realValue + "%");
		} else if (name.contains(SearchForm.OP_BOTH_LIKE_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_BOTH_LIKE_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "like", "%" + realValue + "%");
		} else if (name.contains(SearchForm.OP_EQ_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "=", realValue);
		} else if (name.contains(SearchForm.OP_NOT_EQ_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_NOT_EQ_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "<>", realValue);
		} else if (name.contains(SearchForm.OP_EQ_AND_GT_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_AND_GT_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, ">=", realValue);
		} else if (name.contains(SearchForm.OP_EQ_AND_LT_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_EQ_AND_LT_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, "<=", realValue);
		} else if (name.contains(SearchForm.OP_IN_SUFFIX)) {
			String fieldName = StringUtils.substringBefore(name, SearchForm.OP_IN_SUFFIX);
			Object realValue = converValue(name, value);
			e = new ParamEntry(fieldName, QCriteria.Op.IN, realValue);
		} else {
			return null;
		}
		return e;

	}

	/**
	 * 解释SearchForm生成查询条件
	 * 
	 * @param searchForm
	 * @return
	 */
	public static QCriteria parseForm(SearchForm searchForm) {

		QCriteria criteria = new QCriteria();
		Map<String, String> form = searchForm.getForm();

		for (Iterator<Map.Entry<String, String>> iterator = form.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = iterator.next();
			String name = entry.getKey();
			String value = entry.getValue();

			if (StringUtils.isBlank(value))
				continue;
			// 解析一个查询条件
			ParamEntry e = parseOne(name, value);
			if (e != null) {
				criteria.addEntry(e);
			}
		}

		return criteria;
	}
}
