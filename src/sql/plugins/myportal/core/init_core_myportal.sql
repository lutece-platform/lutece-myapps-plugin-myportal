
--
-- Dumping data for table core_admin_right
--
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES 
('MYPORTAL_WIDGET_MANAGEMENT','myportal.adminFeature.myportal_widget_management.name',1,'jsp/admin/plugins/myportal/ManageWidgets.jsp','myportal.adminFeature.myportal_widget_management.description',0,'myportal','APPLICATIONS','images/admin/skin/plugins/myportal/myportal.png', NULL);
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES
('MYPORTAL_CATEGORY_MANAGEMENT','myportal.adminFeature.myportal_category_management.name',1,'jsp/admin/plugins/myportal/ManageCategories.jsp','myportal.adminFeature.myportal_category_management.description',0,'myportal','APPLICATIONS','images/admin/skin/plugins/myportal/myportal.png', NULL);
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES
('MYPORTAL_DEFAULT_PAGE_BUILDER','myportal.adminFeature.myportal_default_page_builder.name',1,'jsp/admin/plugins/myportal/BuildDefaultPage.jsp','myportal.adminFeature.myportal_default_page_builder.description',0,'myportal','APPLICATIONS','images/admin/skin/plugins/myportal/myportal.png', NULL);

--
-- Dumping data for table core_user_right
--
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_WIDGET_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_WIDGET_MANAGEMENT',2);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_CATEGORY_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_CATEGORY_MANAGEMENT',2);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_DEFAULT_PAGE_BUILDER',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYPORTAL_DEFAULT_PAGE_BUILDER',2);
