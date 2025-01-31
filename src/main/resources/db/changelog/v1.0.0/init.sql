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
   page NUMERIC,
   per_page NUMERIC,
   paging_order VARCHAR(10),

   PRIMARY KEY (id)
);

CREATE TABLE USERS (
  id BIGSERIAL,
  created_time TIMESTAMP,
  user_id NUMERIC NOT NULL,
  username VARCHAR(255),
  authorization_code VARCHAR(255),
  token_id bigint references CLIENT_ACCESS_TOKENS (id),
  search_id bigint references SEARCH_PARAMETERS (id),

  PRIMARY KEY (id)
);

CREATE TABLE AUTO_PUBLISHING_RESUME (
    id BIGSERIAL,
    resume VARCHAR(255) NOT NULL,
    user_id bigint references USERS(id) on delete cascade,
    created_time TIMESTAMP,
    last_updated_time TIMESTAMP,
    publish_count NUMERIC DEFAULT 0,

    PRIMARY KEY (id)
);

