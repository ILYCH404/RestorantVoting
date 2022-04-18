INSERT INTO RESTAURANT (address, description)
VALUES ('Невский проспект', 'Клод-Моне'),
       ('Сенная', 'Зима'),
       ('Дыбенко', 'Чернигов');

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO MEALS (description, price)
VALUES ('Манная каша', 100),
       ('Солянка', 500),
       ('Пюре с котлетой', 700),
       ('Стейк', 1500),
       ('Борщ', 1000),
       ('Селедка под шубой', 500),
       ('Картошка', 500),
       ('Блины', 600);