package ru.otus.java.basic.homeworks.lesson_32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Простой HTTP-сервер для обработки HTTP-запросов.
 * Принимает входящие соединения на указанном порту и обрабатывает
 * запросы через диспетчер.
 */
public class HttpServer {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
    private final int port;
    private final Dispatcher dispatcher;

    /**
     * Создает новый HTTP-сервер на указанном порту.
     *
     * @param port порт, на котором будет запущен сервер
     */
    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    /**
     * Запускает HTTP-сервер и начинает прослушивание входящих соединений.
     * Работает в бесконечном цикле, принимая множество соединений.
     * Каждый запрос обрабатывается в отдельном потоке для поддержки конкурентности.
     * Читает данные запроса, парсит их и передает диспетчеру для обработки.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        logger.info("Обработка запроса в потоке: " + Thread.currentThread().getName());
                        byte[] buffer = new byte[8192];
                        int n = socket.getInputStream().read(buffer);
                        if (n < 1) {
                            return;
                        }
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);
                        dispatcher.execute(request, socket.getOutputStream());
                        logger.info("Запрос обработан в потоке: " + Thread.currentThread().getName());
                    } catch (IOException e) {
                        logger.severe("Ошибка при обработке запроса: " + e.getMessage());
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            logger.warning("Ошибка при закрытии сокета: " + e.getMessage());
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            logger.severe("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
