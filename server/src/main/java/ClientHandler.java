import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Обработчик клиентского подключения.
 */
public class ClientHandler {
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    private String username;
    private AvailableRoles role;

    private static final System.Logger logger = System.getLogger(ClientHandler.class.getName());

    /**
     * Создаёт обработчик для клиента.
     *
     * @param server сервер
     * @param socket сокет клиента
     * @throws IOException если ошибка создания потоков
     */
    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        logger.log(System.Logger.Level.INFO, "Клиент подключен, порт: " + socket.getPort());

        // Цикл аутентификации
        boolean isAuthenticate = false;
        try {
            while (!isAuthenticate) {
                sendMsg("Перед работой с чатом необходимо выполнить аутентификацию");
                sendMsg("/auth <login> <password> или зарегистрироваться");
                sendMsg("/reg <login> <password> <username>");

                String message = in.readUTF();
                if (message.startsWith("/")) {
                    if (message.equals("/exit")) {
                        sendMsg("/exitok");
                        disconnect();
                        return;
                    }

                    if (message.startsWith("/auth ")) {
                        String[] token = message.trim().split("\\s+");
                        if (token.length != 3) {
                            sendMsg("Неверный формат команды /auth");
                            continue;
                        }
                        if (server.getAuthenticatedProvider().authenticate(this, token[1], token[2])) {
                            isAuthenticate = true;
                        }
                        continue;
                    }

                    if (message.startsWith("/reg ")) {
                        String[] token = message.trim().split("\\s+");
                        if (token.length != 4) {
                            sendMsg("Неверный формат команды /reg");
                            continue;
                        }
                        if (server.getAuthenticatedProvider().register(this, token[1], token[2], token[3])) {
                            isAuthenticate = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.log(System.Logger.Level.WARNING, "Ошибка в цикле аутентификации", e);
            disconnect();
            return;
        }

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        AvailableCommands cmd = AvailableCommands.findCommand(message);

                        if (cmd == null) {
                            sendMsg("! Неизвестная команда. " + AvailableCommands.getHelpMessage());
                        } else if (cmd == AvailableCommands.HELP) {
                            sendMsg(AvailableCommands.getHelpMessage());
                        } else if (cmd == AvailableCommands.INFO) {
                            sendMsg("> Вы: " + username + ", ваша роль: " + role);
                        } else if (cmd == AvailableCommands.EXIT) {
                            sendMsg("/exitok");
                        } else if (cmd == AvailableCommands.NICK) {
                            handleNick(message);
                        } else if (cmd == AvailableCommands.WHISPER) {
                            handleWhisper(message);
                        } else if (cmd == AvailableCommands.ALL) {
                            handleBroadcast(message);
                        } else if (cmd == AvailableCommands.OP) {
                            handleOp(message);
                        } else if (cmd == AvailableCommands.DEOP) {
                            handleDeop(message);
                        } else if (cmd == AvailableCommands.KICK) {
                            handleKick(message);
                        }
                    } else {
                        server.broadcastMessage(role.getPrefix() + username + ": " + message);
                    }
                }
            } catch (IOException e) {
                if (e instanceof java.io.EOFException) {
                    logger.log(System.Logger.Level.INFO, "Клиент отключился");
                } else {
                    logger.log(System.Logger.Level.ERROR, "Ошибка чтения сообщения от клиента", e);
                }
            } finally {
                disconnect();
            }

        }).start();
    }

    /**
     * Отправляет сообщение клиенту.
     *
     * @param message сообщение
     */
    public void sendMsg(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка отправки сообщения", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает роль пользователя.
     *
     * @return роль пользователя
     */
    public AvailableRoles getRole() {
        return role;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param username новое имя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Устанавливает роль пользователя.
     *
     * @param role новая роль
     */
    public void setRole(AvailableRoles role) {
        this.role = role;
    }

    /**
     * Повышает пользователя до владельца чата.
     */
    public void promoteToOwner() {
        this.role = AvailableRoles.OWNER;
        sendMsg("! Вы стали владельцем чата");
    }

    /**
     * Повышает пользователя до администратора.
     */
    public void promoteToAdmin() {
        this.role = AvailableRoles.ADMIN;
        sendMsg("! Вы получили права администратора");
    }

    /**
     * Понижает администратора до обычного пользователя.
     */
    public void demoteToUser() {
        this.role = AvailableRoles.USER;
        sendMsg("! Ваши права администратора сняты");
    }

    /**
     * Обрабатывает команду смены никнейма.
     *
     * @param message сообщение с командой
     */
    private void handleNick(String message) {
        String[] parts = message.split("\\s+", 2);
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            String newNick = parts[1];

            if (newNick.equals(username)) {
                sendMsg("! Вы уже используете этот ник");
            } else if (server.findClientByUsername(newNick) != null) {
                sendMsg("! Ник " + newNick + " уже занят");
            } else if (newNick.trim().isEmpty()) {
                sendMsg("! Ник не может быть пустым");
            } else if (newNick.contains(" ")) {
                sendMsg("! Ник не может содержать пробелы");
            } else if (newNick.startsWith("/")) {
                sendMsg("! Ник не может начинаться с /");
            } else {
                String oldNick = username;
                setUsername(newNick);
                sendMsg("! Ваш ник изменён. Новый ник: " + getUsername());
                server.broadcastMessage(role.getPrefix() + oldNick + " теперь известен как " + role.getPrefix() + getUsername());
            }
        } else {
            sendUsageMessage(AvailableCommands.NICK);
        }
    }

    /**
     * Обрабатывает команду отправки личного сообщения.
     *
     * @param message сообщение с командой
     */
    private void handleWhisper(String message) {
        String[] parts = message.split("\\s+", 3);
        if (parts.length >= 3 && !parts[1].isEmpty()) {
            String recipientNick = parts[1];
            String messageText = parts[2];

            if (recipientNick.equals(username)) {
                sendMsg("! Нельзя отправить ЛС самому себе");
            } else {
                ClientHandler recipient = server.findClientByUsername(recipientNick);
                if (recipient != null) {
                    recipient.sendMsg("> ЛС от " + username + ": " + messageText);
                    sendMsg("* Сообщение отправлено пользователю " + recipientNick);
                } else {
                    sendMsg("! Пользователь " + recipientNick + " не найден");
                }
            }
        } else {
            sendUsageMessage(AvailableCommands.WHISPER);
        }
    }

    /**
     * Обрабатывает команду выдачи прав администратора.
     *
     * @param message сообщение с командой
     */
    private void handleOp(String message) {
        if (role != AvailableRoles.OWNER && role != AvailableRoles.ADMIN) {
            sendMsg("! Команда доступна только администраторам");
            return;
        }

        String[] parts = message.split("\\s+", 2);
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            String targetNick = parts[1];

            if (targetNick.equals(username)) {
                sendMsg("! Вы уже администратор");
            } else {
                ClientHandler target = server.findClientByUsername(targetNick);
                if (target == null) {
                    sendMsg("! Пользователь с ником " + targetNick + " не найден");
                } else if (target.getRole() == AvailableRoles.ADMIN || target.getRole() == AvailableRoles.OWNER) {
                    sendMsg("! " + targetNick + " уже имеет права администратора");
                } else {
                    target.promoteToAdmin();
                    server.broadcastMessage("> " + target.getRole().getPrefix() + targetNick + " теперь " + target.getRole().getDescription());
                }
            }
        } else {
            sendUsageMessage(AvailableCommands.OP);
        }
    }

    /**
     * Обрабатывает команду снятия прав администратора.
     *
     * @param message сообщение с командой
     */
    private void handleDeop(String message) {
        if (role != AvailableRoles.OWNER) {
            sendMsg("! Команда доступна только владельцу");
            return;
        }

        String[] parts = message.split("\\s+", 2);
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            String targetNick = parts[1];

            if (targetNick.equals(username)) {
                sendMsg("! Нельзя снять права у самого себя");
            } else {
                ClientHandler target = server.findClientByUsername(targetNick);
                if (target == null) {
                    sendMsg("! Пользователь с ником " + targetNick + " не найден");
                } else if (target.getRole() == AvailableRoles.OWNER) {
                    sendMsg("! Нельзя снять права у владельца");
                } else if (target.getRole() == AvailableRoles.USER) {
                    sendMsg("! " + targetNick + " не является администратором");
                } else {
                    target.demoteToUser();
                    server.broadcastMessage("> " + targetNick + " больше не администратор");
                }
            }
        } else {
            sendUsageMessage(AvailableCommands.DEOP);
        }
    }

    /**
     * Обрабатывает команду отключения пользователя.
     *
     * @param message сообщение с командой
     */
    private void handleKick(String message) {
        if (role != AvailableRoles.OWNER && role != AvailableRoles.ADMIN) {
            sendMsg("! Вам недоступна данная команда");
            return;
        }

        String[] parts = message.split("\\s+", 2);
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            String targetNick = parts[1];

            if (targetNick.equals(username)) {
                sendMsg("! Нельзя выгнать самого себя");
            } else {
                ClientHandler target = server.findClientByUsername(targetNick);
                if (target == null) {
                    sendMsg("! Пользователь с ником " + targetNick + " не найден");
                } else if (target.getRole() == AvailableRoles.OWNER) {
                    sendMsg("! Нельзя выгнать владельца");
                } else {
                    target.sendMsg("! Вы были отключены администратором " + role.getPrefix() + username);
                    target.disconnect();
                    server.broadcastMessage("> " + role.getPrefix() + username + " отключил " + target.getRole().getPrefix() + targetNick);
                }
            }
        } else {
            sendUsageMessage(AvailableCommands.KICK);
        }
    }

    /**
     * Отправляет готовый шаблон по использованию команды.
     *
     * @param cmd команда
     */
    private void sendUsageMessage(AvailableCommands cmd) {
        sendMsg("* " + cmd.getDescription() + ": " + cmd.getUsage());
    }

    /**
     * Обрабатываем команду отправки сообщения от имени сервера.
     *
     * @param message сообщение с командой
     */
    private void handleBroadcast(String message) {
        if (role != AvailableRoles.OWNER && role != AvailableRoles.ADMIN) {
            sendMsg("! Команда доступна только администраторам");
            return;
        }

        String[] parts = message.split("\\s+", 2);
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            server.broadcastMessage("[СЕРВЕР] " + parts[1]);
        } else {
            sendUsageMessage(AvailableCommands.ALL);
        }
    }

    /**
     * Отключает клиента и закрывает ресурсы.
     */
    private void disconnect() {
        server.unsubscribe(this);
        logger.log(System.Logger.Level.INFO, "Client disconnected port: " + socket.getPort());
        closeResource(in, "DataInputStream");
        closeResource(out, "DataOutputStream");
        closeResource(socket, "Socket");
    }

    /**
     * Закрывает ресурс с обработкой исключения.
     *
     * @param resource ресурс для закрытия
     * @param name     имя ресурса для логирования
     */
    private void closeResource(AutoCloseable resource, String name) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.log(System.Logger.Level.WARNING, "Ошибка закрытия " + name, e);
            }
        }
    }
}
