CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE IF NOT EXISTS users
(
    id         VARCHAR(255) NOT NULL,
    email      CITEXT       NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS authorities
(
    authority  VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    PRIMARY KEY (authority)
);

CREATE TABLE IF NOT EXISTS user_authorities
(
    user_id VARCHAR(255) NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    authority VARCHAR(255) NOT NULL REFERENCES authorities (authority) ON DELETE CASCADE,
    PRIMARY KEY (user_id, authority)
);

CREATE TABLE IF NOT EXISTS keys
(
    id      varchar(255) PRIMARY KEY,
    salt    varchar(255),
    version int
);

CREATE TABLE IF NOT EXISTS config
(
    id           varchar(255) PRIMARY KEY,
    domain_url   varchar(255),
    key          varchar(255) REFERENCES keys (id),
    setup_status varchar(255)
);

