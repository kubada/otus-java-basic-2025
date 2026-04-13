package ru.otus.java.basic.homeworks.lesson_32;

/**
 * Главный класс приложения для запуска HTTP-сервера.
 * Создает и запускает сервер на порту 8189.
 */
public class Application {
    /**
     * Точка входа в приложение.
     * Создает экземпляр HTTP-сервера на порту 8189 и запускает его.
     */
    static void main() {
        new HttpServer(8189).start();
    }
}