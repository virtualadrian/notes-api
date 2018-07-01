INSERT INTO app_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');
INSERT INTO app_user(id, first_name, last_name, password, username, email_address, enabled, locked, account_expiration_date, password_expiration_date) values (1, 'adrian1FN', 'adrian1LN','$2a$10$d14HldAb3UZhH5NVI8fple9k99uzJ1rFfsqUd1ozkZnwlNa2R7xAm', 'adrian9', 'adrian9@test.com', 1,	0,	'2117-04-08 21:37:22',	'2117-04-08 21:37:22');
INSERT INTO app_user(id, first_name, last_name, password, username, email_address, enabled, locked, account_expiration_date, password_expiration_date) values (2, 'adrian1FN', 'adrian1LN', '$2a$10$d14HldAb3UZhH5NVI8fple9k99uzJ1rFfsqUd1ozkZnwlNa2R7xAm', 'adrian3', 'adrian3@test.com', 1,	0,	'2117-04-08 21:37:22',	'2117-04-08 21:37:22');
INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
INSERT INTO account(id, username, first_name, last_name, email) VALUES (0, 'adrian9', 'adrian1FN', 'adrian1LN', 'adrian9@test.com');
INSERT INTO account(id, username, first_name, last_name, email) VALUES (1, 'adrian3', 'adrian1FN', 'adrian1LN', 'adrian3@test.com');
