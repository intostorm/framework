package com.ccesun.sample.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.plugins.search.IndexDocumentUtils;
import com.ccesun.framework.plugins.search.SearchUtils;
import com.ccesun.sample.domain.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/app-context.xml" })
public class ContactServiceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ContactServiceTest.class);

	@Autowired
	private ContactService contactService;

	@Before
	public void setUp() throws Exception {
		AppContext.getInstance().put("plugin.search.searchIndexDir",
				"D:\\lunc1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		long begin = logger.isDebugEnabled() ? System.currentTimeMillis() : 0;

		String[] names = new String[] {

		"孕晚期调整饮食可减少分娩痛苦", "小宝宝常吐奶 妈妈应对有妙招", "产后消除妊娠纹 妙用鸡蛋来帮忙",
				"夏季穿齐B短裙增加患阴道炎危险", "宝宝不学爬长大学习更困难", "怎样口服避孕药",
				"吉林省城镇计划生育家庭独生子女父母退休后奖励实施意见", "老人喝苹果醋有益健康", "牙疼很痛苦啊" };
		for (String one : names) {
			Contact contact = new Contact();
			contact.setName(one);
			contact.setPhone(null);
			// contact.setAbc("1111");

			contactService.save(contact);
		}
		if (logger.isDebugEnabled()) {
			long time = System.currentTimeMillis() - begin;
			String txt1 = String.format("%s-%s 耗时:%s毫秒", "", "", time);
			logger.debug(txt1);
		}
	}

	/**
	 * 用来测试保存之后的更新操作
	 * 
	 * @author mawm at 2013-4-16 上午9:43:07
	 */
	@Test
	public void testSaveUpdate() {
		long begin = logger.isDebugEnabled() ? System.currentTimeMillis() : 0;

		List<Contact> list = new ArrayList<Contact>();
		String[] names = new String[] { "孕晚期调整饮食可减少分娩痛苦", "小宝宝常吐奶 妈妈应对有妙招",
				"产后消除妊娠纹 妙用鸡蛋来帮忙", "夏季穿齐B短裙增加患阴道炎危险", "宝宝不学爬长大学习更困难",
				"怎样口服避孕药", "吉林省城镇计划生育家庭独生子女父母退休后奖励实施意见", "老人喝苹果醋有益健康", "牙疼很痛苦啊" };
		for (String one : names) {
			Contact contact = new Contact();
			contact.setName(one);
			contact.setPhone(null);
			// contact.setAbc("1111");

			Contact save = contactService.save(contact);
			list.add(save);
		}
		if (logger.isDebugEnabled()) {
			long time = System.currentTimeMillis() - begin;
			String txt1 = String.format("%s-%s 耗时:%s毫秒", "新增操作完成", "", time);
			logger.debug(txt1);
		}
		begin = logger.isDebugEnabled() ? System.currentTimeMillis() : 0;
		for (Contact one : list) {
			one.setName(one.getName() + one.getRecordId());
			Contact save = contactService.save(one);
		}
		if (logger.isDebugEnabled()) {
			long time = System.currentTimeMillis() - begin;
			String txt1 = String.format("%s-%s 耗时:%s毫秒", "更新操作完成", "", time);
			logger.debug(txt1);
		}
	}

	@Test
	public void testCreateDOc() {
		Contact contact = new Contact();
		contact.setName("<title>孕晚期调整饮食可减<a href=''>少分娩痛苦</a></title>");
		contact.setPhone(null);
		IndexDocumentUtils.createDocument(contact);
	}

	@SuppressWarnings("serial")
	@Test
	public void testSearchPhraseQuery() throws ParseException {
		PageRequest pageRequest = new PageRequest(1, 10);

		{
			// 短语搜索（根据零碎的短语组合成新的词组进行搜索）
			PhraseQuery query1 = new PhraseQuery();

			Term word1 = new Term("name", "痛苦");

			query1.add(word1);

			// 设置坡度
			query1.setSlop(10);

			Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
					Contact.class, query1);

			for (Contact one : contactPage.getContent()) {
				System.out.println(String.format("%s-%s-%s", one.getRecordId(),
						one.getName(), one.getPhone()));

			}
		}
		System.out.println("----------------------------------");
		{
			// 短语搜索（根据零碎的短语组合成新的词组进行搜索）
			PhraseQuery query1 = new PhraseQuery();

			Term word1 = new Term("name", "痛苦");
			Term word2 = new Term("name", "子女");
			Term word3 = new Term("name", "妈妈");
			query1.add(word1);
			query1.add(word2);
			query1.add(word3);
			// 设置坡度
			query1.setSlop(10);

			Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
					Contact.class, query1);

			for (Contact one : contactPage.getContent()) {
				System.out.println(String.format("%s-%s-%s", one.getRecordId(),
						one.getName(), one.getPhone()));

			}
		}
		System.out.println("----------------------------------");
		{
			// 短语搜索（根据零碎的短语组合成新的词组进行搜索）
			PhraseQuery query1 = new PhraseQuery();

			Term word1 = new Term("name", "痛苦");
			Term word2 = new Term("name", "子女");
			Term word3 = new Term("name", "妈妈");
			query1.add(word1);
			query1.add(word2);
			query1.add(word3);
			// 设置坡度
			query1.setSlop(10);

			Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
					Contact.class, query1);

			for (Contact one : contactPage.getContent()) {
				System.out.println(String.format("%s-%s-%s", one.getRecordId(),
						one.getName(), one.getPhone()));

			}
		}
		System.out.println("----------------------------------");
		{
			Analyzer analyzer = SearchUtils.analyzer();
			QueryParser qp = new QueryParser(Version.LUCENE_36, "name",
					analyzer);

			Query query1 = qp.parse("痛苦 子女");

			Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
					Contact.class, query1);

			for (Contact one : contactPage.getContent()) {
				System.out.println(String.format("%s-%s-%s", one.getRecordId(),
						one.getName(), one.getPhone()));

			}
		}
	}

	@SuppressWarnings("serial")
	@Test
	public void testSearchParseQuery() throws ParseException {
		PageRequest pageRequest = new PageRequest(1, 10);
		Query query1 = SearchUtils.parseQuery("name", "痛苦 子女 父母");
		Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
				Contact.class, query1);

		for (Contact one : contactPage.getContent()) {
			System.out.println(String.format("%s-%s-%s", one.getRecordId(),
					one.getName(), one.getPhone()));

		}
	}

	@SuppressWarnings("serial")
	@Test
	public void testSearch2() {

		PageRequest pageRequest = new PageRequest(1, 10);

		// 查询条件，需在bean上设置过@SearchableField，且index不能是Index.NO
		Map<String, String> paramMap = new HashMap<String, String>() {
			{
				put("name", "痛苦");
			}
		};

		Page<Contact> contactPage = SearchUtils.findPage(pageRequest,
				Contact.class, paramMap);

		for (Contact one : contactPage.getContent()) {
			System.out.println(one.getName());
			System.out.println(one.getPhone());
		}
	}

	@Test
	public void testFind() {
		// QCriteria criteria = new QCriteria();

		// System.out.println(contactService.find(criteria).size());
		String jpql = "select o from Contact o where o.id in (:ids)";
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("ids", Arrays.asList(376, 378));
		System.out.println(contactService.find(jpql, params).size());

		// String jpql = "select o from Contact o where o.id in (?)";
		// EntityManager entityManager =
		// contactService.getDao().getEntityManager();
		// System.out.println(entityManager.createQuery(jpql).setParameter("ids",
		// Arrays.asList(376, 377)).getResultList().size());
	}

	@Test
	public void testCount() {
	}

	@Test
	public void testRemove() {
	}

	//
	//
	//
	// // 聲明一個IndexSearcher對象
	//
	// private IndexSearcher searcher = null;
	//
	// // 聲明一個Query對象
	//
	// private Query query = null;
	//
	// ChineseAnalyzer analyzer = new ChineseAnalyzer();
	//
	// Highlighter highlighter = null;

	// public final Hits search(String keyword) {
	//
	// System.out.println("正在檢索關鍵字:" + keyword);
	//
	// try {
	//
	// Date start = new Date();
	//
	// /***** 一個關鍵字，對一個字段進行查詢 *****/
	//
	// QueryParser qp = new QueryParser("content", analyzer);
	//
	// query = qp.parse(keyword);
	//
	// // Hits hits = searcher.search(query);
	//
	// /***** 模糊查詢 *****/
	//
	// // Term term = new Term("content",keyword);
	//
	// // FuzzyQuery fq = new FuzzyQuery(term);
	//
	// // Hits hits = searcher.search(fq);
	//
	// /***** 一個關鍵字，在兩個字段中查詢 *****/
	//
	// /*
	// *
	// * 1.BooleanClause.Occur[]的三種類型：
	// *
	// * MUST : + and
	// *
	// * MUST_NOT : - not
	// *
	// * SHOULD : or
	// *
	// * 2.下面查詢的意思是：content中必須包含該關鍵字，而title有沒有都無所謂
	// *
	// * 3.下面的這個查詢中，Occur[]的長度必須和Fields[]的長度一致。每個限制條件對應一個字段
	// */
	//
	// // BooleanClause.Occur[] flags = new
	// //
	// BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,BooleanClause.Occur.MUST};
	//
	// // query=MultiFieldQueryParser.parse(keyword,new
	// // String[]{"title","content"},flags,analyzer);
	//
	// /***** 兩個(多個)關鍵字對兩個(多個)字段進行查詢,默認匹配規則 *****/
	//
	// /*
	// *
	// * 1.關鍵字的個數必須和字段的個數相等
	// *
	// * 2.由於沒有指定匹配規定，默認為"SHOULD"
	// *
	// * 因此，下面查詢的意思是："title"中含有keyword1 或 "content"含有keyword2.
	// *
	// * 在此例中，把keyword1和keyword2相同
	// */
	//
	// // query=MultiFieldQueryParser.parse(new
	// // String[]{keyword,keyword},new
	// // String[]{"title","content"},analyzer);
	//
	// /***** 兩個(多個)關鍵字對兩個(多個)字段進行查詢,手工指定匹配規則 *****/
	//
	// /*
	// *
	// * 1.必須 關鍵字的個數 == 字段名的個數 == 匹配規則的個數
	// *
	// * 2.下面查詢的意思是："title"必須不含有keyword1,並且"content"中必須含有keyword2
	// */
	//
	// // BooleanClause.Occur[] flags = new
	// //
	// BooleanClause.Occur[]{BooleanClause.Occur.MUST_NOT,BooleanClause.Occur.MUST};
	//
	// // query=MultiFieldQueryParser.parse(new
	// // String[]{keyword,keyword},new
	// // String[]{"title","content"},flags,analyzer);
	//
	// /***** 對日期型字段進行查詢 *****/
	//
	// /***** 對數字範圍進行查詢 *****/
	//
	// /*
	// *
	// * 1.兩個條件必須是同一個字段
	// *
	// * 2.前面一個條件必須比後面一個條件小，否則找不到數據
	// *
	// * 3.new RangeQuery中的第三個參數，表示是否包含"="
	// *
	// * true: >= 或 <=
	// *
	// * false: > 或 <
	// *
	// * 4.找出 55>=id>=53 or 60>=id>=57:
	// */
	//
	// // Term lowerTerm1 = new Term("id","53");
	//
	// // Term upperTerm1 = new Term("id","55");
	//
	// // RangeQuery rq1 = new RangeQuery(lowerTerm1,upperTerm1,true);
	//
	// //
	//
	// // Term lowerTerm2 = new Term("id","57");
	//
	// // Term upperTerm2 = new Term("id","60");
	//
	// // RangeQuery rq2 = new RangeQuery(lowerTerm2,upperTerm2,true);
	//
	// //
	//
	// // BooleanQuery bq = new BooleanQuery();
	//
	// // bq.add(rq1,BooleanClause.Occur.SHOULD);
	//
	// // bq.add(rq2,BooleanClause.Occur.SHOULD);
	//
	// // 手工拼範圍
	//
	// // query = QueryParser.Parse("{200004 TO 200206}", "pubmonth", new
	// // SimpleAnalyzer());
	//
	// // Lucene用[] 和{}分別表示包含和不包含.
	//
	// // String temp = "startDate:["+nextWeek[0]+" TO "+nextWeek[1]+"] ";
	//
	// // temp = temp + " OR endDate:["+nextWeek[0]+" TO "+nextWeek[1]+"]";
	//
	// // Query query1 = qp.parse(temp);
	//
	// // Hits hits = searcher.search(bq);
	//
	// /***** 排序 *****/
	//
	// /*
	// *
	// * 1.被排序的字段必須被索引過(Indexecd)，在索引時不能 用 Field.Index.TOKENIZED
	// *
	// * (用UN_TOKENIZED可以正常實現.用NO時查詢正常，但排序不能正常設置升降序)
	// *
	// * 2.SortField類型
	// *
	// * SCORE、DOC、AUTO、STRING、INT、FLOAT、CUSTOM
	// *
	// * 此類型主要是根據字段的類型選擇
	// *
	// * 3.SortField的第三個參數代表是否是降序
	// *
	// * true:降序 false:升序
	// */
	//
	// // Sort sort = new Sort(new SortField[]{new SortField("id",
	// // SortField.INT, true)});
	//
	// // Hits hits = searcher.search(query,sort);
	//
	// /*
	// *
	// * 按日期排序
	// */
	//
	// // Sort sort = new Sort(new SortField[]{new SortField("createTime",
	// // SortField.INT, false)});
	//
	// /***** 過濾器 ******/
	//
	// // QueryParser qp1 = new QueryParser("content",analyzer);
	//
	// // Query fquery = qp1.parse("我");
	//
	// //
	//
	// // BooleanQuery bqf = new BooleanQuery();
	//
	// // bqf.add(fquery,BooleanClause.Occur.SHOULD);
	//
	// //
	//
	// // QueryFilter qf = new QueryFilter(bqf);
	//
	// Hits hits = searcher.search(query);
	//
	// Date end = new Date();
	//
	// System.out.println("檢索完成,用時" + (end.getTime() - start.getTime())
	// + "毫秒");
	//
	// return hits;
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// return null;
	//
	// }
	//
	// }

}
