CREATE SEQUENCE IF NOT EXISTS product_sequence
    START WITH 1
    INCREMENT BY 1;

ALTER TABLE products
    ALTER COLUMN id SET DEFAULT nextval('product_sequence');



INSERT INTO products(name, quantity, price, sale, created_at, updated_at)
VALUES ('молоко', 100, 95.0, 10, now(), now()),
       ('хлеб', 100, 50.0, 0, now(), now()),
       ('чай', 100, 200.0, 20, now(), now())
ON CONFLICT (name) DO NOTHING;


SELECT setval('product_sequence', (SELECT max(id) FROM products));
