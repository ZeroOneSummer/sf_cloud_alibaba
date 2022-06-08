INSERT INTO `bms_user` VALUES
('1', 'admin', '24b9315cca2b5f32b9861423e46ae74c30d2d738', 'jkvO2gNDCUIK6i11Mo01', '615810037@qq.com', '17748648941', '1', '1', '2020-03-21 11:11:11', '');

INSERT INTO `bms_role` VALUES
('1', '超级管理员', '拥有所有功能菜单权限', '1', '2020-03-21 11:11:11');

INSERT INTO `bms_menu` VALUES
('1', '0', '系统管理', null, null, '0', 'system', '0'),
('2', '1', '用户管理', 'sys/user', null, '1', 'user', '1'),
('3', '1', '角色管理', 'sys/role', null, '1', 'role', '2'),
('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3'),
('5', '1', 'SQL监控', 'http://localhost:18082/admin/druid/sql.html', null, '1', 'sql', '4');

INSERT INTO `bms_user_role` VALUES ('1', '1', '1');

INSERT INTO `bms_role_menu` VALUES
('1', '1', '1'),
('2', '1', '2'),
('3', '1', '3'),
('4', '1', '4'),
('5', '1', '5');
