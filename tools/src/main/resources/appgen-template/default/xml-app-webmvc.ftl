<?xml version="1.0" encoding="UTF-8" ?>
<beans:beans xmlns="http://www.springframework.org/schema/mvc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:p="http://www.springframework.org/schema/p" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:security="http://www.springframework.org/schema/security"
	xmlns:util="http://www.springframework.org/schema/util"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.1.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.1.xsd
        http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.1.xsd
		http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.1.xsd">

	<beans:import resource="classpath:spring/root-webmvc.xml"/>
	
	<!-- <beans:bean id="jspViewResolver" class="com.ccesun.framework.core.spring.RequestHistoryJspViewResolver" >
        <beans:property name="prefix" value="/WEB-INF/views/" />
        <beans:property name="suffix" value=".jsp" />
    </beans:bean>  -->
    
    <beans:bean id="tilesViewResolver" class="com.ccesun.framework.core.spring.RequestHistoryTilesViewResolver" >
		<beans:property name="cache" value="false" />
		<beans:property name="viewClass" value="org.springframework.web.servlet.view.tiles2.TilesView" />
	</beans:bean>
	
	<beans:bean
		class="org.springframework.web.servlet.view.tiles2.TilesConfigurer"
		id="tilesConfigurer">
		<beans:property name="definitions">
			<beans:list>
				<beans:value>/WEB-INF/layouts/layouts.xml</beans:value>
				<beans:value>/WEB-INF/views/**/views.xml</beans:value>
			</beans:list>
		</beans:property>
	</beans:bean>
	
	<context:component-scan base-package="${basePackage}.web.controller" />

</beans:beans>