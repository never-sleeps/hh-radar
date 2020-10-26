create table CLIENT_ACCESS_TOKENS (
  id BIGSERIAL,
  access_token VARCHAR(255),
  token_type VARCHAR(50),
  expires_in NUMERIC NOT NULL,
  refresh_token VARCHAR(255),

  primary key (id)
);

create table SEARCH_PARAMETERS (
   id BIGSERIAL,
   area VARCHAR(20),
   specialization VARCHAR(20),
   text VARCHAR(255),
   experience VARCHAR(20),
   employment VARCHAR(20),
   schedule VARCHAR(20),
   order_by VARCHAR(20),

   PRIMARY KEY (id)
);

CREATE TABLE USERS (
  id BIGSERIAL,
  username VARCHAR(255) NOT NULL,
  authorization_code VARCHAR(255),
  token_id bigint references CLIENT_ACCESS_TOKENS (id),
  search_id bigint references SEARCH_PARAMETERS (id),

  PRIMARY KEY (id)
);