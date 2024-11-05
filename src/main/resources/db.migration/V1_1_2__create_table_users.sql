CREATE TABLE if not exists users
(
    id       INTEGER NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    email    VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);