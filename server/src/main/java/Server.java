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
    private final AuthenticatedProvider authenticatedProvider;

    private static final System.Logger logger = System.getLogger(Server.class.getName());

    /**
     * Создаёт сервер на указанном порту.
     *
     * @param port порт сервера
     */
    public Server(int port) {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
        this.authenticatedProvider = new PostgresqlAuthProvider(this);
    }

    /**
     * Запускает сервер и начинает принимать подключения клиентов.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.log(System.Logger.Level.INFO, "Сервер запущен, порт: " + port);
            authenticatedProvider.initialize();

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
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
        broadcastMessage("> " + clientHandler.getRole().getPrefix() + clientHandler.getUsername() + " присоединился к чату");
        clients.add(clientHandler);
    }

    /**
     * Отписывает клиента от получения сообщений.
     *
     * @param clientHandler обработчик клиента
     */
    public void unsubscribe(ClientHandler clientHandler) {
        if (clients.remove(clientHandler)) {
            broadcastMessage("> " + clientHandler.getRole().getPrefix() + clientHandler.getUsername() + " покинул чат");
        }
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

    /**
     * Возвращает количество подключенных участников.
     *
     * @return количество подключенных участников
     */
    public int getClientsCount() {
        return clients.size();
    }

    /**
     * Проверяет, занято ли имя пользователя среди подключенных клиентов.
     *
     * @param username имя пользователя для проверки
     * @return true если имя занято, false если свободно
     */
    public boolean isUsernameBusy(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Возвращает количество и имена подключенных клиентов с префиксами ролей.
     *
     * @return количество и имена подключенных клиентов
     */
    public String getOnlineUsersInfo() {
        if (clients.isEmpty()) {
            return "> Вы один в чате";
        }

        StringBuilder sb = new StringBuilder("> Подключенных участников ");
        sb.append(clients.size()).append(": ");
        for (int i = 0; i < clients.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(clients.get(i).getRole().getPrefix()).append(clients.get(i).getUsername());
        }
        return sb.toString();
    }

    /**
     * Ищет пользователя в списке подключенных клиентов.
     *
     * @param username имя пользователя для поиска
     * @return ClientHandler пользователя или null, если не найден
     */
    public ClientHandler findClientByUsername(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Возвращает провайдер аутентификации.
     *
     * @return провайдер аутентификации
     */
    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

}
