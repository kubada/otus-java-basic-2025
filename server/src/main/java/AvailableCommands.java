/**
 * Перечисление доступных команд чата.
 */
public enum AvailableCommands {
    AUTH("/auth", "/auth <nickname> <password>", "аутентификация (только при входе)"),
    REG("/reg", "/reg <nickname> <password> <username>", "регистрация (только при входе)"),
    HELP("/help", "/help", "отображает доступные команды"),
    INFO("/info", "/info", "отображает информацию о пользователе"),
    NAME("/name", "/name <новое_имя_пользователя>", "смена имени пользователя"),
    WHISPER("/w", "/w <имя_пользователя> <сообщение>", "отправка ЛС"),
    ALL("/all", "/all <сообщение>", "отправка от имени сервера (доступно для ролей OWNER, ADMIN)"),
    KICK("/kick", "/kick <имя_пользователя>", "отключить пользователя (доступно для ролей OWNER, ADMIN)"),
    OP("/op", "/op <имя_пользователя>", "выдать права администратора (доступно для ролей OWNER, ADMIN)"),
    DEOP("/deop", "/deop <имя_пользователя>", "забрать права администратора (доступно для ролей OWNER)"),
    EXIT("/exit", "/exit", "выход из чата");

    private final String command;
    private final String usage;
    private final String description;

    /**
     * Создаёт команду с указанным использованием и описанием.
     *
     * @param command     команда
     * @param usage       синтаксис команды
     * @param description описание команды
     */
    AvailableCommands(String command, String usage, String description) {
        this.command = command;
        this.usage = usage;
        this.description = description;
    }

    /**
     * Возвращает синтаксис команды.
     *
     * @return синтаксис команды
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Формирует справочное сообщение со списком всех доступных команд.
     *
     * @return текст справки по командам
     */
    public static String getHelpMessage() {
        StringBuilder sb = new StringBuilder("Доступные команды:");
        for (AvailableCommands cmd : values()) {
            if (!cmd.usage.isEmpty()) {
                sb.append("\n  ").append(cmd.usage).append(" - ").append(cmd.description);
            }
        }
        return sb.toString();
    }

    /**
     * Ищет команду по сообщению пользователя.
     * Для команды EXIT проверяет точное совпадение, для остальных команд проверяет начало строки.
     *
     * @param message сообщение пользователя
     * @return найденная команда или null, если команда не найдена
     */
    public static AvailableCommands findCommand(String message) {
        for (AvailableCommands cmd : values()) {
            if (cmd == EXIT && message.equals(cmd.command)) {
                return cmd;
            } else if (message.startsWith(cmd.command)) {
                return cmd;
            }
        }
        return null;
    }

}
