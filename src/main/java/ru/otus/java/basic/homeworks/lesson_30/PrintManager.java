package ru.otus.java.basic.homeworks.lesson_30;

/**
 * Менеджер синхронизированной печати символов в консоль.
 * Обеспечивает строгую последовательность печати символов из разных потоков.
 */
public class PrintManager {
    /**
     * Счётчик очереди для определения, какой поток может печатать.
     * Значения: 0 = очередь первого символа, 1 = второго, 2 = третьего.
     */
    int counter = 0;

    /**
     * Печатает символ в консоль с соблюдением очерёдности.
     * Поток ожидает своей очереди через механизм wait/notify.
     *
     * @param letter       символ для печати
     * @param expectedTurn ожидаемое значение счётчика для данного потока (0, 1 или 2)
     * @throws InterruptedException если поток прерван во время ожидания
     */
    public synchronized void print(char letter, int expectedTurn) throws InterruptedException {
        while (counter != expectedTurn) {
            wait();
        }
        System.out.print(letter);
        counter = (counter + 1) % 3;
        notifyAll();
    }
}
