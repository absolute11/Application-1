INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');


INSERT INTO users (username, email, github_id) VALUES ('admin', 'admin@example.com', 'admin-github-id');
INSERT INTO users (username, email, github_id) VALUES ('user', 'user@example.com', 'user-github-id');


INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));