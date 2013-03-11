package com.ccesun.framework.plugins.area.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.QCriteria.Op;
import com.ccesun.framework.core.service.SearchFormSupportService;
import com.ccesun.framework.plugins.area.AreaHelper;
import com.ccesun.framework.plugins.area.dao.AreaDao;
import com.ccesun.framework.plugins.area.domain.Area;

@Service
public class AreaServiceImpl extends SearchFormSupportService<Area, String> implements AreaService {

	@Autowired
	private AreaDao areaDao;
	
	@Override
	public IDao<Area, String> getDao() {
		return areaDao;
	}

	@Override
	public List<Area> findSubAreas(String areaCode) {
		QCriteria query = new QCriteria();
		int len = AreaHelper.getSubAreaLength(areaCode);
		query.where("LENGTH(o.areacode)=?", len);
		query.addEntry("areacode", Op.LIKE, areaCode+'%');
		return find(query);
	}

	@Override
	public List<Area> readAreaAndSubAreas(String areaCode) {
		Area area = findByPk(areaCode);
		List<Area> subAreas = findSubAreas(areaCode);
		subAreas.add(0, area);
		
		return subAreas;
	}

}