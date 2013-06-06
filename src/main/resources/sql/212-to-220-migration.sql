alter table f_file add column (is_hot tinyint not null default 0,is_new tinyint not null default 0);

alter table f_file add column (percent int default 1);

update f_file set dtype='2' where  ux_size is null;

create table f_tag(
  id int auto_increment,
  name varchar(35) not null,
  primary key(id)
)ENGINE=InnoDB;

insert into f_tag(name) values ("男");
insert into f_tag(name) values ("女");
insert into f_tag(name) values ("隐藏");

create table f_file_tag(
  file_id int not null,
  tag_id int not null,
  primary key(file_id,tag_id)
)ENGINE=InnoDB;

create table f_topic(
  id int auto_increment,
  name varchar(50) not null,
  value varchar(35),
  description varchar(255),
  icon varchar(255),
  sort int,
  primary key(id)
)ENGINE=InnoDB;

create table f_file_topic(
  id int auto_increment,
  theme_id int,
  topic_id int,
  sort int,
  primary key(id)
)ENGINE=InnoDB;

/*=========================================================================================================*/
create table log_f_coop(
  id bigint auto_increment,
  imei varchar(50),
	imsi varchar(50),
	resolution varchar(50),
	operators varchar(35),
	from_market varchar(255),
	client_version varchar(35),
	net_env varchar(35),
	language varchar(35),
	price varchar(35),
	app_name varchar(100),
	do_type varchar(35),
	charge_type varchar(35),
	client_type varchar(35),
	deduct varchar(50),
	create_time timestamp  not null default 0,
	index ct_index(create_time),
	primary key(id)
);

alter table f_polling_enhancement add column content_info varchar(100);

create table f_coop(
  id bigint auto_increment,
  name varchar(50) not null,
  value varchar(50) not null,
  channel varchar(50),
  primary key(id)
)ENGINE=InnoDB;

alter table log_f_coop add column(model varchar(35),charge_time varchar(35));