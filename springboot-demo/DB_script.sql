create database test1;
use test1;

create table `test_table` (
  `id` int not null,
  `val` varchar(50) not null,
  primary key (`id`)
) engine=innodb default charset=utf8;

insert into test_table values (1,'a'),(2,'b'),(3,'c'),(4,'d');

create database test2;
use test2;

create table `test_table` (
  `id` int not null,
  `val` varchar(50) not null,
  primary key (`id`)
) engine=innodb default charset=utf8;

insert into test_table values (1,'A'),(2,'B'),(3,'C'),(4,'D');