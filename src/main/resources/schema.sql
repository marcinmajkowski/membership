CREATE SEQUENCE IF NOT EXISTS check_in_id_seq;
CREATE TABLE IF NOT EXISTS check_in (
  id               BIGINT       NOT NULL DEFAULT nextval('check_in_id_seq'),
  card_code        VARCHAR(128) NOT NUll,
  creation_instant TIMESTAMP    NOT NUll,
  PRIMARY KEY (id)
);
ALTER SEQUENCE check_in_id_seq
OWNED BY check_in.id;
