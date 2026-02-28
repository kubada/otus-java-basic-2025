package ru.otus.java.basic.homeworks.lesson_21;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Приложение для демонстрации работы с массивами и замера производительности.
 */
public class Application {
    static void main() {
        double[] x = measureCreateArray();
        measureCalcArray(x);
    }

    /**
     * Создаёт массив из 100 миллионов элементов типа double.
     *
     * @return массив из 100_000_000 элементов
     */
    public static double[] createArray() {
        return new double[100000000];
    }

    /**
     * Создаёт массив и замеряет время выполнения.
     *
     * @return созданный массив
     */
    public static double[] measureCreateArray() {
        LocalDateTime start = LocalDateTime.now();
        double[] array = createArray();
        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        System.out.printf("* Время выполнения createArray: %d мс (%d сек) %n", duration.toMillis(), duration.toSeconds());

        return array;
    }

    /**
     * Заполняет массив значениями по формуле: 1.14 * cos(i) * sin(i * 0.2) * cos(i / 1.2).
     *
     * @param array массив для заполнения
     */
    public static void calcArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
    }

    /**
     * Заполняет массив значениями и замеряет время выполнения.
     *
     * @param array массив для заполнения
     */
    public static void measureCalcArray(double[] array) {
        LocalDateTime start = LocalDateTime.now();

        calcArray(array);

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        System.out.printf("* Время выполнения calcArray: %d мс (%d сек) %n", duration.toMillis(), duration.toSeconds());
    }
}
