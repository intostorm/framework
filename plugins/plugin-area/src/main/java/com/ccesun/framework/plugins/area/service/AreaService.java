package com.ccesun.framework.plugins.area.service;

import java.util.List;

import com.ccesun.framework.core.service.ISearchFormSupportService;
import com.ccesun.framework.plugins.area.domain.Area;

public interface AreaService extends ISearchFormSupportService<Area, String> {

	/**
	 * 读取指定地区的子地区
	 * @param areaCode
	 * @return
	 */
	public List<Area> findSubAreas(String areaCode);
	/**
	 * 读取指定地区以及其全部子地区,指定地区在List的第一位
	 * @param areaCode
	 * @return
	 */
	public List<Area> readAreaAndSubAreas(String areaCode);
}