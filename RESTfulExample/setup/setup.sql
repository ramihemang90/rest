
create table users(
	id int primary key,
	username varchar(80) not null,
	password varchar(80) not null
	
);

create table customers(
	id int primary key,
	firstname varchar(80),
	email_address varchar(255),
	mobile varchar(15)
);


create table user_customer_mappings(
	user_id int not null,
	customer_id int not null
);

insert into users(id,username,password) values(1,"admin","admin");
insert into customers(id,firstname,email_address,mobile) values(1,"Hemang Rami","send2hemang@gmail.com","123456677");

insert into user_customer_mappings(user_id,customer_id) values(1,1);



/*select c.email_address from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=1;
 * select c.mobile from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=1
 * 
 * */
