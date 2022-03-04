CREATE TABLE product (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    CONSTRAINT id UNIQUE (id)
);

INSERT INTO product (id, name, price, quantity_in_stock) VALUES (1, 'Product A', 10.99, 10);