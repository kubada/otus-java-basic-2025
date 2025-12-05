package ru.otus.java.basic.homeworks.lesson_3;

import java.util.Scanner;

public class App {
    static void main() {
        greetings();
        checkSign(1, 3, -2);
        selectColor();
        compareNumbers();
        addOrSubtractAndPrint(1, 2, true);
        asterisk();
    }

    public static void greetings() {
        System.out.println("# greetings");
        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");

        // System.out.println("Hello\nWorld\nfrom\nJava");
    }

    public static void checkSign(int a, int b, int c) {
        System.out.println("\n# checkSign");

        int sum = a + b + c;

        if (sum >= 0) {
            System.out.println("Сумма положительная");
        }

        if (sum <= 0) {
            System.out.println("Сумма отрицательная");
        }
    }

    public static void selectColor() {
        System.out.println("\n# selectColor");

        int data = 1;

        if (data <= 10) {
            System.out.println("Красный");
        }

        if (data > 10 && data <= 20 ) {
            System.out.println("Жёлтый");
        }

        if (data > 20) {
            System.out.println("Зелёный");
        }
    }

    public static void compareNumbers() {
        System.out.println("\n# compareNumbers");

        int a = 1;
        int b = 2;

        if (a >= b) {
            System.out.println("a >= b");
        }

        else System.out.println("a < b");
    }

    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        System.out.println("\n# addOrSubtractAndPrint");

        if (increment == true) {
            System.out.println("increment = true: " + (initValue + delta));
        }

        else {
            System.out.println("increment = false: " + (initValue - delta));
        }
    }

    public static void asterisk() {
        System.out.println("\n# asterisk");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число: ");

        int in = scanner.nextInt();

        System.out.printf("Введённое число: %d \n", in);

        if (in == 1) {
            greetings();
        } else if (in == 2) {
            checkSign(2, 5, -10);
        } else if (in == 3) {
            selectColor();
        } else if (in == 4) {
            compareNumbers();
        } else if (in == 5) {
            addOrSubtractAndPrint(4, 7, false);
        } else {
            System.out.println("Неверный номер! Введите от 1 до 5.");
        }

        scanner.close();
    }

}
