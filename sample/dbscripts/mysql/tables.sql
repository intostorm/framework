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
  `func_id` int(11) not null auto_increment comment '主键',
  `func_name` varchar(255) default null comment '功能名',
  `func_groupcode` varchar(255) default null comment '功能组编码',
  `func_url` varchar(255) default null comment '功能地址',
  `func_remarks` varchar(255) default null comment '描述',
  `func_level` int(11) default null comment '功能级别',
  `parent_id` int(11) default null comment '父功能',
  `func_order` int(11) default null comment '序号',
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
  `func_id` int(11) default null comment '功能ID',
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
) engine=innodb default charset=utf8;