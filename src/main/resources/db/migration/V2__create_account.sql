CREATE TABLE IF NOT EXISTS spread (
    id UUID PRIMARY KEY,
    spread_levels JSONB NOT NULL,

    CONSTRAINT spread_levels_is_array CHECK (jsonb_typeof(spread_levels) = 'array')
);

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
    spread_id UUID NOT NULL,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,

    CONSTRAINT fk_account_spread
        FOREIGN KEY (spread_id) REFERENCES spread(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);
