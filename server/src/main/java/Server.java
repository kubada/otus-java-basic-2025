import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Сервер для обработки подключений чата.
 */
public class Server {
    private final int port;
    private final List<ClientHandler> clients;

    private static final System.Logger logger = System.getLogger(Server.class.getName());

    /**
     * Создаёт сервер на указанном порту.
     *
     * @param port порт сервера
     */
    public Server(int port) {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
    }

    /**
     * Запускает сервер и начинает принимать подключения клиентов.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.log(System.Logger.Level.INFO, "Server started. port", port);

            while (true) {
                Socket socket = serverSocket.accept();
                subscribe(new ClientHandler(this, socket));
            }

        } catch (IOException e) {
            logger.log(System.Logger.Level.ERROR, "Ошибка запуска сервера", e);
        }
    }

    /**
     * Подписывает клиента на получение сообщений.
     *
     * @param clientHandler обработчик клиента
     */
    public void subscribe(ClientHandler clientHandler) {
        broadcastMessage("Подключился пользователь " + clientHandler.getUsername());
        clients.add(clientHandler);
    }

    /**
     * Отписывает клиента от получения сообщений.
     *
     * @param clientHandler обработчик клиента
     */
    public void unsubscribe(ClientHandler clientHandler) {
        broadcastMessage("Пользователь " + clientHandler.getUsername() + " покинул чат");
        clients.remove(clientHandler);
    }

    /**
     * Отправляет сообщение всем подключённым клиентам.
     *
     * @param message сообщение для отправки
     */
    public void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }
}
