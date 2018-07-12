CREATE SEQUENCE IF NOT EXISTS check_in_id_seq;
CREATE TABLE IF NOT EXISTS check_in (
  id               BIGINT       NOT NULL DEFAULT nextval('check_in_id_seq'),
  card_code        VARCHAR(128) NOT NUll,
  creation_instant TIMESTAMP    NOT NUll,
  PRIMARY KEY (id)
);
ALTER SEQUENCE check_in_id_seq
OWNED BY check_in.id;

CREATE SEQUENCE IF NOT EXISTS customer_id_seq;
CREATE TABLE IF NOT EXISTS customer (
  id         BIGINT       NOT NULL DEFAULT nextval('customer_id_seq'),
  first_name VARCHAR(128) NOT NUll,
  last_name  VARCHAR(128),
  card_code  VARCHAR(128),
  PRIMARY KEY (id)
);
ALTER SEQUENCE customer_id_seq
OWNED BY customer.id;

CREATE TABLE IF NOT EXISTS card (
  code        VARCHAR(128) PRIMARY KEY CHECK (code <> ''),
  customer_id BIGINT NOT NULL REFERENCES customer (id)
);
