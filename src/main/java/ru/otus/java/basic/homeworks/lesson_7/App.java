package ru.otus.java.basic.homeworks.lesson_7;

import java.util.Arrays;

public class App {
    static void main() {
        System.out.println("Сумма всех элементов массива, которые больше 0: " + sumOfPositiveElements(new int[][]{{0, -8, 15}, {16, 23, -42}}));
        printSquare(4);
        zeroingDiagonal(new int[][]{{4, 8, 15}, {16, 23, 42}, {99, 100, 101}});  // квадратный 3x3
        zeroingDiagonal(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}});  // не квадратный 2x4
        findMax(new int[][]{{4, 8, 15}, {16, 23, 42}});
        System.out.println("Сумма всех элементов массива из второй строки (если существует): " + sumSecondRow());
    }

    /**
     * Принимает в качестве аргумента целочисленный двумерный массив, считает и возвращает сумму всех элементов массива, которые больше 0;
     */
    public static int sumOfPositiveElements(int[][] array) {
        System.out.println("\n# sumOfPositiveElements");

        System.out.println("array: " + Arrays.deepToString(array));

        int sum = 0;

        for (int[] i : array) {
            for (int j : i) {
                if (j > 0) {
                    sum += j;
                }
            }
        }

        return sum;
    }

    /**
     * Принимает в качестве аргумента int size и выводит в консоль квадрат из символов "*" со сторонами соответствующей длины;
     */
    public static void printSquare(int size) {
        System.out.println("\n# printSquare");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    /**
     * Принимает в качестве аргумента двумерный целочисленный массив, и зануляющий его диагональные элементы (любую из диагоналей, или обе);
     */
    public static void zeroingDiagonal(int[][] array) {
        System.out.println("\n# zeroingDiagonal");

        int rows = array.length;        // количество строк
        int cols = array[0].length;     // количество столбцов

        System.out.printf("array:" + Arrays.deepToString(array) + ". размер массива: %d x %d\n", rows, cols);

        // минимальный размер для диагонали
        int size = Math.min(rows, cols);

        if (rows == cols) {
            // "квадратный" массив - рандомно выбираем какую диагональ занулить
            int choice = (int) (Math.random() * 3);  // 0, 1 или 2

            if (choice == 0) {
                System.out.println("'Квадратный' массив - обнуляем главную диагональ");
                for (int i = 0; i < size; i++) {
                    array[i][i] = 0;
                }
            } else if (choice == 1) {
                System.out.println("'Квадратный' массив - обнуляем побочную диагональ");
                for (int i = 0; i < size; i++) {
                    array[i][size - 1 - i] = 0;
                }
            } else {
                System.out.println("'Квадратный' массив - обнуляем обе диагонали");
                for (int i = 0; i < size; i++) {
                    array[i][i] = 0;                    // главная
                    array[i][size - 1 - i] = 0;         // побочная
                }
            }
        } else {
            // массив не "квадратный" - обнуляем сколько получится (главную диагональ)
            System.out.println("Не 'квадратный' массив - обнуляем главную диагональ (сколько получится)");
            for (int i = 0; i < size; i++) {
                array[i][i] = 0;
            }
        }

        System.out.println("array после: " + Arrays.deepToString(array));
    }

    /**
     * Находит и возвращает максимальный элемент массива;
     */
    public static void findMax(int[][] array) {
        System.out.println("\n# findMax");

        System.out.println("array: " + Arrays.deepToString(array));

        int max = array[0][0];  // первый элемент двумерного массива

        for (int[] i : array) {
            for (int j : i) {
                max = Math.max(max, j);
            }
        }

        System.out.println("Максимальный элемент двумерного массива: " + max);

    }

    /**
     * Считает сумму элементов второй строки двумерного массива, если второй строки не существует, то в качестве результата возвращает -1
     *
     * @return сумма всех элементов второй строки, если второй строки нет "-1"
     */
    public static int sumSecondRow() {
        System.out.println("\n# sumSecondRow");

        int[][] array = new int[][]{{4, 8, 15}, {16, 23, 42}};

        System.out.println("array: " + Arrays.deepToString(array));

        int sum = 0;

        if (array[1].length >= 2) {
            for (int i : array[1]) {
                sum += i;
            }
        } else return -1;

        return sum;
    }
}
