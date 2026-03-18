import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.*;

/**
 * Провайдер аутентификации через PostgreSQL.
 */
public class PostgresqlAuthProvider implements AuthenticatedProvider {
    private final Server server;
    private Connection connection;

    private static final System.Logger logger = System.getLogger(PostgresqlAuthProvider.class.getName());

    public PostgresqlAuthProvider(Server server) {
        this.server = server;
    }

    @Override
    public void initialize() {
        DatabaseConnection.connect();
        DatabaseConnection.initializeSchema();
        connection = DatabaseConnection.getConnection();

        if (connection == null) {
            throw new RuntimeException("Не удалось получить соединение с БД");
        }

        logger.log(System.Logger.Level.INFO, "PostgresqlAuthProvider инициализирован");
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String nickname, String password) {
        User user = getUserByNickname(nickname);

        if (user == null) {
            clientHandler.sendMsg("! Неверный ник или пароль");
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash);
        if (!result.verified) {
            clientHandler.sendMsg("! Неверный ник или пароль");
            return false;
        }

        if (server.isUsernameBusy(user.username)) {
            clientHandler.sendMsg("! Имя пользователя " + user.username + " уже используется в чате");
            return false;
        }

        clientHandler.setNickname(user.nickname);
        clientHandler.setUsername(user.username);
        clientHandler.setRole(user.role);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/authok " + user.role.getPrefix() + user.username);
        logger.log(System.Logger.Level.INFO, "Пользователь " + nickname + " успешно аутентифицирован");
        return true;
    }

    @Override
    public boolean register(ClientHandler clientHandler, String nickname, String password, String username) {
        if (password.length() < 3) {
            clientHandler.sendMsg("! Пароль должен содержать минимум 3 символа");
            return false;
        }

        if (isNicknameExists(nickname)) {
            clientHandler.sendMsg("! Ник занят");
            return false;
        }

        if (isUsernameExists(username)) {
            clientHandler.sendMsg("! Имя пользователя занято");
            return false;
        }

        if (server.isUsernameBusy(username)) {
            clientHandler.sendMsg("! Имя пользователя " + username + " уже используется в чате");
            return false;
        }

        String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        String sql = "INSERT INTO users (nickname, username, password_hash, role_id) VALUES (?, ?, ?, 3)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nickname);
            ps.setString(2, username);
            ps.setString(3, passwordHash);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка регистрации пользователя", e);
            clientHandler.sendMsg("! Ошибка регистрации. Попробуйте позже");
            return false;
        }

        clientHandler.setNickname(nickname);
        clientHandler.setUsername(username);
        clientHandler.setRole(AvailableRoles.USER);
        server.subscribe(clientHandler);
        clientHandler.sendMsg("/regok " + AvailableRoles.USER.getPrefix() + username);
        logger.log(System.Logger.Level.INFO, "Зарегистрирован новый пользователь: " + nickname + "(" + username + ")");
        return true;
    }

    /**
     * Получает пользователя из БД по nickname.
     *
     * @param nickname ник для поиска
     * @return объект User или null если не найден
     */
    private User getUserByNickname(String nickname) {
        String sql = "SELECT u.id, u.nickname, u.username, u.password_hash, r.name as role_name " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE u.nickname = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nickname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String dbNickname = rs.getString("nickname");
                    String dbUsername = rs.getString("username");
                    String passwordHash = rs.getString("password_hash");
                    String roleName = rs.getString("role_name");

                    AvailableRoles role = AvailableRoles.valueOf(roleName);

                    return new User(id, dbNickname, dbUsername, passwordHash, role);
                }
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка получения пользователя из БД", e);
        }

        return null;
    }

    /**
     * Проверяет, существует ли nickname в БД.
     *
     * @param nickname ник для проверки
     * @return true если существует
     */
    private boolean isNicknameExists(String nickname) {
        String sql = "SELECT COUNT(*) FROM users WHERE nickname = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nickname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка проверки nickname", e);
        }
        return false;
    }

    /**
     * Проверяет, существует ли username в БД.
     *
     * @param username имя пользователя для проверки
     * @return true если существует
     */
    private boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка проверки username", e);
        }
        return false;
    }

    /**
     * Обновляет роль пользователя в БД.
     *
     * @param nickname ник пользователя (для поиска)
     * @param newRole  новая роль
     * @return true если обновление успешно
     */
    public boolean updateUserRole(String nickname, AvailableRoles newRole) {
        int roleId = switch (newRole) {
            case OWNER -> 1;
            case ADMIN -> 2;
            case USER -> 3;
        };

        String sql = "UPDATE users SET role_id = ? WHERE nickname = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setString(2, nickname);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                logger.log(System.Logger.Level.INFO, "Роль пользователя " + nickname + " изменена на " + newRole);
                return true;
            } else {
                logger.log(System.Logger.Level.WARNING, "Пользователь " + nickname + " не найден в БД при обновлении роли");
                return false;
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка обновления роли пользователя", e);
            return false;
        }
    }

    /**
     * Обновляет имя пользователя в БД.
     *
     * @param nickname ник пользователя (для поиска)
     * @param newUsername новое имя пользователя
     * @return true если обновление успешно
     */
    public boolean updateUsername(String nickname, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE nickname = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, nickname);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                logger.log(System.Logger.Level.INFO, "Имя пользователя (nickname: " + nickname + ") изменено на " + newUsername);
                return true;
            } else {
                logger.log(System.Logger.Level.WARNING, "Пользователь " + nickname + " не найден в БД при обновлении имени");
                return false;
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка обновления имени пользователя", e);
            return false;
        }
    }

    /**
     * Внутренний класс для хранения данных пользователя из БД.
     */
    private static class User {
        final long id;
        final String nickname;
        final String username;
        final String passwordHash;
        final AvailableRoles role;

        User(long id, String nickname, String username, String passwordHash, AvailableRoles role) {
            this.id = id;
            this.nickname = nickname;
            this.username = username;
            this.passwordHash = passwordHash;
            this.role = role;
        }
    }
}
