INSERT INTO customer (id, name, active, created_at, updated_at)
VALUES
  ('3f6a2c2b-9f1e-4a7e-9c8e-2a1b7b1a1e10', 'Jan Kowalski', TRUE, now(), now()),
  ('a2b4c6d8-e0f2-4a1b-9c3d-5e7f9a1b2c3d', 'Anna Nowak', TRUE, now(), now());

INSERT INTO spread (id, spread_levels) VALUES
    ('11111111-1111-1111-1111-111111111111',
     '[
        {"threshold": 0,     "commission": 10.00},
        {"threshold": 1000,  "commission": 8.00},
        {"threshold": 5000,  "commission": 6.00},
        {"threshold": 10000, "commission": 5.00}
      ]'::jsonb),

    ('22222222-2222-2222-2222-222222222222',
     '[
        {"threshold": 0,     "commission": 15.00},
        {"threshold": 2000,  "commission": 12.50},
        {"threshold": 8000,  "commission": 9.75}
      ]'::jsonb),

    ('33333333-3333-3333-3333-333333333333',
     '[
        {"threshold": 0,      "commission": 5.00},
        {"threshold": 10000,  "commission": 4.25},
        {"threshold": 50000,  "commission": 3.50},
        {"threshold": 100000, "commission": 2.00}
      ]'::jsonb),
    ('44444444-4444-4444-4444-444444444444',
     '[
        {"threshold": 0, "commission": 5.00}
      ]'::jsonb);

 INSERT INTO account (
     id, customer_id, name, currency, balance, active, iban, created_at, updated_at, spread_id
 ) VALUES
     ('3f6b58f6-3c6a-4b12-9b80-6d7c3a2f5c11', '3f6a2c2b-9f1e-4a7e-9c8e-2a1b7b1a1e10',
      'Konto Oszczędnościowe', 'PLN', 125000, TRUE, 'PL-1020-5555-1234-5678-9012-3456', NOW(), NOW(),
      '11111111-1111-1111-1111-111111111111'
     ),
     ('9a2c7dfe-8c3b-4d2e-9f71-0a1b2c3d4e5f', 'a2b4c6d8-e0f2-4a1b-9c3d-5e7f9a1b2c3d',
      'Savings Account', 'EUR', 9876543, TRUE, 'DE-3704-0044-0532-0130-00', NOW(), NOW(),
      '44444444-4444-4444-4444-444444444444'
     );