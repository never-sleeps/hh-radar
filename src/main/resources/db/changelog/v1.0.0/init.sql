create table CLIENT_ACCESS_TOKENS (
  id BIGSERIAL,
  access_token VARCHAR(255),
  token_type VARCHAR(50),
  expires_in NUMERIC NOT NULL,
  refresh_token VARCHAR(255),

  primary key (id)
);

CREATE TABLE USERS (
  id BIGSERIAL,
  username VARCHAR(255) NOT NULL,
  authorization_code VARCHAR(255),
  token_id bigint references CLIENT_ACCESS_TOKENS (id),

  PRIMARY KEY (id)
);