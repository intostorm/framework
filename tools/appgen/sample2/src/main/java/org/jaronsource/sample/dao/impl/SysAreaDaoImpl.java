package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysArea;
import org.jaronsource.sample.dao.SysAreaDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysAreaDaoImpl extends GenericDao<SysArea, Integer> implements SysAreaDao {

}