package com.ccesun.framework.plugins.dictionary.dao;

import org.springframework.stereotype.Repository;

import com.ccesun.framework.core.dao.support.GenericDao;
import com.ccesun.framework.plugins.dictionary.domain.Dictionary;

@Repository
public class DictionaryDaoImpl extends GenericDao<Dictionary, Integer> implements DictionaryDao {

}