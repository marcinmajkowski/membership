CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS customer_id_seq;
CREATE TABLE IF NOT EXISTS customer (
  id         BIGINT       NOT NULL DEFAULT nextval('customer_id_seq'),
  first_name VARCHAR(128) NOT NUll,
  last_name  VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);
ALTER SEQUENCE customer_id_seq
OWNED BY customer.id;

CREATE SEQUENCE IF NOT EXISTS card_id_seq;
CREATE TABLE IF NOT EXISTS card (
  id          BIGINT       NOT NULL DEFAULT nextval('card_id_seq'),
  code        VARCHAR(128) NOT NULL UNIQUE CHECK (code <> ''),
  customer_id BIGINT       NOT NULL REFERENCES customer (id) ON DELETE CASCADE,
  PRIMARY KEY (id)
);
ALTER SEQUENCE card_id_seq
OWNED BY card.id;

CREATE SEQUENCE IF NOT EXISTS check_in_id_seq;
CREATE TABLE IF NOT EXISTS check_in (
  id          BIGINT    NOT NULL DEFAULT nextval('check_in_id_seq'),
  customer_id BIGINT REFERENCES customer (id) ON DELETE SET NULL,
  timestamp   TIMESTAMP NOT NUll,
  PRIMARY KEY (id)
);
ALTER SEQUENCE check_in_id_seq
OWNED BY check_in.id;

CREATE TABLE IF NOT EXISTS payment (
  id          BIGINT         NOT NULL,
  customer_id BIGINT REFERENCES customer (id) ON DELETE SET NULL,
  amount      NUMERIC(12, 2) NOT NULL,
  timestamp   TIMESTAMP      NOT NUll,
  PRIMARY KEY (id)
);
