alter table f_file add column (is_hot tinyint not null default 0,is_new tinyint not null default 0);

create table f_gender(
  id int auto_increment,
  name varchar(35) not null,
  primary key(id)
)ENGINE=InnoDB;

insert into f_gender(name) values ("男");
insert into f_gender(name) values ("女");

create table f_file_gender(
  file_id int not null,
  gender_id int not null,
  primary key(file_id,gender_id)
)ENGINE=InnoDB;