create table f_polling_enhancement(
  id int auto_increment,
  uuid varchar(35) not null,
  content_name varchar(50) not null,
  fm varchar(255),
  appk varchar(255),
  app_url varchar(255),
  img_name varchar(255),
  img_link varchar(255),
  percent int,
  store varchar(35) not null,
  dtype varchar(35),
  status tinyint not null default 1,
  create_time timestamp not null default 0,
	primary key(id)
)ENGINE=InnoDB;