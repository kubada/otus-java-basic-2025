package ru.otus.java.basic.homeworks.lesson_31;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Application
 */
class ApplicationTest {
    private static final Logger logger = Logger.getLogger(ApplicationTest.class.getName());

    @Test
    @DisplayName("Должен вернуть элементы после последней единицы")
    void shouldReturnElementsAfterLastOne() {
        logger.info("Тест: должен вернуть элементы после последней единицы");

        int[] input = {1, 2, 1, 2, 2};
        int[] expected = {2, 2};
        int[] actual = Application.getElementsAfterLastOne(input);

        assertArrayEquals(expected, actual);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть пустой массив если единица последняя")
    void shouldReturnEmptyArrayWhenOneIsLast() {
        logger.info("Тест: должен вернуть пустой массив если единица последняя");

        int[] input = {2, 2, 1};
        int[] expected = {};
        int[] actual = Application.getElementsAfterLastOne(input);

        assertArrayEquals(expected, actual);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен бросить RuntimeException если нет единиц")
    void shouldThrowExceptionWhenNoOnesFound() {
        logger.info("Тест: должен бросить RuntimeException если нет единиц");

        int[] input = {2, 2, 2, 2};

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> Application.getElementsAfterLastOne(input));

        assertEquals("Массив не содержит '1'", exception.getMessage());
        logger.info("Тест пройден - исключение брошено как ожидалось");
    }

    @Test
    @DisplayName("Должен работать с массивом из одной единицы")
    void shouldHandleSingleOneElement() {
        logger.info("Тест: должен работать с массивом из одной единицы");

        int[] input = {1};
        int[] expected = {};
        int[] actual = Application.getElementsAfterLastOne(input);

        assertArrayEquals(expected, actual);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть true для массива с 1 и 2")
    void shouldReturnTrueForValidArray() {
        logger.info("Тест: должен вернуть true для массива с 1 и 2");

        int[] input = {1, 2};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertTrue(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть true для массива с несколькими 1 и 2")
    void shouldReturnTrueForValidArrayWithMultipleElements() {
        logger.info("Тест: должен вернуть true для массива с несколькими 1 и 2");

        int[] input = {1, 2, 2, 1};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertTrue(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть false для массива только с единицами")
    void shouldReturnFalseForOnlyOnes() {
        logger.info("Тест: должен вернуть false для массива только с единицами");

        int[] input = {1, 1};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertFalse(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть false для массива только с двойками")
    void shouldReturnFalseForOnlyTwos() {
        logger.info("Тест: должен вернуть false для массива только с двойками");

        int[] input = {2, 2};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertFalse(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть false если есть другие числа")
    void shouldReturnFalseForInvalidNumbers() {
        logger.info("Тест: должен вернуть false если есть другие числа");

        int[] input = {1, 3};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertFalse(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть false для массива с числами вне диапазона")
    void shouldReturnFalseForOutOfRangeNumbers() {
        logger.info("Тест: должен вернуть false для массива с числами вне диапазона");

        int[] input = {1, 2, 0};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertFalse(result);
        logger.info("Тест пройден");
    }

    @Test
    @DisplayName("Должен вернуть false для пустого массива")
    void shouldReturnFalseForEmptyArray() {
        logger.info("Тест: должен вернуть false для пустого массива");

        int[] input = {};
        boolean result = Application.containsOnlyOneAndTwo(input);

        assertFalse(result);
        logger.info("Тест пройден");
    }
}
