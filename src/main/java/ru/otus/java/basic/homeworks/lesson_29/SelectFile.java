package ru.otus.java.basic.homeworks.lesson_29;

import java.io.File;
import java.util.Scanner;

/**
 * Класс для выбора файла пользователем.
 */
public class SelectFile {
    /**
     * Запрашивает у пользователя имя файла и возвращает выбранный файл.
     * Поддерживает команду /l для просмотра доступных файлов.
     *
     * @param scanner объект Scanner для чтения ввода пользователя
     * @return выбранный пользователем файл
     */
    public static File selectFile(Scanner scanner) {
        while (true) {
            System.out.print("Введите имя файла (или /l для просмотра списка): ");
            String input = scanner.nextLine().trim();

            if (input.equals("/l")) {
                new ListFiles().listFiles();
                continue;
            }

            File file = new File("files", input);

            if (file.exists() && file.isFile()) {
                System.out.println("Файл найден: " + file.getName());
                return file;
            } else {
                System.out.println("Файл не найден. Попробуйте снова. (или /l для просмотра списка).");
            }
        }
    }
}