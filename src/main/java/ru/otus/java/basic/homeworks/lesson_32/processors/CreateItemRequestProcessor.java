package ru.otus.java.basic.homeworks.lesson_32.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;
import ru.otus.java.basic.homeworks.lesson_32.app.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Обработчик HTTP-запросов для создания нового элемента (Item).
 * Принимает POST-запрос с JSON-телом, десериализует его в объект Item
 * и возвращает HTTP-ответ со статусом 201 Created.
 */
public class CreateItemRequestProcessor implements RequestProcessor {
    private static final Logger logger = Logger.getLogger(CreateItemRequestProcessor.class.getName());
    /**
     * Обрабатывает HTTP-запрос на создание элемента.
     * Извлекает JSON из тела запроса, преобразует в объект Item и возвращает статус 201.
     *
     * @param request HTTP-запрос с JSON-телом
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(request.getBody(), Item.class);
        logger.info("Создан элемент: " + item);
        String response = """
                HTTP/1.1 201 Created\r
                Content-Type: application/json\r
                \r
                """;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
