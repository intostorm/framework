package com.ccesun.sample.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccesun.sample.domain.SysUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/app-context.xml"})
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
	}

	@Test
	public void testRemove() {
	}

}
