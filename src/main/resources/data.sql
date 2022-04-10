INSERT INTO RESTAURANT (address, description)
VALUES ('Невский проспект', 'Клод-Моне'),
       ('Сенная', 'Зима'),
       ('Дыбенко', 'Чернигов');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.com', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO MEALS (calories, description, price)
VALUES (500, 'Манная каша', 100),
       (700, 'Солянка', 500),
       (1000, 'Пюре с котлетой', 700),
       (2000, 'Стейк', 1500),
       (1200, 'Борщ', 1000),
       (700, 'Селедка под шубой', 500),
       (700, 'Борщ', 500),
       (1200, 'Борщ', 600),
       (800, 'Борщ', 700),
       (900, 'Борщ', 800),
       (950, 'Борщ', 999),
       (1100, 'Борщ', 1200);