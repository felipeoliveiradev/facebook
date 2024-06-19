CREATE TABLE token (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    access_token VARCHAR(255) NOT NULL,
    expires_in VARCHAR(255) NOT NULL,
    refresh_expires_in VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    token_type VARCHAR(255) NOT NULL,
    not_before_policy VARCHAR(255) NOT NULL,
    scope VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
    )