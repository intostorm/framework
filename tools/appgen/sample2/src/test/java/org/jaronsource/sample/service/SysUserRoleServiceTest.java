package org.jaronsource.sample.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/app-context.xml"})
public class SysUserRoleServiceTest {

	@Autowired
	private SysUserRoleService sysUserRoleService;
	
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
	}
	
	@Test
	public void testCount() {
	}

	@Test
	public void testRemove() {
	}

}
