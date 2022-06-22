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