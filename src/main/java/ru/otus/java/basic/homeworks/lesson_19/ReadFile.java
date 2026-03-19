package ru.otus.java.basic.homeworks.lesson_19;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для чтения содержимого файла.
 */
public class ReadFile {
    /**
     * Читает и выводит содержимое файла в консоль.
     *
     * @param fileName имя файла для чтения
     */
    public static void read(String fileName) {
        System.out.println("\n--- Содержимое файла ---");
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(fileName))) {
            int n = in.read();
            while (n != -1) {
                System.out.print((char) n);
                n = in.read();
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        System.out.println("\n--- Конец файла ---\n");
    }
}
