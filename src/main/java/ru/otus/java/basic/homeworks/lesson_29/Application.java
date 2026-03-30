package ru.otus.java.basic.homeworks.lesson_29;

import java.io.File;
import java.util.Scanner;

public class Application {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        File selectedFile = SelectFile.selectFile(scanner);

        System.out.print("Введите искомую последовательность символов: ");
        String searchString = scanner.nextLine();

        int count = SearchInFile.search(selectedFile.getPath(), searchString);

        if (count >= 0) {
            System.out.printf("Найдено вхождений: %d%n", count);
        } else {
            System.out.println("Не удалось выполнить поиск из-за ошибки.");
        }

        scanner.close();
    }
}