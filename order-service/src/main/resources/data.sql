INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN')
ON CONFLICT(name) DO NOTHING;

INSERT INTO users(username, password, email)
VALUES ('user1', '123', 'user1@gmail.com'),
       ('admin1', '123', 'admin1@gmail.com')
ON CONFLICT(username) DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'user1@gmail.com'),
        (select id from roles where name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE email = 'admin1@gmail.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'))
ON CONFLICT DO NOTHING;

select setval(pg_get_serial_sequence('roles', 'id'), coalesce(max(id), 1))
from roles;
select setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id), 1))
from users;
