INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.com', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTORAUNT (address, description)
VALUES ('Невский проспект', 'Клод-Моне'),
       ('Сенная', 'Зима'),
       ('Дыбенко', 'Чернигов');

INSERT INTO MEALS (calories, description, price, RESTAURANT_ID)
VALUES (500, 'Манная каша', 100, 1),
       (700, 'Солянка', 500, 2),
       (1000, 'Пюре с котлетой', 700, 3)