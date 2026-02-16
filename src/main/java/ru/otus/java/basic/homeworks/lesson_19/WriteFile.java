package ru.otus.java.basic.homeworks.lesson_19;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Класс для записи данных в файл.
 */
public class WriteFile {
    /**
     * Добавляет строку в конец файла.
     *
     * @param file    файл для записи
     * @param content строка для добавления в файл
     */
    public static boolean write(File file, String content) {
        try (FileOutputStream out = new FileOutputStream(file, true)) {
            byte[] buffer = (System.lineSeparator() + content).getBytes(StandardCharsets.UTF_8);
            out.write(buffer);
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
            return false;
        }
    }
}
