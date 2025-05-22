CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    subscription_type VARCHAR(50) NOT NULL
);

INSERT INTO users (user_id, name, ip_address, country, subscription_type)
VALUES
    (1, 'John Doe', '192.168.1.1', 'USA', 'PREMIUM'),
    (3, 'Random User', '192.168.3.3', 'Canada', 'BASIC'),
    (5, 'Krasulya Maxim', '192.168.4.4', 'Russia', 'PREMIUM')
ON CONFLICT (user_id) DO NOTHING;