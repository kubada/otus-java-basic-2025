package ru.otus.java.basic.homeworks.lesson_29;

import java.io.File;
import java.util.Scanner;

public class Application {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        File selectedFile = SelectFile.selectFile(scanner);

        String searchString;
        while (true) {
            System.out.print("Введите искомую последовательность символов: ");
            searchString = scanner.nextLine();

            if (searchString.isEmpty()) {
                System.out.println("Поиск пустой строки невозможен. Попробуйте снова.");
            } else {
                break;
            }
        }

        int count = SearchInFile.search(selectedFile.getPath(), searchString);

        if (count >= 0) {
            System.out.printf("Найдено вхождений: %d%n", count);
        } else {
            System.out.println("Не удалось выполнить поиск из-за ошибки.");
        }

        scanner.close();
    }
}