package ru.otus.java.basic.homeworks.lesson_5;

import java.util.Arrays;

public class App {
    static void main() {
        methodOne(3, "ru.otus.java.basic.lesson_3");

        int[] arrayTwoMethodTwo = {4, 8, 15, 16, 23, 42};
        methodTwo(arrayTwoMethodTwo);

        int[] arrayMethodThree = {4, 8, 15, 16, 23, 42};
        methodThree(0, arrayMethodThree);

        int[] arrayMethodFour = {4, 8, 15, 16, 23, 42};
        methodFour(1, arrayMethodFour);

        int[] arrayMethodFive = {4, 8, 15, 16, 23, 42};
        methodFive(arrayMethodFive);

        int[] arrayAsteriskOne = {1, 2, 3, 4, 5, 6};
        int[] arrayAsteriskTwo = {1, 4, 4, 4, 0, 6};
        asterisk(arrayAsteriskOne, arrayAsteriskTwo);
    }

    /**
     * Принимает в качестве аргументов целое число и строку, выводит в консоль строку указанное количество раз.
     *
     * @param repeat int
     * @param text   String
     */
    public static void methodOne(int repeat, String text) {
        System.out.println("# methodOne");

        for (int i = 0; i < repeat; i++) {
            System.out.println(text);
        }
    }

    /**
     * Принимает в качестве аргумента целочисленный массив.
     * Суммирует все элементы, значение которых больше 5.
     * Выводит в консоль полученную сумму.
     */
    public static void methodTwo(int[] array) {
        System.out.println("\n# methodTwo");
        System.out.println("array: " + Arrays.toString(array));

        int sum = 0;

        for (int i : array) {
            if (i > 5) {
                // System.out.println("Элемент массива, значение которого больше 5:" + i);
                sum = sum + i;
            }
        }

        System.out.println("Сумма всех элементов массива, значения которых больше 5: " + sum);
    }

    /**
     * Принимает в качестве аргументов целое число и ссылку на целочисленный массив.
     * Заполняет каждую ячейку массива указанным числом.
     */
    public static void methodThree(int number, int[] array) {
        System.out.println("\n# methodThree");
        System.out.println("array: " + Arrays.toString(array));

        for (int i = 0; i < array.length; i++) {
            array[i] = number;
        }

        System.out.println("array после цикла: " + Arrays.toString(array));

        // Arrays.fill(array, number);
    }

    /**
     * Принимает в качестве аргументов целое число и ссылку на целочисленный массив.
     * Увеличивает каждый элемент массива на указанное число.
     */
    public static void methodFour(int number, int[] array) {
        System.out.println("\n# methodFour");
        System.out.println("array: " + Arrays.toString(array));

        for (int i = 0; i < array.length; i++) {
            array[i] += number;
        }

        System.out.println("array после цикла: " + Arrays.toString(array));


    }

    /**
     * Принимает в качестве аргумента целочисленный массив.
     * Выводит в консоль сумму элементов большей половины массива.
     */
    public static void methodFive(int[] array) {
        System.out.println("\n# methodFive");
        System.out.println("array: " + Arrays.toString(array));

        int sumLeft = 0;
        int sumRight = 0;

        for (int i = 0; i < array.length / 2; i++) {
            sumLeft = sumLeft + array[i];
        }

        for (int i = array.length / 2; i < array.length; i++) {
            sumRight = sumRight + array[i];
        }

        if (sumLeft > sumRight) {
            System.out.println("Сумма большей половины массива (левая): " + sumLeft);
        } else System.out.println("Сумма большей половины массива (правая): " + sumRight);

        // System.out.println(Math.max(sumLeft, sumRight));

    }

    /**
     * Сверить два массива, и посчитать сколько элементов в них имеют одно значение и стоят на одном и том же месте.
     * Пример: ('1' 2 3 '4' 5 '6') ('1' 4 4 '4' 0 '6') => 3 элемента
     */
    public static void asterisk(int[] arrayOne, int[] arrayTwo) {
        System.out.println("\n# asterisk");


    }

}
