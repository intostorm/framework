package com.ccesun.sample.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.SearchForm;
import com.ccesun.framework.util.JsonUtils;
import com.ccesun.sample.domain.SysUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/app-context.xml" })
public class SysUserServiceTest {

	@Autowired
	private SysUserService sysUserService;

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
		String jpql = "select o from SysUser o where o.userName = ?";
		List<SysUser> result = sysUserService.executeQuery(jpql, "admin");
		System.out.println(result);
	}

	@Test
	public void testCount() {
		SearchForm searchForm = new SearchForm();
		searchForm.addFormEntry("userId_in_splitint", "1,2,3");
		Page<SysUser> findPage = sysUserService.findPage(searchForm);

		System.out.println(findPage);
		List<SysUser> content = findPage.getContent();
		for (SysUser u : content) {
			System.out.println(String.format("%s,%s", u.getUserId(),
					u.getUserName()));
		}
		// System.out.println(JsonUtils.parseJson(findPage));

		searchForm = new SearchForm();
		searchForm.addFormEntry("userName_in_split", "aaa,bbb,3");
		findPage = sysUserService.findPage(searchForm);

		System.out.println(findPage);
		content = findPage.getContent();
		for (SysUser u : content) {
			System.out.println(String.format("%s,%s", u.getUserId(),
					u.getUserName()));
		}

		// lengweichun
	}

	@Test
	public void testRemove() {
	}

}
