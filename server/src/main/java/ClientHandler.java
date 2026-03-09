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
        logger.log(System.Logger.Level.INFO, "Client connected port", socket.getPort());

        username = "user" + socket.getPort();
        sendMsg("Вы подключились под ником: " + username);

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    //  /служебные сообщения
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMsg("/exitok");
                            break;
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
     * Отключает клиента и закрывает ресурсы.
     */
    private void disconnect() {
        server.unsubscribe(this);
        logger.log(System.Logger.Level.INFO, "Client disconnected port", socket.getPort());
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
