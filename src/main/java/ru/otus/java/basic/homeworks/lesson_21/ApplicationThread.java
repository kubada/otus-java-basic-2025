package ru.otus.java.basic.homeworks.lesson_21;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Приложение для демонстрации работы с массивами и замера производительности.
 */
public class ApplicationThread {
    static void main() throws InterruptedException {
        double[] array = measureCreateArray();
        measureThreadCalcArray(array);


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
    public static void calcThreadArray(double[] array) throws InterruptedException {
        int quarter = array.length / 4;

        Thread t1 = new Thread(new Task(array, 0, quarter));
        Thread t2 = new Thread(new Task(array, quarter, quarter * 2));
        Thread t3 = new Thread(new Task(array, quarter * 2, quarter * 3));
        Thread t4 = new Thread(new Task(array, quarter * 3, array.length));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    /**
     * Заполняет массив значениями и замеряет время выполнения.
     *
     * @param array массив для заполнения
     */
    public static void measureThreadCalcArray(double[] array) throws InterruptedException {
        LocalDateTime start = LocalDateTime.now();

        calcThreadArray(array);

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        System.out.printf("* Время выполнения calcArray: %d мс (%d сек) %n", duration.toMillis(), duration.toSeconds());
    }
}
