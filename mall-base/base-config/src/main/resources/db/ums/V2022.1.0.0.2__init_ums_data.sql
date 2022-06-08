INSERT INTO `ums_user` VALUES
('1', 'admin', '508ec3a8044444767dd562c21465b900966854a1', 'jkvO2gNDCUIK6i11Mo01', '615810037@qq.com', '17748648941', '1', '1', '2020-03-21 11:11:11', '');

INSERT INTO `ums_role` VALUES
('1', '超级管理员', '拥有所有功能菜单权限', '1', '2020-03-21 11:11:11');

INSERT INTO `ums_menu` VALUES
('1', '0', '系统管理', null, null, '0', 'system', '0'),
('2', '1', '用户管理', 'ums/user', null, '1', 'user', '1'),
('3', '1', '角色管理', 'ums/role', null, '1', 'role', '2'),
('4', '1', '菜单管理', 'ums/menu', null, '1', 'menu', '3'),
('5', '1', 'SQL监控', 'http://localhost:18082/druid/sql.html', null, '1', 'sql', '4');

INSERT INTO `ums_user_role` VALUES ('1', '1', '1');

INSERT INTO `ums_role_menu` VALUES
('1', '1', '1'),
('2', '1', '2'),
('3', '1', '3'),
('4', '1', '4'),
('5', '1', '5');
