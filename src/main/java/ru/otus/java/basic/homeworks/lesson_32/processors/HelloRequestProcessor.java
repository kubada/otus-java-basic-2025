package ru.otus.java.basic.homeworks.lesson_32.processors;

import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Обработчик HTTP-запросов для приветственной страницы.
 * Возвращает простую HTML-страницу с текстом "Hello World!!!".
 */
public class HelloRequestProcessor implements RequestProcessor {
    /**
     * Обрабатывает HTTP-запрос приветствия.
     * Возвращает HTML-страницу с приветственным сообщением.
     *
     * @param request HTTP-запрос
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String response = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html\r
                \r
                <html><body><h1>Hello World!!!</h1></body></html>""";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
