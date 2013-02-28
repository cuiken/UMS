    drop table if exists acct_group;

    drop table if exists acct_group_permission;

    drop table if exists acct_user;

    drop table if exists acct_user_group;

    create table acct_group (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table acct_group_permission (
        group_id bigint not null,
        permission varchar(255) not null
    );

    create table acct_user (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        salt varchar(64),
        status varchar(32),
        primary key (id)
    );

    create table acct_user_group (
        user_id bigint not null,
        group_id bigint not null
    );
    
    
    create table f_category(
	 id bigint generated by default as identity,
	name varchar(50) not null,
	value varchar(50),
	icon varchar(255),
	description varchar(100),
	parent_id int,
	dtype varchar(32) not null,
	primary key(id)
	
);

create table f_file(
	 id bigint generated by default as identity,
	name varchar(50) not null,
	title varchar(50),
	ux_size int,
	apk_size int,
	price decimal(3,1),
	ux_w_path varchar(255),
	ux_h_path varchar(255),
	ux_path varchar(255),
	apk_path varchar(255),
	icon_path varchar(255),
	ad_path varchar(255),
	pre_web_path varchar(255),
	pre_client_path varchar(255),
	market_url varchar(100),
	version varchar(10),
	dtype varchar(32),
	create_time varchar(30),
	modify_time varchar(30),
	primary key(id)

);

create table f_third_url(
	 id bigint generated by default as identity,
	c_mobile varchar(500),
	c_unicome varchar(500),
	c_tele varchar(500),
	theme_id int,
	primary key(id)
);

create table f_file_info(
	 id bigint generated by default as identity,
	title varchar(50) not null,
	language varchar(10),
	author varchar(50),
	short_description varchar(255),
	long_description varchar(500),
	price float,
	f_id int,
	primary key(id)
	
);

create table f_store_info(
	 id bigint generated by default as identity,
	title varchar(50) not null,
	language varchar(10),
	author varchar(50),
	short_description varchar(255),
	long_description varchar(500),
	price float,
	f_id int,
	fi_id int,
	store_id int,
	primary key(id)
	
);

create table f_file_shelf(
	 id bigint generated by default as identity,
	f_id int,
	s_id int,
	sort int,
	primary key(id)
);

create table f_file_store(
	 id bigint generated by default as identity,
	f_id int,
	s_id int,
	primary key(id)
);

create table f_category_file(
	 id bigint generated by default as identity,
	category_id int,
	file_id int,
	primary key(id)
);

create table f_market(
	 id bigint generated by default as identity,
	name varchar(50),
	value varchar(50),
	pk_name varchar(100),
	market_key varchar(255),
	primary key(id)
);

create table f_market_file(
	 id bigint generated by default as identity,
	m_id int,
	f_id int,
	primary key(id)

);

create table f_market_info(
	 id bigint generated by default as identity,
	key_name varchar(30),
	key_value varchar(30),
	theme_id int,
	market_id int,
	primary key(id)
);

create table f_client(
	id bigint generated by default as identity,
	name varchar(50),
	size int,
	pk_name varchar(255),
	path varchar(255),
	version varchar(35),
	create_time varchar(35),
	modify_time varchar(35),
	dtype varchar(32) not null,
	primary key(id)
);

create table f_polling(
	id bigint generated by default as identity,
	uuid varchar(50) not null,
	name varchar(35) not null,
	link varchar(255) not null,
	ser_type varchar(35),
	ser_desc varchar(255),
	show_begin varchar(35),
	show_end varchar(35),
	show_total int,
	show_perday tinyint,
	status tinyint,
	store varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);

create table f_polling_preview(
	id bigint not null auto_increment,
	name varchar(50),
	addr varchar(100),
	spec varchar(25),
	polling int,
	primary key(id)
);

create table log_f_client(
	id bigint not null auto_increment,
	imei varchar(50),
	imsi varchar(50),
	store_type varchar(20),
	down_type varchar(20),
	language varchar(20),
	client_version varchar(20),
	client_type varchar(20),
	resolution varchar(50),
	from_market varchar(50),
	auto_switch tinyint,
	safety_lock tinyint,
	net_env varchar(35),
	operators varchar(35),
	model varchar(35),
	create_time varchar(32),
	primary key(id)
);

create table log_f_store(
	id bigint not null auto_increment,
	imei varchar(50),
	imsi varchar(50),
	app_name varchar(50) not null default '',
	store_type varchar(20),
	down_type varchar(20),
	language varchar(20),
	client_version varchar(20),
	resolution varchar(100),
	from_market varchar(255),
	request_method varchar(255),
	request_params varchar(255),
	create_time varchar(32),
	primary key(id)
);

create table log_f_store2(
	 id bigint generated by default as identity,
	imei varchar(50) not null default '',
	imsi varchar(50) not null default '',
	store_type varchar(20) not null default '',
	down_type varchar(20),
	language varchar(10) not null default '',
	client_version varchar(20) not null default '',
	resolution varchar(20) not null default '',
	from_market varchar(255) not null default '',
	request_method varchar(255),
	request_params varchar(255),
	operator varchar(10) not null default '',
	app_name varchar(50) not null default '',
	client_type varchar(20),
	model varchar(35),
	net_env varchar(10),
	come_from varchar(35),
	content_version varchar(20),
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_f_download(
	 id bigint generated by default as identity,
	imei varchar(50) not null default '',
	imsi varchar(50) not null default '',
	app_name varchar(50) not null default '',
	from_market varchar(255) not null default '',
	request_method varchar(35),
	client_type varchar(20),
	create_time timestamp not null default 0,
	primary key(id)
);

create table lock_user(
	 id bigint generated by default as identity,
	imei varchar(35) not null unique,
	imsi varchar(35) not null default 0,
	model varchar(35) not null default '',
	resolution varchar(20) not null default '',
	register_time timestamp not null default 0,
	active_time timestamp not null default 0,
	primary key(id)
);

create table log_count_unzip(
	 id bigint generated by default as identity,
	app_name varchar(35),
	unzip int,
	market_name varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_count_get_client(
	 id bigint generated by default as identity,
	app_name varchar(35),
	market_name varchar(35),
	download int,
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_f_content(
	id bigint not null auto_increment,
	imei varchar(50),
	imsi varchar(50),
	language varchar(20),
	app_name varchar(35),
	do_type varchar(35),
	net_env varchar(35),
	operators varchar(35),
	resolution varchar(100),
	from_market varchar(255),
	content_version varchar(35),
	client_type varchar(20),
	client_version varchar(35),
	create_time varchar(32),
	primary key(id)
);

create table log_count_client(
	id bigint not null auto_increment,
	create_time varchar(32),
	open_count int not null default 0,
	total_user int not null default 0,
	open_user int not null default 0,
	increment_user int not null default 0,
	total_download int not null default 0,
	down_by_content int not null default 0,
	down_by_share int not null default 0,
	down_by_other int not null default 0,
	visit_store_count int not null default 0,
	visit_store_user int not null default 0,
	total_install int not null default 0,
	install_withfm int not null default 0,
	install_nonfm int not null default 0,
	take_times int not null default 0,
	primary key(id)
	
);

create table log_count_content(
	 id bigint generated by default as identity,
	log_date varchar(32),
	theme_name varchar(32),
	total_visit int,
	visit_by_ad int,
	visit_by_store int,
	total_down int,
	down_by_store int,
	create_time timestamp,
	primary key(id)
);

create table log_cc_market(
	 id bigint generated by default as identity,
	market_name varchar(32),
	total_down int,
	log_c_id int,
	primary key(id)
);

create table log_f_feedback(
	id bigint not null auto_increment,
	content varchar(500) not null default 'content is empty!auto created',
	contact varchar(255),
	params varchar(255),
	status tinyint not null default 0,
	create_time timestamp not null default 0,
	modify_time timestamp not null default 0,
	primary key(id)
);

create table log_f_cmcc(
	id bigint not null auto_increment,
	sid varchar(35) not null default 0,
	theme_id int not null default 0,
	imei varchar(50) not null default 0,
	resolution varchar(35),
	store_type varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_cmcc_result(
	id bigint not null auto_increment,
	theme_id varchar(20),
	sid varchar(35),
	sign varchar(35),
	result varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_f_redirect(
	id bigint not null auto_increment,
	app_name varchar(50),
	link_addr varchar(255),
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_f_poll(
	id bigint not null auto_increment,
	bcid varchar(50),
	app varchar(50),
	url varchar(255),
	imei varchar(50),
	imsi varchar(50),
	language varchar(10),
	version varchar(10),
	from_market varchar(100),
	resolution varchar(20),
	net_env varchar(25),
	operator varchar(25),
	do_type varchar(10),
	create_time timestamp not null default 0,
	
	primary key(id)
);

create index poll_time on log_f_poll (create_time);



create table log_count_c_install_per_market(
	 id bigint generated by default as identity,
	date varchar(35),
	market_name varchar(35),
	installed int,
	create_time timestamp not null default 0,
	primary key(id)
);

create table log_count_c_install_with_content(
	 id bigint generated by default as identity,
	date varchar(35),
	app_name varchar(35),
	market_name varchar(35),
	installed int,
	create_time timestamp not null default 0,
	primary key(id)
);


create table nav_category(
	 id bigint generated by default as identity,
	name varchar(35),
	value varchar(35),
	description varchar(255),
	uuid varchar(100),
	parent_id int,
	dtype varchar(35),
	primary key(id)
);


create table nav_item(
	 id bigint generated by default as identity,
	name varchar(35) not null,
	status varchar(35) not null default 'enabled',
	nav_addr varchar(255),
	uuid varchar(100),
	description varchar(100),
	icon varchar(255),
	primary key(id)

);


create table nav_board_navigator(
	 id bigint generated by default as identity,
	b_id int,
	n_id int,
	primary key(id)
);


create table nav_tag_navigator(
	 id bigint generated by default as identity,
	t_id int,
	n_id int,
	primary key(id)

);

create table nav_category_preview(
	id bigint generated by default as identity,
	name varchar(50),
	value varchar(255),
	level varchar(50),
	dtype varchar(32),
	pid int,
	primary key(id)
);

create table nav_item_preview(
	id bigint generated by default as identity,
	name varchar(50),
	value varchar(255),
	level varchar(50),
	nid int,
	primary key(id)
);

create table nav_log_search(
	id bigint generated by default as identity,
	imei varchar(35) not null default 0,
	key_word varchar(100),
	search_type varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);

create table nav_hotlink(
	id bigint generated by default as identity,
	name varchar(35),
	addr varchar(255),
	descr varchar(255),
	board_id int,
	tag_id int,
	primary key(id)
);

CREATE TABLE `click_log` (
 id bigint generated by default as identity,
  `user_id` varchar(255) NOT NULL,
  `button_id` varchar(100) NOT NULL,
  `date` timestamp NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

create table log_funbrowser_launch(
	id bigint generated by default as identity,
	imei varchar(35) not null default '',
	imsi varchar(35),
	language varchar(10),
	resolution varchar(35),
	op varchar(35),
	model varchar(35),
	net varchar(35),
	launcher varchar(35) not null default 'default',
	version varchar(35),
	create_time timestamp not null default 0,
	primary key(id)
);