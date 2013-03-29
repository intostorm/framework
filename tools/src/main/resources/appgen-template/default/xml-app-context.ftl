<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:jpa="http://www.springframework.org/schema/data/jpa"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd 
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd 
	http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd 
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
	http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.0.xsd">

	<import resource="classpath:spring/root-context.xml"/>
	
<#if !basePackage?starts_with('com.ccesun')>
	<bean id="entityManagerFactory"
		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
		</property>
		<property name="packagesToScan" value="com.ccesun, ${basePackage}" />
		<property name="jpaProperties">
			<props>
				<prop key="hibernate.dialect">${hibernate.dialect}</prop>
				<prop key="hibernate.max_fetch_depth">3</prop>
				<prop key="hibernate.jdbc.fetch_size">50</prop>
				<prop key="hibernate.jdbc.batch_size">10</prop>
				<prop key="hibernate.cache.use_second_level_cache">true</prop>
				<prop key="hibernate.cache.use_structured_entries">true</prop>
				<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
				<!-- <prop key="hibernate.cache.use_query_cache">true</prop> -->
				<!-- <prop key="hibernate.show_sql">true</prop> -->
			</props>
		</property>
	</bean>
</#if>
		
<#if !basePackage?starts_with('com.ccesun')>
	<context:component-scan base-package="com.ccesun, ${basePackage}">
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
	</context:component-scan>
</#if>
</beans>