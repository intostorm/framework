package com.ccesun.framework.plugins.search;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.NIOFSDirectory;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

public class SearchUtils {

	private static final Log logger = LogFactory.getLog(SearchUtils.class);
	
	public static File getSearchIndexDir() {
		String searchIndexDir = AppContext.getInstance().getString("plugin.search.searchIndexDir");
		if (StringUtils.isBlank(searchIndexDir)) {
			searchIndexDir = System.getProperty("java.io.tmpdir");
			if (logger.isWarnEnabled())
				logger.warn("plugin.search.searchIndexDir was not found in config.properties, use tmp dir instead");
		}
		return new File(searchIndexDir);
	}
	
	public static <T> Page<T> findPage(PageRequest pageRequest, Class<T> clazz, String[] fieldNames, Map<String, String> params) {
		
		int pageNo = pageRequest.getPageNo();
		int pageSize = pageRequest.getPageSize();
		
		TopScoreDocCollector topCollector;
		List<T> content;
		try {
			File indexDir = getSearchIndexDir();
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(new NIOFSDirectory(indexDir)));
			
			topCollector = null;  
			content = new ArrayList<T>();  
				
			topCollector = TopScoreDocCollector.create(searcher.maxDoc(), false);  
			searcher.search(getQuery(clazz, params), topCollector);  

			ScoreDoc [] docs = topCollector.topDocs((pageNo - 1) * pageSize, pageSize).scoreDocs;  
			for(ScoreDoc scdoc : docs){  
				Document doc = searcher.doc(scdoc.doc);
				T bean = parseBean(doc, clazz, fieldNames);
				content.add(bean);  
			}
			
			return new Page<T>(content, pageRequest, topCollector.getTotalHits());
		} catch (Exception e) {
			e.printStackTrace();
			return new Page<T>(new ArrayList<T>(), pageRequest, 0);
		}  
            
	}
	
	private static <T> T parseBean(Document doc, Class<T> clazz, String[] fieldNames) {
		
		try {
			T instance = clazz.newInstance();
			for (String fieldName : fieldNames) {
				String value = ((Field) doc.getFieldable(fieldName)).stringValue();
				BeanUtils.copyProperty(instance, fieldName, value);
			}
			
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
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
}
