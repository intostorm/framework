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
) engine=innodb default charset=utf8;