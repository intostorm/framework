package com.ccesun.framework.plugins.search;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

@SuppressWarnings("unchecked")
public final class IndexDocumentUtils {

	private final static Logger log = LoggerFactory
			.getLogger(IndexDocumentUtils.class);

	public static org.apache.lucene.document.Field createClassField(Class clazz) {
		org.apache.lucene.document.Field luceneField = new org.apache.lucene.document.Field(
				"className", clazz.getName(), Store.YES, Index.NOT_ANALYZED);
		return luceneField;
	}

	/**
	 * 取得配置的索引存放目录
	 * 
	 * @param able
	 * @return
	 * @author mawm at 2013-4-15 下午4:01:53
	 */
	public static File getIndexDirectory(SearchableBean able) {
		File indexDir = SearchUtils.getSearchIndexDir();
		if (able == null)
			return indexDir;
		String dir = able.dir();
		if (StringUtils.isEmpty(dir)) {
			return indexDir;
		}
		return new File(dir);
	}

	/**
	 * 取得属性的名称
	 * 
	 * @param ia
	 * @param field
	 * @return
	 * @author mawm at 2013-4-16 上午9:10:00
	 */
	public static String getFieldName(SearchableField ia, Field field) {
		// 对应的属性列名
		String fieldName = StringUtils.isBlank(ia.value()) ? field.getName()
				: ia.value();

		return fieldName;
	}

	/**
	 * 创建索引
	 * 
	 * @param bean
	 * @return
	 */
	public static <T> Document createDocument(T bean) {

		Class<?> clzss = bean.getClass();
		SearchableBean searchableBean = clzss
				.getAnnotation(SearchableBean.class);
		if (searchableBean == null) {
			return null;
		}
		Document doc = new Document();
		// Field[] fields = clzss.getDeclaredFields();
		Collection<Field> fields = BeanUtils.getAllFieldMap(clzss).values();

		int fieldSize = 0;
		for (Field field : fields) {
			String name = field.getName();
			if (name.equals("serialVersionUID"))
				continue;

			SearchableField ia = field.getAnnotation(SearchableField.class);
			if (ia != null) {
				fieldSize++;
				String value = getFieldValue(bean, field);

				// 对应的属性列名
				String fieldName = getFieldName(ia, field);
				org.apache.lucene.document.Field indexField = null;
				if (ia.pk() && ia.pkUseDefault()) {
					// 主键使用默认值
					indexField = new org.apache.lucene.document.Field(
							fieldName, value,
							org.apache.lucene.document.Field.Store.YES,
							org.apache.lucene.document.Field.Index.NOT_ANALYZED// 索引不分词
					);
				} else {
					// 即使是主键,也使用配置的值
					Store store = getStore(ia);
					Index index = getIndex(ia);

					indexField = new org.apache.lucene.document.Field(
							fieldName, value, store, index);
				}

				// 设置权重值
				float boost = getBoost(ia);
				indexField.setBoost(boost);
				doc.add(indexField);
			}
			// 处理主键
			// Id id = field.getAnnotation(Id.class);
			// if (id != null) {
			// // 对象主键也同时进行记录
			// // 对应的属性列名
			// String fieldName = field.getName();
			// String value = getFieldValue(bean, field);
			// org.apache.lucene.document.Field indexField = new
			// org.apache.lucene.document.Field(
			// fieldName, value,
			// org.apache.lucene.document.Field.Store.YES,
			// org.apache.lucene.document.Field.Index.NOT_ANALYZED// 索引不分词
			// );
			//
			// doc.add(indexField);
			// }

		}

		if (fieldSize > 0)
			return doc;
		return null;

	}

	private static Pattern tagPattern = Pattern
			.compile("<.*?>", Pattern.DOTALL);

	/**
	 * @param bean
	 * @param field
	 * @return 通过反射获取字段值
	 * @author mawm at 2013-4-15 下午4:30:55
	 */
	public static <T> String getFieldValue(T bean, Field field) {
		try {
			boolean isMatcher = false;
			// String property = BeanUtils.getProperty(bean, fieldName);

			field.setAccessible(true);

			String property = field.get(bean) == null ? StringUtils.EMPTY
					: field.get(bean).toString();

			String value = property == null ? StringUtils.EMPTY : property;
			StringBuffer sb = new StringBuffer(32);
			if (isParseHtml(field)) {// 是否解析html内容
				if (StringUtils.isNotEmpty(value)) {
					Matcher matcher = tagPattern.matcher(value);
					while (matcher.find()) {
						isMatcher = true;
						matcher.appendReplacement(sb, "");
					}
					matcher.appendTail(sb);
					// value= StringUtils.getHtmlText(value);
				} else {
					return "";
				}
			}
			return isMatcher ? sb.toString() : value;
		} catch (Exception e) {
			log.error("", e);
			return "";
		}
	}

	/**
	 * @param ia
	 * @return 返回索引字段是否存储
	 * @author mawm at 2013-4-15 下午4:30:44
	 */
	private static <T> Store getStore(SearchableField ia) {

		if (ia != null) {// 检查注解的值
			return ia.store();
		}

		return org.apache.lucene.document.Field.Store.NO;
	}

	/**
	 * @param ia
	 * @return 返回索引字段是否索引
	 * @author mawm at 2013-4-15 下午4:30:36
	 */
	private static <T> Index getIndex(SearchableField ia) {

		if (ia != null) {// 检查注解的值
			return ia.index();
		}
		return org.apache.lucene.document.Field.Index.ANALYZED;
	}

	/**
	 * @param field
	 * @return 返回索引字段是否解析HTML
	 * @author mawm at 2013-4-15 下午4:30:16
	 */
	private static <T> boolean isParseHtml(Field field) {
		SearchableField ia = field.getAnnotation(SearchableField.class);
		return isParseHtml(ia);
	}

	private static <T> boolean isParseHtml(SearchableField ia) {

		if (ia != null) {// 检查注解的值
			return ia.parseHtml();

		}

		return true;
	}

	/**
	 * @param ia
	 * @return 返回权重值
	 * @author mawm at 2013-4-15 下午4:30:24
	 */
	private static <T> float getBoost(SearchableField ia) {

		if (ia != null) {// 检查注解的值
			return ia.boost();
		}

		return 10;
	}
}