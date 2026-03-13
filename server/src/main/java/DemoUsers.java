/**
 * Предустановленные демонстрационные пользователи для тестирования.
 */
public enum DemoUsers {
    ADMIN("admin", "admin", "Administrator"),
    BOB("bob", "123", "Bob"),
    TOM("tom", "456", "Tom");

    private final String nickname;
    private final String password;
    private final String username;

    /**
     * Создаёт демонстрационного пользователя.
     *
     * @param nickname    ник пользователя
     * @param password пароль пользователя
     * @param username отображаемое имя пользователя
     */
    DemoUsers(String nickname, String password, String username) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    /**
     * Возвращает ник пользователя.
     *
     * @return ник
     */
    public String getNickname() {
        return nickname;
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
