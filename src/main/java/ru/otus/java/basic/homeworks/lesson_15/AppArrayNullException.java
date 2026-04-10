package ru.otus.java.basic.homeworks.lesson_15;

public class AppArrayNullException extends IllegalArgumentException {
    public AppArrayNullException() {
        super("Ошибка: Пустой массив!");
    }
}
