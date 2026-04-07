/**
 * Перечисление ролей пользователей чата.
 */
public enum AvailableRoles {
    OWNER("~", "владелец"),
    ADMIN("@", "администратор"),
    USER("", "пользователь");

    private final String prefix;
    private final String description;

    /**
     * Создаёт роль с указанным префиксом и описанием.
     *
     * @param prefix      префикс роли для отображения
     * @param description описание роли
     */
    AvailableRoles(String prefix, String description) {
        this.prefix = prefix;
        this.description = description;
    }

    /**
     * Возвращает префикс роли.
     *
     * @return префикс роли
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Возвращает описание роли.
     *
     * @return описание роли
     */
    public String getDescription() {
        return description;
    }
}
