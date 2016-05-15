create table organizations(
	id int primary key AUTO_INCREMENT,
	name varchar(80) not null,
	send_email_subject varchar(500),
	send_email_template LONGTEXT,
	cancel_email_subject varchar(500),
	cancel_email_template LONGTEXT,
	send_sms_template LONGTEXT,
	cancel_sms_template LONGTEXT
);



create table users(
	id int primary key,
	username varchar(80) not null,
	password varchar(80) not null,
	org_id int not null,
	foreign key (org_id) references organizations(id),
	unique(username)
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

insert into organizations values(1,"Default Organization","Subject line for Send Email Template","Dear receipent, \n\n this is send email template.","Cancel email template subject line","Dear receipent, this is cancel template","Received Send SMS","Please ignore prevous message");

insert into users(id,username,password,org_id) values(1,"admin","admin",1);
insert into customers(id,firstname,email_address,mobile) values(1,"Hemang Rami","send2hemang@gmail.com","123456677");

insert into user_customer_mappings(user_id,customer_id) values(1,1);



/*select c.email_address from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=1;
 * select c.mobile from customers c,user_customer_mappings m where c.id=m.customer_id and m.user_id=1
 * 
 * select send_email_subject,send_email_template from organizations o, users u where o.id=u.org_id and u.id=1;
 * select cancel_email_subject,cancel_email_template from organizations o, users u where o.id=u.org_id and u.id=1;
 * select send_sms_template from organizations o, users u where o.id=u.org_id and u.id=1;
 * select cancel_sms_template from organizations o, users u where o.id=u.org_id and u.id=1;
 * 
 * */
