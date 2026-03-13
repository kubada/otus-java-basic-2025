import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Клиент для подключения к чат-серверу.
 */
public class Client {
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final Scanner sc;

    private static final System.Logger logger = System.getLogger(Client.class.getName());

    /**
     * Создаёт клиента и подключается к серверу.
     *
     * @param host адрес сервера
     * @param port порт сервера
     */
    public Client(String host, int port) {

        try {
            sc = new Scanner(System.in);
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/")) {
                            if (message.equals("/exitok")) {
                                break;
                            }
                            if (message.startsWith("/authok ")) {
                                System.out.println("Успешный вход; Имя пользователя: " + message.split(" ")[1]);
                                continue;
                            }
                            if (message.startsWith("/regok ")) {
                                System.out.println("Успешная регистрация; Имя пользователя: " + message.split(" ")[1]);
                                continue;
                            }
                        }
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    if (e instanceof java.io.EOFException || e instanceof
                            java.net.SocketException) {
                        System.out.println("! Соединение с сервером разорвано");
                    } else {
                        logger.log(System.Logger.Level.ERROR, "Ошибка чтения сообщения", e);
                    }
                } finally {
                    disconnect();
                }
            }).start();

            while (true) {
                String message = sc.nextLine();
                out.writeUTF(message);
                if (message.equals("/exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Отключает клиента от сервера и закрывает ресурсы.
     */
    private void disconnect() {
        closeResource(in, "DataInputStream");
        closeResource(out, "DataOutputStream");
        closeResource(socket, "Socket");
        if (sc != null) {
            sc.close();
        }
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
