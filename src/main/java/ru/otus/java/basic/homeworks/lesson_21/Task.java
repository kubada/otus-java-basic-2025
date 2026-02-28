package ru.otus.java.basic.homeworks.lesson_21;

/**
 * Задача для заполнения части массива в отдельном потоке.
 */
public class Task implements Runnable {

    private final double[] array;
    private final int startIndex;
    private final int endIndex;

    /**
     * Создаёт задачу для заполнения части массива.
     *
     * @param array      массив для заполнения
     * @param startIndex начальный индекс (включительно)
     * @param endIndex   конечный индекс (не включительно)
     */
    public Task(double[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * Выполняет заполнение части массива по формуле: 1.14 * cos(i) * sin(i * 0.2) * cos(i / 1.2).
     */
    @Override
    public void run() {
        for (int i = this.startIndex; i < this.endIndex; i++) {
            this.array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
    }
}
