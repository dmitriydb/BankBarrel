CREATE TABLE webapp_user
(
id serial primary key,
username varchar(255) unique not null,
registration_ts timestamp not null,
last_login_ts timestamp,
client_id integer
);

CREATE TABLE webapp_operation_type (
name varchar(255) primary key
);

INSERT INTO webapp_operation_type (name) VALUES
('REGISTRATION'),
('LOGIN'),
('LOGOUT'),
('ACCOUNT_OPENING'),
('ACCOUNT_PAGE_ACCESS'),
('PROFILE_ACCESS'),
('DEPOSIT'),
('WITHDRAW'),
('TRANSFER');

CREATE TABLE webapp_operation_status(
id serial primary key,
operation_type varchar(255) references webapp_operation_type(name),
status varchar(255) unique not null
);

INSERT INTO webapp_operation_status (operation_type, status) VALUES
('REGISTRATION', 'INIT'),
('REGISTRATION', 'SUCCESS'),
('REGISTRATION', 'WRONGDATA');

CREATE TABLE webapp_operation (
operation_id serial primary key,
init_user integer references webapp_user(id),
operation_type varchar(255) references webapp_operation_type(name),
operation_status varchar(255) references webapp_operation_status(status),
finished boolean,
startTs timestamp,
finishedTs timestamp,
requestJson varchar
);

CREATE TABLE webapp_operation_history (
id serial primary key,
operation_id integer references webapp_operation(operation_id),
startTs timestamp,
finishedTs timestamp,
status varchar(255) references webapp_operation_status(status)
);
