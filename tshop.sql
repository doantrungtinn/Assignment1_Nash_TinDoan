
CREATE TABLE product_category (
	id INT PRIMARY KEY,
	name VARCHAR(25)  NOT NULL 
);

CREATE TABLE product_inventory (
	id INT PRIMARY KEY,
	quantity INT NOT NULL
);

CREATE TABLE discount (
	id INT PRIMARY KEY,
	name VARCHAR(25) NOT NULL,
	discount_percent DECIMAL 
);

CREATE TABLE product (
	id INT PRIMARY KEY,
	name VARCHAR,
	category_id INT,
	inventory_id INT,
	price DECIMAL,
	discount_id INT
);

CREATE TABLE order_details(
	id INT PRIMARY KEY,
	user_id INT,
	total DECIMAL,
	payment_id INT 
);

CREATE TABLE order_items(
	id INT PRIMARY KEY,
	order_id INT,
	product_id INT,
	quantity INT
); 


CREATE TABLE users (
	id INT PRIMARY KEY,
	username VARCHAR(25),
	password TEXT,
	Role	TEXT,
	first_name VARCHAR(25),
	last_name VARCHAR(25),
	phone INT
);

CREATE TABLE shopping_session( 
	id INT PRIMARY KEY,
	user_id VARCHAR,
	total DECIMAL
);

CREATE TABLE cart_items(
	id INT PRIMARY KEY,
	session_id INT,
	product_id INT, 
	quantity INT,
	user_id INT
);

CREATE TABLE payment_details (
	id INT PRIMARY KEY,
	order_id INT, 
	amount INT,
	provider VARCHAR(25),
	status VARCHAR(25)
);

CREATE TABLE user_address(
	id INT PRIMARY KEY,
	user_id INT,
	address VARCHAR(100),
	city VARCHAR(25),
	phone VARCHAR(15)
);


CREATE TABLE user_payment(
	id INT PRIMARY KEY,
	user_id INT,
	payment_type VARCHAR(25),
	provider VARCHAR, 
	account_no INT 
);

CREATE TABLE rating(
	id INT PRIMARY KEY,
	reviews_id INT, 
	user_id INT, 
	rating INT
);








