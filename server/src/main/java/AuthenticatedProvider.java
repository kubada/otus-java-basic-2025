/**
 * Интерфейс провайдера аутентификации пользователей.
 */
public interface AuthenticatedProvider {
    /**
     * Инициализирует провайдер аутентификации.
     */
    void initialize();

    /**
     * Аутентифицирует пользователя по логину и паролю.
     *
     * @param clientHandler обработчик клиента
     * @param login         логин пользователя
     * @param password      пароль пользователя
     * @return true если аутентификация успешна, false иначе
     */
    boolean authenticate(ClientHandler clientHandler, String login, String password);

    /**
     * Регистрирует нового пользователя.
     *
     * @param clientHandler обработчик клиента
     * @param login         логин нового пользователя
     * @param password      пароль нового пользователя
     * @param username      отображаемое имя пользователя в чате
     * @return true если регистрация успешна, false иначе
     */
    boolean register(ClientHandler clientHandler, String login, String password, String username);
}
