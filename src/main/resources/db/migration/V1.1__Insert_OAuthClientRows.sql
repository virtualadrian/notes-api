INSERT INTO oauth_client_details (client_id,
                                  resource_ids,
                                  client_secret,
                                  scope,
                                  authorized_grant_types,
                                  web_server_redirect_uri,
                                  authorities,
                                  access_token_validity,
                                  refresh_token_validity,
                                  additional_information,
                                  autoapprove)
VALUES ('webApplicationUI',
        null,
        '$2a$10$Lo308b7.frlQfV0cQfRc0uin3SK.x7SeYd3o1blx8Wg36n.nwb96W',
        'read,write',
        'password,authorization_code,refresh_token',
        null,
        null,
        36000,
        36000,
        null,
        true);

DELETE FROM app_user_role;
DELETE FROM app_role;
DELETE FROM app_user;
DELETE FROM account;
INSERT INTO app_role (id, role_name, description) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');
INSERT INTO app_user(id, first_name, last_name, password, username, email_address, enabled, locked, account_expiration_date, password_expiration_date) values (1, 'adrian1FN', 'adrian1LN','$2a$10$d14HldAb3UZhH5NVI8fple9k99uzJ1rFfsqUd1ozkZnwlNa2R7xAm', 'adrian9', 'adrian9@test.com', 1,	0,	'2117-04-08 21:37:22',	'2117-04-08 21:37:22');
INSERT INTO app_user(id, first_name, last_name, password, username, email_address, enabled, locked, account_expiration_date, password_expiration_date) values (2, 'adrian1FN', 'adrian1LN','$2a$10$d14HldAb3UZhH5NVI8fple9k99uzJ1rFfsqUd1ozkZnwlNa2R7xAm', 'adrian3', 'adrian3@test.com', 1,	0,	'2117-04-08 21:37:22',	'2117-04-08 21:37:22');
INSERT INTO app_user(id, first_name, last_name, password, username, email_address, enabled, locked, account_expiration_date, password_expiration_date) values (3, 'TesterTurkey', 'FriedChickenNGravy', '$2a$10$z9VeSQBID6iyuje3bsRIQ.vJRu/ReXv/j0BGgCep46OTuahzoeCzW', 'testerturkey', 'testerturkey@test.com', 1,	0,	'2117-04-08 21:37:22',	'2117-04-08 21:37:22');
INSERT INTO app_user_role(user_id, role_id) VALUES(1,1);
INSERT INTO app_user_role(user_id, role_id) VALUES(3,1);
INSERT INTO app_user_role(user_id, role_id) VALUES(2,2);
INSERT INTO account(id, username, first_name, last_name, email) VALUES (0, 'adrian9', 'adrian1FN', 'adrian1LN', 'adrian9@test.com');
INSERT INTO account(id, username, first_name, last_name, email) VALUES (1, 'adrian3', 'adrian1FN', 'adrian1LN', 'adrian3@test.com');
INSERT INTO account(id, username, first_name, last_name, email) VALUES (2, 'testerturkey', 'TesterTurkey', 'FriedChickenNGravy', 'testerturkey@test.com');
