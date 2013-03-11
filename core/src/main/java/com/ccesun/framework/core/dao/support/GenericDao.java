package com.ccesun.framework.core.dao.support;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ccesun.framework.core.dao.support.QCriteria.WhereEntry;
import com.ccesun.framework.util.StringUtils;

public class GenericDao<T extends IEntity<I>, I extends Serializable> implements IDao<T, I> {

    protected Log log = LogFactory.getLog(getClass());

    @PersistenceContext
    protected EntityManager entityManager;  //JPA 实例管理对象

    private Class<T> clazz;  
    
    @SuppressWarnings(value = "unchecked")
    public GenericDao() {

        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

        if (types[0] instanceof ParameterizedType) {
            // If the class has parameterized types, it takes the raw type.
            ParameterizedType type = (ParameterizedType) types[0];
            clazz = (Class<T>) type.getRawType();
        } else {
            clazz = (Class<T>) types[0];
        }
    }

    public GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int execute(String jpql, Object...params ){
    	if(params.length > 0){
    		int add = jpql.indexOf("?0") > -1 ? 0 : 1;
    		Query query = entityManager.createQuery(jpql);
    		for(int i = 0, len = params.length; i < len; i++){
    			query.setParameter(i + add, params[i]);
    		}
    		return query.executeUpdate();
    	}else{
    		Query query = entityManager.createQuery(jpql);
    		return query.executeUpdate();
    	}
    }

    public int execute(String jpql, Map<String, ?> params ){
		Query query = entityManager.createQuery(jpql.toString());
		setQueryParam(query, params);
		return query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public <X> List<X> executeQuery(String jpql, Map<String, ?> params){
    	Query query = entityManager.createQuery(jpql.toString());
		setQueryParam(query, params);
		return query.getResultList();
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public <X> List<X> executeQuery(String jpql, Object... params){
		if(params.length > 0){
			int add = jpql.indexOf("?0") > -1 ? 0 : 1;
    		Query query = entityManager.createQuery(jpql);
    		for(int i = 0, len = params.length; i < len; i++){
    			query.setParameter(i+add, params[i]);
    		}
    		return query.getResultList();
    	}else{
    		Query query = entityManager.createQuery(jpql);
    		return query.getResultList();
    	}
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public <X> X executeQueryOne(String jpql, Object... params) {
    	Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		query.setMaxResults(1);
		List<X> tmpList = query.getResultList();
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X executeQueryOne(String jpql, Map<String, ?> params) {
		Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		query.setMaxResults(1);
		List<X> tmpList = query.getResultList();
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}
	
	@Override
	public <X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Object... params) {
		Page<X> result = null;
		long count = count(jpql, params);
		
		if (count > 0) {
			List<X> content = executeQuery(pageRequest, jpql, params);
			result = new Page<X>(content, pageRequest, count);
		} else {
			result = new Page<X>(new ArrayList<X>(), pageRequest, count);
		}
			
		return result;
	}

	@Override
	public <X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		Page<X> result = null;
		long count = count(jpql, params);
		
		if (count > 0) {
			List<X> content = executeQuery(pageRequest, jpql, params);
			result = new Page<X>(content, pageRequest, count);
		} else {
			result = new Page<X>(new ArrayList<X>(), pageRequest, count);
		}
			
		return result;
	}
	
	@Override
	public T save(T target) {
		if (target.isNew()) {
            entityManager.persist(target);
        } else {
            entityManager.merge(target);
        }
		return target;
	}

	public void flush() {
		entityManager.flush();
	}

	@Override
	public T[] save(T... targets) {
		for (T target : targets) {
            save(target);
        }
		return targets;
	}

	@Override
	public void remove(T target) {
		entityManager.remove(target);
	}

	@Override
	public void remove(T... targets) {
		for (T target : targets) {
            entityManager.remove(target);
        }
	}
	
	@Override
	public void remove(I id) {
		remove(findByPk(id));
	}
	
	@Override
	public boolean exists(I id) {
		return findByPk(id) != null;
	}

	@Override
	public T findByPk(I id) {
		return (T) entityManager.find(clazz, id);
	}
	
	@Override
	public T findReferenceByPk(I id) {
		return (T) entityManager.getReference(clazz, id);
	}

	@Override
	public T findOne(QCriteria criteria) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder whereSqlBuilder = new StringBuilder();
        
        processFetch(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);

        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
        jpqlDataBuilder.append(whereSqlBuilder);

        TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
        setQueryParam(query, parameterMap);
        query.setMaxResults(1);
		List<T> tmpList = query.getResultList();
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@Override
	public T findOne(String jpql, Object... params) {
		
		TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
		setQueryParam(query, params);
		query.setMaxResults(1);
		
		List<T> tmpList = query.getResultList();
		
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}
	
	@Override
	public T findOne(String jpql, Map<String, ?> params) {
		
		TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
		for(Map.Entry<String, ?> entry : params.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.setMaxResults(1);
		
		List<T> tmpList = query.getResultList();
		
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@Override
	public List<T> findAll() {
		return find((Sort) null, new QCriteria());
	}
	
	@Override
	public List<T> find(QCriteria criteria) {

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder whereSqlBuilder = new StringBuilder();
        
        processFetch(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);

        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
        jpqlDataBuilder.append(whereSqlBuilder);

        TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
        setQueryParam(query, parameterMap);

        return query.getResultList();
	}


	@Override
	public List<T> find(String jpql, Object... params) {
		
		TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
		setQueryParam(query, params);
		
		return query.getResultList();
	}
	
	@Override
	public List<T> find(String jpql, Map<String, ?> params) {
		
		TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
		for(Map.Entry<String, ?> entry : params.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}
	
	@Override
	public long count(QCriteria criteria) {

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        StringBuilder whereSqlBuilder = new StringBuilder();

        processFetchCount(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);
        

        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT count(o) FROM %s o", clazz.getSimpleName()));
        
        jpqlDataBuilder.append(whereSqlBuilder);

        Query query = entityManager.createQuery(jpqlDataBuilder.toString());
        
        setQueryParam(query, parameterMap);

        return (Long) query.getSingleResult();
	}
	
	@Override
	public long count(String jpql, Object... params) {
		
		StringBuilder countBuilder = new StringBuilder("SELECT COUNT(o) FROM ");
		String tm = jpql.substring(jpql.toLowerCase().indexOf("from ") + 5);
		tm = tm.replaceAll("(?i) fetch", "");
		countBuilder.append(tm);
		
		TypedQuery<Long> query = entityManager.createQuery(countBuilder.toString(), Long.class);
		setQueryParam(query, params);
		boolean hasGroupBy = jpql.toLowerCase().indexOf("group by") > -1;
		if (hasGroupBy)
			return query.getResultList().size();
		else
			return query.getSingleResult();
	}

	@Override
	public long count(String jpql, Map<String, ?> params) {
		StringBuilder countBuilder = new StringBuilder("SELECT COUNT(o) FROM ");
		String tm = jpql.substring(jpql.toLowerCase().indexOf("from ") + 5);
		tm = tm.replaceAll("(?i) fetch", "");
		countBuilder.append(tm);
		
		TypedQuery<Long> query = entityManager.createQuery(countBuilder.toString(), Long.class);
		for(Map.Entry<String, ?> entry : params.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		boolean hasGroupBy = jpql.toLowerCase().indexOf("group by") > -1;
		if (hasGroupBy)
			return query.getResultList().size();
		else
			return query.getSingleResult();
	}
	
	@Override
	public List<T> find(Sort sort, QCriteria criteria) {
		
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        StringBuilder whereSqlBuilder = new StringBuilder();

        processFetch(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);
        
        StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
        
        jpqlDataBuilder.append(whereSqlBuilder);
        jpqlDataBuilder.append(sortBuilder);

        TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
        
        setQueryParam(query, parameterMap);
        
        return query.getResultList();
	}
	
	@Override
	public List<T> find(PageRequest pageRequest, QCriteria criteria) {
		
		Sort sort = pageRequest.getSort();
		
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder whereSqlBuilder = new StringBuilder();

        processFetch(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);

        StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
        
        jpqlDataBuilder.append(whereSqlBuilder);
        jpqlDataBuilder.append(sortBuilder);

        TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
        
        setQueryParam(query, parameterMap);
        query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());
        
        return query.getResultList();
	}

	@Override
	public List<T> find(Sort sort, String jpql, Object... params) {
		
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		setQueryParam(query, params);
		
		return query.getResultList();
	}
	
	@Override
	public List<T> find(Sort sort, String jpql, Map<String, ?> params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		for(Map.Entry<String, ?> entry : params.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return query.getResultList();
	}
	
	@Override
	public List<T> find(PageRequest pageRequest, String jpql, Object... params) {
		
		Sort sort = pageRequest.getSort();
		
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		setQueryParam(query, params);
		
		query.setFirstResult(pageRequest.getOffset());
		query.setMaxResults(pageRequest.getPageSize());
		
		return query.getResultList();
	}

	@Override
	public List<T> find(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		
		Sort sort = pageRequest.getSort();
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		for(Map.Entry<String, ?> entry : params.entrySet()){
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		query.setFirstResult(pageRequest.getOffset());
		query.setMaxResults(pageRequest.getPageSize());
		
		return query.getResultList();
	}
	
	@Override
	public Page<T> findPage(PageRequest pageRequest, QCriteria criteria) {
		Page<T> result = null;
		long count = count(criteria);
		
		if (count > 0) {
			List<T> content = find(pageRequest, criteria);
			result = new Page<T>(content, pageRequest, count);
		} else {
			result = new Page<T>(new ArrayList<T>(), pageRequest, count);
		}
			
		return result;
	}

	@Override
	public Page<T> findPage(PageRequest pageRequest, String jpql, Object... params) {
		Page<T> result = null;
		long count = count(jpql, params);
		
		if (count > 0) {
			List<T> content = find(pageRequest, jpql, params);
			result = new Page<T>(content, pageRequest, count);
		} else {
			result = new Page<T>(new ArrayList<T>(), pageRequest, count);
		}
			
		return result;
	}
	
	@Override
	public Page<T> findPage(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		Page<T> result = null;
		long count = count(jpql, params);
		
		if (count > 0) {
			List<T> content = find(pageRequest, jpql, params);
			result = new Page<T>(content, pageRequest, count);
		} else {
			result = new Page<T>(new ArrayList<T>(), pageRequest, count);
		}
			
		return result;
	}
	
	private void setQueryParam(Query query, Object[] params) {
		int position = 1;
		for (Object param : params) {
			query.setParameter(position++, param);
		}
	}
	
	private void setQueryParam(Query query, Map<String, ?> parameterMap) {
		for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Object value = parameterMap.get(key);
            query.setParameter(key, value);
        }
	}
	
	private void processFetchCount(QCriteria criteria, StringBuilder whereSqlBuilder) {
		
		List<Fetch> fetches = criteria.getFetchList();
		
		for (Fetch fetch : fetches) {
        	Fetch.JoinType joinType = fetch.getJoinType();
        	if (Fetch.JoinType.JOINTYPE_INNER.equals(joinType) || Fetch.JoinType.JOINTYPE_INNER_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" INNER JOIN o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_LEFT.equals(joinType) || Fetch.JoinType.JOINTYPE_LEFT_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" LEFT JOIN o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_RIGHT.equals(joinType) || Fetch.JoinType.JOINTYPE_RIGHT_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" RIGHT JOIN o.%s", fetch.getTargetObjectName()));
        	else 
        		whereSqlBuilder.append(String.format(" LEFT JOIN o.%s", fetch.getTargetObjectName()));
        }
		
	}

	private void processFetch(QCriteria criteria, StringBuilder whereSqlBuilder) {
		
		List<Fetch> fetches = criteria.getFetchList();
		
		for (Fetch fetch : fetches) {
        	Fetch.JoinType joinType = fetch.getJoinType();
        	if (Fetch.JoinType.JOINTYPE_INNER.equals(joinType))
        		whereSqlBuilder.append(String.format(" INNER JOIN o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_INNER_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" INNER JOIN FETCH o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_LEFT.equals(joinType))
        		whereSqlBuilder.append(String.format(" LEFT JOIN o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_LEFT_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" LEFT JOIN FETCH o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_RIGHT.equals(joinType))
        		whereSqlBuilder.append(String.format(" RIGHT JOIN o.%s", fetch.getTargetObjectName()));
        	else if (Fetch.JoinType.JOINTYPE_RIGHT_FETCH.equals(joinType))
        		whereSqlBuilder.append(String.format(" RIGHT JOIN FETCH o.%s", fetch.getTargetObjectName()));
        	else 
        		whereSqlBuilder.append(String.format(" LEFT JOIN o.%s", fetch.getTargetObjectName()));
        }
		
	}

	private void processWhere(QCriteria criteria, StringBuilder whereSqlBuilder, Map<String, Object> parameterMap) {
		
		List<QCriteria.ParamEntry> entries = criteria.getEntries();
		whereSqlBuilder.append(" WHERE 1=1");
		
        for (Iterator<QCriteria.ParamEntry> iteratorParams = entries.iterator(); iteratorParams.hasNext();) {
        	QCriteria.ParamEntry entry = iteratorParams.next();
        	
        	if(entry.getValue() == null){//如果参数是NULL
        		processWhereNullValue(whereSqlBuilder, entry);
        	}else{
        		processWhereGeneralValue(whereSqlBuilder, parameterMap, entry);
        	}
        }
        
        List<WhereEntry> whereEntries = criteria.getWhereEntries();
        if (whereEntries != null && !whereEntries.isEmpty()) {
        	Pattern p = Pattern.compile("(\\?)");
        	int index = 0;
			for(WhereEntry whereEntry : whereEntries){
				List<Object> whereParams = whereEntry.getWhereParams();
				String whereClause = whereEntry.getWhereClause();
				if(whereParams == null || whereParams.isEmpty()){
					whereSqlBuilder.append(" AND (" + whereClause + ")");
				}else{
					Matcher m = p.matcher(whereClause);
					
					StringBuffer sb = new StringBuffer(100);
					while (m.find()) {
						m.appendReplacement(sb, ":__param__" + index);
						parameterMap.put("__param__" + index, whereParams.get(index));
						index++;
					}
					m.appendTail(sb);
					whereSqlBuilder.append(" AND (" + sb.toString() + ")");
				}
			}
        }
        
	}
	

	private void processWhereGeneralValue(StringBuilder whereSqlBuilder,
			Map<String, Object> parameterMap, QCriteria.ParamEntry entry) {
		whereSqlBuilder.append(" AND");
		String fieldName = entry.getParamName();
		String paramName = StringUtils.generateString();

		String queryCase = entry.getQueryCase();
		Object value = entry.getValue();
		whereSqlBuilder.append(" o." + fieldName);
		whereSqlBuilder.append(" " + queryCase);
		whereSqlBuilder.append(" :" + paramName);
		parameterMap.put(paramName, value);
	}

	private void processWhereNullValue(StringBuilder whereSqlBuilder,
			QCriteria.ParamEntry entry) {
		whereSqlBuilder.append(" AND");
		whereSqlBuilder.append(' ').append(entry.getParamName()).append(" IS NULL ");
	}

	private void processSort(Sort sort, StringBuilder sortBuilder) {
		if (sort != null) {
	        Boolean orderByPresent = Boolean.FALSE;
	        for (Sort.OrderEntry orderEntry : sort) {
	        	sortBuilder.append(orderByPresent ? "," : " ORDER BY");
	        	sortBuilder.append(" o." + orderEntry.getOrderBy());
	        	sortBuilder.append(" " + orderEntry.getOrder());
	        	orderByPresent = Boolean.TRUE;
	        }
		} else {
			sortBuilder.append(" ORDER BY 1");
		}
			
	}

	@Override
	public T findOne(Sort sort, QCriteria criteria) {
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();

        StringBuilder whereSqlBuilder = new StringBuilder();

        processFetch(criteria, whereSqlBuilder);
        processWhere(criteria, whereSqlBuilder, parameterMap);
        
        StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder();
        jpqlDataBuilder.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
        
        jpqlDataBuilder.append(whereSqlBuilder);
        jpqlDataBuilder.append(sortBuilder);

        TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
        query.setMaxResults(1);
        setQueryParam(query, parameterMap);
        List<T> tmpList = query.getResultList();
        return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@Override
	public T findOne(Sort sort, String jpql, Object... params) {
		
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		setQueryParam(query, params);
		
		query.setMaxResults(1);
		List<T> tmpList = query.getResultList();
		
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@Override
	public T findOne(Sort sort, String jpql, Map<String, ?> params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		TypedQuery<T> query = entityManager.createQuery(jpqlDataBuilder.toString(), clazz);
		setQueryParam(query, params);
		
		query.setMaxResults(1);
		List<T> tmpList = query.getResultList();
		
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> executeQuery(Sort sort, String jpql, Object... params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> executeQuery(Sort sort, String jpql, Map<String, ?> params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> executeQuery(PageRequest pageRequest, String jpql, Object... params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(pageRequest.getSort(), sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());
        
		setQueryParam(query, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> List<X> executeQuery(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(pageRequest.getSort(), sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		query.setFirstResult(pageRequest.getOffset());
        query.setMaxResults(pageRequest.getPageSize());
        
		setQueryParam(query, params);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X executeQueryOne(Sort sort, String jpql, Object... params) {
		
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		query.setMaxResults(1);
		List<X> tmpList = query.getResultList();
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X executeQueryOne(Sort sort, String jpql, Map<String, ?> params) {
		StringBuilder sortBuilder = new StringBuilder();
        processSort(sort, sortBuilder);
        
        StringBuilder jpqlDataBuilder = new StringBuilder(jpql);
        jpqlDataBuilder.append(sortBuilder);
        
		Query query = entityManager.createQuery(jpql);
		setQueryParam(query, params);
		query.setMaxResults(1);
		List<X> tmpList = query.getResultList();
		return tmpList.isEmpty() ? null : tmpList.get(0);
	}
	
	public static void main(String[] args) {
		String test = "group bY";
		System.out.println(test.toLowerCase().indexOf("by"));
	}

}