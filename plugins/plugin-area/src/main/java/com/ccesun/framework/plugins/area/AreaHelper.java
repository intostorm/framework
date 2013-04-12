package com.ccesun.framework.plugins.area;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ccesun.framework.core.AppContext;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.QCriteria.Op;
import com.ccesun.framework.plugins.area.domain.Area;
import com.ccesun.framework.plugins.area.service.AreaService;


@Component
@Lazy
public class AreaHelper {

	/**省级**/
	public static final int SHENG_LEVEL = 1;
    /**市级**/
	public static final int SHI_LEVEL = 2;
    /**县级**/
	public static final int XIAN_LEVEL = 3;
    /**乡级**/
	public static final int XIANG_LEVEL = 4;
    /**村级**/
	public static final int CUN_LEVEL = 5;
    /**社级**/
	public static final int SHE_LEVEL = 6;
    /**户级**/
	public static final int HU_LEVEL = 7;
    /**个人级**/
	public static final int PERSON_LEVEL = 8;

	public static final int SHENG_CODE_LENGTH = 2;

	public static final int SHI_CODE_LENGTH = 2;

	public static final int XIAN_CODE_LENGTH = 2;

	public static final int XIANG_CODE_LENGTH = 3;

	public static final int CUN_CODE_LENGTH = 3;

	public static final int SHE_CODE_LENGTH = 3;

	public static final int HU_CODE_LENGTH = 4;

	public static final int PERSON_CODE_LENGTH = 2;

	private static final String NAV_SEP = "-";
	
	private static final int cacheAreaLevel = AppContext.getInstance().getInt("plugin.area.cacheAreaLevel");

	//private static final String AREA_CACHE_PATH = AreaHelper.class.getResource("/").getPath() + "../conf/"; 
	//private static final String AREA_CACHE_FILE = "area-cache.xml"; 
	
	@Autowired
	private AreaService areaService;

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AreaHelper.class);
	
	/*private void updateCacheFromDB() {
		
		List<Area> areaList = areaService.find(new QCriteria());

		Document document = DocumentHelper.createDocument(); 
		Element root = document.addElement("areas"); 
		
		for (Area area : areaList) {
			Element areaElement = root.addElement("area"); 
			areaElement.addAttribute("areaCode", area.getAreacode()); 
			areaElement.addAttribute("areaName", area.getAreaName()); 
			areaElement.addAttribute("fullAreaName", area.getFullAreaName()); 
			areaElement.addAttribute("available", String.valueOf(area.isAvailable()));
		} 									
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8"); 
			File file = new File(AREA_CACHE_PATH + AREA_CACHE_FILE);
			if(!file.exists()){
				File dir = file.getParentFile();
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			XMLWriter output = new XMLWriter(new FileWriter(AREA_CACHE_PATH + AREA_CACHE_FILE), format);
			output.write(document); 
			output.close(); 
		} catch (IOException e) {
			log.error("An error encounterd while reading area cache!", e);
		}

	}*/

	public static Map<String, Area> areas = new TreeMap<String, Area>();

	public static Map<String, Area> getAreas() {
		return areas;
	}
	
	@PostConstruct
	public void init() {
		
		List<Area> areaList = areaService.findChildrenByPrarentCodeAndEndLevel(null, cacheAreaLevel);
    	HashMap<String, Area> areasFromCache = new HashMap<String, Area>(areaList.size());
    	for(Area area : areaList){
    		areasFromCache.put(area.getAreacode(), area);
    	}
    	AreaHelper.areas = areasFromCache;
	}

	public void setAreas(Map<String, Area> areas) {
		AreaHelper.areas = areas;
	}

	public void update() {
		clear();
		init();
	}

	public void clear() {
		areas.clear();
	}

	public Area lookupArea(String areaCode) {
		int level = AreaHelper.getLevel(areaCode);
		Area area = null;
		if(level > cacheAreaLevel){
			area = areaService.findOne(new QCriteria().addEntry("areacode", Op.EQ, areaCode));
		}else{
			area = areas.get(areaCode);
		}
		return area;
	}
	
	public String lookupAreaName(String areaCode) {
		Area area = lookupArea(areaCode);
		return area == null ? StringUtils.EMPTY : area.getAreaName();
	}
	
	public String lookupFullAreaName(String areaCode) {
		Area area = lookupArea(areaCode);
		return area == null ? StringUtils.EMPTY : area.getFullAreaName();
	}

	public boolean isValid(String areaCode) {
		return lookupArea(areaCode) != null;
	}

	public Map<String, Area> lookupAreaAndNextLevelAreas(String areaCode) {
		
		Map<String, Area> results = new TreeMap<String, Area>();
		
		if (!isValid(areaCode))
			return results;
		
		Area parent = lookupArea(areaCode);
		if(parent != null){
			results.put(areaCode, parent);
		}
		results.putAll(lookupNextLevelAreas(areaCode));
		return results;
	}
	
	public Map<String, Area> lookupNextLevelAreas(String areaCode) {
		
		Map<String, Area> results = new TreeMap<String, Area>();
		
		if (!isValid(areaCode))
			return results;
		
		int level = getLevel(areaCode);
		int nextLevel = level + 1;

		if(nextLevel > cacheAreaLevel){
			List<Area> tmpResult = areaService.findChildrenByPrarentCodeAndEndLevel(areaCode, nextLevel);
			for (Area area : tmpResult) {
				
				if (area.getAreacode().startsWith(areaCode)
						&& !area.getAreacode().equals(areaCode)
						&& getLevel(area.getAreacode()) <= nextLevel) {
	
					results.put(area.getAreacode(), area);
				}
			}
		} else {	
		
			Iterator<Map.Entry<String, Area>> iter = areas.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Area> entry = iter.next();
				if (getLevel(entry.getKey()) == nextLevel
						&& entry.getKey().startsWith(areaCode)) {
					results.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return results;
	}

	public Map<String, Area> lookupNextLevelAreasFuzzy(String areaCode) {

		Map<String, Area> results = new TreeMap<String, Area>();
		
		if (!isValid(areaCode))
			return results;
		
		int level = getLevelFuzzy(areaCode);
		int nextLevel = level + 1;

		Iterator<Map.Entry<String, Area>> iter = areas.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Area> entry = iter.next();
			if (getLevel(entry.getKey()) == nextLevel
					&& entry.getKey().startsWith(areaCode)) {

				results.put(entry.getKey(), entry.getValue());
			}
		}
		return results;
	}

	public Map<String, Area> lookupSubAreas(String areaCode) {

		return lookupSubAreas(areaCode, CUN_LEVEL);
	}

	public Map<String, Area> lookupSubAreas(String areaCode, int endLevel) {
		
		Map<String, Area> results = new TreeMap<String, Area>();
		
		if (!isValid(areaCode))
			return results;
		
		if(endLevel > cacheAreaLevel){
			List<Area> tmpResult = areaService.findChildrenByPrarentCodeAndEndLevel(areaCode, endLevel);
			for (Area area : tmpResult) {
				
				if (area.getAreacode().startsWith(areaCode)
						&& !area.getAreacode().equals(areaCode)
						&& getLevel(area.getAreacode()) <= endLevel) {
	
					results.put(area.getAreacode(), area);
				}
			}
		}	
		
		else {	
			Iterator<Map.Entry<String, Area>> iter = areas.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Area> entry = iter.next();
				if (entry.getKey().startsWith(areaCode)
						&& !entry.getKey().equals(areaCode)
						&& getLevel(entry.getKey()) <= endLevel) {
	
					results.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return results;
	}

	public  static int getLevel(String areaCode) {

		if (areaCode == null || areaCode.length() == 0)
			return 0;

		if (areaCode.length() == SHENG_CODE_LENGTH)
			return SHENG_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH)
			return SHI_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH)
			return XIAN_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH)
			return XIANG_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH)
			return CUN_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH)
			return SHE_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH + HU_CODE_LENGTH)
			return HU_LEVEL;

		if (areaCode.length() == SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH + HU_CODE_LENGTH + PERSON_CODE_LENGTH)
			return PERSON_LEVEL;

		return 0;
	}
	
	public static String getLevelName(String areaCode){
		int level = getLevel(areaCode);
		String levelName = StringUtils.EMPTY;
		switch(level){
		case SHENG_LEVEL:
			levelName = "省";
			break;

		case SHI_LEVEL:
			levelName = "市";
			break;

		case XIAN_LEVEL:
			levelName = "县";
			break;

		case XIANG_LEVEL:
			levelName = "乡/镇";
			break;

		case CUN_LEVEL:
			levelName = "村";
			break;

		case SHE_LEVEL:
			levelName = "组/社";
			break;

		case HU_LEVEL:
			levelName = "户";
			break;

		case PERSON_LEVEL:
			levelName = "人";
			break;
		default:
			levelName = "未知";
			break;
		}
		return levelName;
	}

	public static int getLength(int level) {

		int length = 0;

		switch (level) {
		case SHENG_LEVEL:
			length = SHENG_CODE_LENGTH;
			break;

		case SHI_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH;
			break;

		case XIAN_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH;
			break;

		case XIANG_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH
					+ XIANG_CODE_LENGTH;
			break;

		case CUN_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH
					+ XIANG_CODE_LENGTH + CUN_CODE_LENGTH;
			break;

		case SHE_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH
					+ XIANG_CODE_LENGTH + CUN_CODE_LENGTH + SHE_CODE_LENGTH;
			break;

		case HU_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH
					+ XIANG_CODE_LENGTH + CUN_CODE_LENGTH + SHE_CODE_LENGTH
					+ HU_CODE_LENGTH;
			break;

		case PERSON_LEVEL:
			length = SHENG_CODE_LENGTH + SHI_CODE_LENGTH + XIAN_CODE_LENGTH
					+ XIANG_CODE_LENGTH + CUN_CODE_LENGTH + SHE_CODE_LENGTH
					+ HU_CODE_LENGTH + PERSON_CODE_LENGTH;
			break;

		default:
			break;
		}

		return length;
	}
	/**
	 * 获得子地区长度
	 * @param areaCode
	 * @return
	 */
	public static final int getSubAreaLength(String areaCode){
		int level = getLevel(areaCode);
		return getLength(++level);
	}
	/**
	 * 是否有子地区,根据指定地区级别判断,社级以上返回true
	 * @param areaCode
	 * @return
	 */
	public static final boolean hasSubArea(String areaCode){
		int level = getLevel(areaCode);
		return level < SHE_LEVEL;
	}

	public static int getLevelFuzzy(String areaCode) {

		if (areaCode.length() < SHENG_CODE_LENGTH)
			return 0;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH)
			return SHENG_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH)
			return SHI_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH)
			return XIAN_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH)
			return XIANG_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH)
			return CUN_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH + HU_CODE_LENGTH)
			return SHE_LEVEL;

		if (areaCode.length() < SHENG_CODE_LENGTH + SHI_CODE_LENGTH
				+ XIAN_CODE_LENGTH + XIANG_CODE_LENGTH + CUN_CODE_LENGTH
				+ SHE_CODE_LENGTH + HU_CODE_LENGTH + PERSON_CODE_LENGTH)
			return HU_LEVEL;

		return PERSON_LEVEL;
	}

	public String getFullCunCodeByAreaCode(String areaCode) {
		if (!isPersonIdValid(areaCode))
			return null;
		return areaCode.substring(0, getLength(CUN_LEVEL));
	}

	public String getFullSheCodeByAreaCode(String areaCode) {

		return areaCode.substring(0, getLength(SHE_LEVEL));
	}

	public String getFamilyCodeByAreaCode(String areaCode) {

		return areaCode.substring(getLength(SHE_LEVEL), getLength(SHE_LEVEL)
				+ HU_CODE_LENGTH);
	}

	public String getXiangCodeByAreaCode(String areaCode) {

		return areaCode.substring(getLength(XIAN_LEVEL), getLength(XIAN_LEVEL)
				+ XIANG_CODE_LENGTH);
	}

	public String getCunCodeByAreaCode(String areaCode) {

		return areaCode.substring(getLength(XIANG_LEVEL),
				getLength(XIANG_LEVEL) + CUN_CODE_LENGTH);
	}

	public String getSheCodeByAreaCode(String areaCode) {

		return areaCode.substring(getLength(CUN_LEVEL), getLength(CUN_LEVEL)
				+ SHE_CODE_LENGTH);
	}

	public String getFullFamilyCodeByAreaCode(String areaCode) {
		return getFullSheCodeByAreaCode(areaCode)
				+ getFamilyCodeByAreaCode(areaCode);
	}

	public String getPersonIdOfFamilyByAreaCode(String areaCode) {

		return areaCode.substring(getLength(HU_LEVEL), getLength(HU_LEVEL)
				+ PERSON_CODE_LENGTH);
	}

	public boolean isPersonIdValid(String personId) {
		if (StringUtils.isBlank(personId))
			return false;
		if (personId.length() < getLength(PERSON_LEVEL))
			return false;
		return true;
	}

	public String getParentAreaCode(String areaCode) {

		if (!isValid(areaCode))
			return null;

		int level = getLevel(areaCode);
		int parentLength = getLength(level - 1);

		return areaCode.substring(0, parentLength);
	}

	public Object convertAreaName(Object bean, String fieldName) {

		if (bean == null)
			return null;

		String oldValue = null;
		String newValue = null;
		try {
			oldValue = BeanUtils.getProperty(bean, fieldName);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}

		newValue = lookupAreaName(oldValue);

		try {
			BeanUtils.copyProperty(bean, fieldName, newValue);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

		return bean;
	}

	public <T> List<T> convertAreaName(List<T> beans, String fieldName) {

		List<T> results = new ArrayList<T>();
		for (Iterator<T> it = beans.iterator(); it.hasNext();) {
			T bean = it.next();
			if (bean.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(bean); i++) {
					convertAreaName(Array.get(bean, i), fieldName);
				}

			} else {
				convertAreaName(bean, fieldName);
			}
			results.add(bean);

		}
		return results;
	}

	public String generateAreaNavigate(String areaCode) {
		return generateAreaNavigate(areaCode, NAV_SEP);
		
	}
	public String generateAreaNavigate(String areaCode, String sep) {
		
		int level = getLevelFuzzy(areaCode);
		
		if (level > SHE_LEVEL)
			level = SHE_LEVEL;
		
		List<String> tempList = new ArrayList<String>();
		
		for (int i = 1; i <= level; i++) {
			tempList.add(lookupAreaName(areaCode.substring(0, getLength(i))));
		}
		
		return StringUtils.join(tempList.iterator(), sep);
		
	}
	public String generateAreaNavigate(String areaCode, String sep, int startLevel) {
		
		int level = getLevelFuzzy(areaCode);
		
		if (level > SHE_LEVEL)
			level = SHE_LEVEL;
		
		List<String> tempList = new ArrayList<String>();
		
		for (int i = startLevel; i <= level; i++) {
			tempList.add(StringUtils.trimToEmpty(lookupAreaName(areaCode.substring(0, getLength(i)))));
		}
		
		return StringUtils.join(tempList.iterator(), sep);
		
	}
	public String generateAreaNavigate(String areaCode, String sep, int startLevel, int endLevel) {

		int level = getLevelFuzzy(areaCode);

		if (level > SHE_LEVEL)
			level = SHE_LEVEL;

		List<String> tempList = new ArrayList<String>();
		level = level > endLevel ? endLevel : level;
		for (int i = startLevel; i <= level; i++) {
			tempList.add(StringUtils.trimToEmpty(lookupAreaName(areaCode.substring(0, getLength(i)))));
		}

		return StringUtils.join(tempList.iterator(), sep);

	}

}
