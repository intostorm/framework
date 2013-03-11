create table `sys_area` (
  `record_id` int(11) not null auto_increment comment '主键',
  `areacode` varchar(15) default null comment '编码',
  `areaname` varchar(50) default null comment '简称',
  `full_areaname` varchar(255) default null comment '全称',
  `is_available` int(1) default '1' comment '是否可用',
  primary key (`record_id`)
) engine=innodb auto_increment=4 default charset=utf8;

create table `sys_dict` (
  `record_id` int(11) not null auto_increment comment '主键',
  `dict_type` varchar(255) default null comment '类型',
  `dict_key` varchar(255) default null comment '键',
  `dict_value0` varchar(255) default null comment '值0',
  `dict_value1` varchar(255) default null comment '值1',
  `dict_value2` varchar(255) default null comment '值2',
  `dict_value3` varchar(255) default null comment '值3',
  `parent_key` varchar(255) default null comment '父级KEY',
  primary key (`record_id`)
) engine=innodb auto_increment=5 default charset=utf8;

create table `sys_user` (
  `user_id` int(11) not null auto_increment comment '主键',
  `user_name` varchar(255) default null comment '用户名',
  `real_name` varchar(255) default null comment '姓名',
  `passwd` varchar(255) default null comment '密码',
  `is_available` int(1) default null comment '是否可用',
  primary key (`user_id`)
) engine=innodb default charset=utf8;

create table `sys_role` (
  `role_id` int(11) not null auto_increment comment '主键',
  `role_name` varchar(255) default null comment '角色名',
  `role_remarks` varchar(255) default null comment '描述',
  primary key (`role_id`)
) engine=innodb default charset=utf8;

create table `sys_func` (
  `func_id` varchar(255) not null comment '主键',
  `func_name` varchar(255) default null comment '功能名',
  `func_url` varchar(255) default null comment '功能地址',
  `func_remarks` varchar(255) default null comment '描述',
  `func_level` int(11) default null comment '功能级别',
  `parent_id` int(11) default null comment '父功能',
  primary key (`func_id`)
) engine=innodb default charset=utf8;

create table `sys_user_role` (
  `id` int(11) not null auto_increment comment '主键',
  `user_id` int(11) default null comment '用户ID',
  `role_id` int(11) default null comment '角色ID',
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `sys_role_func` (
  `id` int(11) not null auto_increment comment '主键',
  `role_id` int(11) default null comment '角色ID',
  `func_id` varchar(255) default null comment '功能ID',
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `contact` (
  `record_id` int(11) not null auto_increment comment '主键',
  `name` varchar(255) default null comment '姓名',
  `sex` varchar(255) default null comment '性别',
  `phone` varchar(255) default null comment '电话',
  `areacode` varchar(255) default null comment '地区',
  `address` varchar(255) default null comment '地址',
  primary key (`record_id`)
) engine=innodb auto_increment=380 default charset=utf8;