package ru.otus.java.basic.homeworks.lesson_32.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;
import ru.otus.java.basic.homeworks.lesson_32.app.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Обработчик HTTP-запросов для получения списка элементов (Items).
 * Возвращает предопределенный список элементов в формате JSON
 * с HTTP-статусом 200 OK.
 */
public class GetItemsRequestProcessor implements RequestProcessor {
    /**
     * Обрабатывает HTTP-запрос на получение списка элементов.
     * Возвращает JSON-массив с тремя предопределенными элементами.
     *
     * @param request HTTP-запрос
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        List<Item> items = new ArrayList<>(Arrays.asList(
                new Item(1L, "Bread", 50),
                new Item(2L, "Milk", 150),
                new Item(3L, "Cheese", 400)
        ));
        Gson gson = new Gson();
        String result = gson.toJson(items);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" + result;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
