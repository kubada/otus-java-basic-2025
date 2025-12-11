package ru.otus.java.basic.homeworks.practice_1;

import java.util.Random;
import java.util.Scanner;

public class App {
    static void main() {
        System.out.print("Игра \"Угадай число\"\nЗа 3 попытки попробуйте угадать задуманное число от 1 до 10\n");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;

        do {
            int player_choice;

            // random.nextInt(10) + 1: возвращается случайное число от 0 до 9
            // +1 сдвигает диапазон на 1, получается от 1 до 10.
            int intended_number = random.nextInt(10) + 1;

            System.out.print("Задуманное число (представьте, что Вы его не видите ^^): " + intended_number + "\n");

            int attempt = 1;

            while (true) {
                System.out.print("\nПопытка №" + attempt + ": ");

                // Проверяем, что игрок ввёл число
                if (scanner.hasNextInt()) {
                    player_choice = scanner.nextInt();

                    // Сначала проверяем, угадал ли игрок
                    if (player_choice == intended_number) {
                        System.out.println("Угадали!");
                        break;
                    }

                    // Если не угадал, проверяем количество попыток
                    if (attempt < 3) {
                        if (player_choice > intended_number) {
                            System.out.print("Много!");
                        } else {
                            System.out.print("Мало!");
                        }
                        attempt++;
                    } else {
                        System.out.println("Не угадали за 3 попытки :(. Загаданное число: " + intended_number);
                        break;
                    }
                } else {
                    System.out.println("Ввод не является числом. Попробуйте ещё раз.");
                    scanner.next(); // Очищаем некорректный ввод
                }
            }

            // Спрашиваем, хочет ли игрок продолжить
            System.out.print("\nХотите сыграть ещё раз? (да/нет): ");
            scanner.nextLine(); // Очищаем буфер после nextInt()
            String answer = scanner.nextLine().trim().toLowerCase();
            playAgain = answer.equals("да") || answer.equals("yes") || answer.equals("д");

        } while (playAgain);

        System.out.println("\nСпасибо за игру!");
        scanner.close();
    }
}
