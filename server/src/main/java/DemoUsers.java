/**
 * Предустановленные демонстрационные пользователи для тестирования.
 */
public enum DemoUsers {
    ADMIN("admin", "admin", "Administrator"),
    BOB("bob", "123", "Bob"),
    TOM("tom", "456", "Tom");

    private final String login;
    private final String password;
    private final String username;

    /**
     * Создаёт демонстрационного пользователя.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @param username отображаемое имя пользователя
     */
    DemoUsers(String login, String password, String username) {
        this.login = login;
        this.password = password;
        this.username = username;
    }

    /**
     * Возвращает логин пользователя.
     *
     * @return логин
     */
    public String getLogin() {
        return login;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает отображаемое имя пользователя.
     *
     * @return отображаемое имя
     */
    public String getUsername() {
        return username;
    }
}
