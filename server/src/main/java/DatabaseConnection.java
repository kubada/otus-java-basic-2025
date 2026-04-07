import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;


/**
 * Утилитарный класс для управления подключением к PostgreSQL.
 * Connection в PostgreSQL JDBC является потокобезопасным для создания Statement/PreparedStatement.
 * Однако сами Statement/PreparedStatement не должны использоваться одновременно из разных потоков.
 * В данной реализации каждый запрос создаёт свой PreparedStatement, что обеспечивает потокобезопасность.
 */
public class DatabaseConnection {
    private static Connection connection;

    private static final System.Logger logger = System.getLogger(DatabaseConnection.class.getName());

    private static final Dotenv dotenv = Dotenv.load();
    private static final String databaseHost = dotenv.get("DB_HOST");
    private static final String databasePort = dotenv.get("DB_PORT");
    private static final String databaseName = dotenv.get("DB_NAME");
    private static final String databaseUser = dotenv.get("DB_USER");
    private static final String databasePassword = dotenv.get("DB_PASSWORD");

    private static final String databaseUrl = String.format("jdbc:postgresql://%s:%s/%s", databaseHost, databasePort, databaseName);

    static void main() {
        try {
            connect();
        } catch (RuntimeException e) {
            logger.log(System.Logger.Level.ERROR, "Не удалось подключиться к БД", e);
        } finally {
            disconnect();
        }
    }

    /**
     * Устанавливает соединение с базой данных.
     * Должен быть вызван перед использованием getConnection().
     */
    public static void connect() {
        if (connection != null) {
            logger.log(System.Logger.Level.WARNING, "Соединение уже установлено");
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            logger.log(System.Logger.Level.INFO, "Подключение к БД успешно установлено");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Не удалось подключиться к БД", e);
        }
    }

    private static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(System.Logger.Level.WARNING, "Ошибка disconnect", e);
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    /**
     * Инициализирует схему базы данных из файла schema.sql.
     * Создаёт таблицы roles и users, если они не существуют.
     * Безопасно вызывать многократно (использует IF NOT EXISTS).
     */
    public static void initializeSchema() {
        if (connection == null) {
            throw new IllegalStateException("Необходимо сначала вызвать connect()");
        }

        logger.log(System.Logger.Level.INFO, "Инициализация схемы БД...");

        try (InputStream is = DatabaseConnection.class.getResourceAsStream("/schema.sql")) {
            if (is == null) {
                throw new RuntimeException("Файл schema.sql не найден в resources");
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String[] lines = sql.split("\n");
            StringBuilder cleanSql = new StringBuilder();
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.startsWith("--") && !trimmed.isEmpty()) {
                    cleanSql.append(line).append("\n");
                }
            }

            String[] statements = cleanSql.toString().split(";\\s*\n");

            try (Statement statement = connection.createStatement()) {
                for (String stmtText : statements) {
                    String trimmed = stmtText.trim();
                    if (!trimmed.isEmpty()) {
                        statement.execute(trimmed);
                    }
                }
            }

            logger.log(System.Logger.Level.INFO, "Схема БД успешно инициализирована");

        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка чтения schema.sql", e);
            throw new RuntimeException("Не удалось прочитать schema.sql", e);
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка выполнения schema.sql", e);
            throw new RuntimeException("Не удалось инициализировать схему БД", e);
        }
    }
}

