
set foreign_key_checks = 0;

truncate table sys_user;

insert into sys_user (user_name, real_name, passwd, is_available) values ('admin', 'admin', 'admin', 1);

truncate table sys_role;

insert into sys_role (role_name) values ('admin');

truncate table sys_user_role;

insert into sys_user_role(user_id, role_id) values (1, 1);

truncate table sys_func;

insert into sys_func(func_id, func_name, func_url, func_groupcode, func_order) 
values ('1', '功能', '/sysFunc', 'security', 1);

insert into sys_func(func_id, func_name, func_url, func_groupcode, func_order) 
values ('2', '角色', '/sysRole', 'security', 2);

insert into sys_func(func_id, func_name, func_url, func_groupcode, func_order) 
values ('3', '用户', '/sysUser', 'security', 3);

insert into sys_func(func_id, func_name, func_url, func_groupcode, func_order) 
values ('4', '联系人', '/contact', 'APP', 1);

insert into sys_func(func_id, func_name, func_url, func_groupcode, func_order) 
values ('5', '文件上传', '/fileUpload', 'APP', 2);


truncate table sys_role_func;

insert into sys_role_func (role_id, func_id)
values (1, '1');

insert into sys_role_func (role_id, func_id)
values (1, '2');

insert into sys_role_func (role_id, func_id)
values (1, '3');

insert into sys_role_func (role_id, func_id)
values (1, '4');

insert into sys_role_func (role_id, func_id)
values (1, '5');

set foreign_key_checks = 1;
