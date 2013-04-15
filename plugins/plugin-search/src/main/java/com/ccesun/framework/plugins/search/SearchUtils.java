package com.ccesun.framework.plugins.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.plugins.aop.SearchIndexAdvice;
import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

public class SearchUtils {

	private static final Log logger = LogFactory.getLog(SearchUtils.class);

	/**
	 * 获得index目录 默认是从config.properties中读取plugin.search.searchIndexDir配置项，
	 * 如果配置项不存在，读取临时目录
	 * 
	 * @return
	 */
	public static File getSearchIndexDir() {
		String searchIndexDir = AppContext.getInstance().getString(
				"plugin.search.searchIndexDir");
		if (StringUtils.isBlank(searchIndexDir)) {
			searchIndexDir = System.getProperty("java.io.tmpdir");
			if (logger.isWarnEnabled())
				logger.warn("plugin.search.searchIndexDir was not found in config.properties, use tmp dir instead");
		}
		return new File(searchIndexDir);
	}

	/**
	 * 查出符合条件的分页对象
	 * <p>
	 * 示例：<br>
	 * PageRequest pageRequest = new PageRequest(1, 10);<br>
	 * Map<String, String> params = new HashMap<String, String>();<br>
	 * params.put("name", "searchtest");<br>
	 * Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
	 * Contact.class, params);<br>
	 * 
	 * @param pageRequest
	 *            分页条件
	 * @param clazz
	 *            待查询的对象Class
	 * @param params
	 *            查询条件
	 * @return 符合条件的分页对象
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest, Class<T> clazz,
			Map<String, String> params) {

		Query query = getQuery(clazz, params);
		return findPage(pageRequest, clazz, query);

	}

	/**
	 * @param pageRequest
	 * @param clazz
	 * @param query
	 *            可以自行构造多种查询规则进行检索
	 * @return
	 * @author mawm at 2013-4-12 下午4:11:49
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest, Class<T> clazz,
			Query query) {
		int pageNo = pageRequest.getPageNo();
		int pageSize = pageRequest.getPageSize();

		TopScoreDocCollector topCollector;
		IndexSearcher searcher = null;
		List<T> content;
		IndexReader open = null;
		NIOFSDirectory directory = null;
		try {
			File indexDir = getSearchIndexDir();
			directory = new NIOFSDirectory(indexDir);
			open = IndexReader.open(directory);
			searcher = new IndexSearcher(open);

			topCollector = null;
			content = new ArrayList<T>();

			topCollector = TopScoreDocCollector
					.create(searcher.maxDoc(), false);
			searcher.search(query, topCollector);// ,

			ScoreDoc[] docs = topCollector.topDocs((pageNo - 1) * pageSize,
					pageSize).scoreDocs;
			for (ScoreDoc scdoc : docs) {
				Document doc = searcher.doc(scdoc.doc);
				T bean = parseBean(doc, clazz);
				content.add(bean);
			}
			return new Page<T>(content, pageRequest,
					topCollector.getTotalHits());
		} catch (Exception e) {
			logger.error("", e);
			return new Page<T>(new ArrayList<T>(), pageRequest, 0);
		} finally {
			try {
				directory.close();
			} catch (Exception e2) {
				logger.error("", e2);
			}
			try {
				open.close();
			} catch (IOException e1) {
				logger.error("", e1);
			}
			try {
				searcher.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}

	}

	private static <T> T parseBean(Document doc, Class<T> clazz) {

		try {
			String[] fieldNames = getFieldNames(clazz);
			T instance = clazz.newInstance();
			for (String fieldName : fieldNames) {
				String value = ((Field) doc.getFieldable(fieldName))
						.stringValue();
				BeanUtils.copyProperty(instance, fieldName, value);
			}

			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String[] getFieldNames(Class<?> clazz) {
		List<String> tempResult = new ArrayList<String>();
		Collection<java.lang.reflect.Field> fields = BeanUtils.getAllFieldMap(
				clazz).values();
		for (java.lang.reflect.Field field : fields) {
			SearchableField searchableField = field
					.getAnnotation(SearchableField.class);
			if (searchableField != null
					&& Field.Store.YES.equals(searchableField.store())) {
				String fieldName = searchableField.value();

				if (StringUtils.isBlank(fieldName))
					fieldName = field.getName();

				tempResult.add(fieldName);
			}
		}
		return tempResult.toArray(new String[0]);
	}

	private static <T> Query getQuery(Class<T> clazz, Map<String, String> params) {

		Set<Map.Entry<String, String>> paramEntry = params.entrySet();
		Iterator<Map.Entry<String, String>> iterator = paramEntry.iterator();

		BooleanQuery query = new BooleanQuery();
		Query queryClazz = new TermQuery(new Term("className", clazz.getName()));
		query.add(queryClazz, BooleanClause.Occur.MUST);

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue();
			Query queryEntry = new TermQuery(new Term(paramName, paramValue));
			query.add(queryEntry, BooleanClause.Occur.MUST);
		}

		return query;
	}

	public static <T> void deleteDocuments(Query query, Class<T> clazz) {
		SearchIndexAdvice bean = AppContext.getInstance()
				.getApplicationContext().getBean(SearchIndexAdvice.class);
		if (bean != null) {
			bean.deleteDocuments(query);
		}
	}

	public static <T> void deleteDocuments(Term term, Class<T> clazz) {
		SearchIndexAdvice bean = AppContext.getInstance()
				.getApplicationContext().getBean(SearchIndexAdvice.class);
		if (bean != null) {
			bean.deleteDocuments(term);
		}
	}
	
	/**
	 * @throws IOException
	 * @author mawm at 2013-4-15 上午9:03:47
	 */
	public static void unlock(Directory indexDirectory) throws IOException {
		if (IndexWriter.isLocked(indexDirectory)) {
			IndexWriter.unlock(indexDirectory);
		}
	}
}
