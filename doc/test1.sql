drop table if exists test;
create table test(
id bigint not null comment 'id',
name varchar(50) comment 'name',
primary key (id)
) engine=innodb default charset=utf8mb4 comment='testing';

insert into `test` (id, name) values (1, 'first_test');

select * from test;