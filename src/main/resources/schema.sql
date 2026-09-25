INSERT INTO member (name, email, password, role)
VALUES (
           '어드민',
           'admin@email.com',
           '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
           'ADMIN'
       ),
       (
           '브라운',
           'brown@email.com',
           '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
           'USER'
       );

INSERT INTO theme (name, description)
VALUES ('테마1', '테마1입니다.'),
       ('테마2', '테마2입니다.'),
       ('테마3', '테마3입니다.');

INSERT INTO time (time_value)
VALUES ('10:00'),
       ('12:00'),
       ('14:00'),
       ('16:00'),
       ('18:00'),
       ('20:00');

INSERT INTO reservation (name, date, time_id, theme_id)
VALUES ('어드민', '2024-03-01', 1, 1),
       ('어드민', '2024-03-01', 2, 2),
       ('어드민', '2024-03-01', 3, 3);
