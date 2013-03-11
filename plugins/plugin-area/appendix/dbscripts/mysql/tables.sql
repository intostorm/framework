create table `sys_area` (
  `record_id` int(11) not null auto_increment comment '主键',
  `areacode` varchar(15) default null comment '编码',
  `areaname` varchar(50) default null comment '简称',
  `full_areaname` varchar(255) default null comment '全称',
  `is_available` int(1) default '1' comment '是否可用',
  primary key (`record_id`)
) engine=innodb default charset=utf8;