package ru.otus.java.basic.homeworks.lesson_5;

import java.util.Arrays;
import java.util.Random;

public class App {
    static void main() {
        methodOne(3, "ru.otus.java.basic.lesson_3");

        methodTwo(new int[]{4, 8, 15, 16, 23, 42});

        int[] arrayMethodThree = {4, 8, 15, 16, 23, 42};
        methodThree(0, arrayMethodThree);

        int[] arrayMethodFour = {4, 8, 15, 16, 23, 42};
        methodFour(1, arrayMethodFour);

        methodFive(new int[]{4, 8, 15, 16, 23, 42});

        asterisk();

        int[] arrayOneAsteriskOne = new Random().ints(3, 0, 11).toArray();
        int[] arrayTwoAsteriskOne = new Random().ints(2, 0, 11).toArray();
        int[] arrayThreeAsteriskOne = new Random().ints(5, 0, 11).toArray();

        asteriskOne(arrayOneAsteriskOne, arrayTwoAsteriskOne, arrayThreeAsteriskOne);

        asteriskFour(new int[]{4, 8, 15, 16, 23, 42});
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

        System.out.println("Сумма большей половины массива: " + Math.max(sumLeft, sumRight));

    }

    /**
     * Сверить два массива, и посчитать сколько элементов в них имеют одно значение и стоят на одном и том же месте.
     * Пример: ('1' 2 3 '4' 5 '6') ('1' 4 4 '4' 0 '6') => 3 элемента
     */
    public static void asterisk() {
        System.out.println("\n# asterisk");

        int[] arrayOne = {1, 2, 3, 4, 5, 6};
        int[] arrayTwo = {1, 4, 4, 4, 0, 6};

        int countElemnts = 0;

        for (int iOne = 0; iOne < arrayOne.length; iOne++) {
            System.out.printf("Индекс %d - arrayOne: %d, arrayTwo: %d\n", iOne, arrayOne[iOne], arrayTwo[iOne]);

            if (arrayOne[iOne] == arrayTwo[iOne]) {
                countElemnts++;
            }
        }
        System.out.printf("Элементов с одинаковым значением в двух массивах: %d\n", countElemnts);
    }

    /**
     * Реализуйте метод, принимающий на вход набор целочисленных массивов, и получающий новый массив равный сумме входящих;
     * Пример: { 1, 2, 3 } + { 2, 2 } + { 1, 1, 1, 1, 1} = { 4, 5, 4, 1, 1 }
     */
    public static void asteriskOne(int[]... arrays) {
        System.out.println("\n# asteriskOne");

        int maxLength = 0;

        for (int[] array : arrays) {
            System.out.println("array: " + Arrays.toString(array));

            if (array.length > maxLength) {
                maxLength = array.length;
            }
        }

        int[] result = new int[maxLength];

        for (int[] array : arrays) {
            for (int i = 0; i < maxLength; i++) {
                if (i < array.length) {
                    result[i] += array[i];
                }
            }
        }

        System.out.println("Результирующий массив (сумма входящих): " + Arrays.toString(result));
    }

    /**
     * Реализуйте метод, проверяющий, что есть “точка” в массиве, в которой сумма левой и правой части равны.
     * “Точка находится между элементами”
     * Пример: { 1, 1, 1, 1, 1, | 5 }, { 5, | 3, 4, -2 }, { 7, 2, 2, 2 }, { 9, 4 }
     */
    public static void asteriskTwo() {

    }

    /**
     * Реализуйте метод, проверяющий, что все элементы массива идут в порядке убывания или возрастания (по выбору пользователя)
     */
    public static void asteriskThree() {

    }

    /**
     * Реализуйте метод, “переворачивающий” входящий массив
     * Пример: { 1 2 3 4 } => { 4 3 2 1 }
     */
    public static void asteriskFour(int[] array) {
        System.out.println("\n# asteriskFour");
        System.out.println("array: " + Arrays.toString(array));

        // в условии не сказано, что нужно изменить исходный массив, поэтому просто копируем задом наперёд в новый массив
        int[] reversed = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }

        System.out.println("reversed после цикла: " + Arrays.toString(reversed));

        // если нельзя использовать новый массив, тогда через временную переменную и то достаточно поменять одно половину
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }

        System.out.println("array после цикла: " + Arrays.toString(reversed));
    }

}