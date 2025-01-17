CREATE TABLE item
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    image_url   VARCHAR(2048),
    description VARCHAR(2048),
    writer      VARCHAR(255) NOT NULL
);