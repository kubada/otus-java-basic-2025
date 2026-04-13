package ru.otus.java.basic.homeworks.lesson_32.processors;

import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Обработчик HTTP-запросов по умолчанию для несуществующих маршрутов.
 * Возвращает HTML-страницу с HTTP-статусом 404 Not Found.
 */
public class DefaultNotFoundRequestProcessor implements RequestProcessor {
    /**
     * Обрабатывает HTTP-запрос для несуществующего маршрута.
     * Возвращает HTML-страницу с сообщением об ошибке 404.
     *
     * @param request HTTP-запрос
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String response = """
                HTTP/1.1 404 Not Found\r
                Content-Type: text/html\r
                \r
                <html><body><h1>404.. Page Not Found</h1></body></html>""";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
