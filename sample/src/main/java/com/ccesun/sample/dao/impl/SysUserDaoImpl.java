package com.ccesun.sample.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ccesun.framework.core.dao.support.GenericDao;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.QCriteria.Op;
import com.ccesun.framework.plugins.security.dao.ISecurityDao;
import com.ccesun.framework.plugins.security.domain.IPermission;
import com.ccesun.framework.plugins.security.domain.ISecurityUser;
import com.ccesun.sample.dao.SysUserDao;
import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.domain.SysUser;

@Repository
public class SysUserDaoImpl extends GenericDao<SysUser, Integer> implements SysUserDao, ISecurityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public ISecurityUser getSecurityUser(String... username) {
		Assert.notNull(username);
		Assert.isTrue(username.length > 0);
		
		QCriteria criteria = new QCriteria();
		criteria.addEntry("userName", Op.EQ, username[0]);
		
		SysUser sysUser = findOne(criteria);

		return sysUser;
	}

	@Override
	public Collection<IPermission> loadPermissions(ISecurityUser securityUser) {
		
		Assert.notNull(securityUser);
		
		String sql = "select f.* from sys_func f, sys_role_func rf, sys_role r, sys_user_role ur, sys_user u " +
				"where f.func_id = rf.func_id and r.role_id = rf.role_id and r.role_id = ur.role_id and u.user_id = ur.user_id " +
				"and u.user_name = ?";
		
		String[] params = new String[] {securityUser.getUserName()};
		List<SysFunc> funcList = jdbcTemplate.query(sql, params, new RowMapper<SysFunc>() {

			@Override
			public SysFunc mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysFunc sysFunc = new SysFunc();
				sysFunc.setFuncId(rs.getInt("func_id"));
				sysFunc.setFuncName(rs.getString("func_name"));
				sysFunc.setFuncUrl(rs.getString("func_url"));
				sysFunc.setFuncRemarks(rs.getString("func_remarks"));
				sysFunc.setFuncGroupCode(rs.getString("func_groupcode"));
				sysFunc.setFuncOrder(rs.getInt("func_order"));
				
				SysFunc parent = new SysFunc();
				parent.setFuncId(rs.getInt("parent_id"));
				sysFunc.setParent(parent);
				
				return sysFunc;
			}
		});
		
		return new ArrayList<IPermission>(funcList);
	}

}