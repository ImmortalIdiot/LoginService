CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    subscription_type VARCHAR(50) NOT NULL
);

INSERT INTO users (user_id, name, ip_address, country, subscription_type)
VALUES
    (2, 'Random User2', '192.168.2.2', 'Germany', 'BASIC'),
    (4, 'Jane Doe', '192.168.4.4', 'France', 'PREMIUM'),
    (6, 'Robert Smith', '192.168.6.6', 'Spain', 'BASIC')
ON CONFLICT (user_id) DO NOTHING;