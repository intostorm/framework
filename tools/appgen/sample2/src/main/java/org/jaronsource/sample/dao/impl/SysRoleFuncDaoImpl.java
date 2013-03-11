package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysRoleFunc;
import org.jaronsource.sample.dao.SysRoleFuncDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleFuncDaoImpl extends GenericDao<SysRoleFunc, Integer> implements SysRoleFuncDao {

}