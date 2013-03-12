insert into sys_user (user_name, real_name, passwd, is_available) values ('admin', 'admin', 'admin', 1);
insert into sys_role (role_name) values ('admin');

insert into sys_user_role(user_id, role_id) 
values (1, 1);

insert into sys_func(func_id, func_name, func_url, func_level, func_order) 
values ('1', '联系人', '/contact', 1, 1);

insert into sys_func(func_id, func_name, func_url, func_level, func_order) 
values ('2', '文件上传', '/fileUpload', 1, 2);

insert into sys_role_func (role_id, func_id)
values (1, '1');

insert into sys_role_func (role_id, func_id)
values (1, '2');