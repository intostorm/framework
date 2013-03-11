package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.SysDict;
import org.jaronsource.sample.dao.SysDictDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictDaoImpl extends GenericDao<SysDict, Integer> implements SysDictDao {

}