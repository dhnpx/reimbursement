DROP TABLE IF EXISTS  ticketapi.receipts ;

CREATE TABLE IF NOT EXists ticketapi.receipts (
    receipt_id serial4 NOT NULL PRIMARY KEY,
    ticket_id int4 NOT NULL,
    file_name varchar(100) NOT NULL,
    CONSTRAINT fk_ticket_id
        FOREIGN KEY(ticket_id)
        REFERENCES ticketapi.tickets(ticket_id)
)