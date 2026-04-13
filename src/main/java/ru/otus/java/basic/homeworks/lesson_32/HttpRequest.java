package ru.otus.java.basic.homeworks.lesson_32;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Представляет HTTP-запрос и предоставляет методы для его парсинга и доступа к данным.
 * Класс поддерживает различные HTTP-методы (GET, POST, PUT, DELETE) и извлекает
 * параметры запроса, URI и тело запроса.
 */
public class HttpRequest {
    private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());
    private final String rawRequest;
    private HttpMethod method;
    private String uri;
    private Map<String, String> params;
    private String body;

    /**
     * Возвращает тело HTTP-запроса.
     *
     * @return тело запроса в виде строки
     */
    public String getBody() {
        return body;
    }

    /**
     * Возвращает ключ маршрутизации, состоящий из HTTP-метода и URI.
     *
     * @return строка в формате "METHOD URI" (например, "GET /items")
     */
    public String getRoutingKey() {
        return method + " " + uri;
    }

    /**
     * Возвращает значение параметра запроса по ключу.
     *
     * @param key ключ параметра
     * @return значение параметра или null, если параметр отсутствует
     */
    public String getParameter(String key) {
        return params.get(key);
    }

    /**
     * Создает новый HTTP-запрос из сырой строки запроса.
     * Автоматически парсит запрос при создании.
     *
     * @param rawRequest сырая строка HTTP-запроса
     */
    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    /**
     * Выводит информацию о запросе в лог.
     *
     * @param showRawRequest если true, выводит также сырую строку запроса
     */
    public void info(boolean showRawRequest) {
        if (showRawRequest) {
            logger.info("Raw request: " + rawRequest);
        }
        logger.info("Method: " + method);
        logger.info("URI: " + uri);
        logger.info("Parameters: " + params);
        logger.info("Body: " + body);
    }

    /**
     * Парсит сырую строку HTTP-запроса и извлекает из нее:
     * - HTTP-метод (GET, POST, PUT, DELETE)
     * - URI
     * - параметры запроса (если есть)
     * - тело запроса (для POST и PUT запросов)
     */
    private void parse() {
        params = new HashMap<>();
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        uri = rawRequest.substring(startIndex + 1, endIndex);
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                params.put(keyValue[0], keyValue[1]);
            }
        }
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
    }
}
