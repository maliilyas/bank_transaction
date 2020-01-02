DROP TABLE IF EXISTS customer_transaction
;

DROP TABLE IF EXISTS customer_account
;

CREATE TABLE customer_account
(
    id            LONG        NOT NULL,
    user_name     VARCHAR(50) NOT NULL UNIQUE,
    password      VARCHAR(14) NOT NULL,
    iban          VARCHAR(22) NOT NULL UNIQUE,
    balance       DOUBLE      NOT NULL,
    currency_type VARCHAR(3)  NOT NULL,

    CONSTRAINT pk_t_customer PRIMARY KEY (id)
)
;

CREATE TABLE customer_transaction
(
    id                    LONG AUTO_INCREMENT,
    creation_time         LONG        NOT NULL,
    customer_from_iban    VARCHAR(22) NOT NULL,
    customer_to_iban      VARCHAR(22) NOT NULL,
    amount                DOUBLE      NOT NULL,
    transaction_reference VARCHAR(20) NOT NULL,
    transaction_message   VARCHAR(50) NOT NULL,
    transaction_status    VARCHAR(10) NOT NULL,
    transaction_comment   VARCHAR(50),

    CONSTRAINT pk_t_customer_transaction PRIMARY KEY (id)
)
;


// Inserting data into table customer_account
INSERT INTO customer_account
VALUES (1, 'aliilyas', 'ma420', 'DE75512108001245126199', 6564.45, 'EUR')
;

INSERT INTO customer_account
VALUES (2, 'goethe', 'faust123', 'DE12500105170648489890', 10000000000, 'EUR')
;

INSERT INTO customer_account
VALUES (3, 'homework', 'revolut', 'DE27100777770209299700', 9000000000000, 'EUR')
;