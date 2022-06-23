drop table if exists test;
create table test(
id bigint not null comment 'id',
name varchar(50) comment 'name',
primary key (id)
) engine=innodb default charset=utf8mb4 comment='testing';

create table demo(
                     id bigint not null comment 'id',
                     name varchar(50) comment 'name',
                     password varchar(50) comment 'password',
                     primary key (id)
) engine=innodb default charset=utf8mb4 comment='demo';

insert into `test` (id, name) values (1, 'first_test');
insert into `test` (id, name) values (2, 'second_test');

insert into `demo` (id, name, password) values (1, 'first_test', '111');
insert into `demo` (id, name, password) values (2, 'second_test', '222');

select * from test;
select * from demo;

# 电子书表
drop table if exists `ebook`;
create table `ebook` (
                         `id` bigint not null comment 'id',
                         `name` varchar(50) comment '名称',
                         `category1_id` bigint comment '分类1',
                         `category2_id` bigint comment '分类2',
                         `description` varchar(200) comment '描述',
                         `cover` varchar(200) comment '封面',
                         `doc_count` int not null default 0 comment '文档数',
                         `view_count` int not null default 0 comment '阅读数',
                         `vote_count` int not null default 0 comment '点赞数',
                         primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='电子书';

insert into `ebook` (id, name, description) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');