INSERT INTO users(id_user, user_name, user_email, user_password, user_account_non_expired, user_credentials_non_expired, user_account_non_locked, user_last_password_reset_date, user_enabled, created_at, updated_at)
	VALUES (1, 'admin', 'admin@email.com', '$2a$10$foOg.PH1tvrO2iI9SZzOdejIrLzUOvhnWiMqoPwRAgVc5eNV8iVQG', 1, 1, 1, '2015-07-07', 1, '2016-06-01', '2016-06-01');

INSERT INTO authorities(
id_authoritie, authority_name, authority_description, authority_enabled, created_at, updated_at)
VALUES (1, 'ADMIN', 'ADMIN', 1, '2016-06-01', '2016-06-01');



INSERT INTO public.users_authorities(
user_id, authoritie_id, updated_at, created_at)
VALUES (1, 1, '2016-06-01', '2016-06-01');