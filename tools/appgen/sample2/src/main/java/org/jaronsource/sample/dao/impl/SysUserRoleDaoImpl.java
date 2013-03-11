package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysUserRole;
import org.jaronsource.sample.dao.SysUserRoleDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserRoleDaoImpl extends GenericDao<SysUserRole, Integer> implements SysUserRoleDao {

}