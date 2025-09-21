CREATE TABLE IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL,
    active BOOLEAN NOT NULL,
    number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);