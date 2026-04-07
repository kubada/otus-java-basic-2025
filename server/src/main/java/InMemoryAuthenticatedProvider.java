import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Провайдер аутентификации с хранением данных в памяти.
 */
@Deprecated
public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {
    /**
     * Внутренний класс для представления пользователя.
     */
    private static class User {
        private final String nickname;
        private final String password;
        private final String username;

        /**
         * Создаёт пользователя с указанными данными.
         *
         * @param nickname    ник пользователя
         * @param password пароль пользователя
         * @param username отображаемое имя пользователя
         */
        public User(String nickname, String password, String username) {
            this.nickname = nickname;
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
            this.users.add(new User(demoUser.getNickname(), demoUser.getPassword(), demoUser.getUsername()));
        }
    }

    @Override
    public void initialize() {
        System.out.println("Сервер аутентификации запущен в режиме InMemory");
    }

    /**
     * Ищет username по нику и паролю.
     *
     * @param nickname    ник пользователя
     * @param password пароль пользователя
     * @return username если найден, null иначе
     */
    private String getUsernameByNicknameAndPassword(String nickname, String password) {
        for (User u : users) {
            if (u.nickname.equals(nickname) && u.password.equals(password)) {
                return u.username;
            }
        }
        return null;
    }

    /**
     * Проверяет существование ника в базе.
     *
     * @param nickname ник для проверки
     * @return true если ник существует, false иначе
     */
    private boolean isNicknameAlreadyExists(String nickname) {
        for (User u : users) {
            if (u.nickname.equals(nickname)) {
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
    public boolean authenticate(ClientHandler clientHandler, String nickname, String password) {
        String authUsername = getUsernameByNicknameAndPassword(nickname, password);
        if (authUsername == null) {
            clientHandler.sendMsg("Некорректный ник / пароль");
            return false;
        }
        if (server.isUsernameBusy(authUsername)) {
            clientHandler.sendMsg("Имя пользователя занято");
            return false;
        }
        clientHandler.setUsername(authUsername);

        if (server.getClientsCount() == 0) {
            clientHandler.setRole(AvailableRoles.OWNER);
        } else {
            clientHandler.setRole(AvailableRoles.USER);
        }

        server.subscribe(clientHandler);
        clientHandler.sendMsg("Вы подключились с именем: " + authUsername);
        clientHandler.sendMsg(server.getOnlineUsersInfo());
        clientHandler.sendMsg(AvailableCommands.getHelpMessage());
        clientHandler.sendMsg("/authok " + authUsername);
        return true;
    }

    @Override
    public boolean register(ClientHandler clientHandler, String nickname, String password, String username) {
        if (nickname.trim().length() < 3) {
            clientHandler.sendMsg("Ник должен состоять из 3+ символов");
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
        if (isNicknameAlreadyExists(nickname)) {
            clientHandler.sendMsg("Ник занят");
            return false;
        }
        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMsg("Имя пользователя занято");
            return false;
        }
        users.add(new User(nickname, password, username));
        clientHandler.setUsername(username);

        if (server.getClientsCount() == 0) {
            clientHandler.setRole(AvailableRoles.OWNER);
        } else {
            clientHandler.setRole(AvailableRoles.USER);
        }

        server.subscribe(clientHandler);
        clientHandler.sendMsg("Вы успешно зарегистрировались с именем: " + username);
        clientHandler.sendMsg(server.getOnlineUsersInfo());
        clientHandler.sendMsg(AvailableCommands.getHelpMessage());
        clientHandler.sendMsg("/regok " + username);
        return true;
    }
}
