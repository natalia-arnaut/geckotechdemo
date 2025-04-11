create table recipe
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description text NOT NULL,
    cook_time TIME DEFAULT NULL,
    cook_time_custom VARCHAR(40) DEFAULT NULL,
    type ENUM('breakfast', 'lunch', 'dinner'),
    PRIMARY KEY (id),
    KEY idx_name (name),
    KEY idx_cook_time (cook_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table ingredient
(
    recipe_id int NOT NULL,
    name VARCHAR(255) NOT NULL,
    amount VARCHAR(50),
    CONSTRAINT `ingredient_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE,
    KEY idx_ingredient_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
