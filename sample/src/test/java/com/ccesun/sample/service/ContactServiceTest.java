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
