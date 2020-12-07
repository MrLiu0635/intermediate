create table material(id int primary key,name varchar(255),unit varchar(1023), unitprice varchar(255));
insert into material(id, name, unit, unitprice) values(1, 'iphone12', '部', '7888.00000');
insert into material(id, name, unit, unitprice) values(2, '充电插头', '个', '199.00000');
insert into material(id, name, unit, unitprice) values(3, '苹果', '千克', '25.00000');
insert into material(id, name, unit, unitprice) values(4, '青岛啤酒', 'ml', '0.02000');

create table stock(id int primary key, materialid int, quantity varchar(255));
insert into stock(id, materialid, quantity) values(1, 1, '0.00000');
insert into stock(id, materialid, quantity) values(2, 2, '0.00000');
insert into stock(id, materialid, quantity) values(3, 3, '0.00000');
insert into stock(id, materialid, quantity) values(4, 4, '0.00000');

create table sale_order(id int primary key, documenttype int, number varchar(255), bizdate TIMESTAMP, salemployee varchar(255), customer varchar(255), amounttotal varchar(36) );

create table sale_order_line(id varchar(36) primary key, materialid int, quantity varchar(255), amount varchar(255), orderid int);