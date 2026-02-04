package ru.otus.java.basic.homeworks.lesson_15;

import java.util.Random;

public class Application {
    /**
     * Основной метод.
     * Генерирует четыре сценария:
     * 1. Корректный массив 4x4 с рандомными числами - вывод суммы.
     * 2. Массив неверного количества строк - AppArraySizeException.
     * 3. Массив с неверным количеством элементов в строке - AppArraySizeException.
     * 4. Массив с текстом в случайной ячейке - AppArrayDataException.
     */
    static void main() {
        Random random = new Random();

        // Сценарий 1: корректный массив с рандомными числами
        System.out.println("Сценарий 1: Корректный массив 4x4");
        try {
            String[][] correctMatrix = new String[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    correctMatrix[i][j] = String.valueOf(random.nextInt(100));
                }
            }
            int sum = matrixMethod(correctMatrix);
            System.out.println("Сумма элементов массива: " + sum);
        } catch (AppArrayNullException | AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Сценарий 2: массив неверного размера
        System.out.println("\nСценарий 2: Массив неверного размера");
        try {
            String[][] wrongSizeMatrix = new String[3][4];
            matrixMethod(wrongSizeMatrix);
        } catch (AppArrayNullException | AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Сценарий 3: массив с неверным количеством элементов в строке
        System.out.println("\nСценарий 3: Неверное количество элементов в строке");
        try {
            // создаём массив с 4 строками, но одна строка содержит не 4 элемента
            String[][] wrongRowMatrix = {
                    {"1", "2", "3", "4"},
                    {"5", "6", "7"},          // ← только 3 элемента!
                    {"9", "10", "11", "12"},
                    {"13", "14", "15", "16"}
            };
            matrixMethod(wrongRowMatrix);
        } catch (AppArrayNullException | AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Сценарий 4: массив с текстом в случайной ячейке
        System.out.println("\nСценарий 4: Массив с текстом в ячейке");
        try {
            String[][] badDataMatrix = new String[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    badDataMatrix[i][j] = String.valueOf(random.nextInt(100));
                }
            }
            // подставляем текст в случайную ячейку
            int badRow = random.nextInt(4);
            int badCol = random.nextInt(4);
            badDataMatrix[badRow][badCol] = "error";
            matrixMethod(badDataMatrix);
        } catch (AppArrayNullException | AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }


    /**
     * Основной метод обработки массива.
     * Если передан массив другого размера бросает исключение AppArraySizeException.
     * Обходит все элементы массива, преобразовывает в int и суммирует.
     * Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит текст вместо числа)
     * бросает исключение AppArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
     *
     * @param array двумерный строковый массив размером 4х4
     */
    public static int matrixMethod(String[][] array) {
        if (array == null || array.length == 0) {
            throw new AppArrayNullException();
        }

        if (array.length != 4) {
            throw new AppArraySizeException("массив не является 4x4");
        }

        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null || array[i].length != 4) {
                throw new AppArraySizeException("строка № " + i + " не содержит 4 элемента");
            }

            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException("невозможно преобразовать в int ячейку [" + i + "][" + j + "]");
                }
            }
        }

        return sum;
    }
}
