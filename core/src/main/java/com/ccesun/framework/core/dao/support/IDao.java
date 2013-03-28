package com.ccesun.framework.core.dao.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * 泛型DAO接口
 * @author Jaron
 *
 * @param <T>
 * @param <I>
 */
public interface IDao<T extends IEntity<I>, I extends Serializable> {

	/**
	 * 得到JPA中的EntityManager
	 * @return EntityManager对象
	 */
	public EntityManager getEntityManager();
	
	/**
	 * <p>保存对象
	 * 
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * simpleDao.save(foo1);<br>
	 * 
	 * @param target 待保存的对象
	 * @return 已保存的对象
	 */
	T save(T target);
	
	/**
	 * <p>一次保存任意多个对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * Foo foo2 = new Foo();<br>
	 * ...<br>
	 * simpleDao.save(foo1, foo2, ...);
	 * 
	 * @param targets 待保存的对象（任意个数，用","号分隔开）
	 * @return 已保存对象的数组
	 */
	T[] save(T... targets);

	/**
	 * <p>删除对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * simpleDao.remove(foo1);
	 * 
	 * @param target 待删除的对象
	 */
	void remove(T target);

	/**
	 * <p>删除任意多个对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Foo foo1 = new Foo();<br>
	 * Foo foo2 = new Foo();<br>
	 * simpleDao.remove(foo1, foo2);
	 * 
	 * @param targets 待删除的对象
	 */
	void remove(T... targets);
	
	/**
	 * <p>按id删除对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * int id = 1; <br>
	 * simpleDao.remove(id);
	 * 
	 * @param id 待删除对象的id
	 */
	void remove(I id);

	/**
	 * <p>检查对象是否在数据库中存在
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * int id = 1; <br>
	 * simpleDao.exists(id);
	 * 
	 * @param id 待检查对象的id
	 * @return 是否在数据库中存在
	 */
	boolean exists(I id);
	
	/**
	 * <p>按主键查找对象引用<br>
	 * 如果对象已在持久化上下文中，直接返回此对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Foo foo = simpleDao.findReferenceByPk(id);
	 * @param id 主键
	 * @return 返回找到的对象引用
	 */
	T findReferenceByPk(I id);
	
	/**
	 * <p>按主键查找对象<br>
	 * 如果对象已在持久化上下文中，直接返回此对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Foo foo = simpleDao.findByPk(id);
	 * @param id 主键
	 * @return 返回找到的对象 或 null
	 */
	T findByPk(I id);

	/**
	 * <p>根据查询条件，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Foo foo = simpleDao.findOne(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(QCriteria criteria);
	
	/**
	 * <p>根据查询条件，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Foo foo = simpleDao.findOne(sort, criteria);
	 * 
	 * @param sort 排序条件
	 * @param criteria 查询条件
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(Sort sort, QCriteria criteria);

	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * String jpql = "select o from Foo o where o.name = ?";<br>
	 * Foo foo = simpleDao.findOne(jpql, "name1"); //name1作为jpql参数的值
	 *  
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(String jpql, Object... params);
	
	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * String jpql = "select o from Foo o where o.name = :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1");
	 * Foo foo = simpleDao.findOne(jpql, params); //name1作为jpql参数的值
	 *  
	 * @param jpql jpql查询语句
	 * @param params jpql参数
	 * @return 符合查询条件的第一个对象 或 null
	 */
	T findOne(String jpql, Map<String, ?> params);
	
	/**
	 * <p>使用jpql语句，查出第一个符合条件的对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name = ?";<br>
	 * Foo foo = simpleDao.findOne(sort, jpql, "name1"); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name = :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1");
	 * Foo foo = simpleDao.findOne(sort, jpql, params); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * List<Foo> fooList = simpleDao.findAll();
	 * @return 所有对象的List
	 */
	List<T> findAll();

	/**
	 * 查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String criteria = new QCriteria();<br>
	 * List<Foo> fooList = simpleDao.find(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合条件的对象集合
	 */
	List<T> find(QCriteria criteria);
	
	/**
	 * 使用jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.find(jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(String jpql, Object... params);
	
	/**
	 * 使用jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.find(jpql, params); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	List<T> find(String jpql, Map<String, ?> params);

	/**
	 * 使用 排序条件、查询条件，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * List<Foo> fooList = simpleDao.find(sort, criteria);
	 * 
	 * @param sort 排序条件
	 * @param criteria 查询条件
	 * @return 符合条件的对象集合
	 */
	List<T> find(Sort sort, QCriteria criteria);

	/**
	 * 使用 分页条件、查询条件，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * List<Foo> fooList = simpleDao.find(pageRequest, criteria);
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.find(sort, jpql, "name1".concat("%")); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.find(sort, jpql, params); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.find(pageRequest, jpql, "name1".concat("%")); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.find(pageRequest, jpql, params); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>

	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * long count = simpleDao.count(criteria);
	 * 
	 * @param criteria 查询条件
	 * @return 符合条件的对象数量
	 */
	long count(QCriteria criteria);

	/**
	 * 使用 jpql语句，查出符合条件的对象数量
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * long count = simpleDao.count(jpql, "name1".concat("%")); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	long count(String jpql, Object... params);
	
	/**
	 * 使用 jpql语句，查出符合条件的对象数量
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * long count = simpleDao.count(jpql, params); //name1作为jpql参数的值
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	long count(String jpql, Map<String, ?> params);

	/**
	 * 使用 分页条件、查询条件，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * QCriteria criteria = new QCriteria();<br>
	 * criteria.addEntry("name", Op.EQ, "name1"); //name 是 Foo的一个属性， name1是属性的值 <br>
	 * Page<Foo> fooPage = simpleDao.findPage(pageRequest, criteria); 
	 * 
	 * @param pageRequest 分页条件
	 * @param criteria 查询条件
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(PageRequest pageRequest, QCriteria criteria);

	/**
	 * 使用 分页条件、jpql语句，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Page<Foo> fooPage = simpleDao.findPage(pageRequest, jpql, "name1".concat("%")); //name1作为jpql参数的值
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Page<Foo> fooPage = simpleDao.findPage(pageRequest, jpql, params); //name1作为jpql参数的值
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的分页对象
	 */
	Page<T> findPage(PageRequest pageRequest, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.executeQuery(jpql, "name1".concat("%"));
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.executeQuery(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc(name);
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.executeQuery(sort, jpql, "name1".concat("%"));
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Sort sort = new Sort().asc(name);
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.executeQuery(sort, jpql, params);
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(Sort sort, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的对象集合
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc(name);
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * List<Foo> fooList = simpleDao.executeQuery(pageRequest, jpql, "name1".concat("%"));
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Sort sort = new Sort().asc(name);
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * List<Foo> fooList = simpleDao.executeQuery(pageRequest, jpql, params);
	 * 
	 * @param pageRequest 分页条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的对象集合
	 */
	<X> List<X> executeQuery(PageRequest pageRequest, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Foo foo = (Foo) simpleDao.executeQueryOne(jpql, "name1".concat("%"));
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(String jpql, Object... params);

	/**
	 * 使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Foo foo = (Foo) simpleDao.executeQueryOne(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的第一个对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Foo foo = (Foo) simpleDao.executeQueryOne(sort, jpql, "name1".concat("%"));
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * Sort sort = new Sort().asc("name");<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Foo foo = (Foo) simpleDao.executeQueryOne(sort, jpql, params);
	 * 
	 * @param sort 排序条件
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 符合条件的第一个对象
	 */
	<X> X executeQueryOne(Sort sort, String jpql, Map<String, ?> params);
	
	/**使用 jpql语句，查出符合条件的分页对象
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * Page<Foo> foo = simpleDao.executeQueryPage(pageRequest, jpql, "name1".concat("%"));
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * Sort sort = new Sort().asc("name");<br>
	 * PageRequest pageRequest = new PageRequest(1, 10, sort); //PageRequest有多个构造器<br>
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * Page<Foo> foo = simpleDao.executeQueryPage(pageRequest, jpql, params);
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
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like ?";<br>
	 * simpleDao.execute(jpql, "name1".concat("%"));
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 受影响的对象数量
	 */
	public int execute(String jpql, Object... params);

	/**
	 * 执行jpql语句，用于批量执行更新或删除操作
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * 
	 * String jpql = "select o from Foo o where o.name like :name";<br>
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "name1".concat("%"));
	 * simpleDao.execute(jpql, params);
	 * 
	 * @param jpql jpql语句
	 * @param params jpql参数
	 * @return 受影响的对象数量
	 */
	public int execute(String jpql, Map<String, ?> params);
	
	/**
	 * 同步持久层上下文与数据库的状态
	 * <p>示例：<br>
	 * 假定SimpleDao是IDao的实现类 <br>
	 * simpleDao.flush();
	 */
	public void flush();

}
