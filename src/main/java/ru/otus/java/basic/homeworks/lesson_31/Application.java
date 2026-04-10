package ru.otus.java.basic.homeworks.lesson_31;

import java.util.Arrays;
import java.util.logging.Logger;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    static void main() {
        logger.info("Приложение запущено");

        int[] array1 = {1, 2, 1, 2, 2};
        int[] result1 = getElementsAfterLastOne(array1);
        logger.info("Ввод: " + Arrays.toString(array1) + " => Результат: " + Arrays.toString(result1));

        try {
            int[] array2 = {2, 2, 2, 2};
            int[] result2 = getElementsAfterLastOne(array2);
            logger.info("Ввод: " + Arrays.toString(array2) + " => Результат: " + Arrays.toString(result2));
        } catch (RuntimeException e) {
            logger.severe("Ввод: [2, 2, 2, 2] => RuntimeException: " + e.getMessage());
        }

        int[] array3 = {1, 2, 2, 1};
        boolean result3 = containsOnlyOneAndTwo(array3);
        logger.info("Ввод: " + Arrays.toString(array3) + " => Результат: " + result3);
    }

    /**
     * Возвращает элементы массива, идущие после последней единицы.
     *
     * @param array входной массив целых чисел
     * @return новый массив с элементами после последней единицы
     * @throws RuntimeException если массив не содержит единиц
     */
    public static int[] getElementsAfterLastOne(int[] array) {
        logger.info("getElementsAfterLastOne вызывается с массивом: " + Arrays.toString(array));

        int lastOneIndex = -1;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 1) {
                lastOneIndex = i;
                break;
            }
        }

        if (lastOneIndex == -1) {
            logger.warning("В массиве не найдена единица.");
            throw new RuntimeException("Массив не содержит '1'");
        }

        int[] result = Arrays.copyOfRange(array, lastOneIndex + 1, array.length);

        logger.info("Возвращаемый массив: " + Arrays.toString(result));
        return result;
    }

    /**
     * Проверяет, что массив состоит только из чисел 1 и 2.
     * Должна быть хотя бы одна единица И хотя бы одна двойка.
     *
     * @param array входной массив целых чисел
     * @return true если массив содержит только 1 и 2 (и того и другого хотя бы по одному)
     */
    public static boolean containsOnlyOneAndTwo(int[] array) {
        logger.info("containsOnlyOneAndTwo вызывается с массивом: " + Arrays.toString(array));

        boolean hasOne = false;
        boolean hasTwo = false;

        for (int num : array) {
            if (num != 1 && num != 2) {
                logger.info("Найден неверный номер: " + num);
                return false;
            }

            if (num == 1) {
                hasOne = true;
            }
            if (num == 2) {
                hasTwo = true;
            }
        }

        boolean result = hasOne && hasTwo;
        logger.info("Результат: " + result + " (hasOne=" + hasOne + ", hasTwo=" + hasTwo + ")");
        return result;
    }
}
