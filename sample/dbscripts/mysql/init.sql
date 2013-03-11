insert into sys_area (areacode, areaname, full_areaname, is_available)
values ('22', '吉林省', '吉林省', 1);

insert into sys_area (areacode, areaname, full_areaname, is_available)
values ('2201', '长春市', '吉林省长春市', 1);

insert into sys_area (areacode, areaname, full_areaname, is_available)
values ('220101', '宽城区', '吉林省长春市宽城区', 1);

insert into sys_dict (dict_type, dict_key, dict_value0)
values ('XINGB', '0', '性别');

insert into sys_dict (dict_type, dict_key, dict_value0)
values ('XINGB', '1', '男');

insert into sys_dict (dict_type, dict_key, dict_value0)
values ('XINGB', '2', '女');

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '01', '女装', null);

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '02', '男装', null);

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '0101', '连衣裙', '01');

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '0102', '毛衣', '01');

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '0201', '夹克', '02');

insert into sys_dict (dict_type, dict_key, dict_value0, parent_key)
values ('FUZHUANG', '0202', '风衣', '02');

insert into sys_user (user_name, real_name, passwd, is_available) values ('admin', 'admin', 'admin', 1);
insert into sys_role (role_name) values ('admin');

insert into sys_user_role(user_id, role_id) 
values (1, 1);

insert into sys_func(func_id, func_name, func_url, func_level) 
values ('contact', '联系人', '/contact', 1);

insert into sys_func(func_id, func_name, func_url, func_level) 
values ('fileUpload', '文件上传', '/fileUpload', 1);

insert into sys_role_func (role_id, func_id)
values (1, 'contact');

insert into sys_role_func (role_id, func_id)
values (1, 'fileUpload');