drop database if exists expenditure_management;

create database expenditure_management;

use expenditure_management;

drop table if exists roles;

create table roles (id int auto_increment primary key, name varchar(20) not null unique);

insert into roles(name) values('User'),('Admin');

drop table if exists users;

create table users (
id int auto_increment primary key,
username varchar(200) not null unique,
email varchar(255) not null unique, 
password varchar(255) not null, 
role_id int not null,
created_at datetime not null, 
enabled BOOLEAN NOT NULL DEFAULT TRUE,
foreign key(role_id) references roles(id));

insert into users (username,email, password, role_id, created_at) values
('admin','admin@gmail.com','$2a$12$AsNkCmTvvwY0bxy.Pv3e.u1quvwCbfaUfGe46qfUqaZf9DxY30jDa',
2,now()),
('rahul','rahul@gmail.com','$2a$12$74OIVFFNxfoiqEXCPLqja.dHlbmPD2gKJYDUUqiLI1OrRqcU.MfY2',
1,now());

drop table if exists user_info;

create table user_info (
id int auto_increment primary key,
user_id int not null,
name varchar(255) not null, 
income decimal(10,2),
savings decimal(10,2),
financial_notes varchar(255),
created_at datetime default current_timestamp default now(),
updated_at datetime default current_timestamp on update current_timestamp,
last_expense_date datetime,
foreign key(user_id) references users(id)
);

drop table if exists categories;

create table categories (
    id int auto_increment primary key,
    name varchar(100) not null unique,
    description varchar(100)
);

drop table if exists payment_methods;

create table payment_methods 
(id int auto_increment primary key, 
name varchar(255) not null);

insert into payment_methods(name) values('CASH'),('CARD'),('UPI'),('BANK_TRANSFER');

drop table if exists expenses;

create table expenses (
    id int auto_increment primary key,
    user_id int not null,
    description varchar(255),
    amount decimal(10,2) not null,
    expense_date datetime not null,
    category_id int not null,
    payment_method_id int not null,
    created_at datetime default current_timestamp,
    foreign key (user_id) references users(id),
    foreign key (category_id) references categories(id),
    foreign key (payment_method_id) references payment_methods(id)
);


-- update user_info
-- set last_expense_date = (
--     select max(e.expense_date)
--     from expenses e
--     where e.user_id = user_info.user_id
-- );
-- logic in adding an expense logic



drop table if exists budgets;

create table budgets (
    id int auto_increment primary key,
    user_id int not null,
    category_id int not null,
    amount decimal(10,2) not null,
    spent_amount decimal(10,2) not null default 0.0,
    month date not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,
    notes varchar(255),
    status enum('ACTIVE', 'EXCEEDED', 'UNDER') default 'ACTIVE',
    foreign key (user_id) references users(id),
    foreign key (category_id) references categories(id),
    unique(user_id, category_id, month)
);


drop table if exists admin_requests;

create table admin_requests (
id int primary key auto_increment, 
requested_user_id int not null unique,
message varchar(255) not null,
foreign key (requested_user_id) references users(id)
);










