package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.SysDictDao;
import org.jaronsource.sample.domain.SysDict;
import org.jaronsource.sample.service.SysDictService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends SearchFormSupportService<SysDict, Integer> implements SysDictService {

	@Autowired
	private SysDictDao sysDictDao;
	
	@Override
	public IDao<SysDict, Integer> getDao() {
		return sysDictDao;
	}

}