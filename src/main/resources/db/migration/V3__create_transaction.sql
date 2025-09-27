CREATE TABLE IF NOT EXISTS "transaction" (
    id UUID PRIMARY KEY,
    source_account_id UUID NOT NULL,
    destination_account_id UUID NOT NULL,
    base_currency VARCHAR(3) NOT NULL,
    quote_currency VARCHAR(3) NOT NULL,
    reference_currency VARCHAR(3) NOT NULL,
    amount_in_quote_currency NUMERIC(38, 2),
    amount_in_reference_currency NUMERIC(38, 18),
    amount_in_base_currency NUMERIC(38, 18),
    commission NUMERIC(38, 2),
    base_to_quote_rate NUMERIC(38, 10),
    base_to_reference_rate NUMERIC(38, 10),
    title VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_transaction_source_account
        FOREIGN KEY (source_account_id) REFERENCES account (id),
    CONSTRAINT fk_transaction_destination_account
        FOREIGN KEY (destination_account_id) REFERENCES account (id)
);

CREATE INDEX IF NOT EXISTS idx_transaction_source_account_id
    ON "transaction"(source_account_id);

CREATE INDEX IF NOT EXISTS idx_transaction_destination_account_id
    ON "transaction"(destination_account_id);