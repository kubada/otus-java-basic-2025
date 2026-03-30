package ru.otus.java.basic.homeworks.lesson_30;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Создаёт три задачи, каждая из которых печатает свой символ 5 раз.
 * Синхронизация через PrintManager, выводит строго "ABCABCABCABCABC".
 */
public class Application {
    static void main() {
        PrintManager manager = new PrintManager();

        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    manager.print('A', 0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    manager.print('B', 1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    manager.print('C', 2);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.shutdown();
    }
}
