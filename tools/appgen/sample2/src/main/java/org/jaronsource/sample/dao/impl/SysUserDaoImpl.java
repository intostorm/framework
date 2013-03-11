package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysUser;
import org.jaronsource.sample.dao.SysUserDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserDaoImpl extends GenericDao<SysUser, Integer> implements SysUserDao {

}