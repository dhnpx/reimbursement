DROP TABLE IF EXISTS ticketapi.tickets;

CREATE TABLE IF NOT EXISTS ticketapi.tickets (
    ticket_id serial4 NOT NULL PRIMARY KEY, 
    user_id int4 NOT NULL,
    amount int4 NOT NULL,
    type varchar(10) NOT NULL DEFAULT 'other' :: character varying,
    description varchar(200) NOT NULL,
    status varchar (20) NOT NULL DEFAULT 'pending' :: character varying,
    CONSTRAINT fk_user_id
        FOREIGN KEY(user_id)
        REFERENCES ticketapi.accounts(user_id)
)