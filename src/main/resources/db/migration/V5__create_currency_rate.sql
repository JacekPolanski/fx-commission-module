CREATE TABLE IF NOT EXISTS currency_rate (
    currency_code VARCHAR(10) PRIMARY KEY,
    rate NUMERIC(19,8) NOT NULL CHECK (rate > 0)
);

INSERT INTO currency_rate (currency_code, rate) VALUES
('USD', 0.90272271),
('EUR', 1.00000000),
('GBP', 1.14595648),
('JPY', 0.00632690),
('CHF', 0.90271028),
('PLN', 0.21892377)
ON CONFLICT (currency_code) DO NOTHING;