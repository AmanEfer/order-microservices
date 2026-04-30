INSERT INTO roles(name)
VALUES ('USER'),
       ('ADMIN')
ON CONFLICT(name) DO NOTHING;

INSERT INTO users(username, password, email)
VALUES ('user1', '$2a$10$HYem0A5FzIFsSIT87eCbW.lyWlRnFRsjcTQXxK/LvjdpP8qFCrrGe', 'user1@gmail.com'),
       ('admin1', '$2a$10$HYem0A5FzIFsSIT87eCbW.lyWlRnFRsjcTQXxK/LvjdpP8qFCrrGe', 'admin1@gmail.com')
ON CONFLICT(username) DO NOTHING;
-- пароль для пользователей в БД: 123

INSERT INTO user_roles(user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'user1@gmail.com'),
        (select id from roles where name = 'USER')),
       ((SELECT id FROM users WHERE email = 'admin1@gmail.com'),
        (SELECT id FROM roles WHERE name = 'ADMIN'))
ON CONFLICT DO NOTHING;

select setval(pg_get_serial_sequence('roles', 'id'), coalesce(max(id), 1))
from roles;
select setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id), 1))
from users;
