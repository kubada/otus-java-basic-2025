-- Схема базы данных для чата

-- ----------------------------------------------------------------------------
-- Таблица: roles
-- Хранит доступные роли в чате (OWNER, ADMIN, USER)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS roles
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(20) NOT NULL UNIQUE, -- OWNER, ADMIN, USER
    description TEXT,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Комментарии
COMMENT ON TABLE roles IS 'Роли пользователей в чате';
COMMENT ON COLUMN roles.name IS 'Название роли (OWNER, ADMIN, USER)';
COMMENT ON COLUMN roles.description IS 'Описание роли';

-- Роли
INSERT INTO roles (id, name, description)
VALUES (1, 'OWNER', 'Владелец чата (полные права)'),
       (2, 'ADMIN', 'Администратор (управление пользователями)'),
       (3, 'USER', 'Обычный пользователь')
ON CONFLICT (id) DO NOTHING;

-- Установить правильную последовательность для roles
SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));

-- ----------------------------------------------------------------------------
-- Таблица: users
-- Хранит зарегистрированных пользователей с аутентификацией
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users
(
    id            BIGSERIAL PRIMARY KEY,
    nickname      VARCHAR(50)  NOT NULL UNIQUE,    -- для логина, уникальный
    username      VARCHAR(100) NOT NULL,           -- отображаемое имя в чате
    password_hash TEXT         NOT NULL,           -- BCrypt хэш пароля
    role_id       BIGINT       NOT NULL DEFAULT 3, -- 3 = USER по умолчанию
    created_at    TIMESTAMPTZ           DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_nickname_format CHECK (
        nickname ~ '^[a-zA-Z0-9_-]+$' AND          -- только буквы, цифры, _, -
        LENGTH(nickname) >= 3 AND
        LENGTH(nickname) <= 50
        ),
    CONSTRAINT chk_username_not_empty CHECK (TRIM(username) <> ''),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_users_nickname ON users (nickname);
CREATE INDEX IF NOT EXISTS idx_users_role ON users (role_id);

-- Комментарии
COMMENT ON TABLE users IS 'Пользователи чата с аутентификацией';
COMMENT ON COLUMN users.nickname IS 'Уникальный ник для входа (логин), не меняется';
COMMENT ON COLUMN users.username IS 'Отображаемое имя в чате, может меняться';
COMMENT ON COLUMN users.password_hash IS 'BCrypt хэш пароля (NOT NULL для безопасности)';
COMMENT ON COLUMN users.role_id IS 'Внешний ключ на таблицу roles';

-- ----------------------------------------------------------------------------
-- Начальный пользователь: Administrator
-- ----------------------------------------------------------------------------
-- Логин/Пароль:
-- admin/admin
INSERT INTO users (nickname, username, password_hash, role_id)
VALUES ('admin', 'Administrator', '$2a$12$OV8MB4374t8n/B9cWQpnLOFf9AgOJzuYM.GtJVPPK5FSo3f83uI6S', 1)
ON CONFLICT (nickname) DO NOTHING;

-- Конец схемы
