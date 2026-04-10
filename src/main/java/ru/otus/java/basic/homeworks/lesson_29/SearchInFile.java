package ru.otus.java.basic.homeworks.lesson_29;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Класс для поиска подстроки в файле.
 */
public class SearchInFile {
    /**
     * Подсчитывает количество вхождений искомой строки в файле (с учётом регистра).
     *
     * @param fileName     путь к файлу
     * @param searchString искомая строка
     * @return количество найденных вхождений
     */
    public static int search(String fileName, String searchString) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                int index = 0;
                while ((index = line.indexOf(searchString, index)) != -1) {
                    count++;
                    index += searchString.length();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return -1;
        }

        return count;
    }
}