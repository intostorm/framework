package com.ccesun.sample.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.plugins.search.SearchUtils;
import com.ccesun.sample.domain.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/app-context.xml"})
public class ContactServiceTest {

	@Autowired
	private ContactService contactService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		Contact contact = new Contact();
		contact.setName("五");
		contact.setPhone("15500000001");
		//contact.setAbc("1111");
		
		contactService.save(contact);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testSearch() {
		
		PageRequest pageRequest = new PageRequest(1, 10);
		
		// 查询条件，需在bean上设置过@SearchableField，且index不能是Index.NO
		Map<String, String> paramMap = new HashMap<String, String>() {{
			put("name", "五");
		}};
		
		Page<Contact> contactPage = SearchUtils.findPage(pageRequest, Contact.class, paramMap);
		
		for (Contact one : contactPage.getContent()) {
			System.out.println(one.getName());
			System.out.println(one.getPhone());
		}
	}

	@Test
	public void testFind() {
		//QCriteria criteria = new QCriteria();
		
		//System.out.println(contactService.find(criteria).size());
		String jpql = "select o from Contact o where o.id in (:ids)";
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("ids", Arrays.asList(376, 378));
		System.out.println(contactService.find(jpql, params).size());
				
		//String jpql = "select o from Contact o where o.id in (?)";
		//EntityManager entityManager = contactService.getDao().getEntityManager();
		//System.out.println(entityManager.createQuery(jpql).setParameter("ids", Arrays.asList(376, 377)).getResultList().size());
	}
	
	@Test
	public void testCount() {
	}

	@Test
	public void testRemove() {
	}

}
