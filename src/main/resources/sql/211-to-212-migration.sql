create table log_app_use(
  id bigint auto_increment,
  imei varchar(50),
	imsi varchar(50),
	resolution varchar(50),
	op varchar(35),
	fm varchar(255),
	version varchar(35),
	net varchar(35),
	language varchar(35),
	st varchar(35),
	app varchar(100),
	come_from varchar(100),
	mac varchar(100),
	create_time timestamp  not null default 0,
	index ct_index(create_time),
	primary key(id)
);