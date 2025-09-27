CREATE TABLE IF NOT EXISTS technical_account_ledger (
    id UUID PRIMARY KEY,
    transaction_id uuid,
    account_id UUID,
    debit NUMERIC(38, 18),
    credit NUMERIC(38, 18),
    currency VARCHAR(3),
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
