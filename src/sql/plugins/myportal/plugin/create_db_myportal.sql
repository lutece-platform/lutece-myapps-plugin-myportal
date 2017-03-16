
--
-- Structure for table myportal_category
--
DROP TABLE IF EXISTS myportal_category;
CREATE TABLE myportal_category (
id_category int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
description varchar(50) NOT NULL default '',
PRIMARY KEY (id_category)
);

--
-- Structure for table myportal_column_style
--
DROP TABLE IF EXISTS myportal_column_style;
CREATE TABLE myportal_column_style (		
id_style int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
css_class varchar(50) NOT NULL default '',
PRIMARY KEY (id_style)
);

--
-- Structure for table myportal_default_page
--
DROP TABLE IF EXISTS myportal_default_page;
CREATE TABLE myportal_default_page (
id_widget_component int(11) NOT NULL default '0',
id_widget int(11) NOT NULL default '0',
widget_column int(11) NOT NULL default '0',
widget_order int(11) NOT NULL default '0',
PRIMARY KEY (id_widget_component)
);

-- -------------------------------------------
-- Structure for table myportal_icon --
-- -------------------------------------------
DROP TABLE IF EXISTS myportal_icon;
CREATE TABLE myportal_icon
(
 	id_icon INT DEFAULT 0 NOT NULL,
	name VARCHAR(255) DEFAULT NULL,
	mime_type VARCHAR(50) DEFAULT NULL,
	file_value LONG VARBINARY,
	width INT DEFAULT NULL,
	height INT DEFAULT NULL,
	display_fo SMALLINT DEFAULT '0',
	PRIMARY KEY (id_icon)
);


--
-- Structure for table myportal_page_builder_parameter
--
DROP TABLE IF EXISTS myportal_page_builder_parameter;
CREATE TABLE myportal_page_builder_parameter (
	parameter_key varchar(100) NOT NULL,
	parameter_value varchar(100) NOT NULL,
	PRIMARY KEY (parameter_key)
);

--
-- Structure for table myportal_widget_style
--
DROP TABLE IF EXISTS myportal_widget_style;
CREATE TABLE myportal_widget_style (		
id_style int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
css_class varchar(50) NOT NULL default '',
PRIMARY KEY (id_style)
);

--
-- Structure for table myportal_widget
--
DROP TABLE IF EXISTS myportal_widget;
CREATE TABLE myportal_widget (		
id_widget int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
description varchar(50) NOT NULL default '',
id_category int(11) NOT NULL default '0',
widget_type varchar(50) NOT NULL default '',
id_icon int(11) NOT NULL default '0',
config_data long varbinary default NULL,
id_style int(11) NOT NULL default '0',
status smallint NOT NULL default '0',
is_essential smallint NOT NULL default '0',
is_new smallint NOT NULL default '0',
PRIMARY KEY (id_widget)
);

--
-- Structure for table myportal_user_pageconfig
--
DROP TABLE IF EXISTS myportal_user_pageconfig;
CREATE TABLE myportal_user_pageconfig (
user_guid varchar(50) NOT NULL default '',
user_pageconfig LONG VARCHAR DEFAULT NULL,
PRIMARY KEY (user_guid)
);
