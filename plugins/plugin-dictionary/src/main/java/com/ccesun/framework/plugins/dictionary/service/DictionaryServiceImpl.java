package com.ccesun.framework.plugins.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.framework.plugins.dictionary.dao.DictionaryDao;
import com.ccesun.framework.plugins.dictionary.domain.Dictionary;

@Service
public class DictionaryServiceImpl extends SearchFormSupportService<Dictionary, Integer> implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Override
	public IDao<Dictionary, Integer> getDao() {
		return dictionaryDao;
	}

}