package ru.otus.java.basic.homeworks.lesson_3;

import java.util.Scanner;
import java.util.Random;

public class App {
    static void main() {
        greetings();
        checkSign(1, 3, -2);
        selectColor();
        compareNumbers();
        addOrSubtractAndPrint(1, 2, true);
        asterisk();
    }

    /**
     * При вызове должен отпечатать в столбец 4 слова: Hello, World, from, Java;
     */
    public static void greetings() {
        System.out.println("# greetings");

        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");

        // System.out.println("Hello\nWorld\nfrom\nJava");
    }

    /**
     * Принимает 3 int аргумента (a, b и c), считает их сумму, и если она больше или равна 0,
     * то вывести в консоль сообщение “Сумма положительная”, в противном случае - “Сумма отрицательная”;
     *
     * @param a int
     * @param b int
     * @param c int
     *
     */
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

    /**
     * В теле объявлена int переменная data с любым значением.
     * Если data меньше 10 включительно, то в консоль должно быть выведено сообщение “Красный”,
     * если от 10 до 20 включительно, то “Желтый”, если больше 20 - “Зеленый”;
     */
    public static void selectColor() {
        System.out.println("\n# selectColor");

        int data = 1;

        if (data <= 10) {
            System.out.println("Красный");
        }

        if (data > 10 && data <= 20) {
            System.out.println("Жёлтый");
        }

        if (data > 20) {
            System.out.println("Зелёный");
        }
    }

    /**
     * В теле объявлено две int переменные (a, b) с любыми значениями.
     * Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”, в противном случае “a < b”;
     */
    public static void compareNumbers() {
        System.out.println("\n# compareNumbers");

        int a = 1;
        int b = 2;

        if (a >= b) {
            System.out.println("a >= b");
        } else System.out.println("a < b");
    }

    /**
     * Если increment = true, то (initValue + delta) и вывести результат в консоль, иначе (initValue - delta);
     *
     * @param initValue int
     * @param delta     int
     * @param increment boolean
     */
    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        System.out.println("\n# addOrSubtractAndPrint");

        if (increment) {
            System.out.println("increment = true: " + (initValue + delta));
        } else {
            System.out.println("increment = false: " + (initValue - delta));
        }
    }

    /**
     * Запрашиваем ввод числа от 1 до 5, после ввода выполняем метод,
     * соответствующий указанному номеру со случайным значением аргументов;
     */
    public static void asterisk() {
        System.out.println("\n# asterisk");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введите число: ");

        int in = scanner.nextInt();

        System.out.printf("Введённое число: %d \n", in);

        if (in == 1) {
            greetings();
        } else if (in == 2) {
            checkSign(random.nextInt(), random.nextInt(), random.nextInt());
        } else if (in == 3) {
            selectColor();
        } else if (in == 4) {
            compareNumbers();
        } else if (in == 5) {
            addOrSubtractAndPrint(random.nextInt(), random.nextInt(), random.nextBoolean());
        } else {
            System.out.println("Неверный номер! Введите от 1 до 5.");
        }

        scanner.close();
    }

}
