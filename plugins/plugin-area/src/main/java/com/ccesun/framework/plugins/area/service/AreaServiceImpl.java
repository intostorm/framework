package com.ccesun.framework.plugins.area.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
	public List<Area> findChildrenByPrarentCodeAndEndLevel(String parentCode, int endAreaLevel) {
		
		QCriteria query = new QCriteria();
		if (StringUtils.isNotBlank(parentCode)) {
			query.addEntry("areacode", Op.LIKE, parentCode + "%");
		}
		
		if (endAreaLevel > 0) {
			int len = AreaHelper.getLength(endAreaLevel);
			query.where("LENGTH(o.areacode) <= ?", len);
		}
		
		return find(query);
	}

}