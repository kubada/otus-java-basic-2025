package ru.otus.java.basic.homeworks.lesson_19;

import java.io.File;
import java.util.Scanner;

/**
 * Класс для выбора файла пользователем.
 */
public class SelectFile {
    /**
     * Запрашивает у пользователя номер файла и возвращает выбранный файл.
     * После выбора выводит содержимое файла.
     *
     * @param files   массив файлов для выбора
     * @param scanner объект Scanner для чтения ввода пользователя
     * @return выбранный пользователем файл
     */
    public static File selectFile(File[] files, Scanner scanner) {
        int in;

        while (true) {
            System.out.print("\nВведите номер файла: ");

            if (scanner.hasNextInt()) {
                in = scanner.nextInt();

                if (in >= 1 && in <= files.length) {
                    File selectedFile = files[in - 1];
                    System.out.println("Выбран файл: " + selectedFile.getName());
                    ReadFile.read(selectedFile.toString());
                    scanner.nextLine();
                    return selectedFile;
                } else {
                    System.out.println("Неверное число. Допустимо: 1-" + files.length);
                }
            } else {
                System.out.println("Ввод не является числом. Попробуйте ещё раз.");
                scanner.next();
            }
        }
    }
}
