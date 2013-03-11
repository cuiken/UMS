insert into acct_user (id, login_name, name, email, password, salt, status) values(1,'admin','Admin','admin@springside.org.cn','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','enabled');
insert into acct_user (id, login_name, name, email, password, salt, status) values(2,'user','Calvin','user@springside.org.cn','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');
insert into acct_user (id, login_name, name, email, password, salt, status) values(3,'user2','Jack','jack@springside.org.cn','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');
insert into acct_user (id, login_name, name, email, password, salt, status) values(4,'user3','Kate','kate@springside.org.cn','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');
insert into acct_user (id, login_name, name, email, password, salt, status) values(5,'user4','Sawyer','sawyer@springside.org.cn','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');
insert into acct_user (id, login_name, name, email, password, salt, status) values(6,'user5','Ben','ben@springside.org.cn','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','enabled');

insert into acct_group (id, name) values(1,'Admin');
insert into acct_group (id, name) values(2,'User');

insert into acct_group_permission(group_id,permission) values(1,'user:view');
insert into acct_group_permission(group_id,permission) values(1,'user:edit');
insert into acct_group_permission(group_id,permission) values(2,'user:view');

insert into acct_user_group (user_id, group_id) values(1,1);
insert into acct_user_group (user_id, group_id) values(1,2);
insert into acct_user_group (user_id, group_id) values(2,2);
insert into acct_user_group (user_id, group_id) values(3,2);
insert into acct_user_group (user_id, group_id) values(4,2);
insert into acct_user_group (user_id, group_id) values(5,2);
insert into acct_user_group (user_id, group_id) values(6,2);
