package ru.otus.java.basic.homeworks.lesson_32.processors;

import ru.otus.java.basic.homeworks.lesson_32.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Интерфейс для обработчиков HTTP-запросов.
 * Все классы-обработчики должны реализовывать этот интерфейс
 * для обработки конкретных типов HTTP-запросов.
 */
public interface RequestProcessor {
    /**
     * Выполняет обработку HTTP-запроса и записывает ответ в выходной поток.
     *
     * @param request HTTP-запрос для обработки
     * @param output поток вывода для записи HTTP-ответа
     * @throws IOException если возникает ошибка при записи в поток вывода
     */
    void execute(HttpRequest request, OutputStream output) throws IOException;
}