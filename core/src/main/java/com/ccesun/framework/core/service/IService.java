package com.ccesun.framework.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccesun.framework.core.dao.support.IDao;
import com.ccesun.framework.core.dao.support.IEntity;
import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.Sort;

/**
 * 泛型Service接口
 * @author Jaron
 *
 * @param <T>
 * @param <I>
 */
public interface IService<T extends IEntity<I>, I extends Serializable> {
	
	/**
	 * 得到IDao实例
	 * @return IDao实例
	 */
	IDao<T, I> getDao();
	
	/**
	 * <p>保存对象
	 * 
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * simpleService.save(foo1);<br>
	 * 
	 * @param target 待保存的对象
	 * @return 已保存的对象
	 */
	T save(T target);
	
	/**
	 * <p>一次保存任意多个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * Foo foo2 = new Foo();<br>
	 * ...<br>
	 * simpleService.save(foo1, foo2, ...);
	 * 
	 * @param targets 待保存的对象（任意个数，用","号分隔开）
	 * @return 已保存对象的数组
	 */
	T[] save(T... targets);

	/**
	 * <p>删除对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * simpleService.remove(foo1);
	 * 
	 * @param target 待删除的对象
	 */
	void remove(T target);

	/**
	 * <p>删除任意多个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * Foo foo2 = new Foo();<br>
	 * simpleService.remove(foo1, foo2);
	 * 
	 * @param targets 待删除的对象
	 */
	void remove(T... targets);
	
	/**
	 * <p>按id删除对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * int id = 1; <br>
	 * simpleService.remove(id);
	 * 
	 * @param id 待删除对象的id
	 */
	void remove(I id);

	/**
	 * <p>检查对象是否在数据库中存在
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * int id = 1; <br>
	 * simpleService.exists(id);
	 * 
	 * @param id 待检查对象的id
	 * @return 是否在数据库中存在
	 */
	boolean exists(I id);
	
	/**
	 * <p>按主键查找对象引用<br>
	 * 如果对象已在持久化上下文中，直接返回此对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Foo foo = simpleService.findReferenceByPk(id);
	 * @param id 主键
	 * @return 返回找到的对象 引用
	 */
	T findReferenceByPk(I id);
	
	/**
	 * <p>按主键查找对象<br>
	 * 如果对象已在持久化上下文中，直接返回此对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Foo foo = simpleService.exists(id);
	 * @param id 主键
	 * @return 返回找到的对象 或 null
	 */
	T findByPk(I id);

	/**
	 * <p>根据查询条件，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Foo foo = simpleService.findOne(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(QCriteria criteria);
	
	/**
	 * <p>根据查询条件，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Foo foo = simpleService.findOne(sort, criteria);
	 * 
	 * @param sort 排序条件
	 * @param criteria 查询条件
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(Sort sort, QCriteria criteria);

	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * String jpql = "select o from Foo o where o.name = ?";<br>
	 * Foo foo = simpleService.findOne(jpql, "name1"); //name1作为jpql参数的值
	 *  
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(String jpql, Object... params);
	
	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * String jpql = "select o from Foo o where o.name = :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1");
	 * Foo foo = simpleService.findOne(jpql, params); //name1作为jpql参数的值
	 *  
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(String jpql, Map<String, ?> params);
	
	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name = ?";<br>
	 * Foo foo = simpleService.findOne(sort, jpql, "name1"); //name1作为jpql参数的值
	 *  
	 * @param sort 排序条件
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(Sort sort, String jpql, Object... params);
	
	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name = :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1");
	 * Foo foo = simpleService.findOne(sort, jpql, params); //name1作为jpql参数的值
	 *  
	 * @param sort 排序条件
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(Sort sort, String jpql, Map<String, ?> params);
	
	/**
	 * 查出所有对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * List<Foo> fooList = simpleService.findAll();
	 * @return 所有对象的List
	 */
	List<T> findAll();

	/**
	 * 查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String criteria = new QCriteria();<br>
	 * List<Foo> fooList = simpleService.find(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合条件的对象集合
	 */
	List<T> find(QCriteria criteria);
	
	/**
	 * 使用jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.find(jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(String jpql, Object... params);
	
	/**
	 * 使用jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.find(jpql, params); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(String jpql, Map<String, ?> params);

	/**
	 * 使用 排序条件、查询条件，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * List<Foo> fooList = simpleService.find(sort, criteria);
	 * 
	 * @param sort 排序条件
	 * @param criteria 查询条件
	 * @return 符合条件的对象集合
	 */
	List<T> find(Sort sort, QCriteria criteria);

	/**
	 * 使用 分页条件、查询条件，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * List<Foo> fooList = simpleService.find(pageRequest, criteria);
	 * 
	 * @see PageRequest
	 * @param pageRequest 分页条件
	 * @param criteria 查询条件
	 * @return 符合条件的对象集合
	 */
	List<T> find(PageRequest pageRequest, QCriteria criteria);

	/**
	 * 使用 排序条件、jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.find(sort, jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(Sort sort, String jpql, Object... params);
	
	/**
	 * 使用 排序条件、jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.find(sort, jpql, params); //name1作为jpql参数的值
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(Sort sort, String jpql, Map<String, ?> params);

	/**
	 * 使用 分页条件、jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.find(pageRequest, jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(PageRequest pageRequest, String jpql, Object... params);
	
	/**
	 * 使用 分页条件、jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.find(pageRequest, jpql, params); //name1作为jpql参数的值
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(PageRequest pageRequest, String jpql, Map<String, ?> params);

	/**
	 * 使用 查询条件，查出符合条件的对象数量
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>

	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * long count = simpleService.count(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合条件的对象数量
	 */
	long count(QCriteria criteria);

	/**
	 * 使用 jpql语句，查出符合条件的对象数量
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * long count = simpleService.count(jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	long count(String jpql, Object... params);
	
	/**
	 * 使用 jpql语句，查出符合条件的对象数量
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * long count = simpleService.count(jpql, params); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	long count(String jpql, Map<String, ?> params);

	/**
	 * 使用 分页条件、查询条件，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Page<Foo> fooPage = simpleService.findPage(pageRequest, criteria); 
	 * 
	 * @param pageRequest 分页条件
	 * @param criteria 查询条件
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(PageRequest pageRequest, QCriteria criteria);

	/**
	 * 使用 分页条件、jpql语句，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Page<Foo> fooPage = simpleService.findPage(pageRequest, jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(PageRequest pageRequest, String jpql, Object... params);
	
	/**
	 * 使用 分页条件、jpql语句，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Page<Foo> fooPage = simpleService.findPage(pageRequest, jpql, params); //name1作为jpql参数的值
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(PageRequest pageRequest, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.executeQuery(jpql, "name1".concat("%"));
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.executeQuery(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc(name);
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.executeQuery(sort, jpql, "name1".concat("%"));
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(Sort sort, String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Sort sort = new Sort().asc(name);
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.executeQuery(sort, jpql, params);
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(Sort sort, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc(name);
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleService.executeQuery(pageRequest, jpql, "name1".concat("%"));
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(PageRequest pageRequest, String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Sort sort = new Sort().asc(name);
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleService.executeQuery(pageRequest, jpql, params);
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(PageRequest pageRequest, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Foo foo = (Foo) simpleService.executeQueryOne(jpql, "name1".concat("%"));
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Foo foo = (Foo) simpleService.executeQueryOne(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Foo foo = (Foo) simpleService.executeQueryOne(sort, jpql, "name1".concat("%"));
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(Sort sort, String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Foo foo = (Foo) simpleService.executeQueryOne(sort, jpql, params);
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(Sort sort, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Page<Foo> foo = simpleService.executeQueryPage(pageRequest, jpql, "name1".concat("%"));
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的分页对象
	 */
	<X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Page<Foo> foo = simpleService.executeQueryPage(pageRequest, jpql, params);
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的分页对象
	 */
	<X> Page<X> executeQueryPage(PageRequest pageRequest, String jpql, Map<String, ?> params);
	
	/**
	 * 执行jpql语句，用于批量执行更新或删除操作
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * simpleService.execute(jpql, "name1".concat("%"));
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 受影响的对象数量
	 */
	public int execute(String jpql, Object... params);

	/**
	 * 执行jpql语句，用于批量执行更新或删除操作
	 * <p>示例：<br>
	 * 假定SimpleService是IService的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * simpleService.execute(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 受影响的对象数量
	 */
	public int execute(String jpql, Map<String, ?> params);
}
