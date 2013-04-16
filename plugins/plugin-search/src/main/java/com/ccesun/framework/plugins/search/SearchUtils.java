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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.callback.IFind;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

public class SearchUtils {
	/**
	 * @return 中文分词
	 * @author mawm at 2013-4-16 上午10:04:14
	 */
	public static Analyzer analyzer() {
		Analyzer a = new IKAnalyzer(true);
		return a;
	}

	/**
	 * 创建目录
	 * 
	 * @param indexDir
	 * @return
	 * @throws IOException
	 * @author mawm at 2013-4-16 上午10:17:50
	 */
	public static Directory createNIOFSDirectory(String indexDir)
			throws IOException {
		return createNIOFSDirectory(new File(indexDir));
	}

	/**
	 * 创建目录
	 * 
	 * @param indexDir
	 * @return
	 * @throws IOException
	 * @author mawm at 2013-4-16 上午10:17:23
	 */
	public static Directory createNIOFSDirectory(File indexDir)
			throws IOException {
		NIOFSDirectory i = new NIOFSDirectory(indexDir);
		return i;
	}

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
	 * @param target
	 *            待查询的对象Class
	 * @param params
	 *            查询条件
	 * @return 符合条件的分页对象
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest,
			Class<T> target, Map<String, String> params) {

		Query query = getQuery(target, params);
		return findPage(pageRequest, target, query);

	}

	/**
	 * @param pageRequest
	 * @param target
	 * @param query
	 *            可以自行构造多种查询规则进行检索
	 * @return
	 * @author mawm at 2013-4-12 下午4:11:49
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest,
			Class<T> target, Query query) {
		boolean addTargeClassQuery = true;
		return findPage(pageRequest, target, query, addTargeClassQuery);
	}

	/**
	 * 添加target的calssName到查询条件中
	 * 
	 * @param target
	 * @param query
	 * @return
	 * @author mawm at 2013-4-16 上午8:51:05
	 */
	public static <T> Query addTargetClassQuery(Class<T> target, Query query) {
		if (target == null)
			return query;
		BooleanQuery bq = new BooleanQuery();
		Query queryClazz = subQueryClassName(target);
		bq.add(queryClazz, BooleanClause.Occur.MUST);
		bq.add(query, BooleanClause.Occur.MUST);
		return bq;
	}

	/**
	 * 类名称的子查询
	 * 
	 * @param target
	 * @return
	 * @author mawm at 2013-4-16 上午9:18:36
	 */
	public static <T> Query subQueryClassName(Class<T> target) {
		Query queryClazz = new TermQuery(
				new Term("className", target.getName()));
		return queryClazz;
	}

	/**
	 * @param pageRequest
	 * @param target
	 * @param query
	 *            可以自行构造多种查询规则进行检索
	 * @param addTargetClassQuery
	 *            是否把当前target的类名称封装进去
	 * @return
	 * @author mawm at 2013-4-15 下午5:06:17
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest,
			Class<T> target, Query query, boolean addTargetClassQuery) {
		IFind<T, T> find = null;
		return findPage(pageRequest, target, query, addTargetClassQuery, find);
	}

	/**
	 * 查询操作,并且把对象转换为完整的对象
	 * 
	 * @param pageRequest
	 * @param target
	 * @param query
	 * @param find
	 * @return
	 * @author mawm at 2013-4-16 上午10:27:11
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest,
			Class<T> target, Query query, IFind<T, T> find) {
		boolean addTargetClassQuery = true;
		return findPage(pageRequest, target, query, addTargetClassQuery, find);
	}

	/**
	 * @param pageRequest
	 * @param target
	 * @param query
	 *            可以自行构造多种查询规则进行检索
	 * @param addTargetClassQuery
	 *            是否把当前target的类名称封装进去
	 * @param find
	 *            用来把lucence中存储的部分对象转换为真实对象的方法,因为缓存中的对象记录了对象的pk主键
	 * @return
	 * @author mawm at 2013-4-16 上午10:27:37
	 */
	public static <T> Page<T> findPage(PageRequest pageRequest,
			Class<T> target, Query query, boolean addTargetClassQuery,
			IFind<T, T> find) {

		Query queryAll = null;
		if (addTargetClassQuery) {// 把target添加到查询中
			Query bq = addTargetClassQuery(target, query);
			queryAll = bq;

		} else {
			queryAll = query;
		}

		int pageNo = pageRequest.getPageNo();
		int pageSize = pageRequest.getPageSize();

		TopScoreDocCollector topCollector;
		IndexSearcher searcher = null;
		List<T> content;
		IndexReader open = null;
		Directory directory = null;
		try {
			File indexDir = getSearchIndexDir();
			directory = createNIOFSDirectory(indexDir);
			open = IndexReader.open(directory);
			searcher = new IndexSearcher(open);

			topCollector = null;
			content = new ArrayList<T>();
			boolean docsScoredInOrder = false;
			topCollector = TopScoreDocCollector.create(searcher.maxDoc(),
					docsScoredInOrder);
			searcher.search(queryAll, topCollector);

			ScoreDoc[] docs = topCollector.topDocs((pageNo - 1) * pageSize,
					pageSize).scoreDocs;
			for (ScoreDoc scdoc : docs) {
				Document doc = searcher.doc(scdoc.doc);
				T bean = parseBean(doc, target);
				// 调用回调接口来加载真实的对象
				if (find != null) {
					bean = find.find(bean);
				}
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

	private static <T> Query getQuery(Class<T> target,
			Map<String, String> params) {

		Set<Map.Entry<String, String>> paramEntry = params.entrySet();
		Iterator<Map.Entry<String, String>> iterator = paramEntry.iterator();

		BooleanQuery query = new BooleanQuery();
		Query queryClazz = subQueryClassName(target);
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

	/**
	 * 创建对象的主键查询Query对象
	 * 
	 * @param target
	 * @author mawm at 2013-4-16 上午8:58:07
	 */
	public static <T> BooleanQuery makePKQuery(T target) {
		Class<? extends Object> clazz = target.getClass();
		Collection<java.lang.reflect.Field> fields = BeanUtils.getAllFieldMap(
				clazz).values();
		BooleanQuery bq = new BooleanQuery();
		int fieldSize = 0;
		for (java.lang.reflect.Field field : fields) {
			String name = field.getName();
			if ("serialVersionUID".equals(name))
				continue;

			SearchableField ia = field.getAnnotation(SearchableField.class);
			if (ia != null && ia.pk()) {
				// 对应的属性列名
				String fieldName = IndexDocumentUtils.getFieldName(ia, field);
				String fieldValue = IndexDocumentUtils.getFieldValue(target,
						field);
				// 构建查询条件
				Query q = new TermQuery(new Term(fieldName, fieldValue));
				bq.add(q, BooleanClause.Occur.MUST);

			}
		}
		Query queryClazz = subQueryClassName(clazz);
		bq.add(queryClazz, BooleanClause.Occur.MUST);
		return bq;
	}

	/**
	 * 删除主键规则的索引
	 * 
	 * @param indexWriter
	 * @param query
	 * @param target
	 * @param addTargetClassQuery
	 * @author mawm at 2013-4-16 上午8:57:04
	 */
	public static <T> void deleteDocuments(IndexWriter indexWriter,
			Query query, Class<T> target, boolean addTargetClassQuery) {
		// SearchIndexAdvice bean = AppContext.getInstance()
		// .getApplicationContext().getBean(SearchIndexAdvice.class);
		// if (bean != null) {
		// bean.deleteDocuments(query);
		// }
		Query queryAll = null;
		if (addTargetClassQuery) {// 把target添加到查询中
			Query bq = addTargetClassQuery(target, query);
			queryAll = bq;

		} else {
			queryAll = query;
		}
		if (indexWriter != null && queryAll != null) {
			try {
				indexWriter.deleteDocuments(queryAll);
			} catch (CorruptIndexException e) {
				logger.error("", e);
			} catch (IOException e) {
				logger.error("", e);
			}
		}

	}

	public static <T> void deleteDocuments(Term term, Class<T> target) {
		// SearchIndexAdvice bean = AppContext.getInstance()
		// .getApplicationContext().getBean(SearchIndexAdvice.class);
		// if (bean != null) {
		// bean.deleteDocuments(term);
		// }
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

	/**
	 * 把对field字段进行生成查询操作的关键字解析,并转换为query对象 <code>
	 * <pre>
	 * 
	 * 	PageRequest pageRequest = new PageRequest(1, 10);
		Query query1 = SearchUtils.parseQuery("name", "痛苦 子女 父母");
		Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
				Contact.class, query1);
	 * </pre>
	 * 
	 * </code>
	 * 
	 * @param field
	 * @param content
	 * @return
	 * @throws ParseException
	 * @author mawm at 2013-4-16 上午10:07:57
	 */
	public static Query parseQuery(String field, String content)
			throws ParseException {
		Analyzer analyzer = SearchUtils.analyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_36, "name", analyzer);

		Query query1 = qp.parse(content);
		return query1;
	}
}
