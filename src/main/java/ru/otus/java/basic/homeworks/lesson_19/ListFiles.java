package ru.otus.java.basic.homeworks.lesson_19;

import java.io.File;

/**
 * Класс для работы со списком файлов в папке.
 */
public class ListFiles {
    /**
     * Выводит список файлов из папки "files" и возвращает массив файлов.
     *
     * @return массив файлов из папки "files"
     * @throws IllegalStateException если папка пуста или путь указан неверно
     */
    public File[] listFiles() {
        File folder = new File("files");
        File[] files = folder.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                System.out.printf("%d. %s%n", (i + 1), files[i].getName());
            }
        } else {
            throw new IllegalStateException("Папка пуста или путь указан неверно");
        }
        return files;
    }
}


