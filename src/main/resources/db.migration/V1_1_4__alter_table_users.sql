CREATE SEQUENCE IF NOT EXISTS users_id_seq;
ALTER TABLE users
ALTER COLUMN id SET DEFAULT nextval('users_id_seq');
