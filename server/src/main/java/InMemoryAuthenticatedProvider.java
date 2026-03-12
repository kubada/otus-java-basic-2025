import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Провайдер аутентификации с хранением данных в памяти.
 */
public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {
    /**
     * Внутренний класс для представления пользователя.
     */
    private static class User {
        private final String login;
        private final String password;
        private final String username;

        /**
         * Создаёт пользователя с указанными данными.
         *
         * @param login    логин пользователя
         * @param password пароль пользователя
         * @param username отображаемое имя пользователя
         */
        public User(String login, String password, String username) {
            this.login = login;
            this.password = password;
            this.username = username;
        }
    }

    private final Server server;
    private final List<User> users;

    /**
     * Создаёт провайдер аутентификации с предустановленными пользователями.
     *
     * @param server экземпляр сервера
     */
    public InMemoryAuthenticatedProvider(Server server) {
        this.server = server;
        this.users = new CopyOnWriteArrayList<>();
        for (DemoUsers demoUser : DemoUsers.values()) {
            this.users.add(new User(demoUser.getLogin(), demoUser.getPassword(), demoUser.getUsername()));
        }
    }

    @Override
    public void initialize() {
        System.out.println("Сервер аутентификации запущен в режиме InMemory");
    }

    /**
     * Ищет username по логину и паролю.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return username если найден, null иначе
     */
    private String getUsernameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.username;
            }
        }
        return null;
    }

    /**
     * Проверяет существование логина в базе.
     *
     * @param login логин для проверки
     * @return true если логин существует, false иначе
     */
    private boolean isLoginAlreadyExists(String login) {
        for (User u : users) {
            if (u.login.equals(login)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет существование username в базе.
     *
     * @param username отображаемое имя для проверки
     * @return true если username существует, false иначе
     */
    private boolean isUsernameAlreadyExists(String username) {
        for (User u : users) {
            if (u.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {
        String authUsername = getUsernameByLoginAndPassword(login, password);
        if (authUsername == null) {
            clientHandler.sendMsg("Некорректный логин / пароль");
            return false;
        }
        if (server.isUsernameBusy(authUsername)) {
            clientHandler.sendMsg("Логин занят");
            return false;
        }
        clientHandler.setUsername(authUsername);

        if (server.getClientsCount() == 0) {
            clientHandler.setRole(AvailableRoles.OWNER);
        } else {
            clientHandler.setRole(AvailableRoles.USER);
        }

        server.subscribe(clientHandler);
        clientHandler.sendMsg("Вы подключились под ником: " + authUsername);
        clientHandler.sendMsg(server.getOnlineUsersInfo());
        clientHandler.sendMsg(AvailableCommands.getHelpMessage());
        clientHandler.sendMsg("/authok " + authUsername);
        return true;
    }

    @Override
    public boolean register(ClientHandler clientHandler, String login, String password, String username) {
        if (login.trim().length() < 3) {
            clientHandler.sendMsg("Логин должен состоять из 3+ символов");
            return false;
        }
        if (password.trim().length() < 3) {
            clientHandler.sendMsg("Пароль должен состоять из 3+ символов");
            return false;
        }
        if (username.trim().length() < 3) {
            clientHandler.sendMsg("Имя пользователя должно состоять из 3+ символов");
            return false;
        }
        if (isLoginAlreadyExists(login)) {
            clientHandler.sendMsg("Логин занят");
            return false;
        }
        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMsg("Имя пользователя занято");
            return false;
        }
        users.add(new User(login, password, username));
        clientHandler.setUsername(username);

        if (server.getClientsCount() == 0) {
            clientHandler.setRole(AvailableRoles.OWNER);
        } else {
            clientHandler.setRole(AvailableRoles.USER);
        }

        server.subscribe(clientHandler);
        clientHandler.sendMsg("Вы успешно зарегистрировались и подключились под ником: " + username);
        clientHandler.sendMsg(server.getOnlineUsersInfo());
        clientHandler.sendMsg(AvailableCommands.getHelpMessage());
        clientHandler.sendMsg("/regok " + username);
        return true;
    }
}
