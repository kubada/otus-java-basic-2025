package ru.otus.java.basic.homeworks.lesson_19;

import java.io.File;
import java.util.Scanner;

public class Application {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    static void main() {
        Scanner scanner = new Scanner(System.in);
        String continueChoice;

        do {
            File[] files = new ListFiles().listFiles();
            File selectedFile = SelectFile.selectFile(files, scanner);

            System.out.printf("Хотите добавить текст в файл? (%s/%s): ",
                    colored("Да", GREEN),
                    colored("Нет", RED));
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Д") || choice.equalsIgnoreCase("Да")) {
                System.out.print("Что добавить в файл? ");
                String content = scanner.nextLine();

                if (WriteFile.write(selectedFile, content)) {
                    System.out.println("В файл добавлено: " + content);
                }
            }

            System.out.printf("\nПродолжить работу? (%s/%s): ",
                    colored("Да", GREEN),
                    colored("Нет", RED));
            continueChoice = scanner.nextLine();
        } while (continueChoice.equalsIgnoreCase("Д") || continueChoice.equalsIgnoreCase("Да"));

    }

    private static String colored(String text, String color) {
        return color + text + RESET;
    }
}
