package ru.otus.java.basic.homeworks.lesson_32;

import ru.otus.java.basic.homeworks.lesson_32.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Диспетчер HTTP-запросов.
 * Маршрутизирует входящие HTTP-запросы к соответствующим обработчикам
 * на основе комбинации HTTP-метода и URI.
 */
public class Dispatcher {
    private final Map<String, RequestProcessor> processors;
    private final RequestProcessor defaultNotFoundRequestProcessor;

    /**
     * Создает новый диспетчер и регистрирует обработчики для различных маршрутов.
     * Инициализирует карту обработчиков для следующих маршрутов:
     * - GET /calculator - обработчик калькулятора
     * - GET /hello - обработчик приветствия
     * - GET /items - обработчик получения списка элементов
     * - POST /items - обработчик создания элемента
     */
    public Dispatcher() {
        this.defaultNotFoundRequestProcessor = new DefaultNotFoundRequestProcessor();
        this.processors = new HashMap<>();
        this.processors.put("GET /calculator", new CalculatorRequestProcessor());
        this.processors.put("GET /hello", new HelloRequestProcessor());
        this.processors.put("GET /items", new GetItemsRequestProcessor());
        this.processors.put("POST /items", new CreateItemRequestProcessor());
    }

    /**
     * Выполняет обработку HTTP-запроса, направляя его к соответствующему обработчику.
     * Если обработчик для данного маршрута не найден, используется обработчик 404.
     *
     * @param request HTTP-запрос для обработки
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!processors.containsKey(request.getRoutingKey())) {
            defaultNotFoundRequestProcessor.execute(request, output);
            return;
        }
        processors.get(request.getRoutingKey()).execute(request, output);
    }
}
