package com.ccesun.framework.plugins.area.dao;

import org.springframework.stereotype.Repository;

import com.ccesun.framework.core.dao.support.GenericDao;
import com.ccesun.framework.plugins.area.domain.Area;

@Repository
public class AreaDaoImpl extends GenericDao<Area, String> implements AreaDao {

}