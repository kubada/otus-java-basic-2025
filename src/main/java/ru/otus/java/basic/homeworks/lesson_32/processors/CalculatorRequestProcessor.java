package ru.otus.java.basic.homeworks.lesson_32.processors;

import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Обработчик HTTP-запросов для выполнения операции сложения двух чисел.
 * Принимает два параметра запроса 'a' и 'b', складывает их и возвращает
 * результат в виде HTML-страницы.
 */
public class CalculatorRequestProcessor implements RequestProcessor {
    /**
     * Обрабатывает HTTP-запрос калькулятора.
     * Извлекает параметры 'a' и 'b' из запроса, выполняет сложение
     * и возвращает результат в HTML-формате.
     *
     * @param request HTTP-запрос с параметрами 'a' и 'b'
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        String result = a + " + " + b + " = " + (a + b);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>" + result + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
