package com.ccesun.framework.plugins.area.web.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;

import com.ccesun.framework.core.annotation.DocDescription;
import com.ccesun.framework.plugins.area.domain.Area;
import com.ccesun.framework.util.NumberUtils;

@DocDescription("根据地区编码进行相关加载,按type进行加载")
public class LookupAreaObjTag extends AreaNameTag {

	private static final long serialVersionUID = -6889214742917288270L;

	/**
	 * 处理后的结果
	 * 
	 * @author mawm at 2013-3-19 上午10:50:57
	 */
	@DocDescription("注册到全局context的键值(key名称),可以在页面其他位置获取到结果值")
	protected String var;
	@DocDescription("类型,默认值=0,0=lookupArea对象,1=lookupAreaAndNextLevelAreas返回`Map<String, Area>`类型,2=lookupNextLevelAreas返回`Map<String, Area>`类型,3=lookupNextLevelAreasFuzzy返回`Map<String, Area>`类型,4=areaHelper.lookupSubAreas,5=areaHelper.lookupSubAreas(启用参数endLevel)")
	protected String type;
	@DocDescription("结束级别,只针对type=5的时候被调用")
	private String endLevel;

	public void setEndLevel(String endLevel) {
		this.endLevel = endLevel;
	}

	public int doStartTag() throws JspException {
		initAreaHelper();
		Object result = null;
		int t = NumberUtils.toInt(type, 0);
		switch (t) {
		case 1:
			Map<String, Area> resultMap = areaHelper
					.lookupAreaAndNextLevelAreas(areaCode);
			result = resultMap;
			break;
		case 2:
			Map<String, Area> resultMap2 = areaHelper
					.lookupNextLevelAreas(areaCode);
			result = resultMap2;
			break;
		case 3:
			Map<String, Area> resultMap3 = areaHelper
					.lookupNextLevelAreasFuzzy(areaCode);
			result = resultMap3;
			break;

		case 4:

			Map<String, Area> resultMap4 = areaHelper.lookupSubAreas(areaCode);
			result = resultMap4;
			break;
		case 5:

			int el = NumberUtils.toInt(endLevel);
			Map<String, Area> resultMap5 = areaHelper.lookupSubAreas(areaCode,
					el);
			result = resultMap5;
			break;

		case 0:
		default:
			result = areaHelper.lookupArea(areaCode);
			break;
		}

		pageContext.setAttribute(var, result);
		return SKIP_BODY;
	}

	public void setVar(String var) {
		this.var = var;
	}

}
