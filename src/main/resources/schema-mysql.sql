CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL,
    account_expired INT,
    account_locked INT,
    credentials_expired INT,
    enabled INT,
    PRIMARY KEY (id)
);

INSERT INTO users(id, user_name, password, role,account_expired, account_locked, credentials_expired, enabled)
VALUES (1, 'admin@gmail.com', '{bcrypt}$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu','ADMIN', 0, 0, 0, 1);


INSERT INTO users(id, user_name, password, role,account_expired, account_locked, credentials_expired, enabled)
VALUES (2, 'user@gmail.com','{bcrypt}$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu','USER', 0, 0, 0, 1);

INSERT INTO users(id, user_name, password, role,account_expired, account_locked, credentials_expired, enabled)
VALUES (3, 'user2@gmail.com','{bcrypt}$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu','USER', 0, 0, 0, 1);


CREATE TABLE IF NOT EXISTS user_account (
     id BIGINT NOT NULL AUTO_INCREMENT,
     user_id INT,
     amount FLOAT,
     account_number VARCHAR(50) NOT NULL,
     intrash VARCHAR(5) NOT NULL,
     action VARCHAR(15) NOT NULL,
     creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (id),
     FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO user_account(id, user_id, amount, account_number,intrash, action)
VALUES (1,2,2000.0,'123456789','NO','Creation');

INSERT INTO user_account(id, user_id, amount, account_number,intrash, action)
VALUES (2,3,1000.0,'987654321','NO','Creation');

CREATE TABLE IF NOT EXISTS account_transaction (
    id BIGINT NOT NULL AUTO_INCREMENT,
    transaction_type VARCHAR(30) NOT NULL,
    account_number VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    transfer_account_number VARCHAR(50),
    amount FLOAT,
    user_id INT,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT
);
