
--
-- Structure for table myportal_category
--

DROP TABLE IF EXISTS myportal_category;
CREATE TABLE myportal_category (		
id_category int(11) NOT NULL default '0',
id_parent int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
description varchar(50) NOT NULL default '',
PRIMARY KEY (id_category)
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
icon_url varchar(50) NOT NULL default '',
is_movable int(11) NOT NULL default '0',
is_removable int(11) NOT NULL default '0',
is_resizable int(11) NOT NULL default '0',
config_data LONG VARCHAR DEFAULT NULL,
PRIMARY KEY (id_widget)
);

--
-- Structure for table myportal_layout
--

DROP TABLE IF EXISTS myportal_layout;
CREATE TABLE myportal_layout (		
id_layout int(11) NOT NULL default '0',
name varchar(50) NOT NULL default '',
description varchar(50) NOT NULL default '',
layout varchar(50) NOT NULL default '',
PRIMARY KEY (id_layout)
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

