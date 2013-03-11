package com.ccesun.sample.dao.impl;

import com.ccesun.sample.domain.SysFunc;
import com.ccesun.sample.dao.SysFuncDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysFuncDaoImpl extends GenericDao<SysFunc, Integer> implements SysFuncDao {

}