DROP TABLE ticketapi.accounts;

CREATE TABLE ticketapi.accounts (
    user_id serial4 NOT NULL PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role varchar(20) NOT NULL DEFAULT 'employee' :: character varying,
    surname varchar(100) NOT NULL,
    given_name varchar(100) NOT NULL,
    address1 varchar(100) NOT NULL,
    address2 varchar(30),
    city varchar(30) NOT NULL,
    state varchar(30) NOT NULL,
    zip int4 NOT NULL
)