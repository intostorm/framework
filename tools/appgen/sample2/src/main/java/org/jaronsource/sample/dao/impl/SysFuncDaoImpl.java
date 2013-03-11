package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysFunc;
import org.jaronsource.sample.dao.SysFuncDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysFuncDaoImpl extends GenericDao<SysFunc, String> implements SysFuncDao {

}