package com.ccesun.framework.plugins.area.service;

import java.util.List;

import com.ccesun.framework.core.service.ISearchFormSupportService;
import com.ccesun.framework.plugins.area.domain.Area;

public interface AreaService extends ISearchFormSupportService<Area, String> {

	/**
	 * 读取指定地区的子地区
	 * @param areaCode
	 * @param endAreaLevel
	 * @return
	 */
	public List<Area> findChildrenByPrarentCodeAndEndLevel(String parentCode, int endAreaLevel);
	
}