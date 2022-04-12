CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `admin` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
	`product_id` int(11) NOT NULL AUTO_INCREMENT,
    `picture_link` varchar(50) DEFAULT NULL,
    `name` varchar (50) NOT NULL,
    `description` varchar(200) DEFAULT NULL,
    `brand` varchar (50) NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` double(5,2) NOT NULL,
    `active` boolean NOT NULL DEFAULT false,
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sales` (
	`transaction_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `product_id` int(11) NOT NULL,
    `quantity_bought` int(11) NOT NULL,
    `transaction_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`transaction_id`),
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES products(`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cart` (
	`cart_id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` int(11) NOT NULL,
	`session_id` int(11) NOT NULL,
	`status` smallint(6) NOT NULL DEFAULT 0,
	PRIMARY KEY (`cart_id`),
	FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cart_item` (
	`cart_item_id` int(11) NOT NULL AUTO_INCREMENT,
	`cart_id` int(11) NOT NULL,
	`price` DOUBLE(5,2) NOT NULL DEFAULT 0,
	`quantity` int(11) NOT NULL DEFAULT 0,
	`active` BOOLEAN NOT NULL,
	PRIMARY KEY (`cart_item_id`),
	FOREIGN KEY (`cart_id`) REFERENCES cart(`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;