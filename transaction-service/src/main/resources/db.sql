DROP TABLE IF EXISTS customer_transaction
;

DROP TABLE IF EXISTS transaction_status
;

DROP TABLE IF EXISTS customer_account
;

DROP SEQUENCE IF EXISTS s_customer_transaction_id
;

DROP SEQUENCE IF EXISTS s_transaction_status_id
;

CREATE SEQUENCE s_customer_transaction_id START WITH 1
;

CREATE SEQUENCE s_transaction_status_id START WITH 1
;

CREATE TABLE customer_account
(
    id            INT         NOT NULL,
    user_name     VARCHAR(50) NOT NULL,
    password      VARCHAR(14) NOT NULL,
    iban          VARCHAR(22) NOT NULL,
    currency_type VARCHAR(3)  NOT NULL,
    balance       DOUBLE      NOT NULL,

    CONSTRAINT pk_t_customer PRIMARY KEY (id)
)
;

CREATE TABLE customer_transaction
(
    id                 INT         NOT NULL,
    customer_id        INT         NOT NULL,
    creation_time      TIMESTAMP   NOT NULL,
    customer_from_iban VARCHAR(22) NOT NULL,
    customer_to_iban   VARCHAR(22) NOT NULL,
    amount             DOUBLE      NOT NULL,
    currency_type      VARCHAR(3)  NOT NULL,

    CONSTRAINT pk_t_customer_transaction PRIMARY KEY (id)
    //CONSTRAINT fk_t_customer_account_id FOREIGN KEY (customer_id) REFERENCES customer_account (id)
)
;


// Inserting data into table customer_account
INSERT INTO customer_account
VALUES (1, 'aliilyas', '!Asg%456Bnf', 'DE75512108001245126199', 'EUR', 5403.45)
;

INSERT INTO customer_account
VALUES (2, 'sorrowsofyoungwerther', '!nmksai34%', 'DE12500105170648489890', 'USD', 1000000000000)
;

INSERT INTO customer_account
VALUES (3, 'homework', '£$9afnkf', 'DE27100777770209299700', 'EUR', 10393232032032032023203)
;