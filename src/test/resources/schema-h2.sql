CREATE TABLE authorities (
  id_authoritie integer NOT NULL,
  authority_description varchar(100) NOT NULL,
  authority_enabled integer NOT NULL,
  authority_name varchar(40) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  PRIMARY KEY (id_authoritie)
);

CREATE TABLE users (
  id_user integer NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  user_account_non_expired integer NOT NULL,
  user_account_non_locked integer NOT NULL,
  user_credentials_non_expired integer NOT NULL,
  user_email varchar(50) NOT NULL,
  user_enabled integer NOT NULL,
  user_last_password_reset_date timestamp NOT NULL,
  user_name varchar(20) NOT NULL,
  user_password varchar(100) NOT NULL,
  PRIMARY KEY (id_user)
);

CREATE TABLE users_authorities (
  authoritie_id integer NOT NULL,
  user_id integer NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  PRIMARY KEY (authoritie_id, user_id)
);

ALTER TABLE users_authorities ADD CONSTRAINT UK_p6riuboavq2dk38kq8at4w9te UNIQUE (authoritie_id);
ALTER TABLE users_authorities ADD CONSTRAINT UK_6uk70o9gngtb60n4qb5sr0xsg UNIQUE (user_id);
ALTER TABLE users_authorities ADD CONSTRAINT FKhpafpofndaokj07nj9lsd29sm FOREIGN KEY (authoritie_id) REFERENCES authorities;
ALTER TABLE users_authorities ADD CONSTRAINT FKq3lq694rr66e6kpo2h84ad92q FOREIGN KEY (user_id) REFERENCES users;
