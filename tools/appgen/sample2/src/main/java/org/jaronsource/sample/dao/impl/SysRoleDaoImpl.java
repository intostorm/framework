package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysRole;
import org.jaronsource.sample.dao.SysRoleDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleDaoImpl extends GenericDao<SysRole, Integer> implements SysRoleDao {

}