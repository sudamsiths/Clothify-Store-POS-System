-- ========================================
-- TABLE CREATION
-- ========================================

CREATE TABLE categories (
    category_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL ,
    description TEXT
);

CREATE TABLE suppliers (
    supplier_id VARCHAR(255) NOT NULL PRIMARY KEY,
    supplier_name VARCHAR(100),
    company_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    item VARCHAR(255)
);

CREATE TABLE customer (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    nic BIGINT UNIQUE,
    address VARCHAR(255),
    dob DATE,
    contactno BIGINT
);

CREATE TABLE products (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    supplier_id VARCHAR(50),
    name VARCHAR(255),
    category VARCHAR(100),
    size VARCHAR(50),
    price DECIMAL(10,2),
    image_url VARCHAR(500),
    qty INT DEFAULT 0,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE SET NULL
);

CREATE TABLE user (
    email VARCHAR(35) NOT NULL PRIMARY KEY,
    password VARCHAR(35)
);
