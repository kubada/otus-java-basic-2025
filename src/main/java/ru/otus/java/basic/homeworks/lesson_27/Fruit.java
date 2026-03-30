package ru.otus.java.basic.homeworks.lesson_27;

/**
 * Базовый класс для всех фруктов.
 * Все фрукты имеют вес.
 */
public class Fruit {
    private final int weight;

    /**
     * Создаёт фрукт с указанным весом.
     *
     * @param weight вес фрукта
     */
    public Fruit(int weight) {
        this.weight = weight;
    }

    /**
     * Возвращает вес фрукта.
     *
     * @return вес фрукта
     */
    public int getWeight() {
        return weight;
    }
}
