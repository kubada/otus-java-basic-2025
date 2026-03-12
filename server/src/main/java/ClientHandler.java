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
        logger.log(System.Logger.Level.INFO, "Client connected port: " + socket.getPort());

        username = "user" + socket.getPort();
        sendMsg("> Вы подключились под ником: " + username);
        sendMsg(server.getOnlineUsersInfo());
        sendMsg(AvailableCommands.getHelpMessage());

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        AvailableCommands cmd = AvailableCommands.findCommand(message);

                        if (cmd == null) {
                            sendMsg("Неизвестная команда. " + AvailableCommands.getHelpMessage());
                        } else if (cmd == AvailableCommands.HELP) {
                            sendMsg(AvailableCommands.getHelpMessage());
                        } else if (cmd == AvailableCommands.EXIT) {
                            sendMsg("/exitok");
                        } else if (cmd == AvailableCommands.NICK) {
                            handleNick(message);
                        } else if (cmd == AvailableCommands.WHISPER) {
                            handleWhisper(message);
                        }
                    } else {
                        server.broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                logger.log(System.Logger.Level.ERROR, "Ошибка чтения сообщения от клиента", e);
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
     * Устанавливает имя пользователя.
     *
     * @param username новое имя
     */
    public void setUsername(String username) {
        this.username = username;
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
                server.broadcastMessage(oldNick + " теперь известен как " + getUsername());
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

    private void sendUsageMessage(AvailableCommands cmd) {
        sendMsg("* " + cmd.getDescription() + ": " + cmd.getUsage());
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
