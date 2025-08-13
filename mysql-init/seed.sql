CREATE TABLE `books` (
                         `isbn` bigint NOT NULL,
                         `author` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `created_at` datetime(6) NOT NULL,
                         `image` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `pages` int NOT NULL,
                         `price` decimal(10,2) NOT NULL,
                         `publish_date` date NOT NULL,
                         `publisher` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `subtitle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         PRIMARY KEY (`isbn`),
                         KEY `idx_book_title` (`title`),
                         KEY `idx_book_author` (`author`),
                         KEY `idx_book_publisher` (`publisher`),
                         KEY `idx_book_publish_date` (`publish_date`),
                         KEY `idx_book_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET GLOBAL local_infile = 1;

LOAD DATA INFILE '/docker-entrypoint-initdb.d/seeds.csv'
INTO TABLE books
FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(isbn,author,created_at,image,pages,price,publish_date,publisher,subtitle,title,updated_at);