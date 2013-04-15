package com.ccesun.framework.plugins.aop;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ccesun.framework.plugins.search.IndexDocumentUtils;
import com.ccesun.framework.plugins.search.SearchUtils;
import com.ccesun.framework.plugins.search.SearchableBean;
import com.ccesun.framework.util.StringUtils;

@Aspect
@Component
public class SearchIndexAdvice implements DisposableBean {

	private static final String METHODNAME_TO_INTERCEPT = "save";

	private static final Log logger = LogFactory
			.getLog(SearchIndexAdvice.class);

	@PostConstruct
	public void init() throws CorruptIndexException, LockObtainFailedException,
			IOException {

	}

	@After("target(com.ccesun.framework.core.service.IService)")
	public void index(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		long begin = logger.isDebugEnabled() ? System.currentTimeMillis() : 0;

		if (!StringUtils.isBlank(methodName)
				&& METHODNAME_TO_INTERCEPT.equals(methodName)
				// && args.length == 1
				&& args[0] != null) {
			Object bean = args[0];

			Class<?> clzss = bean.getClass();
			SearchableBean searchableBean = clzss
					.getAnnotation(SearchableBean.class);
			if (searchableBean == null) {
				return;
			}

			IndexWriter indexWriter = null;
			IndexWriterConfig indexWriterConfig = null;
			Directory indexDirectory = null;

			try {

				Document doc = IndexDocumentUtils.createDocument(bean);
				if (doc != null) {

					File indexDir = IndexDocumentUtils
							.getIndexDirectory(searchableBean);
					// 实例化IKAnalyzer分词器
					Analyzer analyzer = new IKAnalyzer();
					// Analyzer luceneAnalyzer = new
					// StandardAnalyzer(Version.LUCENE_36);
					indexDirectory = new NIOFSDirectory(indexDir);
					// TODO 这里需要根据不同的配置,创建不同的索引目录
					try {
						SearchUtils.unlock(indexDirectory);
					} catch (Exception e) {
						logger.error("", e);
					}
					indexWriterConfig = new IndexWriterConfig(
							Version.LUCENE_36, analyzer);
					indexWriter = new IndexWriter(indexDirectory,
							indexWriterConfig);

					// 创建类的类名称索引
					Field field = IndexDocumentUtils.createClassField(bean
							.getClass());
					doc.add(field);

					if (logger.isDebugEnabled())
						logger.debug(String.format("%s was indexed.", bean
								.getClass().toString()));
					indexWriter.addDocument(doc);
					indexWriter.commit();
				}

			} catch (Exception e) {
				if (logger.isWarnEnabled())
					logger.warn(String.format("Could not index %s.", bean
							.getClass().toString()), e);
			} finally {
				if (indexWriter != null)
					try {
						indexWriter.close();
					} catch (Exception e) {
						logger.error("", e);
					}
				if (indexDirectory != null)
					try {
						indexDirectory.close();
					} catch (IOException e) {
						logger.error("", e);
					}
			}

		}
		if (logger.isDebugEnabled()) {
			long time = System.currentTimeMillis() - begin;
			String txt1 = String.format("%s-%s 耗时:%s毫秒", "", "", time);
			logger.debug(txt1);
		}
	}

	/**
	 * 用来进行存储之前删除文档,避免文档被重复索引,多个累积
	 * 
	 * @param query
	 * @author mawm at 2013-4-15 上午9:13:12
	 */
	// public void deleteDocuments(Query query) {
	// try {
	// indexWriter.deleteDocuments(query);
	// } catch (CorruptIndexException e) {
	// if (logger.isInfoEnabled())
	// logger.error("", e);
	// } catch (IOException e) {
	// if (logger.isInfoEnabled())
	// logger.error("", e);
	// }
	//
	// }
	//
	// /**
	// * 用来进行存储之前删除文档,避免文档被重复索引,多个累积
	// *
	// * @param term
	// * @author mawm at 2013-4-15 上午9:12:22
	// */
	// public void deleteDocuments(Term term) {
	// try {
	// indexWriter.deleteDocuments(term);
	// } catch (CorruptIndexException e) {
	// if (logger.isInfoEnabled())
	// logger.error("", e);
	// } catch (IOException e) {
	// if (logger.isInfoEnabled())
	// logger.error("", e);
	// }
	//
	// }

	@Override
	public void destroy() throws Exception {
		// SearchUtils.unlock(indexDirectory);
	}

}
