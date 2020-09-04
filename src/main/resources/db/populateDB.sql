DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM vote;
DELETE FROM restaurant;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user1@yandex.ru', 'password'),
       ('User2', 'user2@yandex.ru', 'password'),
       ('User3', 'user3@yandex.ru', 'password'),
       ('User4', 'user4@yandex.ru', 'password'),
       ('User5', 'user5@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('ADMIN', 100005);

INSERT INTO restaurant (name)
VALUES ('Жиденький'), --100006
       ('Мажор сити'), --100007
       ('Реальный пацан'); --100008

INSERT INTO meals (name, rest_id, price)
VALUES ('Каша1',            100006, 200),
       ('Котлетка с пюре2', 100006, 400),
       ('Борщ3',            100006, 600),
       ('Хлопья1',          100007, 1100),
       ('Бургер2',          100007, 1900),
       ('Wok3',             100007, 1700),
       ('Ряженка4',         100007, 2000),
       ('Мюсли1',           100008, 100),
       ('Солнка2',          100008, 190),
       ('Плов3',            100008, 299)
