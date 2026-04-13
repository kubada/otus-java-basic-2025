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
        String response;
        try {
            String paramA = request.getParameter("a");
            String paramB = request.getParameter("b");

            if (paramA == null || paramB == null) {
                response = "HTTP/1.1 400 Bad Request\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body><h1>Ошибка: параметры 'a' и 'b' обязательны</h1></body></html>";
            } else {
                int a = Integer.parseInt(paramA);
                int b = Integer.parseInt(paramB);
                String result = a + " + " + b + " = " + (a + b);
                response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body><h1>" + result + "</h1></body></html>";
            }
        } catch (NumberFormatException e) {
            response = "HTTP/1.1 400 Bad Request\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<html><body><h1>Ошибка: параметры 'a' и 'b' должны быть числами</h1></body></html>";
        }
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
