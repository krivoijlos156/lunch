DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER                 NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR              NOT NULL
);

CREATE TABLE vote
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    registered  TIMESTAMP           DEFAULT now() NOT NULL,
    user_id     INTEGER             NOT NULL,
    rest_id     INTEGER             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (rest_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR              NOT NULL,
    rest_id     INTEGER              NOT NULL,
    price       INTEGER              NOT NULL,
    FOREIGN KEY (rest_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_name_rest_id_idx ON meals (name, rest_id);