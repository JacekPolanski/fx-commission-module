CREATE TABLE IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    balance NUMERIC(38,2) NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    iban VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);
