CREATE TABLE operation_sources (
  id serial not null primary key,
  name varchar(255) not null
);

INSERT INTO operation_sources (name) VALUES
('bb-webapp'),
('curl');

CREATE TABLE bank_accounts (
  identifier varchar primary key,
  number varchar(20) unique not null,
  type varchar not null,
  additional_type varchar not null,
  description varchar(1024),
  balance decimal,
  currency varchar(10) not null,
  owner integer not null
);

CREATE TABLE currencies (
  id serial primary key,
  code varchar(10) unique not null
);

INSERT INTO currencies (code) VALUES
('USD'),
('KZT'),
('RUB');

ALTER TABLE bank_accounts ADD CONSTRAINT fk_bank_account_currencies FOREIGN KEY (currency) REFERENCES currencies(code);

CREATE TABLE bank_account_types (
  id serial primary key,
  code varchar(255) unique not null,
  description varchar(255) not null
);

CREATE TABLE bank_account_additional_types (
  id serial primary key,
  code varchar(255) unique not null,
  description varchar(255) not null,
  owner_type integer not null
);

ALTER TABLE bank_account_additional_types ADD CONSTRAINT fk_bank_account_additional_types_owner_types FOREIGN KEY (owner_type)
REFERENCES bank_account_types (id);

ALTER TABLE bank_accounts ADD CONSTRAINT fk_bank_account_types FOREIGN KEY (type) REFERENCES bank_account_types (code);
ALTER TABLE bank_accounts ADD CONSTRAINT fk_bank_account_additional_types FOREIGN KEY (additional_type) REFERENCES bank_account_additional_types (code);

INSERT INTO bank_account_types (code, description) VALUES
('CHECKING', 'Checking account'),
('SAVING', 'Saving account');

INSERT INTO bank_account_additional_types (code, description, owner_type) VALUES
('STUDENT', 'Student account', 1),
('TRADITIONAL', 'Traditional account', 1),
('PREMIUM', 'Premium account', 1),
('SECOND_CHANCE', 'Second chance account', 1),
('INTEREST_BEARING', 'Interest bearing account', 1),
('REWARD', 'Rewards account', 1),
('SAVINGS_ONLY', 'Savings only account', 2);

CREATE TABLE bank_clients (
  identifier serial primary key,
  given_name varchar(255) not null,
  family_name varchar(255) not null,
  telephone varchar(100),
  email varchar(100)
);

ALTER TABLE bank_accounts ADD CONSTRAINT bank_accounts_owners FOREIGN KEY (owner) REFERENCES bank_clients(identifier);

CREATE TABLE currency_rates (
  currency varchar(10) primary key references currencies(code),
  rate decimal not null,
  is_more boolean not null
);

INSERT INTO currency_rates VALUES
('USD', 1, true),
('KZT', 500, false),
('RUB', 100, false);

CREATE TABLE account_openings (
  id serial primary key,
  client integer references bank_clients(identifier),
  opening_date_time timestamp not null,
  currency integer references currencies(id),
  result varchar(255),
  type integer references bank_account_types(id),
  additional_type integer references bank_account_additional_types(id),
  source integer references operation_sources(id),
  resulting_account integer
);

CREATE TABLE account_opening_status (
  code serial primary key,
  status varchar(255) not null
);

CREATE TABLE account_openings_history (
  id serial primary key,
  account_opening integer references account_openings(id),
  status integer references account_opening_status(code),
  ts timestamp not null
);



CREATE TABLE money_deposit (
  id serial primary key,
  account varchar references bank_accounts(identifier),
  amount decimal not null,
  deposit_date_time timestamp not null,
  currency integer references currencies(id),
  result varchar(255),
  source integer references operation_sources(id)
);

CREATE TABLE money_deposit_status (
  code serial primary key,
  status varchar(255) not null
);

CREATE TABLE money_deposit_history (
  id serial primary key,
  money_deposit integer references money_deposit(id),
  status integer references money_deposit_status(code),
  ts timestamp not null
);


CREATE TABLE money_withdraw (
  id serial primary key,
  account varchar references bank_accounts(identifier),
  amount decimal not null,
  withdraw_date_time timestamp not null,
  currency integer references currencies(id),
  result varchar(255),
  source integer references operation_sources(id)
);

CREATE TABLE money_withdraw_status (
  code serial primary key,
  status varchar(255) not null
);

CREATE TABLE money_withdraw_history (
  id serial primary key,
  money_withdraw integer references money_withdraw(id),
  status integer references money_withdraw_status(code),
  ts timestamp not null
);


CREATE TABLE money_transfer (
  id serial primary key,
  from_account varchar references bank_accounts(identifier),
  to_account varchar references bank_accounts(identifier),
  amount decimal not null,
  transfer_date_time timestamp not null,
  currency integer references currencies(id),
  result varchar(255),
  source integer references operation_sources(id)
);

CREATE TABLE money_transfer_status (
  code serial primary key,
  status varchar(255) not null
);

CREATE TABLE money_transfer_history (
  id serial primary key,
  money_transfer integer references money_transfer(id),
  status integer references money_transfer_status(code),
  ts timestamp not null
);
