package com.ccesun.framework.plugins.aop;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import com.ccesun.framework.plugins.search.SearchUtils;
import com.ccesun.framework.plugins.search.SearchableBean;
import com.ccesun.framework.plugins.search.SearchableField;
import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

@Aspect
@Component
public class SearchIndexAdvice implements DisposableBean {

	private static final String METHODNAME_TO_INTERCEPT = "save";
	
	private static final Log logger = LogFactory.getLog(SearchIndexAdvice.class);
	
	private IndexWriter indexWriter;
	private IndexWriterConfig indexWriterConfig;
	private Directory indexDirectory;
	
	@PostConstruct
	public void init() throws CorruptIndexException, LockObtainFailedException, IOException {
		File indexDir = SearchUtils.getSearchIndexDir();
		Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
		indexDirectory = new NIOFSDirectory(indexDir);
		indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, luceneAnalyzer);
		indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
	}
	
	@After("target(com.ccesun.framework.core.service.IService)")
	public void index(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();

		if (!StringUtils.isBlank(methodName) 
				&& METHODNAME_TO_INTERCEPT.equals(methodName)
				//&& args.length == 1 
				&& args[0] != null) {
			Object bean = args[0];
			SearchableBean searchableBean = bean.getClass().getAnnotation(SearchableBean.class);
			if (searchableBean != null) {
	
				try {
					Document document = new Document();
	
					Collection<Field> fields = BeanUtils.getAllFieldMap(bean.getClass()).values();
	
					for (Field field : fields) {
						SearchableField searchableField = field.getAnnotation(SearchableField.class);
						if (searchableField != null) {
							field.setAccessible(true);
		
							String fieldValue = StringUtils.EMPTY;
							fieldValue = field.get(bean) == null ? "" : field.get(bean).toString();
		
							String fieldName = StringUtils.isBlank(searchableField.value()) ? field.getName() : searchableField.value();
							org.apache.lucene.document.Field luceneField = new org.apache.lucene.document.Field(
									fieldName, fieldValue,
									searchableField.store(),
									searchableField.index());
		
							document.add(luceneField);
						}
					}
					
					org.apache.lucene.document.Field luceneField = new org.apache.lucene.document.Field(
							"className", bean.getClass().getName(),
							Store.YES,
							Index.NOT_ANALYZED);
					document.add(luceneField);
					
					if (logger.isDebugEnabled())
						logger.debug(String.format("%s was indexed.", bean.getClass().toString()));
					
					indexWriter.addDocument(document);
					indexWriter.close();
					
				} catch (Exception e) {
					
					if (logger.isWarnEnabled())
						logger.warn(String.format("Could not index %s.", bean.getClass().toString()), e);
				} 
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		if(IndexWriter.isLocked(indexDirectory)){  
			indexWriter.close();  
	        IndexWriter.unlock(indexDirectory);  
	    }  
	}
	

}
