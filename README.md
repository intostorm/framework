# JAVA WEB 开发框架

## 使用方法

### 安装开发工具

+ 安装jdk, eclipse, tomcat, mysql/oracle

+ 安装eclipse插件 Egit, Maven Integration for WTP

### 从git服务器clone开发框架

```
git clone https://github.com/intostorm/framework.git
```

### 运行 framework/appendix/install-maven-jars.bat,安装第三方maven依赖

### 创建开发使用的数据库, 此处以mysql 为例

```
mysql> create database sample
mysql> create table `contact` ( \
  `record_id` int(11) not null auto_increment comment '主键', \
  `name` varchar(255) default null comment '姓名', \
  `sex` varchar(255) default null comment '性别', \
  `phone` varchar(255) default null comment '电话', \
  `areacode` varchar(255) default null comment '地区', \
  `address` varchar(255) default null comment '地址', \
  primary key (`record_id`) \
) engine=innodb default charset=utf8;

```

### 用生成工具tools生成工程代码

打开tools/src/main/resources/datasource-context.xml，添加

```
<bean id="dataSourceSample" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
	<property name="driverClassName" value="com.mysql.jdbc.Driver" />
	<property name="url" value="jdbc:mysql://localhost:3306/sample?characterEncoding=utf-8" />
	<property name="username" value="root" />
	<property name="password" value="" />
</bean>
```

打开tools/src/main/resources/appgen-context.xml，添加

```
<bean id="sample" class="com.ccesun.framework.tools.appgen.AppGenConfiguation">
	<property name="baseOutput" value="appgen" />
	<property name="projectName" value="sample" />
	<property name="basePackage" value="com.ccesun.sample" />
	<property name="dataSource" ref="dataSourceSample" />
	<property name="freemarkerConfig" ref="freemarkerConfig" />
	<property name="hibernateDialect" value="org.hibernate.dialect.MySQL5Dialect" />
	<property name="projectVersion" value="1.0" />
	<property name="frameworkVersion" value="2.0.1" />
</bean>
```

运行com.ccesun.framework.tools.runner.AppGenRunner

在tools/appgen下找到生成的工程代码

### 用生成工具tools生成代码片段（可选）

打开tools/src/main/resources/codegen-context.xml，添加

```
<bean id="sample" class="com.ccesun.framework.tools.codegen.SimpleCodeGenConfiguation">
	<property name="freemarkerConfig" ref="freemarkerConfig" />
	<!-- 引用 tools/src/main/resources/datasource-context.xml 中设置的dataSource -->
	<property name="dataSource" ref="dataSourceSample" /> 
	<!-- 
		设成true生成全部对象， materials 中的条目会被忽略 
		设成false生成 materials 中的条目 
	-->
	<property name="allArticfacts" value="false" />
	<!-- 输出的目录 -->
	<property name="baseOutput" value="codegen/sample" />
	<!-- 基础包名 -->
	<property name="basePackage" value="com.ccesun.sample" />
	<!-- 指定本次生成的对象，当 allArticfacts 为false 时-->
    <property name="materials">
        <list> 
            <bean p:domainName="Contact" p:tableName="contact" class="com.ccesun.framework.tools.codegen.Material" />
        </list>
    </property>
</bean>
```

修改<bean id="codeGen" class="com.ccesun.framework.tools.codegen.CodeGen">中<property name="config" ref="sample" />

```
<bean id="codeGen" class="com.ccesun.framework.tools.codegen.CodeGen">
	<property name="config" ref="sample" />
</bean>
```

运行com.ccesun.framework.tools.runner.CodeGenRunner

在tools/codegen下找到生成的代码片段

## 作者

**zhuyuanbin2010**

+ jinyingxiong@163.com

**Jaron**

+ jaron.su@gmail.com

## 版权所有
Copyright 2013 CCEsun, Inc.
