package com.ccesun.framework.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.Sort;

@Transactional(readOnly=true)
public abstract class GenericService<T extends IEntity<I>, I extends Serializable>
		implements IService<T, I> {

	@Override
	@Transactional
	public T save(T target) {
		return getDao().save(target);
	}

	@Override
	@Transactional
	public T[] save(T... targets) {
		return getDao().save(targets);
	}

	@Override
	@Transactional
	public void remove(T target) {
		getDao().remove(target);
	}

	@Override
	@Transactional
	public void remove(T... targets) {
		getDao().remove(targets);
	}
	
	@Override
	@Transactional
	public void remove(I id) {
		getDao().remove(id);
	}

	@Override
	public boolean exists(I id) {
		return getDao().exists(id);
	}
	
	@Override
	@Transactional
	public int execute(String jql, Object... params) {
		return getDao().execute(jql, params);
	}
	
	@Override
	@Transactional
	public int execute(String jql, Map<String, ?> params) {
		return getDao().execute(jql, params);
	}
	
	@Override
	public <X> X executeQueryOne(String jpql, Object... params) {
		return getDao().executeQueryOne(jpql, params);
	}

	@Override
	public <X> X executeQueryOne(Sort sort, String jpql, Object... params) {
		return getDao().executeQueryOne(sort, jpql, params);
	}

	@Override
	public <X> X executeQueryOne(Sort sort, String jpql, Map<String, ?> params) {
		return getDao().executeQueryOne(sort, jpql, params);
	}
	
	@Override
	public <X> X executeQueryOne(String jpql, Map<String, ?> params) {
		return getDao().executeQueryOne(jpql, params);
	}
	
	@Override
	public <X> List<X> executeQuery(String jql, Map<String, ?> params){
		return getDao().executeQuery(jql, params);
	}
	
	@Override
	public <X> List<X> executeQuery(String jql, Object... params){
		return getDao().executeQuery(jql, params);
	}

	@Override
	public <X> List<X> executeQuery(Sort sort, String jpql, Object... params) {
		return getDao().executeQuery(sort, jpql, params);
	}
	
	@Override
	public <X> List<X> executeQuery(Sort sort, String jpql, Map<String, ?> params) {
		return getDao().executeQuery(sort, jpql, params);
	}
	
	@Override
	public <X> List<X> executeQuery(PageRequest pageRequest, String jpql, Object... params) {
		return getDao().executeQuery(pageRequest, jpql, params);
	}
	
	@Override
	public <X> List<X> executeQuery(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		return getDao().executeQuery(pageRequest, jpql, params);
	}

	@Override
	public <X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Object... params) {
		return getDao().executeQueryPage(pageRequest, jpql, params);
	}

	@Override
	public <X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		return getDao().executeQueryPage(pageRequest, jpql, params);
	}
	
	@Override
	public T findReferenceByPk(I id) {
		return getDao().findReferenceByPk(id);
	}
	
	@Override
	public T findByPk(I id) {
		return getDao().findByPk(id);
	}

	@Override
	public T findOne(QCriteria criteria) {
		return getDao().findOne(criteria);
	}
	
	@Override
	public T findOne(Sort sort, QCriteria criteria) {
		return getDao().findOne(sort, criteria);
	}

	@Override
	public T findOne(String jpql, Object... params) {
		return getDao().findOne(jpql, params);
	}

	@Override
	public T findOne(String jpql, Map<String, ?> params) {
		return getDao().findOne(jpql, params);
	}
	
	@Override
	public T findOne(Sort sort, String jpql, Object... params) {
		return getDao().findOne(sort, jpql, params);
	}

	@Override
	public T findOne(Sort sort, String jpql, Map<String, ?> params) {
		return getDao().findOne(sort, jpql, params);
	}

	@Override
	public List<T> find(QCriteria criteria) {
		return getDao().find(criteria);
	}

	@Override
	public List<T> find(String jpql, Object... params) {
		return getDao().find(jpql, params);
	}

	@Override
	public List<T> find(PageRequest pageRequest, QCriteria criteria) {
		return getDao().find(pageRequest, criteria);
	}

	@Override
	public List<T> find(PageRequest pageRequest, String jpql, Object... params) {
		return getDao().find(pageRequest, jpql, params);
	}

	@Override
	public long count(QCriteria criteria) {
		return getDao().count(criteria);
	}

	@Override
	public long count(String jpql, Object... params) {
		return getDao().count(jpql, params);
	}

	@Override
	public Page<T> findPage(PageRequest pageRequest, QCriteria criteria) {
		return getDao().findPage(pageRequest, criteria);
	}

	@Override
	public Page<T> findPage(PageRequest pageRequest, String jpql,
			Object... params) {
		return getDao().findPage(pageRequest, jpql, params);
	}
	
	protected EntityManager getEntityManager(){
		return getDao().getEntityManager();
	}

	@Override
	public List<T> find(String jpql, Map<String, ?> params) {
		return getDao().find(jpql, params);
	}

	@Override
	public List<T> find(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		return getDao().find(pageRequest, jpql, params);
	}

	@Override
	public long count(String jpql, Map<String, ?> params) {
		return getDao().count(jpql, params);
	}

	@Override
	public Page<T> findPage(PageRequest pageRequest, String jpql, Map<String, ?> params) {
		return getDao().findPage(pageRequest, jpql, params);
	}

	@Override
	public List<T> find(Sort sort, QCriteria criteria) {
		return getDao().find(sort, criteria);
	}

	@Override
	public List<T> find(Sort sort, String jpql, Object... params) {
		return getDao().find(sort, jpql, params);
	}

	@Override
	public List<T> find(Sort sort, String jpql, Map<String, ?> params) {
		return getDao().find(sort, jpql, params);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

}
